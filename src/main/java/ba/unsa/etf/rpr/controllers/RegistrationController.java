package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.dao.UserDaoSQLImpl;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
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
    public Button goBackBtn;
    public Button registerBtn;

    public void initialize() {
        errorMsgLabel.setVisible(false);
        usernameField.getStyleClass().add("invalid");
        usernameField.textProperty().addListener((observableValue, o, n) -> {
            if(n.trim().isEmpty()) {
                usernameField.getStyleClass().removeAll("valid");
                usernameField.getStyleClass().add("invalid");
            } else {
                usernameField.getStyleClass().removeAll("invalid");
                usernameField.getStyleClass().add("valid");
            }
        });

        confirmPasswordField.textProperty().addListener((observableValue, o, n) -> {
            if(n.trim().isEmpty() || !n.equals(passwordField.getText())) {
                confirmPasswordField.getStyleClass().removeAll("valid");
                confirmPasswordField.getStyleClass().add("invalid");
            } else {
                confirmPasswordField.getStyleClass().removeAll("invalid");
                confirmPasswordField.getStyleClass().add("valid");
            }
        });

    }

    public void registerClick(ActionEvent actionEvent) throws BookstoreException {
        User user = new User();
        user.setUsername(usernameField.getText());
        user.setPassword(passwordField.getText());
        try {
            if(usernameField.getText().length() < 6)
                throw new BookstoreException("Username needs to be at least 6 characters.");
            if(passwordField.getText().length() < 8)
                throw new BookstoreException("Password needs to be at least 8 characters.");
            if(!passwordField.getText().equals(confirmPasswordField.getText()))
                throw new BookstoreException("Passwords do not match.");
            new UserDaoSQLImpl().add(user);
        } catch(BookstoreException e) {
            errorMsgLabel.setVisible(true);
            errorMsgLabel.setText(e.getMessage());
            return;
        }
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.getIcons().add(new Image("/images/bookstore_icon.png"));
            stage.setTitle("Bookstore | Main");
            stage.setResizable(false);
            stage.show();

            Stage currentStage = (Stage) registerBtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            throw new BookstoreException(e.getMessage(), e);
        }
    }

    public void goBackClick(ActionEvent actionEvent) throws BookstoreException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.getIcons().add(new Image("/images/bookstore_icon.png"));
            stage.setTitle("Bookstore | Home");
            stage.setResizable(false);
            stage.show();

            Stage currentStage = (Stage) registerBtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            throw new BookstoreException(e.getMessage(), e);
        }
    }
}
