package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.exceptions.BookstoreException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class AdminController {
    public TableView booksTable;
    public TableColumn booksColId;
    public TableColumn booksColTitle;
    public TableColumn booksColAuthorId;
    public TableColumn booksColPublished;
    public TableColumn booksColPrice;
    public TableColumn booksColCategoryId;
    public Button logoutBtn;
    public Button closeBtn;
    public Button deleteBookBtn;
    public Button viewCategoriesBtn;
    public Button viewAuthorsBtn;
    public Label usernameLabel;
    private String username;

    public AdminController(String username) {
        this.username = username;
    }

    @FXML
    public void initialize() {
        usernameLabel.setText(usernameLabel.getText() + username);
    }


    public void logoutAction(ActionEvent actionEvent) {

    }

    public void closeAction(ActionEvent actionEvent) {
        Stage stage = (Stage) usernameLabel.getScene().getWindow();
        stage.close();
    }

    public void viewCategoriesAction(ActionEvent actionEvent) {
    }

    public void viewAuthorsAction(ActionEvent actionEvent) {
    }

    /**
     * Helper function used for displaying a different window
     * @param fxmlFileName name of the FXML file
     * @param title title that will be displayed as "Bookstore | 'title'"
     * @param controller controller for the new window
     * @param actionEvent actionEvent parameter that is passed down by the method that called this method
     * @throws BookstoreException exception is thrown if a file with given name does not exist
     */
    private void changeWindow(String fxmlFileName, String title, Object controller, ActionEvent actionEvent) throws BookstoreException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlFileName + ".fxml"));
        loader.setController(controller);
        Stage newStage = new Stage();
        try {
            newStage.setScene(new Scene(loader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        } catch (IOException e) {
            throw new BookstoreException("FXML file does not exist.");
        }
        newStage.getIcons().add(new Image("/images/bookstore_icon.png"));
        newStage.setTitle("Bookstore | " + title);
        newStage.setResizable(false);
        newStage.show();

        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
