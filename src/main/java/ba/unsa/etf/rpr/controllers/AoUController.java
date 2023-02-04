package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.BookManager;
import ba.unsa.etf.rpr.business.WindowManager;
import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Category;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller for the add/update a book page
 * @author Muaz Sikiric
 */
public class AoUController {
    // components
    public Label pageLabel;
    public Spinner<Double> priceSpinner;
    public TextField titleField;
    public DatePicker publishedPicker;
    public ComboBox<Author> authorCbox;
    public ComboBox<Category> categoryCbox;
    public Button submitBtn;
    public Label errorMsgLabel;

    // managers
    private final WindowManager wm = new WindowManager();
    private final BookManager bookManager = new BookManager();

    private String operation;
    private List<Author> authors;
    private List<Category> categories;
    private Book book;

    /**
     * Constructor
     * @param operation string that can represent "add" or "update" operation
     * @param authors list of authors retrieved from the database
     * @param categories list of categories retrieved from the database
     * @param book book to add/update
     */
    public AoUController(String operation, List<Author> authors, List<Category> categories, Book book) throws BookstoreException {
        if(!operation.equalsIgnoreCase("add") && !operation.equalsIgnoreCase("update"))
            throw new BookstoreException("Invalid operation.");

        this.operation = operation;
        this.authors = authors;
        this.categories = categories;
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    @FXML
    public void initialize() {
        pageLabel.setText(operation + " a book");
        authorCbox.setItems(FXCollections.observableList(authors));
        categoryCbox.setItems(FXCollections.observableList(categories));

        showBook(book);
    }

    /**
     * Event handler for the submit button
     * @param actionEvent
     */
    public void submitAction(ActionEvent actionEvent) {
        try {
            bookManager.validate(titleField.getText(), publishedPicker.getValue());
        } catch (BookstoreException e) {
            errorMsgLabel.setVisible(true);
            errorMsgLabel.setText(e.getMessage());
            return;
        }

        setBook(titleField.getText(), authorCbox.getSelectionModel().getSelectedItem(),
                publishedPicker.getValue(), priceSpinner.getValue(),
                categoryCbox.getSelectionModel().getSelectedItem());

        try {
            if(operation.equalsIgnoreCase("add"))
                bookManager.add(book);
            else if(operation.equalsIgnoreCase("update"))
                bookManager.update(book);

            wm.closeWindow(actionEvent);
        } catch(BookstoreException e) {
            errorMsgLabel.setVisible(true);
            errorMsgLabel.setText("There was an error while adding/updating a book.");
        }
    }

    /**
     * Event handler for the cancel button
     * @param actionEvent
     */
    public void cancelAction(ActionEvent actionEvent) {
        wm.closeWindow(actionEvent);
    }

    private void setBook(String title, Author author, LocalDate published, Double price, Category category) {
        book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublished(published);
        book.setPrice(price);
        book.setCategory(category);
    }

    private void showBook(Book book) {
        if(book == null) {
            authorCbox.getSelectionModel().selectFirst();
            publishedPicker.setValue(LocalDate.now());
            categoryCbox.getSelectionModel().selectFirst();
        } else {
            titleField.setText(book.getTitle());
            authorCbox.getSelectionModel().select(book.getAuthor());
            publishedPicker.setValue(book.getPublished());
            priceSpinner.getValueFactory().setValue(book.getPrice());
            categoryCbox.getSelectionModel().select(book.getCategory());
        }
    }
}
