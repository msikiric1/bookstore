package ba.unsa.etf.rpr.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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

    }

    public void closeClick(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
