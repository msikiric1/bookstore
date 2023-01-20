package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.dao.BookDaoSQLImpl;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class AdminController {
    public TableView booksTable;
    public TableColumn booksColId;
    public TableColumn booksColTitle;
    public TableColumn booksColPublished;
    public TableColumn booksColPrice;
    public Button logoutBtn;
    public Button closeBtn;
    public Button viewCategoriesBtn;
    public Button viewAuthorsBtn;
    public Label usernameLabel;
    private ObservableList<Book> books;
    private ObservableList<Author> authors;
    private ObservableList<Category> categories;
    private String username;

    public AdminController(String username) {
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
        usernameLabel.setText("Hello, " + username + "!");
        booksColId.setResizable(false);
        booksColTitle.setResizable(false);
        booksColPublished.setResizable(false);
        booksColPrice.setResizable(false);
        booksColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        booksColTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        booksColPublished.setCellValueFactory(new PropertyValueFactory<>("published"));
        booksColPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        booksTable.setItems(books);
    }


    public void logoutAction(ActionEvent actionEvent) throws BookstoreException {
        changeWindow("login", "Login", new LoginController(), actionEvent);
    }

    public void closeAction(ActionEvent actionEvent) {
        Stage stage = (Stage) usernameLabel.getScene().getWindow();
        stage.close();
    }

    public void viewCategoriesAction(ActionEvent actionEvent) throws BookstoreException {
        CategoryController categoryController = new CategoryController(books, authors, categories, username);
        changeWindow("categories", "Categories", categoryController, actionEvent);
    }

    public void viewAuthorsAction(ActionEvent actionEvent) throws BookstoreException {
        AuthorController authorController = new AuthorController(books, authors, categories, username);
        changeWindow("authors", "Authors", authorController, actionEvent);
    }

    public void addAction(ActionEvent actionEvent) throws BookstoreException {
        AoUController aouController = new AoUController("Add", authors, categories, null);
        Stage newStage = openWindow("aoubook", "Add", aouController, actionEvent);

        newStage.setOnHiding(event -> {
            Book newBook = aouController.getBook();
            if(newBook != null) {
                books.add(newBook);
                booksTable.refresh();
            }
        });
    }

    public void updateAction(ActionEvent actionEvent) throws BookstoreException {
        Book selectedBook = (Book) booksTable.getSelectionModel().getSelectedItem();
        if(selectedBook == null) return;
        AoUController aouController = new AoUController("Update", authors, categories, selectedBook);
        Stage newStage = openWindow("aoubook", "Add", aouController, actionEvent);

        newStage.setOnHiding(event -> {
            Book updatedBook = aouController.getBook();
            if(updatedBook != null) {
                books.set(books.indexOf(selectedBook), updatedBook);
                booksTable.refresh();
            }
        });
    }

    public void deleteAction(ActionEvent actionEvent) {
        Book selectedBook = (Book) booksTable.getSelectionModel().getSelectedItem();
        if(selectedBook != null) {
            try {
                new BookDaoSQLImpl().delete(selectedBook.getId());
                books.remove(selectedBook);
                booksTable.refresh();
            } catch (BookstoreException e) {
                System.out.println("Error while deleting the book."); // change
            }
        }
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

    private Stage openWindow(String fxmlFileName, String title, Object controller, ActionEvent actionEvent) throws BookstoreException {
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
        return newStage;
    }
}