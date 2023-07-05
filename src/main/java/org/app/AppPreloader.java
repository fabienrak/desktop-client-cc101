package org.app;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;

public class AppPreloader extends Preloader {

    private Stage preloaderStage;
    private Label progress;
    private Scene scene;

    @Override
    public void init() {
        Platform.runLater(() -> {
            Label label = new Label("CHARGEMENT EN COUR ....");
            label.setTextAlignment(TextAlignment.CENTER);
            progress = new Label("0%");

            VBox vbox = new VBox(label, progress);
            vbox.setAlignment(Pos.CENTER);
            scene = new Scene(vbox, 400,400);
        });
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.preloaderStage = stage;
        /*
        VBox loading = new VBox(20);
        loading.setMaxWidth(Region.USE_PREF_SIZE);
        loading.setMaxHeight(Region.USE_PREF_SIZE);
        loading.getChildren().add(new ProgressBar());
        loading.getChildren().add(new Label("Chargement en cour ..."));

        BorderPane root = new BorderPane(loading);
        Scene scene = new Scene(root);

        preloaderStage.setWidth(800);
        preloaderStage.setHeight(600);*/

        preloaderStage.setScene(scene);
        preloaderStage.show();

        /*
        Connection connection = DatabaseConnection.getConnection();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/auth/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(this.getClass().getResource("/css/style.css").toExternalForm());
        stage.setTitle("COMBAT CLUB 101");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();*/
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification preloaderNotification) {
        if (preloaderNotification instanceof ProgressNotification){
            progress.setText(((ProgressNotification) preloaderNotification).getProgress() + "  %");
        }
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
        /*if (stateChangeNotification.getType() == StateChangeNotification.Type.BEFORE_START){
            preloaderStage.hide();
        }*/
        StateChangeNotification.Type type = stateChangeNotification.getType();
        switch (type) {
            case BEFORE_LOAD:
                // Called after MyPreloader#start is called.
                //System.out.println(App.launch() + "BEFORE_LOAD");
                break;
            case BEFORE_INIT:
                // Called before MyApplication#init is called.
                //System.out.println(MyApplication.STEP() + "BEFORE_INIT");
                break;
            case BEFORE_START:
                // Called after MyApplication#init and before MyApplication#start is called.
                //System.out.println(MyApplication.STEP() + "BEFORE_START");
                preloaderStage.hide();
                break;
        }
    }
}
