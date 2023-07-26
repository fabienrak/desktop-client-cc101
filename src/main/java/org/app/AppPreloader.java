package org.app;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppPreloader extends Preloader {
    private Stage preloaderStage;
    @Override
    public void start(Stage stage) throws Exception {
        preloaderStage = stage;
        Parent loader_root = FXMLLoader.load(getClass().getResource("/fxml/splash.fxml"));
        Scene scene = new Scene(loader_root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
        if (stateChangeNotification.getType() == StateChangeNotification.Type.BEFORE_START){
            preloaderStage.hide();
        }
    }
}
