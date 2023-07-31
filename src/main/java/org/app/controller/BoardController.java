package org.app.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements Initializable {

    @FXML
    private Button btn_start_chrono;
    @FXML
    private Button btn_pause_chrono;
    @FXML
    private Button btn_play_chrono;
    @FXML
    private Button btn_stop_chrono;
    @FXML
    private Label label_avantage_c1;
    @FXML
    private Button BTN_ADD_AVANTAGE_C1, BTN_ADD_AVANTAGE_C2;
    @FXML
    private Button BTN_DEL_AVANTAGE_C1, BTN_DEL_AVANTAGE_C2;
    @FXML
    private Button BTN_ADD_PENALITE_C2;
    @FXML
    private Button BTN_DEL_PENALITE_C2;
    @FXML
    private Button BTN_PLUS_1, BTN_PLUS_2, BTN_PLUS_3, BTN_PLUS_4;
    @FXML
    private Button BTN_MOINS_1, BTN_MOINS_2, BTN_MOINS_3, BTN_MOINS_4;
    @FXML
    private Button BTN_PLUS_1_C2, BTN_PLUS_2_C2, BTN_PLUS_3_C2, BTN_PLUS_4_C2;
    @FXML
    private Button BTN_MOINS_1_C2, BTN_MOINS_2_C2, BTN_MOINS_3_C2, BTN_MOINS_4_C2;
    ScoreboardController scoreboardController = ScoreboardController.getInstance();
    int _POINT_1 = 1;
    int _POINT_3 = 3;
    int _POINT_2 = 2;
    int _POINT_4 = 4;

    @FXML
    public void startChronoMatch(){
        scoreboardController.startChrono();
    }
    @FXML
    public void pauseChronoMatch(){
        scoreboardController.pauseChrono();
    }
    @FXML
    public void playChronoMatch(){
        scoreboardController.playChrono();
    }
    @FXML
    public void stopChronoMatch(){
        scoreboardController.stopChrono();
    }

    /**
     * Avantage & Penalite Combattant
     */
    @FXML
    public void ajoutAvantageCombattant1(){
        scoreboardController.ajoutAvantageC1(_POINT_1);
    }
    @FXML
    public void effaceAvantageCombattant1(){
        scoreboardController.effacerAvantageC1(_POINT_1);
    }
    @FXML
    public void ajoutPenaliteCombattant1(){
        scoreboardController.ajoutPenaliteC1(_POINT_1);
    }
    @FXML
    public void effacePenaliteCombattant1(){
        scoreboardController.effacerPenaliteC1(_POINT_1);
    }

    @FXML
    public void ajoutAvantageCombattant2(){
        scoreboardController.ajoutAvantageC2(_POINT_1);
    }
    @FXML
    public void effaceAvantageCombattant2(){
        scoreboardController.effacerAvantageC2(_POINT_1);
    }
    @FXML
    public void ajoutPenaliteCombattant2(){
        scoreboardController.ajoutPenaliteC2(_POINT_1);
    }
    @FXML
    public void effacePenaliteCombattant2(){
        scoreboardController.effacerPenaliteC2(_POINT_1);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Bouton ajout point
        BTN_PLUS_1.setOnAction(actionEvent -> {
            scoreboardController.handleAddPointsCombattant1(_POINT_1);
        });
        BTN_PLUS_2.setOnAction(actionEvent -> {
            scoreboardController.handleAddPointsCombattant1(_POINT_2);
        });
        BTN_PLUS_3.setOnAction(actionEvent -> {
            scoreboardController.handleAddPointsCombattant1(_POINT_3);
        });
        BTN_PLUS_4.setOnAction(actionEvent -> {
            scoreboardController.handleAddPointsCombattant1(_POINT_4);
        });

        //  Bouton efface point
        BTN_MOINS_1.setOnAction(actionEvent -> {
            scoreboardController.handleDelPointsCombattant1(_POINT_1);
        });
        BTN_MOINS_2.setOnAction(actionEvent -> {
           scoreboardController.handleDelPointsCombattant1(_POINT_2);
        });
        BTN_MOINS_3.setOnAction(actionEvent -> {
            scoreboardController.handleDelPointsCombattant1(_POINT_3);
        });
        BTN_MOINS_4.setOnAction(actionEvent -> {
           scoreboardController.handleDelPointsCombattant1(_POINT_4);
        });

        // Combattannt 2
        BTN_PLUS_1_C2.setOnAction(actionEvent -> {
            scoreboardController.handleAddPointsCombattant2(_POINT_1);
        });
        BTN_PLUS_2_C2.setOnAction(actionEvent -> {
            scoreboardController.handleAddPointsCombattant2(_POINT_2);
        });
        BTN_PLUS_3_C2.setOnAction(actionEvent -> {
            scoreboardController.handleAddPointsCombattant2(_POINT_3);
        });
        BTN_PLUS_4_C2.setOnAction(actionEvent -> {
            scoreboardController.handleAddPointsCombattant2(_POINT_4);
        });

        BTN_MOINS_1_C2.setOnAction(actionEvent -> {
            scoreboardController.handleDelPointsCombattant2(_POINT_1);
        });
        BTN_MOINS_2_C2.setOnAction(actionEvent -> {
            scoreboardController.handleDelPointsCombattant2(_POINT_2);
        });
        BTN_MOINS_3_C2.setOnAction(actionEvent -> {
            scoreboardController.handleDelPointsCombattant2(_POINT_3);
        });
        BTN_MOINS_4_C2.setOnAction(actionEvent -> {
            scoreboardController.handleDelPointsCombattant2(_POINT_4);
        });
    }
}
