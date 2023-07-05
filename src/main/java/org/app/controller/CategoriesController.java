package org.app.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.app.model.Categories;
import org.app.utils.DatabaseConnection;
import org.app.utils.Utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoriesController {

    @FXML
    private Button btn_ajout_categorie;
    @FXML
    private Button btn_load_list_categorie;
    @FXML
    private TableView categories_table_data;
    @FXML
    private TableColumn<Categories, IntegerProperty> col_categorie_id;
    @FXML
    private TableColumn<Categories, StringProperty> col_categorie_titre;
    @FXML
    private TableColumn<Categories, IntegerProperty> col_categorie_poids_min;
    @FXML
    private TableColumn<Categories, IntegerProperty> col_categorie_poids_max;
    @FXML
    private Button btn_enregistrer_categorie;
    @FXML
    private Button btn_annuler_ajout_categorie;
    @FXML
    private TextField txt_nom_categorie;
    @FXML
    private TextField txt_poids_min;
    @FXML
    private TextField txt_poids_max;
    Utils appUtils = new Utils();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();


    /**
     * Formulaire ajout nouvelle categorie
     * @throws IOException
     */
    @FXML
    public void showAddCategoryForm() throws IOException {
        Stage parentStage = (Stage) btn_ajout_categorie.getScene().getWindow();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(
                ClubController.class.getResource("/fxml/categories/ajout-categorie.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("AJOUT CATEGORIE");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.initOwner(parentStage);
        stage.show();
    }

    /**
     * Fermeture Stage ajout categorie
     */
    @FXML
    public void closeAddCategoryForm(){
        Stage add_club_stage = (Stage) btn_annuler_ajout_categorie.getScene().getWindow();
        add_club_stage.close();
    }

    /**
     * Enregistrement nouvelle categorie
     */
    @FXML
    public void enregistrementCategorie(){
        String ajout_categorie_query = "INSERT INTO categorie_poids (nom_categorie, poids_max, poids_min) VALUES (?,?,?)";
        if (handleCategoryForm() == true){
            try{
                preparedStatement = connection.prepareStatement(ajout_categorie_query);
                preparedStatement.setString(1,txt_nom_categorie.getText());
                preparedStatement.setInt(2, Integer.parseInt(txt_poids_min.getText()));
                preparedStatement.setInt(3, Integer.parseInt(txt_poids_max.getText()));

                if (preparedStatement.executeUpdate() == 0){
                    appUtils.erreurAlertDialog("ERREUR","ERREUR D INSERTION, VEUILLEZ VERIFIER SVP");
                } else {
                    appUtils.successAlertDialog("SUCCESS","AJOUT NOUVELLE CATEGORIE REUSSI");
                    txt_nom_categorie.clear();
                    txt_poids_min.clear();
                    txt_poids_max.clear();
                    closeAddCategoryForm();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Validation formulaire categorie
     * @return boolean
     */
    public boolean handleCategoryForm(){
        if (txt_nom_categorie.getText().isEmpty() || txt_poids_max.getText().isEmpty() || txt_poids_min.getText().isEmpty()){
            appUtils.warningAlertDialog("Avertissement","VEUILLEZ COMPLETEZ TOUS LES CHAMPS");
            return false;
        }
        txt_poids_max.textProperty().addListener((observableValue, old_value, new_value) -> {
            if(!new_value.matches("\\d*")){
                txt_poids_max.setText(new_value.replaceAll("[^\\d]",""));
            }
        });

        txt_poids_min.textProperty().addListener((observableValue, old_value, new_value) -> {
            if(!new_value.matches("\\d*")){
                txt_poids_min.setText(new_value.replaceAll("[^\\d]",""));
            }
        });
        return true;
    }

    /**
     * Recuoperation liste categorie
     * @return list categorie
     */
    public ObservableList<Categories> getCategories() {
        ObservableList<Categories> list_categories = FXCollections.observableArrayList();
        String list_categorie_query = "SELECT * FROM categorie_poids";
        try{
            preparedStatement = connection.prepareStatement(list_categorie_query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Categories categories = new Categories();
                categories.setId_category(resultSet.getInt("id_cp"));
                categories.setNom_category(resultSet.getString("nom_categorie"));
                categories.setPoids_min(resultSet.getInt("poids_min"));
                categories.setPoids_max(resultSet.getInt("poids_max"));
                list_categories.add(categories);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list_categories;
    }

    /**
     * Remplissage tableview Categorie
     */
    @FXML
    public void afficheListCategorie(){
        ObservableList<Categories> data_categories = getCategories();
        if (data_categories.size() == 0) {
            appUtils.warningAlertDialog("AVERTISSEMENT","AUCUNE DONNEES");
        } else {
            col_categorie_id.setCellValueFactory(new PropertyValueFactory<>("id_category"));
            col_categorie_titre.setCellValueFactory(new PropertyValueFactory<>("nom_category"));
            col_categorie_poids_min.setCellValueFactory(new PropertyValueFactory<>("poids_min"));
            col_categorie_poids_max.setCellValueFactory(new PropertyValueFactory<>("poids_max"));
            categories_table_data.setItems(data_categories);
        }
    }

}
