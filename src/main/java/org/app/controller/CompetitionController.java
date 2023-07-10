package org.app.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import org.app.utils.DatabaseConnection;
import org.app.utils.Utils;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CompetitionController {

    @FXML
    private Button btn_ajout_competition;
    @FXML
    private Button btn_load_list_competition;
    @FXML
    private TableView competition_tableview;
    @FXML
    private TableColumn<Competition, IntegerProperty> col_id_competition;
    @FXML
    private TableColumn<Competition, StringProperty> col_nom_competition;
    @FXML
    private TableColumn<Competition, StringProperty> col_date_debut_competition;
    @FXML
    private TableColumn<Competition, StringProperty> col_date_fin_competition;
    @FXML
    private TableColumn<Competition, StringProperty> col_lieu_competition;
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
    private Button btn_annuler_ajout_competition;
    @FXML
    private ProgressBar progressBar;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();
    Utils appUtils = new Utils();

    /**
     * Formulaire ajout competition
     * @throws IOException
     */
    @FXML
    private void showAddCompetitionForm() throws IOException {
        Stage parentStage = (Stage) btn_ajout_competition.getScene().getWindow();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(
                ClubController.class.getResource("/fxml/competition/ajout-competition.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("AJOUT COMPETITION");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.initOwner(parentStage);
        stage.show();
    }

    /**
     * Fermeture fenetre ajout competition
     */
    public void closeAddCompetitionForm(){
        Stage add_club_stage = (Stage) btn_annuler_ajout_competition.getScene().getWindow();
        add_club_stage.close();
    }

    /**
     * Enregistrement nouveau competition
     */
    @FXML
    private void enregistrerCompetition(){
        String ajout_competition_query = "INSERT INTO competition (nom_competition, date_debut, date_fin, lieu_competition) VALUES (?,?,?,?)";
        if(handleValidateCompetitionForm() == true){
            try{
                preparedStatement = connection.prepareStatement(ajout_competition_query);
                preparedStatement.setString(1,txt_nom_competition.getText());
                preparedStatement.setString(2,txt_date_debut_competition.getValue().toString());
                preparedStatement.setString(3,txt_date_fin_competition.getValue().toString());
                preparedStatement.setString(4,txt_lieu_competition.getText());
                if (preparedStatement.executeUpdate() == 0){
                    appUtils.erreurAlertDialog("ERREUR","ERREUR D INSERTION, VEUILLEZ VERIFIER SVP");
                } else {
                    appUtils.successAlertDialog("SUCCESS","AJOUT COMPETITION REUSSI");
                    txt_nom_competition.clear();
                    txt_lieu_competition.clear();
                    closeAddCompetitionForm();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Validation formulaire ajout competition
     * @return boolean
     */
    private boolean handleValidateCompetitionForm(){
        if (txt_nom_competition.getText().isEmpty() ||
                txt_date_debut_competition.getValue() == null ||
                txt_date_fin_competition.getValue() == null  ||
                txt_lieu_competition.getText().isEmpty()
        ){
            appUtils.warningAlertDialog("AVERTISSEMENT","VEUILLEZ COMPLETEZ TOUS LES CHAMPS");
            return false;
        }
        return true;
    }

    /**
     * affiche liste competition dans tableview
     */
    @FXML
    private void afficheListeCompetition() {
        progressBar.setVisible(true);
        Task<ObservableList<Competition>> get_competition_task = new Task<>() {
            @Override
            protected ObservableList<Competition> call() throws InterruptedException {
                final int TIME_MAX = 100;
                ObservableList<Competition> list_competition = FXCollections.observableArrayList();
                String liste_competition_query = "SELECT * FROM competition";
                try {
                    preparedStatement = connection.prepareStatement(liste_competition_query);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        Competition competition = new Competition();
                        competition.setId_competition(resultSet.getInt("id_cpt"));
                        competition.setNom_competition(resultSet.getString("nom_competition"));
                        competition.setDate_debut(resultSet.getString("date_debut"));
                        competition.setDate_fin(resultSet.getString("date_fin"));
                        competition.setLieu_competition(resultSet.getString("lieu_competition"));
                        list_competition.add(competition);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < TIME_MAX; i++) {
                    updateProgress(i, TIME_MAX);
                    Thread.sleep(100);
                }
                return list_competition;
            }
        };

        progressBar.progressProperty().bind(get_competition_task.progressProperty());
        get_competition_task.setOnSucceeded(workerStateEvent -> {
            competition_tableview.getItems().setAll(get_competition_task.getValue());
            progressBar.setVisible(false);
        });

        get_competition_task.setOnFailed(e -> get_competition_task.getException().printStackTrace());
        new Thread(get_competition_task).start();

        col_id_competition.setCellValueFactory(new PropertyValueFactory<>("id_competition"));
        col_nom_competition.setCellValueFactory(new PropertyValueFactory<>("nom_competition"));
        col_date_debut_competition.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        col_date_fin_competition.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        col_lieu_competition.setCellValueFactory(new PropertyValueFactory<>("lieu_competition"));
    }
}
