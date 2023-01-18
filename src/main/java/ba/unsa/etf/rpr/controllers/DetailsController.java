package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DetailsController {
    public Label pageLabel;
    public VBox detailsBox;
    private Book book = null;
    private Author author = null;
    private String pageTitle;

    public DetailsController(Object object) {
        if (object instanceof Book) {
            this.book = (Book) object;
            this.pageTitle = "Book Details";
        } else if (object instanceof Author) {
            author = (Author) object;
            this.pageTitle = "Author Details";
        }
    }
    @FXML
    public void initialize() {
        pageLabel.setText(pageTitle);
        generateDetails();
    }
    public void closeAction(ActionEvent actionEvent) {
        Stage stage = (Stage) pageLabel.getScene().getWindow();
        stage.close();
    }

    private void generateDetails() {
        if (book != null) {
            detailsBox.getChildren().add(new Label("Title: " + book.getTitle()));
            detailsBox.getChildren().add(new Label("Author: " + book.getAuthor().getName()));
            detailsBox.getChildren().add(new Label("Published: " + book.getPublished()));
            detailsBox.getChildren().add(new Label("Price: " + book.getPrice() + "$"));
            detailsBox.getChildren().add(new Label("Category: " + book.getCategory().getName()));
        } else {

        }
        for(Node detailsText : detailsBox.getChildren()) {
            detailsText.getStyleClass().add("details-text");
        }
    }
}
