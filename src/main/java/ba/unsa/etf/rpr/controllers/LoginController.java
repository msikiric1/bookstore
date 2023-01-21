package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.UserManager;
import ba.unsa.etf.rpr.business.WindowManager;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author Muaz Sikiric
 */
public class LoginController {
    public TextField usernameField;
    public PasswordField passwordField;
    public Label errorMsgLabel;
    private final WindowManager wm = new WindowManager();
    private final UserManager userManager = new UserManager();

    @FXML
    public void initialize() {
        Platform.runLater(() -> usernameField.requestFocus()); // needed to set focus on username field
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
            user = userManager.getUser(usernameField.getText(), passwordField.getText());
        } catch(BookstoreException e) {
            errorMsgLabel.setText("The username or password is incorrect.");
            errorMsgLabel.setVisible(true);
            return;
        }

        if(user.isAdmin()) {
            System.out.println("admin");
            wm.changeWindow("admin", "Admin", new AdminController(null, null, null, usernameField.getText()), actionEvent);
        } else {
            System.out.println("user");
            wm.changeWindow("main", "Main", new MainController(usernameField.getText()), actionEvent);
        }
    }

    public void closeAction(ActionEvent actionEvent) {
        wm.closeWindow(actionEvent);
    }

    public void goToRegistrationAction(ActionEvent actionEvent) throws BookstoreException {
        wm.changeWindow("register", "Register", new RegistrationController(), actionEvent);
    }
}
