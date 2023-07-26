package org.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.app.controller.InitPreloader;
import org.app.utils.DatabaseConnection;

import java.sql.Connection;

public class App extends Application {

    private static Stage primaryStage = null;
    public static Scene primaryScene = null;

    @Override
    public void init() {
        InitPreloader initPreloader = new InitPreloader();
        initPreloader.checkFunction();
    }

    public static void main(String[] args) {
        System.setProperty("javafx.preloader", AppPreloader.class.getCanonicalName());
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        App.primaryStage = stage;
        stage.setX(200);
        stage.setY(200);
    }
}
