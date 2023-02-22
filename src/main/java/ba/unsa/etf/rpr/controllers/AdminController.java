package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.AuthorManager;
import ba.unsa.etf.rpr.business.BookManager;
import ba.unsa.etf.rpr.business.CategoryManager;
import ba.unsa.etf.rpr.business.WindowManager;
import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Category;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller for the books admin page
 * @author Muaz Sikiric
 */
public class AdminController {
    // components
    public TableView<Book> booksTable;
    public TableColumn<Book, Integer> booksColId;
    public TableColumn<Book, String> booksColTitle;
    public TableColumn<Book, LocalDate> booksColPublished;
    public TableColumn<Book, Double> booksColPrice;
    public Label usernameLabel;
    public Label infoLabel;

    // managers
    private final WindowManager windowManager = new WindowManager();
    private final BookManager bookManager = new BookManager();

    private final List<Book> books;
    private final List<Author> authors;
    private final List<Category> categories;
    private final String username;

    /**
     * Constructor
     * @param books list of books retrieved from the database
     * @param authors list of authors retrieved from the database
     * @param categories list of categories retrieved from the database
     * @param username admin username
     */
    public AdminController(List<Book> books, List<Author> authors, List<Category> categories, String username) {
        this.books = books;
        this.authors = authors;
        this.categories = categories;
        this.username = username;
    }

    /**
     * Constructor
     * @param username admin username
     */
    public AdminController(String username) {
        try {
            this.books = FXCollections.observableList(bookManager.getAll());
            this.authors = FXCollections.observableList(new AuthorManager().getAll());
            this.categories = FXCollections.observableList(new CategoryManager().getAll());
        } catch(BookstoreException e) {
            System.out.println("Something's wrong with retrieving data from tables.");
            throw new RuntimeException(e);
        }
        this.username = username;
    }

    @FXML
    public void initialize() {
        usernameLabel.setText("Hello, " + username + "!");
        booksColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        booksColTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        booksColPublished.setCellValueFactory(new PropertyValueFactory<>("published"));
        booksColPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        booksTable.setItems(FXCollections.observableList(books));
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
     * Event handler for the view-authors-and-categories button
     * @param actionEvent
     */
    public void viewACAction(ActionEvent actionEvent) throws BookstoreException {
        AuthorCategoryController acController = new AuthorCategoryController(authors, categories, books, username);
        windowManager.changeWindow("authorcategory", "Authors & Categories", acController, actionEvent);
    }

    /**
     * Event handler for the add-book button
     * @param actionEvent
     */
    public void addAction(ActionEvent actionEvent) throws BookstoreException {
        AoUController aouController = new AoUController("Add", authors, categories, null);
        Stage newStage = windowManager.openWindow("aoubook", "Add", aouController, actionEvent);

        newStage.setOnHiding(event -> {
            Book newBook = aouController.getBook();
            if(newBook == null) return;
            books.add(newBook);
            booksTable.refresh();
            infoLabel.setText("Added a new book successfully.");
        });
    }

    /**
     * Event handler for the update-book button
     * @param actionEvent
     * @throws BookstoreException
     */
    public void updateAction(ActionEvent actionEvent) throws BookstoreException {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if(selectedBook == null) {
            infoLabel.setText("You need to select a book that you want to update.");
            return;
        }

        AoUController aouController = new AoUController("Update", authors, categories, selectedBook);
        Stage newStage = windowManager.openWindow("aoubook", "Update", aouController, actionEvent);

        newStage.setOnHiding(event -> {
            Book updatedBook = aouController.getBook();
            if(updatedBook == null) return;
            updatedBook.setId(selectedBook.getId());
            books.set(books.indexOf(selectedBook), updatedBook);
            booksTable.refresh();
            infoLabel.setText("Updated a new book successfully.");
        });
    }

    /**
     * Event handler for the delete-book button
     * @param actionEvent
     */
    public void deleteAction(ActionEvent actionEvent) {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if(selectedBook == null) {
            infoLabel.setText("You need to select a book that you want to delete.");
            return;
        }

        try {
            bookManager.delete(selectedBook.getId());
            books.remove(selectedBook);
            booksTable.refresh();
            infoLabel.setText("Deleted a book successfully.");
        } catch (BookstoreException e) {
            infoLabel.setText(e.getMessage());
        }
    }
}