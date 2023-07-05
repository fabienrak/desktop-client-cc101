package org.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.app.utils.DatabaseConnection;

import java.sql.Connection;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Connection connection = DatabaseConnection.getConnection();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/auth/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(this.getClass().getResource("/css/style.css").toExternalForm());
        stage.setTitle("COMBAT CLUB 101");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
