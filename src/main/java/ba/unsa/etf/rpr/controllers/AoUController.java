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
    public Label errorMsgLabel;

    // managers
    private final WindowManager windowManager = new WindowManager();
    private final BookManager bookManager = new BookManager();

    private final String operation;
    private final List<Author> authors;
    private final List<Category> categories;
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

        this.operation = operation.toLowerCase();
        this.authors = authors;
        this.categories = categories;
        this.book = book;
    }

    @FXML
    public void initialize() {
        pageLabel.setText(operation + " a book");
        showBook(book);
    }

    /**
     * Event handler for the submit button
     * @param actionEvent
     */
    public void submitAction(ActionEvent actionEvent) {
        setBook(titleField.getText(), authorCbox.getSelectionModel().getSelectedItem(),
                publishedPicker.getValue(), priceSpinner.getValue(),
                categoryCbox.getSelectionModel().getSelectedItem());

        try {
            bookManager.validate(book);
            if(operation.equals("add")) bookManager.add(book);
            else {
                book.setId(book.getId());
                bookManager.update(book);
            }

            windowManager.closeWindow(actionEvent);
        } catch(BookstoreException e) {
            errorMsgLabel.setVisible(true);
            errorMsgLabel.setText(e.getMessage());
        }
    }

    /**
     * Event handler for the cancel button
     * @param actionEvent
     */
    public void cancelAction(ActionEvent actionEvent) { windowManager.closeWindow(actionEvent);     }

    /**
     * Sets the private book attribute
     * @param title book title
     * @param author book author
     * @param published book publish date
     * @param price book price
     * @param category book category
     */
    private void setBook(String title, Author author, LocalDate published, Double price, Category category) {
        if(book == null) book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublished(published);
        book.setPrice(price);
        book.setCategory(category);
    }

    /**
     * Gets the private book attribute
     * @return book
     */
    public Book getBook() {
        return book;
    }

    /**
     * Displays the selected book details or sets default values on the aou book window
     * @param book book that will be displayed
     */
    private void showBook(Book book) {
        authorCbox.setItems(FXCollections.observableList(authors));
        categoryCbox.setItems(FXCollections.observableList(categories));

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
