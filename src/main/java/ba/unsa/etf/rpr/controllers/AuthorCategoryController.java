package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Category;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class AuthorCategoryController {
    public TextField nameField;
    public TextField addressField;
    public TextField phoneField;
    public TableView authorsTable;
    public TableColumn authorsColId;
    public TableColumn authorsColName;
    public TableColumn authorsColAddress;
    public TableColumn authorsColPhone;
    public TableView categoriesTable;
    public TableColumn categoriesColId;
    public TableColumn categoriesColName;
    public Label infoLabel;
    public Label usernameLabel;
    public RadioButton authorsRadioBtn;
    public RadioButton categoriesRadioBtn;
    private ObservableList<Book> books;
    private ObservableList<Author> authors;
    private ObservableList<Category> categories;
    private String username;

    public AuthorCategoryController(ObservableList<Author> authors, ObservableList<Category> categories, ObservableList<Book> books, String username) {
        this.authors = authors;
        this.categories = categories;
        this.books = books;
        this.username = username;
    }

    @FXML
    public void initialize() {
        usernameLabel.setText("Hello, " + username + "!");
        authorsColId.setResizable(false);
        authorsColName.setResizable(false);
        authorsColAddress.setResizable(false);
        authorsColPhone.setResizable(false);
        categoriesColId.setResizable(false);
        categoriesColName.setResizable(false);

        authorsColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        authorsColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        authorsColAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        authorsColPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        authorsTable.setItems(authors);

        categoriesColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        categoriesColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoriesTable.setItems(categories);

        ToggleGroup type = new ToggleGroup();
        authorsRadioBtn.setToggleGroup(type);
        categoriesRadioBtn.setToggleGroup(type);
        authorsRadioBtn.setSelected(true);

        type.selectedToggleProperty().addListener((observable, o, n) -> {
            RadioButton selectedRadioBtn = (RadioButton) type.getSelectedToggle();

            if(selectedRadioBtn.getText().equalsIgnoreCase("authors")) {
                addressField.setVisible(true);
                phoneField.setVisible(true);
            } else {
                addressField.setVisible(false);
                phoneField.setVisible(false);
            }
        });
    }

    public void addAction(ActionEvent actionEvent) {
    }

    public void updateAction(ActionEvent actionEvent) {
    }

    public void deleteAction(ActionEvent actionEvent) {
    }

    public void viewBooksAction(ActionEvent actionEvent) throws BookstoreException {
        AdminController adminController = new AdminController(books, authors, categories, username);
        changeWindow("admin", "Admin", adminController, actionEvent);
    }

    public void logoutAction(ActionEvent actionEvent) throws BookstoreException {
        changeWindow("login", "Login", new LoginController(), actionEvent);
    }

    public void closeAction(ActionEvent actionEvent) {
        Stage stage = (Stage) usernameLabel.getScene().getWindow();
        stage.close();
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
