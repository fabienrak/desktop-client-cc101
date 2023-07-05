package org.app.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.app.model.Emplacement;
import org.app.utils.DatabaseConnection;
import org.app.utils.Utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmplacementController {

    @FXML
    private Button btn_ajout_emplacement;
    @FXML
    private Button btn_enregistrer_emplacement;
    @FXML
    private Button btn_annuler_ajout_emplacement;
    @FXML
    private TextField txt_nom_emplacement;
    @FXML
    private ComboBox combobox_couleur_tatami;
    @FXML
    private Button btn_load_list_emplacement;
    @FXML
    private TableView tableview_emplacement;
    @FXML
    private TableColumn<Emplacement, IntegerProperty> col_id_emplacement;
    @FXML
    private TableColumn<Emplacement, StringProperty> col_nom_emplacement;
    @FXML
    private TableColumn<Emplacement, StringProperty> col_couleur_tatami;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();
    Utils appUtils = new Utils();

    @FXML
    private void showAddEmplacementForm(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/emplacement/ajout-emplacement.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("AJOUT EMPLACEMENT COMBAT");
        stage.initModality(Modality.WINDOW_MODAL);
        EmplacementController controller = loader.getController();
        controller.populateComboBox();
        stage.setResizable(false);
        stage.initOwner(
                ((Node)actionEvent.getSource()).getScene().getWindow() );
        stage.show();
    }

    /**
     * Enregistrement emplacement
     */
    public void enregistrerEmplacement(){
        String ajout_emplacement_query = "INSERT INTO emplacement (nom_emplacement, couleur_tatami) VALUES (?,?)";
        try{
            preparedStatement = connection.prepareStatement(ajout_emplacement_query);
            preparedStatement.setString(1,txt_nom_emplacement.getText());
            preparedStatement.setString(2,combobox_couleur_tatami.getValue().toString());
            if(preparedStatement.executeUpdate() == 0){
                appUtils.erreurAlertDialog("ERREUR","ERREUR D INSERTION, VEUILLEZ VERIFIER SVP");
            } else {
                appUtils.successAlertDialog("SUCCESS","AJOUT NOUVEAU EMPLACEMENT REUSSI");
                txt_nom_emplacement.clear();
                combobox_couleur_tatami.getItems().clear();
                fermerAjoutEmplacement();
            }
        } catch (SQLException e) {
            Logger.getLogger(EmplacementController.class.getName()).log(Level.WARNING, "ERREUR",e);
        }
    }

    /**
     * Charger liste emplacement
     */
    public ObservableList<Emplacement> getListEmplacement(){
        ObservableList<Emplacement> liste_emplacement = FXCollections.observableArrayList();
        String liste_emplacement_query = "SELECT * FROM emplacement";
        try{
            preparedStatement = connection.prepareStatement(liste_emplacement_query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Emplacement emplacement = new Emplacement();
                emplacement.setId_emplacement(resultSet.getInt("id_tatami"));
                emplacement.setNom_emplacement(resultSet.getString("nom_emplacement"));
                emplacement.setCouleur_tatami(resultSet.getString("couleur_tatami"));

                liste_emplacement.add(emplacement);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return liste_emplacement;
    }

    /**
     * Affichage tableview
     */
    public void getListeEmplacement(){
        ObservableList<Emplacement> liste_emplacement = getListEmplacement();
        if (liste_emplacement.size() == 0){
            appUtils.warningAlertDialog("AVERTISSEMENT","AUCUNE DONNEES");
        } else {
            col_id_emplacement.setCellValueFactory(new PropertyValueFactory<>("id_emplacement"));
            col_nom_emplacement.setCellValueFactory(new PropertyValueFactory<>("nom_emplacement"));
            col_couleur_tatami.setCellValueFactory(new PropertyValueFactory<>("couleur_tatami"));
            tableview_emplacement.setItems(liste_emplacement);
        }
    }

    /**
     * Fermer formulaire ajout emplacement
     */
    @FXML
    public void fermerAjoutEmplacement(){
        Stage add_club_stage = (Stage) btn_annuler_ajout_emplacement.getScene().getWindow();
        add_club_stage.close();
    }

    public void populateComboBox(){
        combobox_couleur_tatami.getItems().clear();
        combobox_couleur_tatami.getItems().addAll(
                "ROUGE","BLEU","VERT","JAUNE","MARRON","BLANC"
        );
    }

}
