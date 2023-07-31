package org.app.controller;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    @FXML
    private Label cbt_1_avantage, cbt_2_avantage;
    @FXML
    private Label cbt_1_penalite, cbt_2_penalite;
    @FXML
    private Label label_point_c1, label_point_c2;
    @FXML
    private Button BTN_ADD_AVANTAGE_C1, BTN_ADD_AVANTAGE_C2;
    @FXML
    private Button BTN_DEL_AVANTAGE_C1, BTN_DEL_AVANTAGE_C2;
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
        label_time.setVisible(false);
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
            label_time.setVisible(true);
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
            /*FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), label_time);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.setCycleCount(Animation.INDEFINITE);
            fadeTransition.play();*/
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

    public int getLastAvantageCbt1(){
        return Integer.parseInt(cbt_1_avantage.getText().trim());
    }


    /**
     * Avantage & Penalite Combattant 1
     */
    public void ajoutAvantageC1(Integer avantage_point){
        int dernier_avantage = Integer.parseInt(cbt_1_avantage.getText().trim());
        cbt_1_avantage.setText(String.valueOf(dernier_avantage + avantage_point));
    }

    public void effacerAvantageC1(Integer avantage_point){
        int dernier_avantage = Integer.parseInt(cbt_1_avantage.getText().trim());
        cbt_1_avantage.setText(String.valueOf(dernier_avantage - avantage_point));
    }

    public void ajoutPenaliteC1(Integer penalite_point){
        int dernier_penalite = Integer.parseInt(cbt_1_penalite.getText().trim());
        cbt_1_penalite.setText(String.valueOf(dernier_penalite + penalite_point));
        if (Integer.parseInt(cbt_1_penalite.getText()) == 2){
            int avantage_point_cbt2 = Integer.parseInt(cbt_2_avantage.getText() + 1);
            cbt_2_avantage.setText(String.valueOf(avantage_point_cbt2));
        } else if(Integer.parseInt(cbt_1_penalite.getText()) == 3){
            int point_cbt2 = Integer.parseInt(label_point_c2.getText() + 2);
            label_point_c2.setText(String.valueOf(point_cbt2));
        } else if (Integer.parseInt(cbt_1_penalite.getText()) == 4){
            Alert alert_victoire = new Alert(Alert.AlertType.INFORMATION);
            alert_victoire.setTitle("VICTOIRE");
            alert_victoire.setContentText("VICTOIRE PAR DISQUALIFICATION COMBATTANT 2");
            alert_victoire.showAndWait();
        }
    }

    public void effacerPenaliteC1(Integer penalite_point){
        int dernier_avantage = Integer.parseInt(cbt_1_penalite.getText().trim());
        cbt_1_penalite.setText(String.valueOf(dernier_avantage - penalite_point));
    }

    /**
     * handle point combattant 1
     */
    public void handleAddPointsCombattant1(Integer point_combattant){
        label_point_c1.setText(String.valueOf(point_combattant + Integer.parseInt(label_point_c1.getText())));
    }

    public void handleDelPointsCombattant1(Integer point_combattant){
        label_point_c1.setText(String.valueOf(Integer.parseInt(label_point_c1.getText()) - point_combattant));
    }


    /**
     * Handle Anvatage Point combattant 2
     */

    public void ajoutAvantageC2(Integer avantage_point){
        int dernier_avantage = Integer.parseInt(cbt_2_avantage.getText().trim());
        cbt_2_avantage.setText(String.valueOf(dernier_avantage + avantage_point));
    }

    public void effacerAvantageC2(Integer avantage_point){
        int dernier_avantage = Integer.parseInt(cbt_2_avantage.getText().trim());
        cbt_2_avantage.setText(String.valueOf(dernier_avantage - avantage_point));
    }

    /**
     * Handle Penalite Combattant 2
     */

    public void ajoutPenaliteC2(Integer penalite_point){
        int dernier_penalite = Integer.parseInt(cbt_2_penalite.getText().trim());
        cbt_2_penalite.setText(String.valueOf(dernier_penalite + penalite_point));
        if(Integer.parseInt(cbt_2_penalite.getText()) == 2){
            int avantage_point_cbt1 = Integer.parseInt(cbt_1_avantage.getText() + 1);
            cbt_1_avantage.setText(String.valueOf(avantage_point_cbt1));
        } else if(Integer.parseInt(cbt_2_penalite.getText()) == 3){
            int point_cbt1 = Integer.parseInt(label_point_c1.getText() + 2);
            label_point_c1.setText(String.valueOf(point_cbt1));
        } else if(Integer.parseInt(cbt_2_penalite.getText()) == 4){
            Alert alert_victoire = new Alert(Alert.AlertType.INFORMATION);
            alert_victoire.setTitle("VICTOIRE");
            alert_victoire.setContentText("VICTOIRE PAR DISQUALIFICATION COMBATTANT 1");
            alert_victoire.showAndWait();
        }
    }

    public void effacerPenaliteC2(Integer penalite_point){
        int dernier_avantage = Integer.parseInt(cbt_2_penalite.getText().trim());
        cbt_2_penalite.setText(String.valueOf(dernier_avantage - penalite_point));
    }

    /**
     * Handle Point combattant 2
     */

    public void handleAddPointsCombattant2(Integer point_combattant){
        label_point_c2.setText(String.valueOf(point_combattant + Integer.parseInt(label_point_c2.getText())));
    }

    public void handleDelPointsCombattant2(Integer point_combattant){
        label_point_c2.setText(String.valueOf(Integer.parseInt(label_point_c2.getText()) - point_combattant));
    }

}
