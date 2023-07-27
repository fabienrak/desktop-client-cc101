package org.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class BoardController {

    @FXML
    private Button btn_start_chrono;
    @FXML
    private Button btn_pause_chrono;
    @FXML
    private Button btn_play_chrono;
    @FXML
    private Button btn_stop_chrono;
    ScoreboardController scoreboardController = ScoreboardController.getInstance();
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

}
