package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.exceptions.BookstoreException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class HomeController {
    public Button loginBtn;
    public Button registerBtn;
    public Button closeBtn;

    public void goToLoginClick(ActionEvent actionEvent) throws BookstoreException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setTitle("Bookstore | Login");
            stage.setResizable(false);
            stage.show();

            Stage currentStage = (Stage) loginBtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            throw new BookstoreException(e.getMessage(), e);
        }
    }

    public void goToRegistrationClick(ActionEvent actionEvent) throws BookstoreException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/registration.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setTitle("Bookstore | Register");
            stage.setResizable(false);
            stage.show();

            Stage currentStage = (Stage) loginBtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            throw new BookstoreException(e.getMessage(), e);
        }
    }

    public void closeAppClick(ActionEvent actionEvent) {
    }
}
