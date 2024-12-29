package org.but.feec.javafx.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.but.feec.javafx.api.LibEditView;
import org.but.feec.javafx.data.LibRepository;
import org.but.feec.javafx.api.LibBasicView;
import org.but.feec.javafx.services.LibService;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class LibEditController {

    private static final Logger logger = LoggerFactory.getLogger(LibEditController.class);

    @FXML
    public Button editPersonButton;
    @FXML
    public TextField idTextField;
    @FXML
    private TextField isbnTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField author_nameTextField;
    @FXML
    private TextField genreTextField;

    private LibService libService;
    private LibRepository libRepository;
    private ValidationSupport validation;

    // used to reference the stage and to get passed data through it
    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        libRepository = new LibRepository();
        libService = new LibService(libRepository);

        validation = new ValidationSupport();
        validation.registerValidator(idTextField, Validator.createEmptyValidator("The id must not be empty."));
        idTextField.setEditable(false);
        validation.registerValidator(isbnTextField, Validator.createEmptyValidator("The isbn must not be empty."));
        validation.registerValidator(titleTextField, Validator.createEmptyValidator("The title must not be empty."));
        validation.registerValidator(author_nameTextField, Validator.createEmptyValidator("The author name must not be empty."));
        validation.registerValidator(genreTextField, Validator.createEmptyValidator("The genre must not be empty."));

        editPersonButton.disableProperty().bind(validation.invalidProperty());

        loadPersonsData();

        logger.info("PersonsEditController initialized");
    }

    /**
     * Load passed data from Persons controller. Check this tutorial explaining how to pass the data between controllers: https://dev.to/devtony101/javafx-3-ways-of-passing-information-between-scenes-1bm8
     */
    private void loadPersonsData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof LibBasicView) {
            LibBasicView libBasicView = (LibBasicView) stage.getUserData();
            idTextField.setText(String.valueOf(libBasicView.getId()));
            isbnTextField.setText(String.valueOf(libBasicView.getIsbn()));
            titleTextField.setText(libBasicView.getTitle());
            author_nameTextField.setText(libBasicView.getAuthor_name());
            genreTextField.setText(libBasicView.getGenre());
        }
    }

    @FXML
    public void handleEditPersonButton(ActionEvent event) {
        // can be written easier, its just for better explanation here on so many lines
        Long id = Long.valueOf(idTextField.getText());
        Long isbn = Long.valueOf(isbnTextField.getText());
        String title = titleTextField.getText();
        String author_name = author_nameTextField.getText();
        String publisher = genreTextField.getText();

        LibEditView libEditView = new LibEditView();
        libEditView.setId(id);
        libEditView.setIsbn(isbn);
        libEditView.setTitle(title);
        libEditView.setAuthor_name(author_name);
        libEditView.setPublisher(publisher);

        libService.editPerson(libEditView);

        personEditedConfirmationDialog();
    }

    private void personEditedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Book Edited Confirmation");
        alert.setHeaderText("Your book was successfully edited.");

        Timeline idlestage = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alert.setResult(ButtonType.CANCEL);
                alert.hide();
            }
        }));
        idlestage.setCycleCount(1);
        idlestage.play();
        Optional<ButtonType> result = alert.showAndWait();
    }

}
