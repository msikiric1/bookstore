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

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Controller for the registration page
 * @author Muaz Sikiric
 */
public class RegistrationController {
    // components
    public TextField usernameField;
    public PasswordField passwordField;
    public PasswordField confirmPasswordField;
    public Label errorMsgLabel;

    // managers
    private final WindowManager windowManager = new WindowManager();
    private final UserManager userManager = new UserManager();

    public void initialize() {
        Platform.runLater(() -> usernameField.requestFocus()); // needed to set focus on username field

        usernameField.textProperty().addListener((observable, o, n) -> {
            if(n.trim().isEmpty()) setInvalidStyles(usernameField);
            else removeInvalidStyles(usernameField);
        });

        passwordField.textProperty().addListener((observable, o, n) -> {
            if(n.trim().isEmpty()) setInvalidStyles(passwordField);
            else removeInvalidStyles(passwordField);
        });

        confirmPasswordField.textProperty().addListener((observable, o, n) -> {
            if(n.trim().isEmpty() || !n.equals(passwordField.getText())) setInvalidStyles(confirmPasswordField);
            else removeInvalidStyles(confirmPasswordField);
        });
    }

    /**
     * Event handler for the register button
     * @param actionEvent
     * @throws BookstoreException
     */
    public void registerAction(ActionEvent actionEvent) throws BookstoreException {
        User user = new User();
        try {
            user.setUsername(usernameField.getText());
            user.setPassword(userManager.hashPassword(passwordField.getText()));

            userManager.validateRegistration(usernameField.getText(), passwordField.getText(), confirmPasswordField.getText());
            user = userManager.add(user);
        } catch (UserException | BookstoreException e) {
            if(e instanceof UserException) errorMsgLabel.setText(e.getMessage());
            else errorMsgLabel.setText("User already exists.");
            errorMsgLabel.setVisible(true);
            return;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        windowManager.changeWindow("main", "Main", new MainController(user.getUsername()), actionEvent);
    }

    /**
     * Event handler for the close button
     * @param actionEvent
     */
    public void closeAction(ActionEvent actionEvent) {
        windowManager.closeWindow(actionEvent);
    }

    /**
     * Event handler for the go-to-login button
     * @param actionEvent
     * @throws BookstoreException
     */
    public void goToLoginAction(ActionEvent actionEvent) throws BookstoreException {
        windowManager.changeWindow("login", "Login", new LoginController(), actionEvent);
    }

    /**
     * Applies "invalid" styles to text field
     * @param textField invalid text/password field
     */
    private void setInvalidStyles(TextField textField) {
        textField.getStyleClass().removeAll("default");
        textField.getStyleClass().add("invalid");
    }

    /**
     * Removes "invalid" styles from text field
     * @param textField valid text/password field
     */
    private void removeInvalidStyles(TextField textField) {
        textField.getStyleClass().removeAll("invalid");
        textField.getStyleClass().add("default");
    }
}
