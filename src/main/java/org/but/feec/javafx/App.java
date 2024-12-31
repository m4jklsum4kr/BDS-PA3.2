package org.but.feec.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.crypto.Data;


public class App extends Application {

    private FXMLLoader loader;
    private VBox mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            loader = new FXMLLoader(getClass().getResource("fxml/Login.fxml"));
            mainStage = loader.load();

            primaryStage.setTitle("Library db :3");
            Scene scene = new Scene(mainStage);
            setUserAgentStylesheet(STYLESHEET_MODENA);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception ex) {
            //       ExceptionHandler.handleException(ex);
        }
    }

}