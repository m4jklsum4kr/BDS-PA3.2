package org.but.feec.javafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.but.feec.javafx.api.LibDetailView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibDetailViewController {

    private static final Logger logger = LoggerFactory.getLogger(LibDetailViewController.class);

    @FXML
    private TextField idTextField;

    @FXML
    private TextField isbnTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField author_nameTextField;

    @FXML
    private TextField genreTextField;

    @FXML
    private TextField publisherTextField;

    @FXML
    private TextField publication_dateTextField;

    // used to reference the stage and to get passed data through it
    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        idTextField.setEditable(false);
        isbnTextField.setEditable(false);
        titleTextField.setEditable(false);
        author_nameTextField.setEditable(false);
        genreTextField.setEditable(false);
        publisherTextField.setEditable(false);
        publication_dateTextField.setEditable(false);

        loadPersonsData();

        logger.info("LibDetailViewController initialized");
    }


    private void loadPersonsData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof LibDetailView personBasicView) {
            idTextField.setText(String.valueOf(personBasicView.getId()));
            isbnTextField.setText(String.valueOf(personBasicView.getIsbn()));
            titleTextField.setText(personBasicView.getTitle());
            author_nameTextField.setText(personBasicView.getAuthor_name());
            genreTextField.setText(personBasicView.getGenre());
            publisherTextField.setText(personBasicView.getPublisher());
        }
    }

}
