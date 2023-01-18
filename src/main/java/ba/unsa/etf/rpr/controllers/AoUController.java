package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AoUController {
    public Label pageLabel;
    public Spinner priceSpinner;
    public TextField titleField;
    public DatePicker publishedPicker;
    public ComboBox authorCbox;
    public ComboBox categoryCbox;
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
        authorCbox = new ComboBox(FXCollections.observableArrayList(authors));
    }

    public void submitAction(ActionEvent actionEvent) {
    }

    public void cancelAction(ActionEvent actionEvent) {
    }
}
