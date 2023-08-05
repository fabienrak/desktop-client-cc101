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
import org.app.model.*;
import org.app.services.*;
import org.app.utils.DatabaseConnection;
import org.app.utils.Utils;
import org.controlsfx.control.MaskerPane;

import java.net.URL;
import java.sql.*;
import java.util.*;

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
    private TableColumn<Combattants, Integer> col_poids_combattant;
    @FXML
    private TableColumn<Combattants, String> col_club_combattant;
    @FXML
    private TableColumn<Combattants, String> col_grade_combattant;
    @FXML
    private TableColumn<Combattants, String> col_categorie_combattant;
    @FXML
    private TableColumn<Combattants, Combattants> col_select_combattant;
    @FXML
    private MaskerPane masker_tableview;
    @FXML
    private ComboBox<Integer> cbx_poids;
    @FXML
    private ComboBox<String> cbx_ceinture;
    @FXML
    private ComboBox<String> cbx_genre;
    @FXML
    private TextField txt_duree_match;
    @FXML
    private ComboBox cbx_type_match;
    @FXML
    private ComboBox cbx_time;
    @FXML
    private ComboBox cbx_emplacement_match;
    CombattantService combattantService = new CombattantService();
    EmplacementService emplacementService = new EmplacementService();
    TypeMatchService typeMatchService = new TypeMatchService();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();
    Utils appUtils = new Utils();
    private static CombattantsController instance;
    public CombattantsController(){
        instance = this;
    }
    public static CombattantsController getInstance(){
        return instance;
    }

    /**
     * Populate combobox filtre poids
     */
    public ObservableList<Combattants> filtre_combattant(){
        ObservableList<Combattants> liste_combattant_filtre = FXCollections.observableArrayList();
        String query_filtre;

        return liste_combattant_filtre;
    }


    /**
     * Validate form
     * @return boolean
     */
    public Boolean handleValidateForm(){
        if(txt_nom_combattant.getText().isEmpty() ||
                txt_prenom_combattant.getText().isEmpty() ||
                txt_poids_combattant.getText().isEmpty() ||
                combobox_genre_combattant.getValue().toString().isEmpty() ||
                combobox_club_combattant.getValue().toString().isEmpty() ||
                combobox_categorie_combattant.getValue().toString().isEmpty() ||
                combobox_grade_combattant.getValue().toString().isEmpty()
        ){
            appUtils.warningAlertDialog("AVERTISSEMENT","Veuillez completer tous les champs");
            return false;
        }
        return true;
    }

    /**
     * Clear form after submit
     */
    public void clearForm(){
        txt_nom_combattant.clear();
        txt_prenom_combattant.clear();
        combobox_genre_combattant.setValue(null);
        txt_poids_combattant.clear();
        combobox_grade_combattant.setValue(null);
        combobox_club_combattant.setValue(null);
        combobox_categorie_combattant.setValue(null);
    }

    /**
     * Ajout Combattant
     * TODO : Debug getLastIdCombattant return UnsupportedOperationException
     */
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
                        System.out.println("CLUB CBT : " + getNomClubCombattant() + " CATEGORIE CBT : " + getNomCategorieCombattant());
                        try{
                            tableview_combattant.getItems().add(nouveau_combattants);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        appUtils.successAlertDialog("SUCCESS"," " + nouveau_combattants.getPrenom_combattant()+" Ajouter");
                        clearForm();
                    });
                    getLastIdCombattant.setOnFailed((t) -> {
                        getLastIdCombattant.getException().printStackTrace();
                        System.out.println("ERREUR DELETE CBT");
                    });
                    getLastIdCombattant.start();
                }
            });
            addNewCombattantService.setOnFailed((onFailedEvent) -> addNewCombattantService.getException().printStackTrace());
            addNewCombattantService.start();
        }
    }

    /**
     * Filtrer combattant
     */
    @FXML
    public void filtrerCombattant(){
        if (cbx_poids.getItems() == null || cbx_genre.getItems() == null || cbx_ceinture.getItems() == null){
            appUtils.warningAlertDialog("AVERTISSEMENT","VEUILLEZ REMPLIR LES CRITERES");
        } else {
            masker_tableview.setDisable(false);
            masker_tableview.toFront();

            Service<List<Combattants>> service_filtre_combattant = combattantService.filtreCombattant(
                    cbx_poids.getValue(), cbx_genre.getValue(), cbx_ceinture.getValue()
            );
            service_filtre_combattant.setOnSucceeded(onSucceededEvent -> {
                System.out.println("RETOUR : " + service_filtre_combattant.getValue());
                if (service_filtre_combattant.getValue().isEmpty()){
                    appUtils.warningAlertDialog("AVERTISSEMENT","AUCUNE DONNEE TROUVER");
                } else {
                    ObservableList<Combattants> liste_combattants = FXCollections.observableArrayList(service_filtre_combattant.getValue());
                    System.out.println("TYPE : " + liste_combattants.getClass());
                    tableview_combattant.setItems(liste_combattants);
                }

                masker_tableview.setDisable(true);
                masker_tableview.toBack();
            });
            service_filtre_combattant.setOnFailed(onFailedEvent -> {
                service_filtre_combattant.getException().printStackTrace();
            });
            service_filtre_combattant.start();
        }
    }

    /**
     * Get ID Club combattant
     * @return ID Club
     */
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

    /**
     * get ID categorie combattant
     * @return ID Categorie
     */
    public int getIdCategorieCombattant() {
        String get_id_categorie_query = "SELECT id_cp FROM categorie_poids WHERE categorie=\'" + combobox_categorie_combattant.getValue().toString() + "\'";
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

    /**
     * Get nom club combattant
     * @return nom_combattant
     */
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

    /**
     * Get nom categorie combattant
     * @return nom_categorie
     */
    public String getNomCategorieCombattant() {
        String get_categorie_query = "SELECT categorie FROM categorie_poids WHERE id_cp=\'" + getIdCategorieCombattant() + "\'";
        String nom_categorie = "";
        try {
            preparedStatement = connection.prepareStatement(get_categorie_query);
            resultSet = preparedStatement.executeQuery();
            nom_categorie = resultSet.getString("categorie");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nom_categorie;
    }

    /**
     * Delete combattant
     */
    public void handleDeleteMenu(){
        Combattants combattants_effacer = tableview_combattant.getSelectionModel().getSelectedItem();
        Service<Boolean> deleteCombattantService = combattantService.deleteCombattant(combattants_effacer);
        deleteCombattantService.setOnSucceeded((setOnSucceed) -> {
            if (deleteCombattantService.getValue()){
                // TODO Debug here : tableview_combattant remove deleted data in list
                try{
                    tableview_combattant.getItems().remove(combattants_effacer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                appUtils.successAlertDialog("SUCCESS","Combattant "+combattants_effacer.getPrenom_combattant()+ " Effacer");
            } else {
                appUtils.erreurAlertDialog("ERREUR","Erreur lors de la suppression, veuillez reessayer");
            }
        });
        deleteCombattantService.setOnFailed(setOnFailed -> {
            deleteCombattantService.getException().getMessage();
        });
        deleteCombattantService.start();
    }

    /**
     * Selection combattant dans tableview
     *
     * @return
     */
    public ArrayList<Combattants> getSelectedCoombattant(){

        ObservableList<Combattants> liste_selected = tableview_combattant.getSelectionModel().getSelectedItems();
        ArrayList<Combattants> cbt_match = new ArrayList<>();
        if(liste_selected.size() > 2 || liste_selected.size() == 0){
            appUtils.warningAlertDialog("AVERTISSEMENT","Choisir au moins 2 combattant");
        } else {
            for (Combattants cbt : liste_selected) {
                cbt_match.add(cbt);
            }

            // Recuperation ID emplacement
            int id_tatami = getTatamiById(cbx_emplacement_match.getValue().toString());
            createMatch(
                    cbt_match.get(0).getId_combattant(),
                    cbt_match.get(1).getId_combattant(),
                    cbx_type_match.getValue().toString(),
                    Integer.parseInt(cbx_time.getValue().toString()),
                    id_tatami
            );

            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            if (scoreboardController != null){
                scoreboardController.afficheCombattant_1(cbt_match.get(0).getPrenom_combattant());
                scoreboardController.afficheClubCombattant1(cbt_match.get(0).getClub_combattant());
                scoreboardController.afficheCombattant_2(cbt_match.get(1).getPrenom_combattant());
                scoreboardController.afficheClubCombattant2(cbt_match.get(1).getClub_combattant());
                scoreboardController.afficheTempsMatch(Integer.parseInt(cbx_time.getValue().toString()));
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("ERREUR");
                errorAlert.setContentText("ERREUR INTERNE SURVENUE, OUVRIR LE TABLEAU DE SCORE EN PREMIER");
                errorAlert.showAndWait();
            }
        }
        return cbt_match;
    }

    /**
     * Select combattant
     */
    public void selectCombattant(){
        BoardController boardController = BoardController.getInstance();
        ArrayList<Combattants> liste_combattant_selectionner = getSelectedCoombattant();
        if (liste_combattant_selectionner.size() > 2 || liste_combattant_selectionner.size() == 0){
            appUtils.warningAlertDialog("AVERTISSEMENT","CHOISISSEZ AU MOINS 2 COMBATTANT ET OUVRIR LE TABLEAU DE SCORE EN PREMIER");
        } else {
            boardController.affichePrenomCombattant1(liste_combattant_selectionner.get(0).getPrenom_combattant());
            boardController.affichePrenomCombattant2(liste_combattant_selectionner.get(1).getPrenom_combattant());
        }
    }


    /**
     * Get selected tatami ID
     * @param tatami
     * @return id_tatami
     */
    public int getTatamiById(String tatami){
        String get_tatami_id = "SELECT id_tatami FROM emplacement WHERE nom_emplacement=\'"+ tatami +"\'";
        int _tatami_id = 0;
        try {
            preparedStatement = connection.prepareStatement(get_tatami_id);
            //preparedStatement.setString(1,tatami);
            resultSet = preparedStatement.executeQuery();
            _tatami_id = resultSet.getInt("id_tatami");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return _tatami_id;
    }

    /**
     * Creation match query
     * @param cbt_id_1
     * @param cbt_id_2
     * @param type_match
     * @param duree_match
     * @param tatami_id
     */
    public void createMatch(Integer cbt_id_1, Integer cbt_id_2, String type_match, Integer duree_match, Integer tatami_id){
        String add_combattant_query = "INSERT INTO match ('combattant_1_id', 'combattant_2_id', 'tour_match', 'duree_match','tatami_id') VALUES (?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(add_combattant_query);
            preparedStatement.setInt(1,cbt_id_1);
            preparedStatement.setInt(2,cbt_id_2);
            preparedStatement.setString(3, type_match);
            preparedStatement.setInt(4, duree_match);
            preparedStatement.setInt(5, tatami_id);
            if (preparedStatement.executeUpdate() == 0){
                appUtils.erreurAlertDialog("ERREUR","Veuillez reessayer");
            } else {
                appUtils.successAlertDialog("SUCCESS","Combattant ajouter au Match");
            }
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void populatePoidsCombobox(){
        ObservableList<Integer> poids_value = FXCollections.observableArrayList();
        for (Combattants combattants : tableview_combattant.getItems()){
            poids_value.add(col_poids_combattant.getCellObservableValue(combattants).getValue());
        }
        System.out.println("DATA : " + poids_value);
        Set<Integer> poids_sans_doublon = new HashSet<>(poids_value);

        System.out.println("POIDS : " + poids_sans_doublon);
        cbx_poids.getItems().clear();
        cbx_poids.getItems().addAll(
                poids_sans_doublon
        );
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
        MenuItem match_menu = new MenuItem("MATCH");
        MenuItem delete_menu = new MenuItem("EFFACER");
        contextMenu.getItems().addAll(match_menu, delete_menu);
        tableview_combattant.setContextMenu(contextMenu);

        delete_menu.setOnAction((event) -> {
            handleDeleteMenu();
        });

        match_menu.setOnAction((event) -> {
            getSelectedCoombattant();
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

            cbx_ceinture.getItems().clear();
            cbx_ceinture.getItems().addAll(
                    "CEINTURE BLANC",
                    "CEINTURE JAUNE",
                    "CEINTURE BLEU",
                    "CEINTURE VIOLET",
                    "CEINTURE MARRON",
                    "CEINTURE NOIR",
                    "CEINTURE ROUGE"
            );

            cbx_genre.getItems().clear();
            cbx_genre.getItems().addAll(
              "HOMME","FEMME"
            );

            // Populate emplacement combobox
            Service<List<Emplacement>> emplacement = emplacementService.getEmplacementDataService();
            emplacement.setOnSucceeded(s -> {
                List<String> emplacement_data = new ArrayList<>();
                for (Emplacement tatami : emplacement.getValue()){
                    emplacement_data.add(tatami.getNom_emplacement());
                }
                cbx_emplacement_match.getItems().addAll(emplacement_data);
            });
            emplacement.start();

            // populate type match combobox
            // Si Utilise ENUM TypeMatch : HOTFIX
            //ObservableList<TypeMatch> type_match = FXCollections.observableArrayList(TypeMatch.values());
            //cbx_type_match.getItems().addAll(type_match);

            Service<List<TypeMatchModel>> data_type_match = typeMatchService.getTypeMatchData();
            data_type_match.setOnSucceeded(tm -> {
                List<String> type_match_data = new ArrayList<>();
                for (TypeMatchModel liste_tm : data_type_match.getValue()){
                    type_match_data.add(liste_tm.getNom_type_match());
                }
                cbx_type_match.getItems().addAll(type_match_data);
            });
            data_type_match.start();

            //  Populate Combobox Temps : HOTFIX
            // TODO : a dynamiser
            cbx_time.getItems().clear();
            cbx_time.getItems().addAll(
                    "2","3","4","5","6","10","20"
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
                tableview_combattant.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                ObservableList<Combattants> liste_combattants = FXCollections.observableList(serviceCombattant.getValue());
                populatePoidsCombobox();
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
            serviceCombattant.setOnFailed((setOnFailed) -> {
                serviceCombattant.getException().getMessage();
            });

            serviceCombattant.start();
        });
    }
}
