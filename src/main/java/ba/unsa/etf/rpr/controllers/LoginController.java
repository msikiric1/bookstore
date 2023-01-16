package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.dao.UserDao;
import ba.unsa.etf.rpr.dao.UserDaoSQLImpl;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    @FXML
    public void initialize() {
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
    }

    public void loginClick(ActionEvent actionEvent) {
        User user = new User();
        try {
            user = new UserDaoSQLImpl().getUser(usernameField.getText(), passwordField.getText());
        } catch(BookstoreException e) {
            errorMsgLabel.setText("The username or password is incorrect.");
            return;
        }

        if(user.isAdmin()) {
            System.out.println("admin");
        } else {

        }
    }

    public void closeClick(ActionEvent actionEvent) {
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.close();
    }
}
