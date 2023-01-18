package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Category;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import javafx.collections.FXCollections;
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

    public AoUController(String addOrUpdate, ObservableList<Author> authors, ObservableList<Category> categories) {
        this.addOrUpdate = addOrUpdate;
        this.authors = authors;
        this.categories = categories;
    }

    @FXML
    public void initialize() {
        errorMsgLabel.setVisible(false);
        pageLabel.setText(addOrUpdate + titleField.getText());
        authorCbox.getItems().addAll(authors);
        authorCbox.getSelectionModel().selectFirst();
        categoryCbox.getItems().addAll(categories);
        categoryCbox.getSelectionModel().selectFirst();
        submitBtn.setText(addOrUpdate);
        if(addOrUpdate.equals("Add")) {
            publishedPicker.setValue(LocalDate.now());
        } else {

        }
    }

    public void submitAction(ActionEvent actionEvent) {
        Book book = new Book();
        try {
            validate(titleField, publishedPicker);
        } catch (BookstoreException e) {
            errorMsgLabel.setVisible(true);
            errorMsgLabel.setText(e.getMessage());
            return;
        }
        book.setTitle(titleField.getText());
        book.setAuthor((Author) authorCbox.getValue());
        /*
        try {

        } catch() {

        }
        */
    }

    public void cancelAction(ActionEvent actionEvent) {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    private void validate(TextField titleField, DatePicker publishedPicker) throws BookstoreException {
        if(titleField.getText().length() < 10)
            throw new BookstoreException("Title should be at least 10 characters.");
        if(LocalDate.now().isBefore(publishedPicker.getValue()))
            throw new BookstoreException("Publish date can not be in the future.");
    }
}
