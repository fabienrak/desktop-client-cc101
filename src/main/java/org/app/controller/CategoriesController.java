package org.app.controller;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.app.model.Categorie;
import org.app.services.CategorieService;
import org.app.utils.DatabaseConnection;
import org.app.utils.Utils;
import org.controlsfx.control.MaskerPane;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CategoriesController implements Initializable {

    @FXML
    private TableView categories_table_data;
    @FXML
    private TableColumn<Categorie, Integer> col_categorie_id;
    @FXML
    private TableColumn<Categorie, String> col_categorie_titre;
    @FXML
    private TableColumn<Categorie, String> col_categorie_nom_categorie;
    @FXML
    private TableColumn<Categorie, Integer> col_categorie_poids_max;
    @FXML
    private TableColumn<Categorie, Integer> col_categorie_age_max;
    @FXML
    private Button btn_enregistrer_categorie;
    @FXML
    private TextField txt_categorie;
    @FXML
    private TextField txt_nom_categorie;
    @FXML
    private TextField txt_age_max;
    @FXML
    private TextField txt_poids_max;
    @FXML
    private MaskerPane masker_tableview;
    Utils appUtils = new Utils();
    CategorieService categorieService = new CategorieService();

    public Boolean handleValidateForm(){
        if (txt_categorie.getText().isEmpty() ||
                txt_nom_categorie.getText().isEmpty() ||
                txt_poids_max.getText().isEmpty() ||
                txt_age_max.getText().isEmpty()
        ){
            appUtils.warningAlertDialog("AVERTISSEMENT","Veuillez completer tous les champs");
            return false;
        }
        return true;
    }

    @FXML
    private void handleAddNewCategorie(){
        if(handleValidateForm()){
            Categorie nouveau_categorie = new Categorie(
                    txt_categorie.getText().trim(),
                    txt_nom_categorie.getText().trim(),
                    Integer.parseInt(txt_poids_max.getText().trim()),
                    Integer.parseInt(txt_age_max.getText().trim())
            );
            Service<Boolean> addNewCategorieService = categorieService.addNewCategorie(nouveau_categorie);
            addNewCategorieService.setOnSucceeded(setOnSucceedEvent -> {
                if (addNewCategorieService.getValue()){
                    Service<Integer> getLastIdCategorieService = categorieService.getLastCategorieId();
                    getLastIdCategorieService.setOnSucceeded((t) -> {
                        nouveau_categorie.setId_category(getLastIdCategorieService.getValue());
                        categories_table_data.getItems().add(nouveau_categorie);
                        appUtils.successAlertDialog("SUCCESS","Categorie " + nouveau_categorie.getNom_category() + " Ajouter");
                        txt_categorie.clear();
                        txt_nom_categorie.clear();
                        txt_poids_max.clear();
                        txt_age_max.clear();
                    });
                    getLastIdCategorieService.start();
                }
            });
            addNewCategorieService.start();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_categorie_id.setCellValueFactory(new PropertyValueFactory<>("id_category"));
        col_categorie_titre.setCellValueFactory(new PropertyValueFactory<>("nom_category"));
        col_categorie_nom_categorie.setCellValueFactory(new PropertyValueFactory<>("nom_sous_category"));
        col_categorie_poids_max.setCellValueFactory(new PropertyValueFactory<>("poids_max"));
        col_categorie_age_max.setCellValueFactory(new PropertyValueFactory<>("age_max"));

        Platform.runLater(() -> {
            masker_tableview.setDisable(false);
            masker_tableview.toFront();
            Service<List<Categorie>> getCategorieDataService = categorieService.getCategorieData();
            getCategorieDataService.setOnSucceeded(setOnSucceed -> {
                categories_table_data.getItems().setAll(getCategorieDataService.getValue());
                masker_tableview.setDisable(true);
                masker_tableview.toBack();
            });
            getCategorieDataService.start();
        });
    }
}
