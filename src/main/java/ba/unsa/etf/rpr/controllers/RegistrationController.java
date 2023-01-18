package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.dao.UserDaoSQLImpl;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import ba.unsa.etf.rpr.exceptions.UserException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class RegistrationController {
    public TextField usernameField;
    public PasswordField passwordField;
    public PasswordField confirmPasswordField;
    public Label errorMsgLabel;
    public Button registerBtn;

    public RegistrationController() {}

    public void initialize() {
        errorMsgLabel.setVisible(false);
        usernameField.textProperty().addListener((observableValue, o, n) -> {
            if(n.trim().isEmpty()) {
                usernameField.getStyleClass().removeAll("default");
                usernameField.getStyleClass().add("invalid");
            } else {
                usernameField.getStyleClass().removeAll("invalid");
                usernameField.getStyleClass().add("default");
            }
        });

        passwordField.textProperty().addListener((observableValue, o, n) -> {
            if(n.trim().isEmpty()) {
                passwordField.getStyleClass().removeAll("default");
                passwordField.getStyleClass().add("invalid");
            } else {
                passwordField.getStyleClass().removeAll("invalid");
                passwordField.getStyleClass().add("default");
            }
        });

        confirmPasswordField.textProperty().addListener((observableValue, o, n) -> {
            if(n.trim().isEmpty() || !n.equals(passwordField.getText())) {
                confirmPasswordField.getStyleClass().removeAll("default");
                confirmPasswordField.getStyleClass().add("invalid");
            } else {
                confirmPasswordField.getStyleClass().removeAll("invalid");
                confirmPasswordField.getStyleClass().add("default");
            }
        });

    }

    public void registerAction(ActionEvent actionEvent) throws BookstoreException {
        User user = new User();
        user.setUsername(usernameField.getText());
        user.setPassword(passwordField.getText());
        try {
            if(usernameField.getText().length() < 6)
                throw new UserException("Username needs to be at least 6 characters.");
            if(passwordField.getText().length() < 8)
                throw new UserException("Password needs to be at least 8 characters.");
            if(!passwordField.getText().equals(confirmPasswordField.getText()))
                throw new UserException("Passwords do not match.");
            DaoFactory.userDao().add(user);
        } catch (UserException | BookstoreException e) {
            if(e instanceof UserException)
                errorMsgLabel.setText(e.getMessage());
            else
                errorMsgLabel.setText("User already exists.");
            errorMsgLabel.setVisible(true);
            return;
        }

        changeWindow("main", "Main", new MainController(usernameField.getText()), actionEvent);
    }

    public void closeAction(ActionEvent actionEvent) {
        Stage stage = (Stage) registerBtn.getScene().getWindow();
        stage.close();
    }

    public void goToLoginAction(ActionEvent actionEvent) throws BookstoreException {
        changeWindow("register", "Register", new RegistrationController(), actionEvent);
    }

    /**
     * Helper function used for displaying a different window
     * @param fxmlFileName name of the FXML file
     * @param title title that will be displayed as "Bookstore | 'title'"
     * @param controller controller for the new window
     * @param actionEvent actionEvent parameter that is passed down by the method that called this method
     * @throws BookstoreException exception is thrown if a file with given name does not exist
     */
    private void changeWindow(String fxmlFileName, String title, Object controller, ActionEvent actionEvent) throws BookstoreException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlFileName + ".fxml"));
        loader.setController(controller);
        Stage newStage = new Stage();
        try {
            newStage.setScene(new Scene(loader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        } catch (IOException e) {
            throw new BookstoreException("FXML file does not exist.");
        }
        newStage.getIcons().add(new Image("/images/bookstore_icon.png"));
        newStage.setTitle("Bookstore | " + title);
        newStage.setResizable(false);
        newStage.show();

        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
