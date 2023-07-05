package org.app.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import org.app.model.Combattant;
import org.app.utils.DatabaseConnection;
import org.app.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CombattantController implements Initializable {

    @FXML
    private Button btn_ajout_combattant;
    @FXML
    private TextField txt_nom_combattant;
    @FXML
    private TextField txt_prenom_combattant;
    @FXML
    private DatePicker date_naissance_combattant;
    @FXML
    private ComboBox cbx_genre_combattant;
    @FXML
    private TextField txt_poids_combattant;
    @FXML
    private ComboBox cbx_club_combattant;
    @FXML
    private ComboBox cbx_grade_combattant;
    @FXML
    private ComboBox cbx_categorie_combattant;
    @FXML
    private Button btn_enregistrer_combattant;
    @FXML
    private Button btn_annuler_enregistrement_combattant;
    @FXML
    private Button btn_load_list_combattant;
    @FXML
    private TableView table_combattant;
    @FXML
    private TableColumn<Combattant, IntegerProperty> col_id_combattant;
    @FXML
    private TableColumn<Combattant, StringProperty> col_nom_combattant;
    @FXML
    private TableColumn<Combattant, StringProperty> col_prenom_combattant;
    @FXML
    private TableColumn<Combattant, StringProperty> col_date_naissance_combattant;
    @FXML
    private TableColumn<Combattant, StringProperty> col_genre_combattant;
    @FXML
    private TableColumn<Combattant, StringProperty> col_club_combattant;
    @FXML
    private TableColumn<Combattant, StringProperty> col_grade_combattant;
    @FXML
    private TableColumn<Combattant, StringProperty> col_categorie_combattant;
    @FXML
    private TextField txt_search;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();
    Utils appUtils = new Utils();

    @FXML
    private void afficheAjoutForm(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/combattant/ajout-combattant.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("AJOUT COMBATTANT");
        stage.initModality(Modality.WINDOW_MODAL);
        CombattantController controller = loader.getController();
        controller.populateComboBox();
        controller.populateClub();
        controller.populateGradeComboBox();
        controller.populateCategorieCombobox();
        stage.setResizable(false);
        stage.initOwner(
                ((Node)event.getSource()).getScene().getWindow() );
        stage.show();
    }
    public void populateComboBox(){
        cbx_genre_combattant.getItems().clear();
        cbx_genre_combattant.getItems().addAll(
                "HOMME","FEMME"
        );
    }
    public ArrayList<String> getListClub(){
        ArrayList<String> liste_club = new ArrayList();
        String get_club = "SELECT id_clb, nom_club FROM club";
        try{
            preparedStatement = connection.prepareStatement(get_club);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id_club = resultSet.getInt("id_clb");
                String club_data = resultSet.getString("nom_club");
                liste_club.add(club_data);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return liste_club;
    }
    public void populateClub(){
        List<String> list_club = getListClub();
        Set<String> list_all_club = new HashSet<>(list_club);
        list_all_club.removeAll(Collections.singleton(null));
        list_club.clear();
        list_club.addAll(list_all_club);
        cbx_club_combattant.getItems().clear();
        cbx_club_combattant.getItems().addAll(list_club);
    }
    public void populateGradeComboBox(){
        cbx_grade_combattant.getItems().clear();
        cbx_grade_combattant.getItems().addAll(
                "CEINTURE BLANC",
                "CEINTURE JAUNE",
                "CEINTURE BLEU",
                "CEINTURE VIOLET",
                "CEINTURE MARRON",
                "CEINTURE NOIR",
                "CEINTURE ROUGE"
        );
    }
    public ArrayList<String> getListCategorie(){
        ArrayList liste_categorie = new ArrayList();
        String get_club = "SELECT id_cp, nom_categorie FROM categorie_poids";
        try{
            preparedStatement = connection.prepareStatement(get_club);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int categorie_id = resultSet.getInt("id_cp");
                String categorie_nom = resultSet.getString("nom_categorie");
                liste_categorie.add(categorie_nom);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return liste_categorie;
    }
    public void populateCategorieCombobox(){
        List<String> list_categorie = getListCategorie();
        Set<String> list_all_categorie = new HashSet<>(list_categorie);
        list_all_categorie.removeAll(Collections.singleton(null));
        list_categorie.clear();
        list_categorie.addAll(list_all_categorie);
        cbx_categorie_combattant.getItems().clear();
        cbx_categorie_combattant.getItems().addAll(list_categorie);
    }
    @FXML
    public void closeAjoutCombattantForm(){
        Stage add_club_stage = (Stage) btn_annuler_enregistrement_combattant.getScene().getWindow();
        add_club_stage.close();
    }

    /**
     * Enregistrement nouveau combattant
     */
    @FXML
    public void enregistrerCombattant() throws SQLException {
        String ajout_combattant_query = "INSERT INTO combattant (nom, prenom, date_naissance, genre, poids, grade, club_id, categorie_id) " +
                "VALUES(?,?,?,?,?,?,?,?)";

        String query_get_club = "SELECT id_clb FROM club WHERE nom_club=\'"+ cbx_club_combattant.getValue() +"\'";
        preparedStatement = connection.prepareStatement(query_get_club);
        resultSet = preparedStatement.executeQuery();
        int id_club = resultSet.getInt("id_clb");

        String  query_get_category = "SELECT id_cp FROM categorie_poids WHERE nom_categorie=\'"+ cbx_categorie_combattant.getValue() +"\'";
        preparedStatement = connection.prepareStatement(query_get_category);
        resultSet = preparedStatement.executeQuery();
        int id_category = resultSet.getInt("id_cp");

        try{
            preparedStatement = connection.prepareStatement(ajout_combattant_query);
            preparedStatement.setString(1,txt_nom_combattant.getText());
            preparedStatement.setString(2,txt_prenom_combattant.getText());
            preparedStatement.setString(3,date_naissance_combattant.getValue().toString());
            preparedStatement.setString(4,cbx_genre_combattant.getValue().toString());
            preparedStatement.setInt(5, Integer.parseInt(txt_poids_combattant.getText()));
            preparedStatement.setString(6,cbx_grade_combattant.getValue().toString());
            preparedStatement.setInt(7,id_club);
            preparedStatement.setInt(8, id_category);
            if (preparedStatement.executeUpdate() == 0){
                appUtils.erreurAlertDialog("ERREUR","ERREUR D INSERTION, VEUILLEZ VERIFIER SVP");
            } else {
                appUtils.successAlertDialog("SUCCESS","AJOUT NOUVEAU COMBATTANT REUSSI");
                txt_nom_combattant.clear();
                txt_prenom_combattant.clear();
                txt_poids_combattant.clear();
                cbx_categorie_combattant.getItems().clear();
                cbx_club_combattant.getItems().clear();
                cbx_grade_combattant.getItems().clear();
                cbx_genre_combattant.getItems().clear();
                closeAjoutCombattantForm();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Affichage tableview
     */
    @FXML
    public ObservableList<Combattant> getCombattantsList() {
        ObservableList<Combattant> liste_combattant = FXCollections.observableArrayList();
        String liste_combattant_query = "SELECT id_cb, nom, prenom, date_naissance, genre, poids, grade, nom_club, nom_categorie FROM combattant INNER JOIN club ON combattant.club_id=id_clb INNER JOIN categorie_poids ON combattant.categorie_id=id_cp";
        try{
            preparedStatement = connection.prepareStatement(liste_combattant_query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Combattant combattant = new Combattant();
                combattant.setId_combattant(resultSet.getInt("id_cb"));
                combattant.setNom_combattant(resultSet.getString("nom"));
                combattant.setPrenom_combattant(resultSet.getString("prenom"));
                combattant.setDate_naissance_combattant(resultSet.getString("date_naissance"));
                combattant.setGenre_combattant(resultSet.getString("genre"));
                combattant.setPoids_combattant(resultSet.getInt("poids"));
                combattant.setGrade_combattant(resultSet.getString("grade"));
                combattant.setNom_club_combattant(resultSet.getString("nom_club"));
                combattant.setNom_categorie_combattant(resultSet.getString("nom_categorie"));
                liste_combattant.add(combattant);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return liste_combattant;
    }

    /**
     * Affichage Tableview
     */
    @FXML
    public void afficheListeCombattant(){
        ObservableList<Combattant> liste_combattant = getCombattantsList();
        if (liste_combattant.size() == 0){
            appUtils.warningAlertDialog("AVERTISSEMENT","Aucune donnees");
        } else {
            col_id_combattant.setCellValueFactory(new PropertyValueFactory<>("id_combattant"));
            col_nom_combattant.setCellValueFactory(new PropertyValueFactory<>("nom_combattant"));
            col_prenom_combattant.setCellValueFactory(new PropertyValueFactory<>("prenom_combattant"));
            col_date_naissance_combattant.setCellValueFactory(new PropertyValueFactory<>("date_naissance_combattant"));
            col_genre_combattant.setCellValueFactory(new PropertyValueFactory<>("genre_combattant"));
            col_club_combattant.setCellValueFactory(new PropertyValueFactory<>("nom_club_combattant"));
            col_grade_combattant.setCellValueFactory(new PropertyValueFactory<>("grade_combattant"));
            col_categorie_combattant.setCellValueFactory(new PropertyValueFactory<>("nom_categorie_combattant"));
            table_combattant.setItems(liste_combattant);

            FilteredList<Combattant> filteredList = new FilteredList<>(liste_combattant, b -> true);
            txt_search.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredList.setPredicate(Combattant -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String mot_cle_recherche = newValue.toLowerCase();

                    if (Combattant.getNom_combattant().toLowerCase().indexOf(mot_cle_recherche) > -1) {
                        return true;
                    } else if (Combattant.getPrenom_combattant().toLowerCase().indexOf(mot_cle_recherche) > -1) {
                        return true;
                    } else if(Combattant.getNom_club_combattant().toLowerCase().indexOf(mot_cle_recherche) > -1) {
                        return true;
                    }else if(Combattant.getGenre_combattant().toLowerCase().indexOf(mot_cle_recherche) > -1) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });
            SortedList<Combattant> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(table_combattant.comparatorProperty());
            table_combattant.setItems(sortedList);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
