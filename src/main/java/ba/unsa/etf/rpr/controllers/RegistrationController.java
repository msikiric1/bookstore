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
    // components
    public TextField usernameField;
    public PasswordField passwordField;
    public PasswordField confirmPasswordField;
    public Label errorMsgLabel;

    // managers
    private final WindowManager wm = new WindowManager();
    private final UserManager userManager = new UserManager();

    public void initialize() {
        Platform.runLater(() -> usernameField.requestFocus()); // needed to set focus on username field

        usernameField.textProperty().addListener((observableValue, o, n) -> {
            if(n.trim().isEmpty()) setInvalidStyles(usernameField);
            else removeInvalidStyles(usernameField);
        });

        passwordField.textProperty().addListener((observableValue, o, n) -> {
            if(n.trim().isEmpty()) setInvalidStyles(passwordField);
            else removeInvalidStyles(passwordField);
        });

        confirmPasswordField.textProperty().addListener((observableValue, o, n) -> {
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
        user.setUsername(usernameField.getText());
        user.setPassword(passwordField.getText());
        try {
            userManager.validateRegistration(usernameField.getText(), passwordField.getText(), confirmPasswordField.getText());
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

    /**
     * Event handler for the close button
     * @param actionEvent
     */
    public void closeAction(ActionEvent actionEvent) {
        wm.closeWindow(actionEvent);
    }

    /**
     * Event handler for the go-to-login button
     * @param actionEvent
     * @throws BookstoreException
     */
    public void goToLoginAction(ActionEvent actionEvent) throws BookstoreException {
        wm.changeWindow("login", "Login", new LoginController(), actionEvent);
    }

    /**
     * Helper method for styling invalid text field
     * @param textField text/password field that needs to be styled
     */
    private void setInvalidStyles(TextField textField) {
        textField.getStyleClass().removeAll("default");
        textField.getStyleClass().add("invalid");
    }

    /**
     * Helper method for removing invalid styles from text field
     * @param textField text/password field to remove the styles from
     */
    private void removeInvalidStyles(TextField textField) {
        textField.getStyleClass().removeAll("invalid");
        textField.getStyleClass().add("default");
    }
}
