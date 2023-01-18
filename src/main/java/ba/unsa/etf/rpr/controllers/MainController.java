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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainController {
    public Label usernameLabel;
    public TextField searchField;
    public ListView booksListView;
    public VBox categoriesBox;
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

        for (Category category : categories) {
            Button categoryButton = new Button(category.getName());
            categoryButton.setOnAction(event -> {
                filteredBooks = books.stream().filter(book -> {
                    return book.getCategory().getName().toLowerCase().contains(category.getName().toLowerCase());
                }).collect(Collectors.toCollection(FXCollections::observableArrayList));
                refresh();
            });
            categoriesBox.getChildren().add(categoryButton);
        }

        searchField.textProperty().addListener((observable, o, n) -> {
            searchBooks(n);
            refresh();
        });

    }

    public void logoutAction(ActionEvent actionEvent) throws BookstoreException {
        changeWindow("login", "Login", new LoginController(), actionEvent);
    }

    public void closeAction(ActionEvent actionEvent) {
        Stage stage = (Stage) usernameLabel.getScene().getWindow();
        stage.close();
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

    public void addAction(ActionEvent actionEvent) throws BookstoreException {
        AoUController aouController = new AoUController("Add", authors, categories);
        openWindow("aoubook", "Add a book", aouController, actionEvent);
    }

    public void updateAction(ActionEvent actionEvent) {
    }

    public void deleteAction(ActionEvent actionEvent) {
    }

    private void openWindow(String fxmlFileName, String title, Object controller, ActionEvent actionEvent) throws BookstoreException {
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
    }
}
