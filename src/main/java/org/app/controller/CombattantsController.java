package org.app.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.app.model.Categorie;
import org.app.model.Clubs;
import org.app.model.Combattants;
import org.app.services.CategorieService;
import org.app.services.ClubService;
import org.app.services.CombattantService;
import org.app.utils.DatabaseConnection;
import org.app.utils.Utils;
import org.controlsfx.control.MaskerPane;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CombattantsController implements Initializable {

    @FXML
    private TextField txt_nom_combattant;
    @FXML
    private TextField txt_prenom_combattant;
    @FXML
    private DatePicker txt_date_naissance_combattant;
    @FXML
    private ComboBox combobox_genre_combattant;
    @FXML
    private TextField txt_poids_combattant;
    @FXML
    private ComboBox combobox_club_combattant;
    @FXML
    private ComboBox combobox_grade_combattant;
    @FXML
    private ComboBox combobox_categorie_combattant;
    @FXML
    private Button btn_save_combattant;
    @FXML
    private TextField txt_search_combattant;
    @FXML
    private TableView<Combattants> tableview_combattant;
    @FXML
    private TableColumn<Combattants, Integer> col_id_combattant;
    @FXML
    private TableColumn<Combattants, String> col_nom_combattant;
    @FXML
    private TableColumn<Combattants, String> col_prenom_combattant;
    @FXML
    private TableColumn<Combattants, String> col_date_naissance_combattant;
    @FXML
    private TableColumn<Combattants, String> col_genre_combattant;
    @FXML
    private TableColumn<Combattants, String> col_poids_combattant;
    @FXML
    private TableColumn<Combattants, String> col_club_combattant;
    @FXML
    private TableColumn<Combattants, String> col_grade_combattant;
    @FXML
    private TableColumn<Combattants, String> col_categorie_combattant;
    @FXML
    private MaskerPane masker_tableview;
    CombattantService combattantService = new CombattantService();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();
    Utils appUtils = new Utils();

    public Boolean handleValidateForm(){
        if(txt_nom_combattant.getText().isEmpty() ||
                txt_prenom_combattant.getText().isEmpty() ||
                txt_poids_combattant.getText().isEmpty() ||
                txt_search_combattant.getText().isEmpty() ||
                combobox_genre_combattant.getItems().isEmpty() ||
                combobox_club_combattant.getItems().isEmpty() ||
                combobox_categorie_combattant.getItems().isEmpty() ||
                combobox_grade_combattant.getItems().isEmpty()
        ){
            appUtils.warningAlertDialog("AVERTISSEMENT","Veuillez completer tous les champs");
            return false;
        }
        return true;
    }
    public void clearForm(){
        txt_nom_combattant.clear();
        txt_prenom_combattant.clear();
        combobox_genre_combattant.getItems().clear();
        txt_poids_combattant.clear();
        combobox_grade_combattant.getItems().clear();
        combobox_club_combattant.getItems().clear();
        combobox_categorie_combattant.getItems().clear();
    }
    public void handleAddCombattantButton(){
        if (handleValidateForm()){
            Combattants nouveau_combattants = new Combattants(
                    txt_nom_combattant.getText().trim(),
                    txt_prenom_combattant.getText().trim(),
                    txt_date_naissance_combattant.getValue().toString(),
                    combobox_genre_combattant.getValue().toString(),
                    Integer.parseInt(txt_poids_combattant.getText().trim()),
                    combobox_grade_combattant.getValue().toString(),
                    getIdClubCombattant(),
                    getIdCategorieCombattant()
            );

            Service<Boolean> addNewCombattantService = combattantService.addNewCombattant(nouveau_combattants);
            addNewCombattantService.setOnSucceeded(onSucceededEvent -> {
                if(addNewCombattantService.getValue()){
                    Service<Integer> getLastIdCombattant = combattantService.getLastCombattantId();
                    getLastIdCombattant.setOnSucceeded((t) -> {
                        nouveau_combattants.setId_combattant(getLastIdCombattant.getValue());
                        nouveau_combattants.setClub_combattant(getNomClubCombattant());
                        nouveau_combattants.setCategories_combattant(getNomCategorieCombattant());
                        tableview_combattant.getItems().add(nouveau_combattants);
                        appUtils.successAlertDialog("SUCCESS"," " + nouveau_combattants.getPrenom_combattant()+" Ajouter");
                        clearForm();
                    });
                    getLastIdCombattant.start();
                }
            });
            addNewCombattantService.setOnFailed(onFailedEvent -> {
                addNewCombattantService.getException().printStackTrace();
            });
            addNewCombattantService.start();
        }
    }

    public int getIdClubCombattant() {
        String get_club_query = "SELECT id_clb FROM club WHERE nom_club=\'" + combobox_club_combattant.getValue().toString() + "\'";
        int id_club = 0;
        try {
            preparedStatement = connection.prepareStatement(get_club_query);
            resultSet = preparedStatement.executeQuery();
            id_club = resultSet.getInt("id_clb");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id_club;
    }

    public int getIdCategorieCombattant() {
        String get_id_categorie_query = "SELECT id_cp FROM categorie_poids WHERE nom_categorie=\'" + combobox_categorie_combattant.getValue().toString() + "\'";
        int id_categorie = 0;
        try {
            preparedStatement = connection.prepareStatement(get_id_categorie_query);
            resultSet = preparedStatement.executeQuery();
            id_categorie = resultSet.getInt("id_cp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id_categorie;
    }

    public String getNomClubCombattant() {
        String get_club_query = "SELECT nom_club FROM club WHERE id_clb=\'" + getIdClubCombattant() + "\'";
        String nom_club = "";
        try {
            preparedStatement = connection.prepareStatement(get_club_query);
            resultSet = preparedStatement.executeQuery();
            nom_club = resultSet.getString("nom_club");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nom_club;
    }

    public String getNomCategorieCombattant() {
        String get_categorie_query = "SELECT nom_categorie FROM categorie_poids WHERE id_cp=\'" + getIdCategorieCombattant() + "\'";
        String nom_categorie = "";
        try {
            preparedStatement = connection.prepareStatement(get_categorie_query);
            resultSet = preparedStatement.executeQuery();
            nom_categorie = resultSet.getString("nom_categorie");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nom_categorie;
    }

    public void handleDeleteMenu(){
        Combattants combattants_effacer = tableview_combattant.getSelectionModel().getSelectedItem();
        Service<Boolean> deleteCombattantService = combattantService.deleteCombattant(combattants_effacer);
        deleteCombattantService.setOnSucceeded(setOnSucceed -> {
            if (deleteCombattantService.getValue()){
                tableview_combattant.getItems().remove(combattants_effacer);
                appUtils.successAlertDialog("SUCCESS","Combattant "+combattants_effacer.getPrenom_combattant()+ " Effacer");
            } else {
                appUtils.erreurAlertDialog("ERREUR","Erreur lors de la suppression, veuillez reessayer");
            }
        });
        deleteCombattantService.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClubService clubService = new ClubService();
        CategorieService categorieService = new CategorieService();

        col_id_combattant.setCellValueFactory(new PropertyValueFactory<>("id_combattant"));
        col_nom_combattant.setCellValueFactory(new PropertyValueFactory<>("nom_combattant"));
        col_prenom_combattant.setCellValueFactory(new PropertyValueFactory<>("prenom_combattant"));
        col_date_naissance_combattant.setCellValueFactory(new PropertyValueFactory<>("date_naissance_combattant"));
        col_genre_combattant.setCellValueFactory(new PropertyValueFactory<>("genre_combattant"));
        col_poids_combattant.setCellValueFactory(new PropertyValueFactory<>("poids_combattant"));
        col_grade_combattant.setCellValueFactory(new PropertyValueFactory<>("grade_combattant"));
        col_club_combattant.setCellValueFactory(new PropertyValueFactory<>("club_combattant"));
        col_categorie_combattant.setCellValueFactory(new PropertyValueFactory<>("categories_combattant"));

        ContextMenu contextMenu = new ContextMenu();
        MenuItem edit_menu = new MenuItem("EDITER");
        MenuItem delete_menu = new MenuItem("EFFACER");
        contextMenu.getItems().addAll(edit_menu, delete_menu);
        tableview_combattant.setContextMenu(contextMenu);

        delete_menu.setOnAction((event) -> {
            handleDeleteMenu();
        });

        Platform.runLater(() -> {

            // Populate genre combobox
            combobox_genre_combattant.getItems().clear();
            combobox_genre_combattant.getItems().addAll(
                    "HOMME","FEMME"
            );

            // Populate category combobox
            combobox_grade_combattant.getItems().clear();
            combobox_grade_combattant.getItems().addAll(
                    "CEINTURE BLANC",
                    "CEINTURE JAUNE",
                    "CEINTURE BLEU",
                    "CEINTURE VIOLET",
                    "CEINTURE MARRON",
                    "CEINTURE NOIR",
                    "CEINTURE ROUGE"
            );

            // Populate Club ComboBox
            Service<List<Clubs>> data_club = clubService.getClubData();
            data_club.setOnSucceeded(s -> {
                List<String> club_data = new ArrayList<>();
                for (Clubs liste_club : data_club.getValue()){
                    club_data.add(liste_club.getNom_club());
                }
                combobox_club_combattant.getItems().addAll(club_data);
            });
            data_club.start();

            // Populate Categorie ComboBox
            Service<List<Categorie>> data_categorie = categorieService.getCategorieData();
            data_categorie.setOnSucceeded(s -> {
                List<String> categorie_data = new ArrayList<>();
                for (Categorie categorie : data_categorie.getValue()){
                    categorie_data.add(categorie.getNom_category());
                }
                combobox_categorie_combattant.getItems().addAll(categorie_data);
            });
            data_categorie.start();

            // Tableview Data
            masker_tableview.setDisable(false);
            masker_tableview.toFront();

            Service<List<Combattants>> serviceCombattant = combattantService.getCombattantsData();
            serviceCombattant.setOnSucceeded(onSucceededEvent -> {
                tableview_combattant.getItems().setAll(serviceCombattant.getValue());
                ObservableList<Combattants> liste_combattants = FXCollections.observableList(serviceCombattant.getValue());

                FilteredList<Combattants> filteredList = new FilteredList<>(liste_combattants, b -> true);
                txt_search_combattant.textProperty().addListener((observableValue, oldValue, newValue) -> {
                    filteredList.setPredicate(Combattant -> {
                        if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                            return true;
                        }
                        String mot_cle_recherche = newValue.toLowerCase();

                        if (Combattant.getNom_combattant().toLowerCase().indexOf(mot_cle_recherche) > -1) {
                            return true;
                        } else if (Combattant.getPrenom_combattant().toLowerCase().indexOf(mot_cle_recherche) > -1) {
                            return true;
                        } else if(Combattant.getClub_combattant().toLowerCase().indexOf(mot_cle_recherche) > -1) {
                            return true;
                        }else if(Combattant.getGenre_combattant().toLowerCase().indexOf(mot_cle_recherche) > -1) {
                            return true;
                        }else if(Combattant.getCategories_combattant().toLowerCase().indexOf(mot_cle_recherche) > -1) {
                            return true;
                        } else {
                            return false;
                        }
                    });
                });
                SortedList<Combattants> sortedList = new SortedList<>(filteredList);
                sortedList.comparatorProperty().bind(tableview_combattant.comparatorProperty());
                tableview_combattant.setItems(sortedList);

                masker_tableview.setDisable(true);
                masker_tableview.toBack();
            });
            serviceCombattant.start();
        });

    }
}
