package org.app.controller;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.app.model.Competition;
import org.app.services.CompetitionService;
import org.app.utils.DatabaseConnection;
import org.app.utils.Utils;
import org.controlsfx.control.MaskerPane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CompetitionController implements Initializable {

    @FXML
    private Button btn_ajout_competition;
    @FXML
    private Button btn_load_list_competition;
    @FXML
    private TableView competition_tableview;
    @FXML
    private TableColumn<Competition, Integer> col_id_competition;
    @FXML
    private TableColumn<Competition, String> col_nom_competition;
    @FXML
    private TableColumn<Competition, String> col_date_debut_competition;
    @FXML
    private TableColumn<Competition, String> col_date_fin_competition;
    @FXML
    private TableColumn<Competition, String> col_lieu_competition;
    @FXML
    private TextField txt_nom_competition;
    @FXML
    private DatePicker txt_date_debut_competition;
    @FXML
    private DatePicker txt_date_fin_competition;
    @FXML
    private TextField txt_lieu_competition;
    @FXML
    private Button btn_enregistrer_competition;
    @FXML
    private MaskerPane masker_tableview;
    Utils appUtils = new Utils();
    CompetitionService competitionService = new CompetitionService();

    public Boolean handleValidateForm(){
        if(txt_nom_competition.getText().isEmpty() ||
            txt_lieu_competition.getText().isEmpty() ||
            txt_date_debut_competition.getValue() == null ||
            txt_date_fin_competition.getValue() == null
        ){
            appUtils.warningAlertDialog("AVERTISSEMENT","Veuillez completez tous les champs");
            return false;
        }
        return true;
    }

    @FXML
    private void handleAddCompetition(){
        if (handleValidateForm()){
            Competition nouveau_competition = new Competition(
                    txt_nom_competition.getText().trim(),
                    txt_date_debut_competition.getValue().toString().trim(),
                    txt_date_fin_competition.getValue().toString().trim(),
                    txt_lieu_competition.getText().trim()
            );
            Service<Boolean> addNewCompetition = competitionService.addNewCompetition(nouveau_competition);
            addNewCompetition.setOnSucceeded(onSucceeded -> {
                if(addNewCompetition.getValue()){
                    Service<Integer> getLastIdCompetitionService = competitionService.getLastCompetitionId();
                    getLastIdCompetitionService.setOnSucceeded((t) -> {
                        nouveau_competition.setId_competition(getLastIdCompetitionService.getValue());
                        competition_tableview.getItems().add(nouveau_competition);
                        appUtils.successAlertDialog("SUCCESS","Competition " + nouveau_competition.getNom_competition() + " ajouter");
                        txt_nom_competition.clear();
                        txt_lieu_competition.clear();
                        txt_date_debut_competition.getEditor().clear();
                        txt_date_fin_competition.getEditor().clear();
                    });
                    getLastIdCompetitionService.start();
                }
            });
            addNewCompetition.start();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CompetitionService competitionService = new CompetitionService();

        col_id_competition.setCellValueFactory(new PropertyValueFactory<>("id_competition"));
        col_nom_competition.setCellValueFactory(new PropertyValueFactory<>("nom_competition"));
        col_date_debut_competition.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        col_date_fin_competition.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        col_lieu_competition.setCellValueFactory(new PropertyValueFactory<>("lieu_competition"));

        Platform.runLater(() -> {
            masker_tableview.setDisable(false);
            masker_tableview.toFront();
            Service<List<Competition>> competition_service = competitionService.getCompetitionData();
            competition_service.setOnSucceeded(onSucceeded -> {
                competition_tableview.getItems().setAll(competition_service.getValue());
                masker_tableview.setDisable(true);
                masker_tableview.toBack();
            });
            competition_service.start();
        });
    }
}
