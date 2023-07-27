package org.app.controller;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.app.model.Clubs;
import org.app.services.ClubService;
import org.app.utils.Utils;
import org.controlsfx.control.MaskerPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClubsController implements Initializable {

    @FXML
    private TextField txt_nom_club, txt_adresse_club, txt_search_club;
    @FXML
    private Button btn_save_club, btn_search_club;
    @FXML
    private MaskerPane club_data_loading;
    @FXML
    private TableView<Clubs> tableview_club;
    @FXML
    private TableColumn<Clubs, Integer> col_id_club;
    @FXML
    private TableColumn<Clubs, String> col_nom_club;
    @FXML
    private TableColumn<Clubs, String> col_adresse_club;

    ClubService clubService = new ClubService();
    Utils appUtils = new Utils();

    public boolean handleValidateForm(){
        if (txt_nom_club.getText().isEmpty() || txt_adresse_club.getText().isEmpty()){
            appUtils.warningAlertDialog("AVERTISSEMENT","VEUILLEZ COMPLETEZ TOUS LES CHAMPS");
            return false;
        }
        return true;
    }

    public void clearForm(){
        txt_nom_club.clear();
        txt_adresse_club.clear();
    }

    @FXML
    private void handleAddClubButton(ActionEvent actionEvent){
        if (handleValidateForm()){
            Clubs nouveau_club = new Clubs(
                    txt_nom_club.getText().trim(),
                    txt_adresse_club.getText().trim()
            );
            Service<Boolean> addNewClubService = clubService.addNewClub(nouveau_club);
            addNewClubService.setOnSucceeded(onSucceededEvent -> {
                if (addNewClubService.getValue()){
                    Service<Integer> getLastIdClubService = clubService.getLastIdFromClubTable();
                    getLastIdClubService.setOnSucceeded((t) -> {
                        nouveau_club.setId_club(getLastIdClubService.getValue());
                        tableview_club.getItems().add(nouveau_club);
                        appUtils.successAlertDialog("SUCCESS",nouveau_club.getNom_club() + " Ajouter ");
                        clearForm();
                    });
                    getLastIdClubService.start();
                } else {
                    appUtils.erreurAlertDialog("ERREUR","Erreur lors de l'ajout de nouveau club");
                    clearForm();
                }
            });
            addNewClubService.start();
        }
    }

    @FXML
    private void handleDeleteMenu(){
        Clubs clubs_a_effacer = tableview_club.getSelectionModel().getSelectedItem();
        Service<Boolean> deleteClubService = clubService.deleteClub(clubs_a_effacer);
        deleteClubService.setOnSucceeded((onSucceededEvent) -> {
            if(deleteClubService.getValue()){
                tableview_club.getItems().remove(clubs_a_effacer);
                appUtils.successAlertDialog("SUCCESS","Club " + clubs_a_effacer.getNom_club() + " Effacer");
            } else {
                appUtils.erreurAlertDialog("ERREUR","Erreur lors de la suppression du club " + clubs_a_effacer.getNom_club());
            }
        });
        deleteClubService.setOnFailed(setOnFailedEvent -> {
            deleteClubService.getException().getMessage();
        });
        deleteClubService.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        col_id_club.setCellValueFactory(new PropertyValueFactory<>("id_club"));
        col_nom_club.setCellValueFactory(new PropertyValueFactory<>("nom_club"));
        col_adresse_club.setCellValueFactory(new PropertyValueFactory<>("adresse_club"));

        ContextMenu contextMenu = new ContextMenu();
        MenuItem edit_menu = new MenuItem("EDITER");
        MenuItem delete_menu = new MenuItem("EFFACER");
        contextMenu.getItems().addAll(edit_menu, delete_menu);
        tableview_club.setContextMenu(contextMenu);

        delete_menu.setOnAction((event) -> {
            handleDeleteMenu();
        });

        ClubService clubService = new ClubService();

        Platform.runLater(() -> {
            club_data_loading.setDisable(false);
            club_data_loading.toFront();
            Service<List<Clubs>> getClubDataService = clubService.getClubData();
            getClubDataService.setOnSucceeded(onSucceededEvent -> {
                tableview_club.getItems().setAll(getClubDataService.getValue());
                club_data_loading.setDisable(true);
                club_data_loading.toBack();
            });
            getClubDataService.start();
        });
    }
}
