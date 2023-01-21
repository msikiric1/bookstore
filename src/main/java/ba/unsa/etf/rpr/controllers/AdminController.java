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
import java.util.List;

/**
 *
 * @author Muaz Sikiric
 */
public class AdminController {
    public TableView booksTable;
    public TableColumn booksColId;
    public TableColumn booksColTitle;
    public TableColumn booksColPublished;
    public TableColumn booksColPrice;
    public Label usernameLabel;
    public Label infoLabel;
    private List<Book> books;
    private List<Author> authors;
    private List<Category> categories;
    private String username;
    private final WindowManager wm = new WindowManager();
    private final BookManager bookManager = new BookManager();
    private final AuthorManager authorManager = new AuthorManager();
    private final CategoryManager categoryManager = new CategoryManager();

    public AdminController(List<Book> books, List<Author> authors, List<Category> categories, String username) {
        if(books == null || authors == null || categories == null) {
            try {
                this.books = FXCollections.observableArrayList(bookManager.getAll());
                this.authors = FXCollections.observableArrayList(authorManager.getAll());
                this.categories = FXCollections.observableArrayList(categoryManager.getAll());
            } catch(BookstoreException e) {
                System.out.println("Something's wrong with retrieving data from tables");
                throw new RuntimeException(e);
            }
        } else {
            this.books = books;
            this.authors = authors;
            this.categories = categories;
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
        booksTable.setItems(FXCollections.observableArrayList(books));
    }


    public void logoutAction(ActionEvent actionEvent) throws BookstoreException {
        wm.changeWindow("login", "Login", new LoginController(), actionEvent);
    }

    public void closeAction(ActionEvent actionEvent) {
        wm.closeWindow(actionEvent);
    }

    public void viewACAction(ActionEvent actionEvent) throws BookstoreException {
        AuthorCategoryController acController = new AuthorCategoryController(authors, categories, books, username);
        wm.changeWindow("authorcategory", "Authors & Categories", acController, actionEvent);
    }

    public void addAction(ActionEvent actionEvent) throws BookstoreException {
        AoUController aouController = new AoUController("Add", authors, categories, new Book());
        Stage newStage = wm.openWindow("aoubook", "Add", aouController, actionEvent);

        newStage.setOnHiding(event -> {
            Book newBook = aouController.getBook();
            if(newBook != null) {
                books.add(newBook);
                booksTable.refresh();
                infoLabel.setText("Info: Added a new book successfully.");
            }
        });
    }

    public void updateAction(ActionEvent actionEvent) throws BookstoreException {
        Book selectedBook = (Book) booksTable.getSelectionModel().getSelectedItem();
        if(selectedBook == null) {
            infoLabel.setText("Info: You need to select a book that you want to update.");
            return;
        }
        AoUController aouController = new AoUController("Update", authors, categories, selectedBook);
        Stage newStage = wm.openWindow("aoubook", "Add", aouController, actionEvent);

        newStage.setOnHiding(event -> {
            Book updatedBook = aouController.getBook();
            if(updatedBook != null) {
                books.set(books.indexOf(selectedBook), updatedBook);
                booksTable.refresh();
                infoLabel.setText("Info: Updated a new book successfully.");
            }
        });
    }

    public void deleteAction(ActionEvent actionEvent) {
        Book selectedBook = (Book) booksTable.getSelectionModel().getSelectedItem();
        if(selectedBook != null) {
            try {
                bookManager.delete(selectedBook.getId());
                books.remove(selectedBook);
                booksTable.refresh();
                infoLabel.setText("Info: Deleted a book successfully.");
            } catch (BookstoreException e) {
                System.out.println("Error while deleting the book."); // change
            }
        } else {
            infoLabel.setText("Info: You need to select a book that you want to delete.");
        }
    }
}