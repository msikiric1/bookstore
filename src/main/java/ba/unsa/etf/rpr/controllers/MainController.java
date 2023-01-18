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

public class MainController {
    public Label usernameLabel;
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
    }

    @FXML
    public void initialize() {
        usernameLabel.setText(usernameLabel.getText() + username);
    }

    public void logoutAction(ActionEvent actionEvent) {
    }

    public void closeAction(ActionEvent actionEvent) {
    }

    public void searchAction(ActionEvent actionEvent) {
    }

    public void detailsAction(ActionEvent actionEvent) {
    }
}
