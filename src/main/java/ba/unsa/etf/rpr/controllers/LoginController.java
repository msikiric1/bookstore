package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.UserManager;
import ba.unsa.etf.rpr.business.WindowManager;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import ba.unsa.etf.rpr.exceptions.UserException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Controller for the login page
 * @author Muaz Sikiric
 */
public class LoginController {
    // components
    public TextField usernameField;
    public PasswordField passwordField;
    public Label errorMsgLabel;

    // managers
    private final WindowManager windowManager = new WindowManager();
    private final UserManager userManager = new UserManager();

    @FXML
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
    }

    /**
     * Event handler for the login button
     * @param actionEvent
     * @throws BookstoreException
     */
    public void loginAction(ActionEvent actionEvent) throws BookstoreException {
        User user;
        try {
            userManager.validateLogin(usernameField.getText(), passwordField.getText());
            user = userManager.getUser(usernameField.getText(), userManager.hashPassword(passwordField.getText()));
        } catch(UserException | BookstoreException e) {
            if(e instanceof UserException) errorMsgLabel.setText(e.getMessage());
            else errorMsgLabel.setText("The username or password is incorrect.");
            errorMsgLabel.setVisible(true);
            return;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        if(user.isAdmin()) {
            System.out.println("admin");
            windowManager.changeWindow("admin", "Admin", new AdminController(user.getUsername()), actionEvent);
        } else {
            System.out.println("user");
            windowManager.changeWindow("main", "Main", new MainController(user.getUsername()), actionEvent);
        }
    }

    /**
     * Event handler for the close button
     * @param actionEvent
     */
    public void closeAction(ActionEvent actionEvent) {
        windowManager.closeWindow(actionEvent);
    }

    /**
     * Event handler for the go-to-registration button
     * @param actionEvent
     * @throws BookstoreException
     */
    public void goToRegistrationAction(ActionEvent actionEvent) throws BookstoreException {
        windowManager.changeWindow("register", "Register", new RegistrationController(), actionEvent);
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
