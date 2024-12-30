package org.but.feec.javafx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.but.feec.javafx.api.InjectionView;
import org.but.feec.javafx.data.LibRepository;
import org.but.feec.javafx.services.LibService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InjectionController {
    private static final Logger logger = LoggerFactory.getLogger(LibCreateController.class);
    @FXML
    private TableColumn<InjectionView, Long> id;
    @FXML
    private TableColumn<InjectionView, String> username;
    @FXML
    private TableColumn<InjectionView, String> first_name;
    @FXML
    private TableColumn<InjectionView, String> last_name;

    @FXML
    private TableView<InjectionView> systemPersonsTableView;
    @FXML
    private TextField inputField;

    private LibService libService;
    private LibRepository libRepository;

    public Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        libRepository = new LibRepository();
        libService = new LibService(libRepository);
//        GlyphsDude.setIcon(exitMenuItem, FontAwesomeIcon.CLOSE, "1em");

        id.setCellValueFactory(new PropertyValueFactory<InjectionView, Long>("id"));
        username.setCellValueFactory(new PropertyValueFactory<InjectionView, String>("username"));
        first_name.setCellValueFactory(new PropertyValueFactory<InjectionView, String>("first_name"));
        last_name.setCellValueFactory(new PropertyValueFactory<InjectionView, String>("last_name"));

        logger.info("InjectionController initialized");

    }
    private ObservableList<InjectionView> initializePersonsData() {

        String input = inputField.getText();
        List<InjectionView> persons = libService.getInjectionView(input);
        return FXCollections.observableArrayList(persons);
    }
    public void handleConfirmButton(ActionEvent actionEvent){
        ObservableList<InjectionView> observablePersonList = initializePersonsData();
        systemPersonsTableView.setItems(observablePersonList);

    }


}