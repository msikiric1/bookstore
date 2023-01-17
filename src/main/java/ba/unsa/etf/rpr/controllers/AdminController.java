package ba.unsa.etf.rpr.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class AdminController {
    public TableView booksTable;
    public TableColumn booksColId;
    public TableColumn booksColTitle;
    public TableColumn booksColAuthorId;
    public TableColumn booksColPublished;
    public TableColumn booksColPrice;
    public TableColumn booksColCategoryId;
    public Button logoutBtn;
    public Button closeBtn;
    public Button deleteBookBtn;
    public Button viewCategoriesBtn;
    public Button viewAuthorsBtn;
    public Label usernameLabel;
    private String username;

    public AdminController(String username) {
        this.username = username;
    }

    @FXML
    public void initialize() {
        usernameLabel.setText(usernameLabel.getText() + username);
    }


    public void logoutAction(ActionEvent actionEvent) {

    }

    public void closeAction(ActionEvent actionEvent) {
        Stage stage = (Stage) usernameLabel.getScene().getWindow();
        stage.close();
    }

    public void viewCategoriesAction(ActionEvent actionEvent) {
    }

    public void viewAuthorsAction(ActionEvent actionEvent) {
    }


}
