package org.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BoardController {

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
    private Button BTN_ADD_AVANTAGE_C1;
    @FXML
    private Button BTN_DEL_AVANTAGE_C1;
    @FXML
    private Button BTN_ADD_PENALITE_C2;
    @FXML
    private Button BTN_DEL_PENALITE_C2;
    @FXML
    private Button BTN_PLUS_1, BTN_PLUS_2, BTN_PLUS_3, BTN_PLUS_4;
    ScoreboardController scoreboardController = ScoreboardController.getInstance();
    int _POINT_1 = 1;
    int _POINT_3 = 3;
    int _POINT_2 = 2;
    int _POINT_4 = 4;
    int _DERNIER_POINTS = scoreboardController.getLastAvantage();

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
    public void handlePoints(){
        BTN_PLUS_1.setOnAction(actionEvent -> {
            scoreboardController.handlePointsCombattant1(_POINT_1);
        });
        BTN_PLUS_2.setOnAction(actionEvent -> {
           scoreboardController.handlePointsCombattant1(_POINT_2);
        });
        BTN_PLUS_3.setOnAction(actionEvent -> {
            scoreboardController.handlePointsCombattant1(_POINT_3);
        });
        BTN_PLUS_4.setOnAction(actionEvent -> {
            scoreboardController.handlePointsCombattant1(_POINT_4);
        });
    }

}
