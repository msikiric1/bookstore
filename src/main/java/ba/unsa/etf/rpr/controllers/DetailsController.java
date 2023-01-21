package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.WindowManager;
import ba.unsa.etf.rpr.domain.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DetailsController {
    public Label titleLabel;
    public Label authorLabel;
    public Label addressLabel;
    public Label contactLabel;
    public Label publishedLabel;
    public Label priceLabel;
    public Label categoryLabel;
    private Book book;
    private WindowManager wm = new WindowManager();

    public DetailsController(Book book) {
        this.book = book;
    }
    @FXML
    public void initialize() {
        generateDetails();
    }
    public void closeAction(ActionEvent actionEvent) {
        wm.closeWindow(actionEvent);
    }

    private void generateDetails() {
        titleLabel.setText("Title: " + book.getTitle());
        authorLabel.setText("Author: " + book.getAuthor().getName());
        addressLabel.setText("Address: " + book.getAuthor().getAddress());
        contactLabel.setText("Contact: " + book.getAuthor().getPhone());
        publishedLabel.setText("Published: " + book.getPublished());
        priceLabel.setText("Price: $" + book.getPrice());
        categoryLabel.setText("Category: " + book.getCategory().getName());
    }
}
