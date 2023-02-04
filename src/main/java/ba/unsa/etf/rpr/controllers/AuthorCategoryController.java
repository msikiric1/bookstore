package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.WindowManager;
import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Category;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

/**
 * Controller for the authors and categories admin page
 * @author Muaz Sikiric
 */
public class AuthorCategoryController {
    // components
    public TextField authorNameField;
    public TextArea authorAddressArea;
    public TextField authorPhoneField;
    public TableView<Author> authorsTable;
    public TableColumn authorsColId;
    public TableColumn authorsColName;
    public TableColumn authorsColAddress;
    public TableColumn authorsColPhone;
    public TextField categoryNameField;
    public TableView<Category> categoriesTable;
    public TableColumn categoriesColId;
    public TableColumn categoriesColName;
    public Label infoLabel;
    public Label usernameLabel;

    // managers
    private final WindowManager wm = new WindowManager();

    private List<Book> books;
    private List<Author> authors;
    private List<Category> categories;
    private String username;


    /**
     * Constructor
     * @param authors list of authors retrieved from the database
     * @param categories list of categories retrieved from the database
     * @param books list of books retrieved from the database
     * @param username admin username
     */
    public AuthorCategoryController(List<Author> authors, List<Category> categories, List<Book> books, String username) {
        this.authors = authors;
        this.categories = categories;
        this.books = books;
        this.username = username;
    }

    @FXML
    public void initialize() {
        usernameLabel.setText("Hello, " + username + "!");
        authorsColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        authorsColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        authorsColAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        authorsColPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        authorsTable.setItems(FXCollections.observableArrayList(authors));

        categoriesColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        categoriesColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoriesTable.setItems(FXCollections.observableArrayList(categories));

        authorsTable.getSelectionModel().selectedItemProperty().addListener((observable, o, n) -> showAuthor(n));
        categoriesTable.getSelectionModel().selectedItemProperty().addListener((observable, o, n) -> showCategory(n));
    }

    public void addAuthorAction(ActionEvent actionEvent) {

    }

    public void updateAuthorAction(ActionEvent actionEvent) {
    }

    public void deleteAuthorAction(ActionEvent actionEvent) {
    }

    public void addCategoryAction(ActionEvent actionEvent) {
    }

    public void updateCategoryAction(ActionEvent actionEvent) {
    }

    public void deleteCategoryAction(ActionEvent actionEvent) {
    }

    /**
     * Event handler for the view-books button
     * @param actionEvent
     */
    public void viewBooksAction(ActionEvent actionEvent) throws BookstoreException {
        AdminController adminController = new AdminController(books, authors, categories, username);
        wm.changeWindow("admin", "Admin", adminController, actionEvent);
    }

    /**
     * Event handler for the logout button
     * @param actionEvent
     */
    public void logoutAction(ActionEvent actionEvent) throws BookstoreException {
        wm.changeWindow("login", "Login", new LoginController(), actionEvent);
    }

    /**
     * Event handler for the close button
     * @param actionEvent
     */
    public void closeAction(ActionEvent actionEvent) {
        wm.closeWindow(actionEvent);
    }

    private void showAuthor(Author author) {
        authorNameField.setText(author.getName());
        authorAddressArea.setText(author.getAddress());
        authorPhoneField.setText(author.getPhone());
    }

    private void showCategory(Category category) {
        categoryNameField.setText(category.getName());
    }
}
