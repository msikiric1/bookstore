package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.dao.UserDao;
import ba.unsa.etf.rpr.dao.UserDaoSQLImpl;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

/**
 *
 * @author Muaz Sikiric
 */
public class LoginController {
    public TextField usernameField;
    public PasswordField passwordField;
    public Button loginBtn;
    public Button closeBtn;
    public Label errorMsgLabel;

    public LoginController() {}

    @FXML
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
    }

    public void loginAction(ActionEvent actionEvent) throws BookstoreException {
        User user;
        try {
            user = new UserDaoSQLImpl().getUser(usernameField.getText(), passwordField.getText());
        } catch(BookstoreException e) {
            errorMsgLabel.setText("The username or password is incorrect.");
            errorMsgLabel.setVisible(true);
            return;
        }

        if(user.isAdmin()) {
            System.out.println("admin");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin.fxml"));
                AdminController adminController = new AdminController(usernameField.getText());
                loader.setController(adminController);
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.getIcons().add(new Image("/images/bookstore_icon.png"));
                stage.setTitle("Bookstore | Admin");
                stage.setResizable(false);
                stage.show();

                Stage currentStage = (Stage) loginBtn.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                throw new BookstoreException(e.getMessage(), e);
            }
        } else {
            System.out.println("user");
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.getIcons().add(new Image("/images/bookstore_icon.png"));
                stage.setTitle("Bookstore | Main");
                stage.setResizable(false);
                stage.show();

                Stage currentStage = (Stage) loginBtn.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                throw new BookstoreException(e.getMessage(), e);
            }
        }
    }

    public void closeAction(ActionEvent actionEvent) {
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.close();
    }

    public void goToRegistrationAction(ActionEvent actionEvent) {

    }
}
