package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.WindowManager;
import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Category;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import javafx.collections.FXCollections;
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
import java.util.List;

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
    private List<Book> books;
    private List<Author> authors;
    private List<Category> categories;
    private String username;

    public AuthorCategoryController(List<Author> authors, List<Category> categories, List<Book> books, String username) {
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
        authorsTable.setItems(FXCollections.observableArrayList(authors));

        categoriesColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        categoriesColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoriesTable.setItems(FXCollections.observableArrayList(categories));

        ToggleGroup type = new ToggleGroup();
        authorsRadioBtn.setToggleGroup(type);
        categoriesRadioBtn.setToggleGroup(type);
        authorsRadioBtn.setSelected(true);

        RadioButton selectedType = (RadioButton) type.getSelectedToggle();
        if(selectedType.getText().equalsIgnoreCase("authors")) {
            authorsTable.getSelectionModel().selectedItemProperty().addListener((observable, o, n) -> {
                Author author = (Author) n;
                nameField.setText(author.getName());
                addressField.setText(author.getAddress());
                phoneField.setText(author.getPhone());
            });
        } else {
            categoriesTable.getSelectionModel().selectedItemProperty().addListener((observable, o, n) -> {
                Category category = (Category) n;
                nameField.setText(category.getName());
                addressField.setText("");
                phoneField.setText("");
            });
        }

        type.selectedToggleProperty().addListener((observable, o, n) -> {
            RadioButton selectedRadioBtn = (RadioButton) type.getSelectedToggle();

            if(selectedRadioBtn.getText().equalsIgnoreCase("authors")) {
                nameField.setPromptText("Author name");
                addressField.setVisible(true);
                phoneField.setVisible(true);
            } else {
                nameField.setPromptText("Category name");
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
        new WindowManager().changeWindow("admin", "Admin", adminController, actionEvent);
    }

    public void logoutAction(ActionEvent actionEvent) throws BookstoreException {
        new WindowManager().changeWindow("login", "Login", new LoginController(), actionEvent);
    }

    public void closeAction(ActionEvent actionEvent) {
        new WindowManager().closeWindow(actionEvent);
    }
}
