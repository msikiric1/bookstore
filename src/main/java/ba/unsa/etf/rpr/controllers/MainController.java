package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Category;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.stream.Collectors;

public class MainController {
    public Label usernameLabel;
    public TextField searchField;
    public ListView booksListView;
    private ObservableList<Book> books;
    private ObservableList<Book> filteredBooks;
    private ObservableList<Author> authors;
    private ObservableList<Category> categories;
            
    private String username;
    public MainController(String username) {
        try {
            books = FXCollections.observableArrayList(DaoFactory.bookDao().getAll());
            authors = FXCollections.observableArrayList(DaoFactory.authorDao().getAll());
            categories = FXCollections.observableArrayList(DaoFactory.categoryDao().getAll());
        } catch(BookstoreException e) {
            System.out.println("Something's wrong with retrieving data from tables");
            throw new RuntimeException(e);
        }
        this.username = username;
        this.filteredBooks = books;
    }

    @FXML
    public void initialize() {
        usernameLabel.setText(usernameLabel.getText() + username);
        refresh();
        searchField.textProperty().addListener((observable, o, n) -> {
            searchBooks(n);
            refresh();
        });
    }

    public void logoutAction(ActionEvent actionEvent) {
    }

    public void closeAction(ActionEvent actionEvent) {
    }

    public void searchAction(ActionEvent actionEvent) {
    }

    public void detailsAction(ActionEvent actionEvent) {
    }

    private void searchBooks(String searchString) {
        filteredBooks = books.stream().filter(book -> {
           return book.getTitle().toLowerCase().contains(searchString.toLowerCase());
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    private void refresh() {
        booksListView.setItems(filteredBooks);
    }
}
