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
import org.app.model.TypeMatchModel;
import org.app.services.CompetitionService;
import org.app.services.TypeMatchService;
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
    /**
     * Pour Type match : HOTFIX
     */
    @FXML
    private TableView<TypeMatchModel> type_match_tableview;
    @FXML
    private TableColumn<TypeMatchModel, Integer> col_id_typematch;
    @FXML
    private TableColumn<TypeMatchModel, String> col_id_nom_type_match;
    @FXML
    private TextField txt_type_match;
    @FXML
    private MaskerPane masker_tableview_tm;
    @FXML
    private Button btn_save_type_match;
    Utils appUtils = new Utils();
    TypeMatchService typeMatchService = new TypeMatchService();
    @FXML
    private MaskerPane masker_tableview;
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

    /**
     * TODO : Mettre dans type match controller
     * @param url
     * @param resourceBundle
     */
    /**
     * Ajout type match
     */
    @FXML
    public void ajoutTypeMatch(){
        if (txt_type_match.getText().isEmpty() || txt_type_match.getText().isBlank()){
            appUtils.warningAlertDialog("AVERTISSEMENT","TOUS LES CHAMPS DOIT ETRE COMPLETER");
        } else {
            TypeMatchModel nouveau_type_match = new TypeMatchModel(
                    txt_type_match.getText().trim()
            );
            Service<Boolean> addNewTypeMatch = typeMatchService.addNewTypeMatch(nouveau_type_match);
            addNewTypeMatch.setOnSucceeded(onSucceededEvent -> {
                if (addNewTypeMatch.getValue()){
                    Service<Integer> getLastIdTypeMatch = typeMatchService.getLastTypeMatchId();
                    getLastIdTypeMatch.setOnSucceeded((t) -> {
                        nouveau_type_match.setId(getLastIdTypeMatch.getValue());
                        //nouveau_type_match.setNom_type_match(nouveau_type_match.getNom_type_match());
                        type_match_tableview.getItems().add(nouveau_type_match);
                        appUtils.successAlertDialog("SUCCESS"," "+nouveau_type_match.getNom_type_match()+" BIEN ENREGISTRER");
                        txt_type_match.clear();
                    });
                    getLastIdTypeMatch.start();
                } else {
                    appUtils.erreurAlertDialog("ERREUR","UNE ERREUR EST SURVENUE, VEUILLEZ REESSAYER");
                    txt_type_match.clear();
                }
            });
            addNewTypeMatch.start();
        }
    }

    public void handleDeleteTypeMatchMenu(){
        TypeMatchModel type_a_effacer = type_match_tableview.getSelectionModel().getSelectedItem();
        Service<Boolean> delete_type_match_service = typeMatchService.deleteTypeMatch(type_a_effacer);
        delete_type_match_service.setOnSucceeded((onSuccededEvent) -> {
            if (delete_type_match_service.getValue()){
                type_match_tableview.getItems().remove(type_a_effacer);
                appUtils.successAlertDialog("SUCCESS","TYPE "+type_a_effacer.getNom_type_match()+" EFFACER");

            } else {
                appUtils.erreurAlertDialog("ERREUR","ERREUR LORS DU SUPPRESSION, VEUILLEZ REESSAYER");
            }
        });
        delete_type_match_service.start();
        delete_type_match_service.setOnFailed(onFailed -> {
            delete_type_match_service.getException().printStackTrace();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CompetitionService competitionService = new CompetitionService();

        col_id_competition.setCellValueFactory(new PropertyValueFactory<>("id_competition"));
        col_nom_competition.setCellValueFactory(new PropertyValueFactory<>("nom_competition"));
        col_date_debut_competition.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        col_date_fin_competition.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        col_lieu_competition.setCellValueFactory(new PropertyValueFactory<>("lieu_competition"));

        /**
         * Pour Type Match : HOTFIX
         */
        col_id_typematch.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_id_nom_type_match.setCellValueFactory(new PropertyValueFactory<>("nom_type_match"));

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

            ContextMenu contextMenu = new ContextMenu();
            MenuItem edit_menu = new MenuItem("EDITER");
            MenuItem delete_menu = new MenuItem("EFFACER");
            contextMenu.getItems().addAll(edit_menu, delete_menu);
            masker_tableview_tm.setDisable(false);
            masker_tableview_tm.toFront();
            Service<List<TypeMatchModel>> type_match_service = typeMatchService.getTypeMatchData();
            type_match_service.setOnSucceeded(onSuccededEvent -> {
                type_match_tableview.getItems().addAll(type_match_service.getValue());
                masker_tableview_tm.setDisable(true);
                masker_tableview_tm.toBack();
            });
            type_match_service.start();
            type_match_tableview.setContextMenu(contextMenu);

            delete_menu.setOnAction((event) -> {
                handleDeleteTypeMatchMenu();
            });
        });
    }
}
