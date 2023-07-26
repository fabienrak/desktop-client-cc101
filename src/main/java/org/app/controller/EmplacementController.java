package org.app.controller;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.app.model.Emplacement;
import org.app.services.EmplacementService;
import org.app.utils.DatabaseConnection;
import org.app.utils.Utils;
import org.controlsfx.control.MaskerPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmplacementController implements Initializable {
    @FXML
    private Button btn_enregistrer_emplacement;
    @FXML
    private TextField txt_nom_emplacement;
    @FXML
    private ComboBox combobox_couleur_tatami;
    @FXML
    private TableView tableview_emplacement;
    @FXML
    private TableColumn<Emplacement, Integer> col_id_emplacement;
    @FXML
    private TableColumn<Emplacement, String> col_nom_emplacement;
    @FXML
    private TableColumn<Emplacement, String> col_couleur_tatami;
    @FXML
    private MaskerPane masker_tableview;
    Utils appUtils = new Utils();
    EmplacementService emplacementService = new EmplacementService();

    public Boolean handleValidateForm(){
        if(txt_nom_emplacement.getText().isEmpty() || combobox_couleur_tatami.getItems().isEmpty()){
            appUtils.warningAlertDialog("AVERTISSEMENT","Veuillez completer tous les champs");
            return false;
        }
        return true;
    }
    @FXML
    private void handleAddEmplacement(){
        if (handleValidateForm()){
            Emplacement nouvelle_emplacement = new Emplacement(
                    txt_nom_emplacement.getText().trim(),
                    combobox_couleur_tatami.getValue().toString()
            );

            Service<Boolean> addNewEmplacementService = emplacementService.addNewEmplacement(nouvelle_emplacement);
            addNewEmplacementService.setOnSucceeded(onSucceeded -> {
                if (addNewEmplacementService.getValue()){
                    Service<Integer> getLastIdEmplacementService = emplacementService.getLastEmplacementId();
                    getLastIdEmplacementService.setOnSucceeded((t) -> {
                        nouvelle_emplacement.setId_emplacement(getLastIdEmplacementService.getValue());
                        tableview_emplacement.getItems().add(nouvelle_emplacement);
                        appUtils.successAlertDialog("SUCCESS","Emplacement "+nouvelle_emplacement.getNom_emplacement()+" ajouter");
                    });
                    getLastIdEmplacementService.start();
                }
            });
            addNewEmplacementService.start();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_id_emplacement.setCellValueFactory(new PropertyValueFactory<>("id_emplacement"));
        col_nom_emplacement.setCellValueFactory(new PropertyValueFactory<>("nom_emplacement"));
        col_couleur_tatami.setCellValueFactory(new PropertyValueFactory<>("couleur_tatami"));

        Platform.runLater(() -> {
            combobox_couleur_tatami.getItems().clear();
            combobox_couleur_tatami.getItems().addAll(
                    "ROUGE","BLEU","VERT","JAUNE","MARRON","BLANC"
            );
            masker_tableview.setDisable(false);
            masker_tableview.toFront();

            Service<List<Emplacement>> getListEmplacementData = emplacementService.getEmplacementDataService();
            getListEmplacementData.setOnSucceeded(setOnSucceeded -> {
                tableview_emplacement.getItems().setAll(getListEmplacementData.getValue());
                masker_tableview.setDisable(true);
                masker_tableview.toBack();
            });
            getListEmplacementData.start();
        });
    }
}
