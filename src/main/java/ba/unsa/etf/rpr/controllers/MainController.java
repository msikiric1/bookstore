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
import java.util.stream.Collectors;

public class MainController {
    public Label usernameLabel;
    public TextField searchField;
    public ListView<Book> booksListView;
    public ComboBox categoryCbox;
    public ComboBox authorCbox;
    public Label infoLabel;
    private List<Book> books;
    private List<Author> authors;
    private List<Category> categories;
    private String username;
    private List<Book> filteredBooks;
    private final WindowManager wm = new WindowManager();
    private final BookManager bookManager = new BookManager();
    private final AuthorManager authorManager = new AuthorManager();
    private final CategoryManager categoryManager = new CategoryManager();

    public MainController(String username) {
        try {
            books = FXCollections.observableArrayList(bookManager.getAll());
            authors = FXCollections.observableArrayList(authorManager.getAll());
            categories = FXCollections.observableArrayList(categoryManager.getAll());
        } catch(BookstoreException e) {
            System.out.println("Something's wrong with retrieving data from tables");
            throw new RuntimeException(e);
        }
        this.username = username;
        this.filteredBooks = books;
    }

    @FXML
    public void initialize() {
        usernameLabel.setText("Hello, " + username + "!");
        booksListView.setItems(FXCollections.observableArrayList(filteredBooks));
        authorCbox.getItems().add("");
        authorCbox.getItems().addAll(authors.stream().map(author -> {
            return author.getName().trim();
        }).collect(Collectors.toCollection(FXCollections::observableArrayList)));
        authorCbox.getSelectionModel().selectFirst();
        categoryCbox.getItems().add("");
        categoryCbox.getItems().addAll(categories.stream().map(category -> {
            return category.getName().trim();
        }).collect(Collectors.toCollection(FXCollections::observableArrayList)));
        categoryCbox.getSelectionModel().selectFirst();

        searchField.textProperty().addListener((observable, o, n) -> {
            searchBooks();
            refresh(filteredBooks);
        });

        categoryCbox.getSelectionModel().selectedItemProperty().addListener((observable, o, n) -> {
            searchBooks();
            refresh(filteredBooks);
        });

        authorCbox.getSelectionModel().selectedItemProperty().addListener((observable, o, n) -> {
            searchBooks();
            refresh(filteredBooks);
        });
    }

    public void logoutAction(ActionEvent actionEvent) throws BookstoreException {
        wm.changeWindow("login", "Login", new LoginController(), actionEvent);
    }

    public void closeAction(ActionEvent actionEvent) {
        wm.closeWindow(actionEvent);
    }

    public void detailsAction(ActionEvent actionEvent) throws BookstoreException {
        Book selectedBook = filteredBooks.stream().filter(book -> {
            if(booksListView.getSelectionModel().getSelectedItem() == null) return false;
            return booksListView.getSelectionModel().getSelectedItem().getId() == book.getId();
        }).findAny().orElse(null);

        if (selectedBook != null)
            wm.openWindow("details", "Book details", new DetailsController(selectedBook), actionEvent);
        else
            infoLabel.setText("Info: You need to select a book that you want to view.");
    }

    private void searchBooks() {
        String searchString = searchField.getText();
        String searchCategory = categoryCbox.getSelectionModel().getSelectedItem() != null ?
                categoryCbox.getSelectionModel().getSelectedItem().toString() : "";
        String searchAuthor = authorCbox.getSelectionModel().getSelectedItem() != null ?
                authorCbox.getSelectionModel().getSelectedItem().toString() : "";
        this.filteredBooks = books.stream().filter(book -> {
           return book.getTitle().toLowerCase().contains(searchString) &&
                   book.getCategory().getName().contains(searchCategory) &&
                   book.getAuthor().getName().contains(searchAuthor);
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    private void refresh(List<Book> books) {
        booksListView.setItems(FXCollections.observableArrayList(books));
    }
}
