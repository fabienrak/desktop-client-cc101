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
    @FXML
    private TextField username_field;
    @FXML
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
    private void switchToDashboard() throws IOException {
        //if (event.getSource() == btn_login){
            stage = (Stage) btn_login.getScene().getWindow();
            parent = FXMLLoader.load(getClass().getResource("/fxml/home/home.fxml"));
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        //}
    }

    /**
     * Verification utilisateur
     */
    @FXML
    private void verificationManager(){
        String manager_query = "SELECT username, password FROM manager";
        if (verifyInfo() == true){
            try{
                preparedStatement = connection.prepareStatement(manager_query);
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    if (username_field.getText().equals(resultSet.getString("username"))){
                        if (mdp_field.getText().equals(resultSet.getString("password"))){
                            switchToDashboard();
                        } else {
                            appUtils.erreurAlertDialog("ERREUR","Mot de passe non valide");
                        }
                    } else {
                        appUtils.erreurAlertDialog("ERREUR","Nom d'utilisateur non valide");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Validationn formulaire
     * @return boolean
     */
    private boolean verifyInfo() {
        if (username_field.getText() == null || username_field.getText().isEmpty()){
            appUtils.warningAlertDialog("AVERTISSEMENT","Veuillez completer tous les champs");
            return false;
        } else if (mdp_field.getText() == null || mdp_field.getText().isEmpty()) {
            appUtils.warningAlertDialog("AVERTISSEMENT","Veuillez completer tous les champs");
            return false;
        }
        return true;
    }
}
