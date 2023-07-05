package org.app.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.app.model.Club;
import org.app.utils.DatabaseConnection;
import org.app.utils.Utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClubController {

    @FXML
    private Button btn_ajout_club;
    @FXML
    private TextField txt_nom_club;
    @FXML
    private TextField txt_adresse_club;
    @FXML
    private Button btn_enregistrer_club;
    @FXML
    private Button btn_annuler_ajout_club;
    @FXML
    private Button btn_load_list;
    @FXML
    private TableView club_tableview;
    @FXML
    private TableColumn<Club, StringProperty> col_id;
    @FXML
    private TableColumn<Club, StringProperty> col_nom;
    @FXML
    private TableColumn<Club, StringProperty> col_adresse;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();
    Utils appUtils = new Utils();

    /**
     * Formulaire Ajout Club
     * @throws IOException
     */
    @FXML
    private void showAddClubForm() throws IOException {
        Stage parentStage = (Stage) btn_ajout_club.getScene().getWindow();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(
                ClubController.class.getResource("/fxml/club/ajout-club.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("AJOUT CLUB");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.initOwner(parentStage);
        stage.show();
    }

    /**
     * Enregistrer nouveau Club
     */
    @FXML
    public void ajoutClub() {
        String ajout_club = "INSERT INTO club (nom_club, adresse_club) VALUES (?,?)";
        if (handleValidateForm() == true){
            try{
                preparedStatement = connection.prepareStatement(ajout_club);
                preparedStatement.setString(1, txt_nom_club.getText());
                preparedStatement.setString(2, txt_adresse_club.getText());

                if(preparedStatement.executeUpdate() == 0){
                    appUtils.erreurAlertDialog("ERREUR","ERREUR D INSERTION, VEUILLEZ VERIFIER SVP");
                } else {
                    appUtils.successAlertDialog("SUCCESS","AJOUT CLUB REUSSI");
                    txt_nom_club.clear();
                    txt_adresse_club.clear();
                    closeAddClubForm();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Fermeture Stage ajout club
     */
    @FXML
    public void closeAddClubForm(){
        Stage add_club_stage = (Stage) btn_annuler_ajout_club.getScene().getWindow();
        add_club_stage.close();
    }

    /**
     * Validation formulaire nouveau club
     * @return boolean
     */
    public boolean handleValidateForm(){

        if (txt_nom_club.getText().isEmpty() || txt_adresse_club.getText().isEmpty()){
            appUtils.warningAlertDialog("Avertissement","VEUILLEZ COMPLETEZ TOUS LES CHAMPS");
            return false;
        }

        return true;
    }


    /**
     * Recuperation liste Club
     * @return Liste Club
     */
    public ObservableList<Club> getListClub(){
        ObservableList<Club>  list_club = FXCollections.observableArrayList();
        String liste_club_query = "SELECT * FROM club";
        try{
            preparedStatement = connection.prepareStatement(liste_club_query);
            resultSet  = preparedStatement.executeQuery();
            while (resultSet.next()){
                Club club = new Club();
                club.setId_club(resultSet.getInt("id_clb"));
                club.setNom_club(resultSet.getString("nom_club"));
                club.setAdresse_club(resultSet.getString("adresse_club"));
                list_club.add(club);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list_club;
    }

    /**
     * Remplissage tableview club
     */
    @FXML
    public void afficheListeClub(){
        ObservableList<Club> data_club = getListClub();
        col_id.setCellValueFactory(new PropertyValueFactory<>("id_club"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom_club"));
        col_adresse.setCellValueFactory(new PropertyValueFactory<>("adresse_club"));
        club_tableview.setItems(data_club);
    }
}
