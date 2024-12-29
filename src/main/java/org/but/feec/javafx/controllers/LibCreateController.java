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
import javafx.util.Duration;
import org.but.feec.javafx.api.LibCreateView;
import org.but.feec.javafx.data.LibRepository;
import org.but.feec.javafx.services.LibService;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class LibCreateController {

    private static final Logger logger = LoggerFactory.getLogger(LibCreateController.class);

    @FXML
    public Button newPersonCreatePerson;
    @FXML
    private TextField newPersonIsbn;

    @FXML
    private TextField newPersonTitle;

    @FXML
    private TextField newPersonPublisher;

    @FXML
    private TextField newPersonAuthorName;

    @FXML
    private TextField newPersonGenre;

    //TODO:service, repo

    private LibService libService;
    private LibRepository libRepository;
    private ValidationSupport validation;

    @FXML
    public void initialize() {
        libRepository = new LibRepository();
        libService = new LibService(libRepository);

        validation = new ValidationSupport();
        validation.registerValidator(newPersonIsbn, Validator.createEmptyValidator("The isbn must not be empty."));
        validation.registerValidator(newPersonTitle, Validator.createEmptyValidator("The title must not be empty."));
        validation.registerValidator(newPersonPublisher, Validator.createEmptyValidator("The publisher must not be empty."));
        validation.registerValidator(newPersonAuthorName, Validator.createEmptyValidator("The author must not be empty."));
        validation.registerValidator(newPersonGenre, Validator.createEmptyValidator("The genre must not be empty."));

        newPersonCreatePerson.disableProperty().bind(validation.invalidProperty());

        logger.info("LibCreateController initialized");
    }

    @FXML
    void handleCreateNewPerson(ActionEvent event) {
        // can be written easier, its just for better explanation here on so many lines
        String isbn = newPersonIsbn.getText();
        String title = newPersonTitle.getText();
        String publisher = newPersonPublisher.getText();
        String author_name = newPersonAuthorName.getText();
        String genre = newPersonGenre.getText();

        LibCreateView libCreateView = new LibCreateView();
        libCreateView.setIsbn(Long.valueOf(isbn));
        libCreateView.setTitle(title);
        libCreateView.setAuthor_name(author_name);
        libCreateView.setGenre(genre);
        libCreateView.setPublisher(publisher);

        libService.createPerson(libCreateView);

        personCreatedConfirmationDialog();
    }

    private void personCreatedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Book Created Confirmation");
        alert.setHeaderText("Your book was successfully created.");

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
