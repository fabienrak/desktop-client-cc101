package org.app.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.app.App;
import org.app.utils.DatabaseConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InitPreloader implements Initializable {

    public Label lbl_process;
    public static Label lbl_loading;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl_loading = lbl_process;
    }

    public String checkFunction(){

        final String[] message = {""};
        Thread thread_function_load_database = new Thread(() -> {
            message[0] = "Connexion a la base de donnee ...";
            Platform.runLater(() -> lbl_loading.setText(message[0]));
            try {
                Thread.sleep(3000);
                Connection connection = DatabaseConnection.getConnection();
                connection.getClientInfo();
                message[0] = "CLIENT : " + connection.getClientInfo();
                //Thread.currentThread();
            } catch (InterruptedException | SQLException e) {
                Logger.getLogger(InitPreloader.class.getName()).log(Level.SEVERE, "ERREUR",e);
            }
        });
    /*
        Thread thread_function_b = new Thread(() -> {
            message[0] = "Fonction Faharoa";
            Platform.runLater(() -> lbl_loading.setText(message[0]));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Logger.getLogger(InitPreloader.class.getName()).log(Level.SEVERE, "ERREUR",e);
            }
        });*/

        Thread thread_function_login = new Thread(() -> {
            message[0] = "Page de connexion ...";
            Platform.runLater(
                () -> {
                    lbl_loading.setText(message[0]);
                    try {
                        Thread.sleep(2000);
                        Stage stage = new Stage();
                        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/auth/login.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        scene.getStylesheets().add(this.getClass().getResource("/css/style.css").toExternalForm());
                        stage.setTitle("COMBAT CLUB 101");
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();
                    } catch (InterruptedException e) {
                        Logger.getLogger(InitPreloader.class.getName()).log(Level.SEVERE, "ERREUR THREAD",e);
                    } catch (IOException e) {
                        Logger.getLogger(InitPreloader.class.getName()).log(Level.SEVERE, "ERREUR IO",e);
                    }
                }
            );
        });

        try {
            thread_function_load_database.start();
            thread_function_load_database.join();
            /*thread_function_b.start();
            thread_function_b.join();*/
            thread_function_login.start();
            thread_function_login.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return message[0];
    }
}
