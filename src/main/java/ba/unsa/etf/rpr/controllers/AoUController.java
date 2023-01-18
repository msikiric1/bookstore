package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Category;
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
        validate(titleField, publishedPicker);
        book.setTitle();
        try {

        } catch() {

        }
    }

    public void cancelAction(ActionEvent actionEvent) {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }


}
