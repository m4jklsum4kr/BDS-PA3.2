package org.but.feec.javafx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.but.feec.javafx.App;
import org.but.feec.javafx.api.LibBasicView;
import org.but.feec.javafx.api.LibDetailView;
import org.but.feec.javafx.data.LibRepository;
import org.but.feec.javafx.exceptions.ExceptionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.but.feec.javafx.services.LibService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class LibController {

    private static final Logger logger = LoggerFactory.getLogger(LibController.class);

    @FXML
    public Button addPersonButton;
    @FXML
    public Button refreshButton;
    @FXML
    private TableColumn<LibBasicView, Long> personsId;
    @FXML
    private TableColumn<LibBasicView, Long> isbn;
    @FXML
    private TableColumn<LibBasicView, String> author_name;
    @FXML
    private TableColumn<LibBasicView, String> title;
    @FXML
    private TableColumn<LibBasicView, String> genre;
    @FXML
    private TableColumn<LibBasicView, String> publisher;
    @FXML
    private TableView<LibBasicView> systemPersonsTableView;

    private LibService libService;
    private LibRepository libRepository;

    public LibController() {
    }

//TODO:repo rewrite
    @FXML
    private void initialize() {
        libRepository = new LibRepository();
        libService = new LibService(libRepository);

        personsId.setCellValueFactory(new PropertyValueFactory<LibBasicView, Long>("id"));
        isbn.setCellValueFactory(new PropertyValueFactory<LibBasicView, Long>("isbn"));
        title.setCellValueFactory(new PropertyValueFactory<LibBasicView, String>("title"));
        author_name.setCellValueFactory(new PropertyValueFactory<LibBasicView, String>("Author Name"));
        genre.setCellValueFactory(new PropertyValueFactory<LibBasicView, String>("genre"));
        publisher.setCellValueFactory(new PropertyValueFactory<LibBasicView, String>("Publisher"));


        ObservableList<LibBasicView> observablePersonsList = initializePersonsData();
        systemPersonsTableView.setItems(observablePersonsList);

        systemPersonsTableView.getSortOrder().add(personsId);

        initializeTableViewSelection();
        loadIcons();

        logger.info("LibController initialized");
    }

    private void initializeTableViewSelection() {
        MenuItem edit = new MenuItem("Edit Book");
        MenuItem detailedView = new MenuItem("Detailed Book view");
        edit.setOnAction((ActionEvent event) -> {
            LibBasicView personView = systemPersonsTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/LibEdit.fxml"));
                Stage stage = new Stage();
                stage.setUserData(personView);
                stage.setTitle("BDS Library Edit Book");

                LibEditController controller = new LibEditController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 600, 500);

                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });

        detailedView.setOnAction((ActionEvent event) -> {
            LibBasicView personView = systemPersonsTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/LibDetailView.fxml"));
                Stage stage = new Stage();

                Long personId = personView.getId();
                LibDetailView libDetailView = libService.getPersonDetailView(personId);

                stage.setUserData(libDetailView);
                stage.setTitle("Book Detailed View");

                LibDetailViewController controller = new LibDetailViewController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 600, 500);

                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });


        ContextMenu menu = new ContextMenu();
        menu.getItems().add(edit);
        menu.getItems().addAll(detailedView);
        systemPersonsTableView.setContextMenu(menu);
    }

    private ObservableList<LibBasicView> initializePersonsData() {
        List<LibBasicView> persons = libService.getPersonsBasicView();
        return FXCollections.observableArrayList(persons);
    }

    private void loadIcons() {
        Image vutLogoImage = new Image(App.class.getResourceAsStream("logos/vut-logo-eng.png"));
        ImageView vutLogo = new ImageView(vutLogoImage);
        vutLogo.setFitWidth(150);
        vutLogo.setFitHeight(50);
    }

    public void handleExitMenuItem(ActionEvent event) {
        System.exit(0);
    }

    public void handleAddPersonButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/LibCreate.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 500);
            Stage stage = new Stage();
            stage.setTitle("Library Create Book");
            stage.setScene(scene);

//            Stage stageOld = (Stage) signInButton.getScene().getWindow();
//            stageOld.close();
//
//            stage.getIcons().add(new Image(App.class.getResourceAsStream("logos/vut.jpg")));
//            authConfirmDialog();

            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    public void handleRefreshButton(ActionEvent actionEvent) {
        ObservableList<LibBasicView> observablePersonsList = initializePersonsData();
        systemPersonsTableView.setItems(observablePersonsList);
        systemPersonsTableView.refresh();
        systemPersonsTableView.sort();
    }
}
