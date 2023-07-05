package org.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.app.utils.DatabaseConnection;
import org.app.utils.Utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private Button btn_login;
    private Stage stage;
    private Parent parent;
    private TextField username_field;
    private TextField mdp_field;

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();
    Utils appUtils = new Utils();

    /**
     * Passer au dashboard
     *
     * @throws IOException
     */
    @FXML
    private void switchToDashboard(ActionEvent event) throws IOException {
        if (event.getSource() == btn_login){
            stage = (Stage) btn_login.getScene().getWindow();
            parent = FXMLLoader.load(getClass().getResource("/fxml/home/home.fxml"));
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Verification utilisateur
     */
    private void verificationManager(ActionEvent actionEvent){
        String manager_query = "SELECT username, password FROM manager";
        
    }

    /**
     * Validationn formulaire
     * @return boolean
     */
    private boolean verifyInfo() {
        if (username_field.getText() == null || username_field.getText().isEmpty()){
            appUtils.warningAlertDialog("AVERTISSEMENT","");
            return false;
        } else if (mdp_field.getText() == null || mdp_field.getText().isEmpty()) {
            appUtils.warningAlertDialog("AVERTISSEMENT","Champ ");
            return false;
        }
        return true;
    }
}
