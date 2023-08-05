package org.app.controller;

import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.app.model.Combattants;
import org.app.model.TypeMatchModel;
import org.app.services.TypeMatchService;
import org.app.utils.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class TypeMatchController implements Initializable {

    @FXML
    private TableView<TypeMatchModel> type_match_tableview;
    @FXML
    private TableColumn<TypeMatchModel, Integer> col_id_typematch;
    private TableColumn<TypeMatchModel, String> col_id_nom_type_match;
    @FXML
    private TextField txt_type_match;
    @FXML
    private Button btn_save_type_match;
    Utils appUtils = new Utils();
    TypeMatchService typeMatchService = new TypeMatchService();

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
        col_id_typematch.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_id_nom_type_match.setCellValueFactory(new PropertyValueFactory<>("nom_type_match"));

        ContextMenu contextMenu = new ContextMenu();
        MenuItem edit_menu = new MenuItem("EDITER");
        MenuItem delete_menu = new MenuItem("EFFACER");
        contextMenu.getItems().addAll(edit_menu, delete_menu);
        type_match_tableview.setContextMenu(contextMenu);

        delete_menu.setOnAction((event) -> {
            handleDeleteTypeMatchMenu();
        });
    }
}
