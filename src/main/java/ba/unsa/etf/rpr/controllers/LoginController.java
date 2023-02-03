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

        usernameField.textProperty().addListener((observableValue, o, n) -> {
            if(n.trim().isEmpty()) setInvalidStyles(usernameField);
            else removeInvalidStyles(usernameField);
        });

        passwordField.textProperty().addListener((observableValue, o, n) -> {
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
            user = userManager.getUser(usernameField.getText(), passwordField.getText());
        } catch(BookstoreException e) {
            errorMsgLabel.setText("The username or password is incorrect.");
            errorMsgLabel.setVisible(true);
            return;
        }

        if(user.isAdmin()) {
            System.out.println("admin");
            windowManager.changeWindow("admin", "Admin", new AdminController(usernameField.getText()), actionEvent);
        } else {
            System.out.println("user");
            windowManager.changeWindow("main", "Main", new MainController(usernameField.getText()), actionEvent);
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
     * Event handler for the registration button
     * @param actionEvent
     * @throws BookstoreException
     */
    public void goToRegistrationAction(ActionEvent actionEvent) throws BookstoreException {
        windowManager.changeWindow("register", "Register", new RegistrationController(), actionEvent);
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
