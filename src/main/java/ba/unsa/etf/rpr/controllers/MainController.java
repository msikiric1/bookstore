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
import javafx.scene.control.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Controller for the main user page
 * @author Muaz Sikiric
 */
public class MainController {
    // components
    public Label usernameLabel;
    public TextField searchField;
    public ListView<Book> booksListView;
    public ComboBox<Category> categoryCbox;
    public ComboBox<Author> authorCbox;
    public Label infoLabel;

    // managers
    private final WindowManager windowManager = new WindowManager();

    private final List<Book> books;
    private final List<Author> authors;
    private final List<Category> categories;
    private final String username;
    private List<Book> filteredBooks;

    /**
     * Constructor
     * @param username username
     */
    public MainController(String username) {
        try {
            books = FXCollections.observableList(new BookManager().getAll());
            authors = FXCollections.observableList(new AuthorManager().getAll());
            categories = FXCollections.observableList(new CategoryManager().getAll());
        } catch(BookstoreException e) {
            System.out.println("Something's wrong with retrieving data from tables.");
            throw new RuntimeException(e);
        }
        this.username = username;
        filteredBooks = books;
    }

    @FXML
    public void initialize() {
        usernameLabel.setText("Hello, " + username + "!");
        booksListView.setItems(FXCollections.observableList(filteredBooks));
        authorCbox.getItems().add(null); // first author needs to be blank
        authorCbox.getItems().addAll(authors);
        categoryCbox.getItems().add(null); // first category needs to be blank
        categoryCbox.getItems().addAll(categories);

        searchField.textProperty().addListener((observable, o, n) -> searchAndRefreshBooks());
        categoryCbox.getSelectionModel().selectedItemProperty().addListener((observable, o, n) -> searchAndRefreshBooks());
        authorCbox.getSelectionModel().selectedItemProperty().addListener((observable, o, n) -> searchAndRefreshBooks());
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
     * Event handler for the book-details button
     * @param actionEvent
     */
    public void detailsAction(ActionEvent actionEvent) throws BookstoreException {
        Book selectedBook = booksListView.getSelectionModel().getSelectedItem();

        if (selectedBook != null)
            windowManager.openWindow("details", "Book details", new DetailsController(selectedBook), actionEvent);
        else
            infoLabel.setText("You need to select a book that you want to view.");
    }

    /**
     * Filters books and refreshes ListView component to only display them
     */
    private void searchAndRefreshBooks() {
        searchBooks();
        booksListView.setItems(FXCollections.observableList(filteredBooks));
    }

    /**
     * Filters books by title, author name and category name
     */
    private void searchBooks() {
        String searchTitle = searchField.getText();
        String searchCategory = Objects.toString(categoryCbox.getSelectionModel().getSelectedItem(), "");
        String searchAuthor = Objects.toString(authorCbox.getSelectionModel().getSelectedItem(), "");

        filteredBooks = books.stream().filter(book -> {
            return book.getTitle().toLowerCase().contains(searchTitle.toLowerCase()) &&
                   book.getCategory().getName().contains(searchCategory) &&
                   book.getAuthor().getName().contains(searchAuthor);
        }).collect(Collectors.toList());
    }
}
