package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.dao.BookDao;
import ba.unsa.etf.rpr.dao.BookDaoSQLImpl;
import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Category;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AoUController {
    public Label pageLabel;
    public Spinner priceSpinner;
    public TextField titleField;
    public DatePicker publishedPicker;
    public ComboBox authorCbox;
    public ComboBox categoryCbox;
    public Button submitBtn;
    public Label errorMsgLabel;
    private String addOrUpdate;
    private ObservableList<Author> authors;
    private ObservableList<Category> categories;
    private Book book;

    public AoUController(String addOrUpdate, ObservableList<Author> authors, ObservableList<Category> categories, Book book) {
        this.addOrUpdate = addOrUpdate;
        this.authors = authors;
        this.categories = categories;
        this.book = book;
    }

    @FXML
    public void initialize() {
        errorMsgLabel.setVisible(false);
        pageLabel.setText(addOrUpdate + titleField.getText());
        authorCbox.getItems().addAll(authors);
        categoryCbox.getItems().addAll(categories);
        submitBtn.setText(addOrUpdate);
        if(addOrUpdate.equalsIgnoreCase("add")) {
            publishedPicker.setValue(LocalDate.now());
            authorCbox.getSelectionModel().selectFirst();
            categoryCbox.getSelectionModel().selectFirst();
        } else if(addOrUpdate.equalsIgnoreCase("update")) {
            if(book != null) {
                titleField.setText(book.getTitle());
                authorCbox.getSelectionModel().select(book.getAuthor());
                publishedPicker.setValue(book.getPublished());
                priceSpinner.getValueFactory().setValue(book.getPrice());
                categoryCbox.getSelectionModel().select(book.getCategory());
            } else {
                System.out.println("Trying to update a book that was not selected.");
            }
        }
    }

    public void submitAction(ActionEvent actionEvent) {
        try {
            validate(titleField, publishedPicker);
        } catch (BookstoreException e) {
            errorMsgLabel.setVisible(true);
            errorMsgLabel.setText(e.getMessage());
            return;
        }
        this.book.setTitle(titleField.getText());
        this.book.setAuthor((Author) authorCbox.getSelectionModel().getSelectedItem());
        this.book.setPublished(publishedPicker.getValue());
        this.book.setPrice((Double) priceSpinner.getValue());
        this.book.setCategory((Category) categoryCbox.getSelectionModel().getSelectedItem());
        try {
            if(addOrUpdate.equalsIgnoreCase("add")) {
                new BookDaoSQLImpl().add(book);
            } else if(addOrUpdate.equalsIgnoreCase("update")) {
                new BookDaoSQLImpl().update(book);
            }
            closeWindow();
        } catch(BookstoreException e) {
            errorMsgLabel.setText("There was an error while adding/updating a book.");
            return;
        }
    }

    public void cancelAction(ActionEvent actionEvent) {
        closeWindow();
    }

    private void validate(TextField titleField, DatePicker publishedPicker) throws BookstoreException {
        if(titleField.getText().length() < 10)
            throw new BookstoreException("Title should be at least 10 characters.");
        if(LocalDate.now().isBefore(publishedPicker.getValue()))
            throw new BookstoreException("Publish date can not be in the future.");
    }

    private void closeWindow() {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    public Book getBook() {
        return book;
    }
}
