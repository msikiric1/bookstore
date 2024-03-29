package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.AuthorManager;
import ba.unsa.etf.rpr.business.CategoryManager;
import ba.unsa.etf.rpr.business.WindowManager;
import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Category;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import javafx.beans.property.SimpleStringProperty;
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
    public TableColumn<Author, Integer> authorsColId;
    public TableColumn<Author, String> authorsColName;
    public TableColumn<Author, String> authorsColAddress;
    public TableColumn<Author, String> authorsColPhone;
    public TextField categoryNameField;
    public TableView<Category> categoriesTable;
    public TableColumn<Category, Integer> categoriesColId;
    public TableColumn<Category, String> categoriesColName;
    public Label infoLabel;
    public Label usernameLabel;

    // managers
    private final WindowManager windowManager = new WindowManager();
    private final AuthorManager authorManager = new AuthorManager();
    private final CategoryManager categoryManager = new CategoryManager();

    // model
    private final AuthorCategoryModel acModel = new AuthorCategoryModel();

    private final List<Book> books;
    private final List<Author> authors;
    private final List<Category> categories;
    private final String username;


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
        authorsTable.setItems(FXCollections.observableList(authors));

        categoriesColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        categoriesColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoriesTable.setItems(FXCollections.observableList(categories));

        authorsTable.getSelectionModel().selectedItemProperty().addListener((observable, o, n) -> { if(n != null) showAuthor(n); });
        categoriesTable.getSelectionModel().selectedItemProperty().addListener((observable, o, n) -> { if(n != null) showCategory(n); });

        authorNameField.textProperty().bindBidirectional(acModel.authorName);
        authorAddressArea.textProperty().bindBidirectional(acModel.authorAddress);
        authorPhoneField.textProperty().bindBidirectional(acModel.authorPhone);
        categoryNameField.textProperty().bindBidirectional(acModel.categoryName);
    }

    /**
     * Event handler for the add-author button
     * @param actionEvent
     */
    public void addAuthorAction(ActionEvent actionEvent) {
        Author newAuthor = acModel.toAuthor();
        try {
            authorManager.add(newAuthor);
            authors.add(newAuthor);
            refreshAuthors();
            infoLabel.setText("Added an author successfully.");
        } catch (BookstoreException e) {
            if(e.getMessage().contains("UNIQUE"))
                infoLabel.setText("Phone number needs to be unique.");
            else infoLabel.setText(e.getMessage());
        }
    }

    /**
     * Event handler for the update-author button
     * @param actionEvent
     */
    public void updateAuthorAction(ActionEvent actionEvent) {
        Author selectedAuthor = authorsTable.getSelectionModel().getSelectedItem();
        if(selectedAuthor == null) {
            infoLabel.setText("You need to select an author that you want to update.");
            return;
        }

        Author updatedAuthor = acModel.toAuthor();
        try {
            updatedAuthor.setId(selectedAuthor.getId());
            authorManager.update(updatedAuthor);
            authors.set(authors.indexOf(selectedAuthor), updatedAuthor);
            refreshAuthors();
            infoLabel.setText("Updated an author successfully.");
        } catch (BookstoreException e) {
            if(e.getMessage().contains("UNIQUE"))
                infoLabel.setText("Phone number needs to be unique.");
            else infoLabel.setText(e.getMessage());
        }
    }

    /**
     * Event handler for the delete-author button
     * @param actionEvent
     */
    public void deleteAuthorAction(ActionEvent actionEvent) {
        Author selectedAuthor = authorsTable.getSelectionModel().getSelectedItem();
        if(selectedAuthor == null) {
            infoLabel.setText("You need to select an author that you want to delete.");
            return;
        }

        try {
            authorManager.delete(selectedAuthor.getId());
            authors.remove(selectedAuthor);
            refreshAuthors();
            infoLabel.setText("Deleted an author successfully.");
        } catch(BookstoreException e) {
            infoLabel.setText(e.getMessage());
        }
    }

    /**
     * Event handler for the add-category button
     * @param actionEvent
     */
    public void addCategoryAction(ActionEvent actionEvent) {
        Category newCategory = acModel.toCategory();
        try {
            categoryManager.add(newCategory);
            categories.add(newCategory);
            refreshCategories();
            infoLabel.setText("Added a category successfully.");
        } catch(BookstoreException e) {
            if(e.getMessage().contains("UNIQUE"))
                infoLabel.setText("Category name needs to be unique.");
            else infoLabel.setText(e.getMessage());
        }
    }

    /**
     * Event handler for the update-category button
     * @param actionEvent
     */
    public void updateCategoryAction(ActionEvent actionEvent) {
        Category selectedCategory = categoriesTable.getSelectionModel().getSelectedItem();
        if(selectedCategory == null) {
            infoLabel.setText("You need to select a category that you want to update.");
            return;
        }

        Category updatedCategory = acModel.toCategory();
        try {
            updatedCategory.setId(selectedCategory.getId());
            categoryManager.update(updatedCategory);
            categories.set(categories.indexOf(selectedCategory), updatedCategory);
            refreshCategories();
            infoLabel.setText("Updated a category successfully.");
        } catch (BookstoreException e) {
            if(e.getMessage().contains("UNIQUE"))
                infoLabel.setText("Category name needs to be unique.");
            else infoLabel.setText(e.getMessage());
        }
    }

    /**
     * Event handler for the delete-category button
     * @param actionEvent
     */
    public void deleteCategoryAction(ActionEvent actionEvent) {
        Category selectedCategory = categoriesTable.getSelectionModel().getSelectedItem();
        if(selectedCategory == null) {
            infoLabel.setText("You need to select a category that you want to delete.");
            return;
        }

        try {
            categoryManager.delete(selectedCategory.getId());
            categories.remove(selectedCategory);
            refreshCategories();
            infoLabel.setText("Deleted a category successfully.");
        } catch(BookstoreException e) {
            infoLabel.setText(e.getMessage());
        }
    }

    /**
     * Event handler for the view-books button
     * @param actionEvent
     */
    public void viewBooksAction(ActionEvent actionEvent) throws BookstoreException {
        AdminController adminController = new AdminController(books, authors, categories, username);
        windowManager.changeWindow("admin", "Admin", adminController, actionEvent);
    }

    /**
     * Event handler for the logout button
     * @param actionEvent
     */
    public void logoutAction(ActionEvent actionEvent) throws BookstoreException {
        windowManager.changeWindow("login", "Login", new LoginController(), actionEvent);
    }

    /**
     * Event handler for the close button
     * @param actionEvent
     */
    public void closeAction(ActionEvent actionEvent) {
        windowManager.closeWindow(actionEvent);
    }

    /**
     * Displays author details in corresponding text fields
     * @param author selected author
     */
    private void showAuthor(Author author) {
        authorNameField.setText(author.getName());
        authorAddressArea.setText(author.getAddress());
        authorPhoneField.setText(author.getPhone());
    }

    /**
     * Displays category details in corresponding text fields
     * @param category selected category
     */
    private void showCategory(Category category) {
        categoryNameField.setText(category.getName());
    }

    /**
     * Refreshes authors TableView component with the new data and removes text from text fields
     */
    private void refreshAuthors() {
        authorsTable.setItems(FXCollections.observableList(authors));
        authorNameField.setText("");
        authorAddressArea.setText("");
        authorPhoneField.setText("");
    }

    /**
     * Refreshes category TableView component with the new data and removes text from text fields
     */
    private void refreshCategories() {
        categoriesTable.setItems(FXCollections.observableList(categories));
        categoryNameField.setText("");
    }

    /**
     * Helper model class used for two-way data binding with author and category forms
     * @author Muaz Sikiric
     */
    public static class AuthorCategoryModel {
        public SimpleStringProperty authorName = new SimpleStringProperty("");
        public SimpleStringProperty authorAddress = new SimpleStringProperty("");
        public SimpleStringProperty authorPhone = new SimpleStringProperty("");
        public SimpleStringProperty categoryName = new SimpleStringProperty("");

        /**
         * Constructs an author based on SimpleStringProperty values
         * @return author
         */
        public Author toAuthor() {
            Author author = new Author();
            author.setName(authorName.getValue());
            author.setAddress(authorAddress.getValue());
            author.setPhone(authorPhone.getValue());
            return author;
        }

        /**
         * Constructs a category based on SimpleStringProperty values
         * @return category
         */
        public Category toCategory() {
            Category category = new Category();
            category.setName(categoryName.getValue());
            return category;
        }
    }
}
