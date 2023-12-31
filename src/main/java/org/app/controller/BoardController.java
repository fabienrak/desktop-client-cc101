package org.app.controller;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.app.model.Combattants;
import org.app.model.Victoire;
import org.app.utils.Utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BoardController implements Initializable {

    @FXML
    private Button btn_start_chrono, btn_pause_chrono, btn_play_chrono, btn_stop_chrono;
    @FXML
    private Label label_avantage_c1,label_avantage_c2,
            label_prenom_cbt1, label_prenom_cbt2,
            label_penalite_c1, label_penalite_c2,
            label_point_c1, label_point_c2;
    @FXML
    private Button BTN_ADD_AVANTAGE_C1, BTN_ADD_AVANTAGE_C2;
    @FXML
    private Button BTN_DEL_AVANTAGE_C1, BTN_DEL_AVANTAGE_C2;
    @FXML
    private Button BTN_ADD_PENALITE_C2, BTN_DEL_PENALITE_C2;
    @FXML
    private Button BTN_PLUS_1, BTN_PLUS_2, BTN_PLUS_3, BTN_PLUS_4;
    @FXML
    private Button BTN_MOINS_1, BTN_MOINS_2, BTN_MOINS_3, BTN_MOINS_4;
    @FXML
    private Button BTN_PLUS_1_C2, BTN_PLUS_2_C2, BTN_PLUS_3_C2, BTN_PLUS_4_C2;
    @FXML
    private Button BTN_MOINS_1_C2, BTN_MOINS_2_C2, BTN_MOINS_3_C2, BTN_MOINS_4_C2;
    @FXML
    private Button BTN_SUBMISSION_C1, BTN_SUBMISSION_C2,
            BTN_POINT_C1, BTN_POINT_C2,
            BTN_ABANDON_C1, BTN_ABANDON_C2,
            BTN_DISQUALIFIE_C1, BTN_DISQUALIFIE_C2, BTN_MATCH_NULL, BTN_RESET;
    Utils app_utils = new Utils();
    ScoreboardController scoreboardController = ScoreboardController.getInstance();
    private static BoardController instance;
    public BoardController() {
        instance = this;
    }
    public static BoardController getInstance(){
        return instance;
    }
    int _POINT_1 = 1;
    int _POINT_3 = 3;
    int _POINT_2 = 2;
    int _POINT_4 = 4;

    @FXML
    public void startChronoMatch(){
        ScoreboardController scoreboardController = ScoreboardController.getInstance();
        //scoreboardController.startChrono();
        scoreboardController.startChrono();
    }
    @FXML
    public void pauseChronoMatch(){
        ScoreboardController scoreboardController = ScoreboardController.getInstance();
        //scoreboardController.pauseChrono();
        scoreboardController.pauseChrono();
    }
    @FXML
    public void playChronoMatch(){
        ScoreboardController scoreboardController = ScoreboardController.getInstance();
        scoreboardController.playChrono();
    }
    @FXML
    public void stopChronoMatch(){
        ScoreboardController scoreboardController = ScoreboardController.getInstance();
        scoreboardController.stopChrono();
    }

    /**
     * Avantage & Penalite Combattant
     */
    @FXML
    public void ajoutAvantageCombattant1(){
        ScoreboardController scoreboardController = ScoreboardController.getInstance();
        scoreboardController.ajoutAvantageC1(_POINT_1);
        int dernier_avantage = scoreboardController.getLastAvantageCbt1();
        label_avantage_c1.setText(String.valueOf(dernier_avantage));
    }
    @FXML
    public void effaceAvantageCombattant1(){
        ScoreboardController scoreboardController = ScoreboardController.getInstance();
        scoreboardController.effacerAvantageC1(_POINT_1);
        int dernier_avantage = scoreboardController.getLastAvantageCbt1();
        label_avantage_c1.setText(String.valueOf(dernier_avantage));
    }
    @FXML
    public void ajoutPenaliteCombattant1(){
        ScoreboardController scoreboardController = ScoreboardController.getInstance();
        scoreboardController.ajoutPenaliteC1(_POINT_1);
        int dernier_penalite = scoreboardController.getLastPenaliteCbt1();
        label_penalite_c1.setText(String.valueOf(dernier_penalite));
    }
    @FXML
    public void effacePenaliteCombattant1(){
        ScoreboardController scoreboardController = ScoreboardController.getInstance();
        scoreboardController.effacerPenaliteC1(_POINT_1);
        int dernier_penalite = scoreboardController.getLastPenaliteCbt1();
        label_penalite_c1.setText(String.valueOf(dernier_penalite));
    }

    @FXML
    public void ajoutAvantageCombattant2(){
        ScoreboardController scoreboardController = ScoreboardController.getInstance();
        scoreboardController.ajoutAvantageC2(_POINT_1);
        int dernier_avantage = scoreboardController.getLastAvantageCbt2();
        label_avantage_c2.setText(String.valueOf(dernier_avantage));
    }
    @FXML
    public void effaceAvantageCombattant2(){
        ScoreboardController scoreboardController = ScoreboardController.getInstance();
        scoreboardController.effacerAvantageC2(_POINT_1);
        int dernier_avantage = scoreboardController.getLastAvantageCbt2();
        label_avantage_c2.setText(String.valueOf(dernier_avantage));
    }
    @FXML
    public void ajoutPenaliteCombattant2(){
        ScoreboardController scoreboardController = ScoreboardController.getInstance();
        scoreboardController.ajoutPenaliteC2(_POINT_1);
        int dernier_penalite = scoreboardController.getLastPenaliteCbt2();
        label_penalite_c2.setText(String.valueOf(dernier_penalite));
    }
    @FXML
    public void effacePenaliteCombattant2(){
        ScoreboardController scoreboardController = ScoreboardController.getInstance();
        scoreboardController.effacerPenaliteC2(_POINT_1);
        int dernier_penalite = scoreboardController.getLastPenaliteCbt2();
        label_penalite_c2.setText(String.valueOf(dernier_penalite));
    }

    @FXML
    public void ajoutMinute(){
        ScoreboardController scoreboardController = ScoreboardController.getInstance();
        scoreboardController.updateChrono();
    }

    @FXML
    public void effaceMinute(){
        ScoreboardController scoreboardController = ScoreboardController.getInstance();
        scoreboardController.removeTime();
    }

    void affichePrenomCombattant1(String prenomCbt1){
        label_prenom_cbt1.setText(prenomCbt1);
    }

    void affichePrenomCombattant2(String prenomCbt2){
        label_prenom_cbt2.setText(prenomCbt2);
    }

    @FXML
    private Label label_chrono;

    void afficheChrono(){
        ScoreboardController scoreboardController = ScoreboardController.getInstance();
        //String temps = scoreboardController.getTimer();
        if (scoreboardController != null){
            label_chrono.setText(scoreboardController.getTimer());
        } else {
            app_utils.warningAlertDialog("AVERTISSEMENT","VEUILLEZ OUVRIR EN PREMIER LE TABLEAU DE SCORE, SELECTIONNER UN COMBATTANT");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficheChrono();

        // Selection combattant
        CombattantsController combattantsController = CombattantsController.getInstance();
        if (combattantsController != null){
            combattantsController.selectCombattant();
        } else {
            app_utils.warningAlertDialog("AVERTISSEMENT","VEUILLEZ OUVRIR EN PREMIER LE TABLEAU DE SCORE");
        }
        btn_start_chrono.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.startChrono();
        });

        // Bouton ajout point si utile
        /*BTN_PLUS_1.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.handleAddPointsCombattant1(_POINT_1);
        });*/
        BTN_PLUS_2.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.handleAddPointsCombattant1(_POINT_2);
            int dernier_point = scoreboardController.getLastPointCbt1();
            label_point_c1.setText(String.valueOf(dernier_point));
        });
        BTN_PLUS_3.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.handleAddPointsCombattant1(_POINT_3);
            int dernier_point = scoreboardController.getLastPointCbt1();
            label_point_c1.setText(String.valueOf(dernier_point));
        });
        BTN_PLUS_4.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.handleAddPointsCombattant1(_POINT_4);
            int dernier_point = scoreboardController.getLastPointCbt1();
            label_point_c1.setText(String.valueOf(dernier_point));
        });

        //  Bouton efface point
        BTN_MOINS_1.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.handleDelPointsCombattant1(_POINT_1);
            int dernier_point = scoreboardController.getLastPointCbt1();
            label_point_c1.setText(String.valueOf(dernier_point));
        });
        BTN_MOINS_2.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
           scoreboardController.handleDelPointsCombattant1(_POINT_2);
            int dernier_point = scoreboardController.getLastPointCbt1();
            label_point_c1.setText(String.valueOf(dernier_point));
        });
        BTN_MOINS_3.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.handleDelPointsCombattant1(_POINT_3);
            int dernier_point = scoreboardController.getLastPointCbt1();
            label_point_c1.setText(String.valueOf(dernier_point));
        });
        BTN_MOINS_4.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.handleDelPointsCombattant1(_POINT_4);
            int dernier_point = scoreboardController.getLastPointCbt1();
            label_point_c1.setText(String.valueOf(dernier_point));
        });

        // Combattannt 2
        /*BTN_PLUS_1_C2.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.handleAddPointsCombattant2(_POINT_1);
        });*/
        BTN_PLUS_2_C2.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.handleAddPointsCombattant2(_POINT_2);
            int dernier_point = scoreboardController.getLastPointCbt2();
            label_point_c2.setText(String.valueOf(dernier_point));
        });
        BTN_PLUS_3_C2.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.handleAddPointsCombattant2(_POINT_3);
            int dernier_point = scoreboardController.getLastPointCbt2();
            label_point_c2.setText(String.valueOf(dernier_point));
        });
        BTN_PLUS_4_C2.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.handleAddPointsCombattant2(_POINT_4);
            int dernier_point = scoreboardController.getLastPointCbt2();
            label_point_c2.setText(String.valueOf(dernier_point));
        });

        BTN_MOINS_1_C2.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.handleDelPointsCombattant2(_POINT_1);
            int dernier_point = scoreboardController.getLastPointCbt2();
            label_point_c2.setText(String.valueOf(dernier_point));
        });
        BTN_MOINS_2_C2.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.handleDelPointsCombattant2(_POINT_2);
            int dernier_point = scoreboardController.getLastPointCbt2();
            label_point_c2.setText(String.valueOf(dernier_point));
        });
        BTN_MOINS_3_C2.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.handleDelPointsCombattant2(_POINT_3);
            int dernier_point = scoreboardController.getLastPointCbt2();
            label_point_c2.setText(String.valueOf(dernier_point));
        });
        BTN_MOINS_4_C2.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.handleDelPointsCombattant2(_POINT_4);
            int dernier_point = scoreboardController.getLastPointCbt2();
            label_point_c2.setText(String.valueOf(dernier_point));
        });

        /**
         * Annonce Victoire
         */

        BTN_SUBMISSION_C1.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.annonceVictoire(Victoire.SUBMISSION_COMBATTANT_1);
        });
        BTN_SUBMISSION_C2.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.annonceVictoire(Victoire.SUBMISSION_COMBATTANT_2);
        });
        BTN_POINT_C1.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.annonceVictoire(Victoire.POINT_COMBATTANT_1);
        });
        BTN_POINT_C2.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.annonceVictoire(Victoire.POINT_COMBATTANT_2);
        });
        BTN_ABANDON_C1.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.annonceVictoire(Victoire.ABANDON_COMBATTANT_1);
        });
        BTN_ABANDON_C2.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.annonceVictoire(Victoire.ABANDON_COMBATTANT_2);
        });
        BTN_DISQUALIFIE_C1.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.annonceVictoire(Victoire.DISQUALIFICATION_COMBATTANT_1);
        });
        BTN_DISQUALIFIE_C2.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.annonceVictoire(Victoire.DISQUALIFICATION_COMBATTANT_2);
        });
        BTN_MATCH_NULL.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.annonceVictoire(Victoire.MATCH_NULL);
        });
        BTN_RESET.setOnAction(actionEvent -> {
            ScoreboardController scoreboardController = ScoreboardController.getInstance();
            scoreboardController.resetScoreBoard();
        });

    }
}
