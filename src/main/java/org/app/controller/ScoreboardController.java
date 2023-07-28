package org.app.controller;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ScoreboardController {

    @FXML
    private Label label_combattant_1;
    @FXML
    private Label label_combattant_2;
    @FXML
    private Label label_club_cbt1;
    @FXML
    private Label label_club_cbt2;
    @FXML
    private Label label_time;


    private static ScoreboardController instance;
    private int duree_minute = 1;
    private int duree_seconde = 0;
    private boolean isPaused = false;
    private Timeline timeline;

    public ScoreboardController(){
        instance = this;
    }

    public static ScoreboardController getInstance(){
        return instance;
    }

    public void afficheCombattant_1(String combattant_1){
        label_combattant_1.setText(combattant_1);
    }

    public void afficheCombattant_2(String combattant_2){
        label_combattant_2.setText(combattant_2);
    }

    public void afficheClubCombattant1(String nom_club_combattant_1){
        label_club_cbt1.setText(nom_club_combattant_1);
    }

    public void afficheClubCombattant2(String nom_club_combattant_2){
        label_club_cbt2.setText(nom_club_combattant_2);
    }

    public void afficheTempsMatch(int duree_match){
        label_time.setText(String.valueOf(Integer.valueOf(duree_match)));
    }

    /**
     * Format time
     * @param minutes
     * @param seconds
     * @return
     */
    private String formatTime(int minutes, int seconds) {
        System.out.println("CHRONO : " + minutes + " : " + seconds);
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Format time number from textfield
     * @return int time_number
     */
    public int formatTimeNumber(){
        int duree_match = Integer.parseInt(label_time.getText().trim());
        System.out.println("TIME FORMATTED : " + duree_match);
        return duree_match;
    }

    /**
     * Start chrono
     */
    public void startChrono(){
        if (isPaused) {

            isPaused = false;
        } else {
            duree_minute = formatTimeNumber();
            duree_seconde = 0;
            label_time.setText(formatTime(duree_minute, duree_seconde));
        }

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (duree_seconde == 0 && duree_minute == 0) {
                //stopTimer();
                return;
            }

            if (duree_seconde == 0) {
                duree_minute--;
                duree_seconde = 59;
            } else {
                duree_seconde--;
            }

            label_time.setText(formatTime(duree_minute, duree_seconde));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Pause Chrono
     */
    public void pauseChrono(){
        if (timeline != null) {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), label_time);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.setCycleCount(Animation.INDEFINITE);
            fadeTransition.play();
            timeline.pause();
        }
        isPaused = true;

    }

    /**
     * Play Chrono
     */
    public void playChrono(){
        if (timeline != null) {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), label_time);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.setCycleCount(Animation.INDEFINITE);
            fadeTransition.stop();
            timeline.play();
        }
        isPaused = false;
    }

    /**
     * Stop Chrono
     */
    public void stopChrono(){
        if (timeline != null) {
            timeline.stop();
        }
        duree_minute = 1;
        duree_seconde = 0;
        label_time.setText(formatTime(duree_minute, duree_seconde));
        isPaused = false;
    }


}
