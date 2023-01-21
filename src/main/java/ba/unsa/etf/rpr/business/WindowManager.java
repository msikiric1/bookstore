package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.exceptions.BookstoreException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

/**
 *
 * @author Muaz Sikiric
 */
public class WindowManager {
    /**
     * Displays a different window instead of the current one
     * @param fxmlFileName name of the FXML file
     * @param title title that will be displayed as "Bookstore | 'title'"
     * @param controller controller for the new window
     * @param actionEvent an event representing some type of action
     * @throws BookstoreException exception is thrown if a file with given name does not exist
     */
    public void changeWindow(String fxmlFileName, String title, Object controller, ActionEvent actionEvent) throws BookstoreException {
        openWindow(fxmlFileName, title, controller, actionEvent);
        closeWindow(actionEvent);
    }

    /**
     * Opens a new window
     * @param fxmlFileName name of the FXML file
     * @param title title that will be displayed as "Bookstore | 'title'"
     * @param controller controller for the new window
     * @param actionEvent an event representing some type of action
     * @throws BookstoreException exception is thrown if a file with given name does not exist
     */
    public Stage openWindow(String fxmlFileName, String title, Object controller, ActionEvent actionEvent) throws BookstoreException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlFileName + ".fxml"));
        loader.setController(controller);
        Stage newStage = new Stage();
        try {
            newStage.setScene(new Scene(loader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        } catch (IOException e) {
            throw new BookstoreException(e.getMessage(), e);
        }
        newStage.getIcons().add(new Image("/images/bookstore_icon.png"));
        newStage.setTitle("Bookstore | " + title);
        newStage.setResizable(false);
        newStage.show();
        return newStage;
    }

    /**
     * Closes the current window
     * @param actionEvent an event representing some type of action
     */
    public void closeWindow(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
