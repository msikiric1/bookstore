package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.UserManager;
import ba.unsa.etf.rpr.business.WindowManager;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import ba.unsa.etf.rpr.exceptions.UserException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationController {
    public TextField usernameField;
    public PasswordField passwordField;
    public PasswordField confirmPasswordField;
    public Label errorMsgLabel;
    private final WindowManager wm = new WindowManager();
    private final UserManager userManager = new UserManager();

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
            userManager.validate(usernameField.getText(), passwordField.getText(), confirmPasswordField.getText());
            userManager.add(user);
        } catch (UserException | BookstoreException e) {
            if(e instanceof UserException)
                errorMsgLabel.setText(e.getMessage());
            else
                errorMsgLabel.setText("User already exists.");

            errorMsgLabel.setVisible(true);
            return;
        }

        wm.changeWindow("main", "Main", new MainController(usernameField.getText()), actionEvent);
    }

    public void closeAction(ActionEvent actionEvent) {
        wm.closeWindow(actionEvent);
    }

    public void goToLoginAction(ActionEvent actionEvent) throws BookstoreException {
        wm.changeWindow("login", "Login", new LoginController(), actionEvent);
    }
}
