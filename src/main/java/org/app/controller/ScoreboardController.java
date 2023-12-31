package org.app.controller;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.app.model.Victoire;
import org.app.utils.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import static java.awt.Color.RED;
import static javafx.application.Platform.exit;

public class ScoreboardController implements Initializable {

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
    private ImageView SUBMISSION_C1, SUBMISSION_C2,
            POINT_C1, POINT_C2,
            ABANDON_C1, ABANDON_C2,
            DISQUALIFIE_C1, DISQUALIFIE_C2,
            MATCH_NULL_C1, MATCH_NULL_C2;
    @FXML
    private Button BTN_ADD_AVANTAGE_C1, BTN_ADD_AVANTAGE_C2;
    @FXML
    private Button BTN_DEL_AVANTAGE_C1, BTN_DEL_AVANTAGE_C2;
    @FXML
    private AnchorPane anchor_cbt1;
    private int duree_minute = 1;
    private int duree_seconde = 0;
    private boolean isPaused = false;
    private Timeline timeline;
    private Stage stage;
    Utils app_utils = new Utils();

    private Timeline timeline_chrono;
    private Duration timeRemaining = Duration.ZERO;
    private boolean isRunning = false;

    private static ScoreboardController instance;
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
        //duree_minute = formatTimeNumber();
        //duree_minute = duree_match;
        //duree_seconde = 0;
        //label_time.setText(formatTime(duree_minute, duree_seconde));
        label_time.setVisible(false);
    }
/*
    public void setupTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeRemaining = timeRemaining.subtract(Duration.seconds(1));
            if (timeRemaining.lessThanOrEqualTo(Duration.ZERO)) {
                timeRemaining = Duration.ZERO;
                stopTimer();
            }
            updateTimerLabel();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    public void startTimer() {
        if (!isRunning) {
            timeline.play();
            isRunning = true;
        }
    }

    public void pauseTimer() {
        if (isRunning) {
            isPaused = !isPaused;
        }
    }

    public void playTimer() {
        if (isRunning) {
            isPaused = false;
        }
    }
    public void addMinuteToTimer() {
        timeRemaining = timeRemaining.add(Duration.minutes(1));
        updateTimerLabel();
    }

    public void stopTimer() {
        timeline.stop();
        isRunning = false;
    }

    public void resetTimer() {
        timeline.stop();
        timeRemaining = Duration.ZERO;
        updateTimerLabel();
        isRunning = false;
    }

    public void updateTimerLabel() {
        int minutes = (int) timeRemaining.toMinutes();
        int seconds = (int) (timeRemaining.toSeconds() % 60);
        String formattedTime = String.format("%02d:%02d", minutes, seconds);
        Platform.runLater(() -> label_time.setText(formattedTime));
    }*/

    //  TODO : HOTFIX - Debug chrono

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

        duree_minute = formatTimeNumber();
        duree_seconde = 0;
        label_time.setVisible(true);
        label_time.setText(formatTime(duree_minute, duree_seconde));

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
            Platform.runLater(() -> label_time.setText(formatTime(duree_minute, duree_seconde)));

            if (duree_minute == 0 && duree_seconde == 5){
                label_time.setTextFill(javafx.scene.paint.Color.RED);
                animateChronoLabel(label_time);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Pause Chrono
     */
    public void pauseChrono(){
        if (timeline != null) {
            timeline.pause();
            label_time.setTextFill(javafx.scene.paint.Color.RED);
        }
        isPaused = true;

    }

    /**
     * Play Chrono
     */
    public void playChrono(){
        if (timeline != null) {
            label_time.setTextFill(javafx.scene.paint.Color.BLACK);
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
        duree_minute = 0;
        duree_seconde = 0;
        label_time.setText(formatTime(duree_minute, duree_seconde));
        label_time.setTextFill(javafx.scene.paint.Color.BLACK);
        animateChronoLabel(label_time);
        isPaused = true;
    }

    /**
     * Add Time
     */
    public void updateChrono(){
       if (isPaused){
           return;
       }
        duree_minute++;
    }

    /**
     * Del Time
     */
    public void removeTime(){
        if (isPaused){
            return;
        }
        duree_minute--;
        if (duree_minute < 0){
            duree_minute=0;
        }
    }

    /**
     * Reset timer
     */
    public void resetTimer(){
        duree_minute=0;
        duree_seconde=0;
        stopChrono();
        timeline.stop();
        label_time.setText(String.format("%02d:%02d", duree_minute, duree_seconde));
    }

    /**
     * Get Timer for board man
     */
    public String getTimer(){
        String time_chrono = String.format("%02d:%02d", duree_minute, duree_seconde);
        return time_chrono;
    }

    private void animateChronoLabel(Label label) {
        Timeline timeline_chrono = new Timeline(
                new KeyFrame(Duration.seconds(0.5), e -> label.setVisible(false)),
                new KeyFrame(Duration.seconds(1.0), e -> label.setVisible(true))
        );
        timeline_chrono.setCycleCount(Animation.INDEFINITE);
        timeline_chrono.play();
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(e -> timeline_chrono.stop());
        label_time.setTextFill(javafx.scene.paint.Color.BLACK);
        pause.play();
        //timeline_chrono.stop();
    }

    /**
     * Recuperation dernier point
     * @return
     */
    public int getLastAvantageCbt1(){
        return Integer.parseInt(cbt_1_avantage.getText().trim());
    }
    public int getLastAvantageCbt2(){
        return Integer.parseInt(cbt_2_avantage.getText().trim());
    }
    public int getLastPenaliteCbt1(){
        return Integer.parseInt(cbt_1_penalite.getText().trim());
    }
    public int getLastPenaliteCbt2(){
        return Integer.parseInt(cbt_2_penalite.getText().trim());
    }
    public int getLastPointCbt1(){
        return Integer.parseInt(label_point_c1.getText().trim());
    }
    public int getLastPointCbt2(){
        return Integer.parseInt(label_point_c2.getText().trim());
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
        if (Integer.parseInt(cbt_1_avantage.getText()) < 0){
            cbt_1_avantage.setText(String.valueOf(0));
            app_utils.warningAlertDialog("AVRTISSEMENT","POINT LIMITER A 0");
        }
    }

    public void ajoutPenaliteC1(Integer penalite_point){
        int dernier_penalite = Integer.parseInt(cbt_1_penalite.getText().trim());
        cbt_1_penalite.setText(String.valueOf(dernier_penalite + penalite_point));
        if (Integer.parseInt(cbt_1_penalite.getText()) == 2){
            int avantage_point_cbt2 = Integer.parseInt(cbt_2_avantage.getText()) + 1;
            cbt_2_avantage.setText(String.valueOf(avantage_point_cbt2));
        } else if(Integer.parseInt(cbt_1_penalite.getText()) == 3){
            int point_cbt2 = Integer.parseInt(label_point_c2.getText()) + 2;
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
        if(Integer.parseInt(cbt_1_penalite.getText()) < 0){
            cbt_1_penalite.setText(String.valueOf(0));
            app_utils.warningAlertDialog("AVRTISSEMENT","POINT LIMITER A 0");
        }
    }

    /**
     * handle point combattant 1
     */
    public void handleAddPointsCombattant1(Integer point_combattant){
        label_point_c1.setText(String.valueOf(point_combattant + Integer.parseInt(label_point_c1.getText())));
    }

    public void handleDelPointsCombattant1(Integer point_combattant){
        label_point_c1.setText(String.valueOf(Integer.parseInt(label_point_c1.getText()) - point_combattant));
        if (Integer.parseInt(label_point_c1.getText()) < 0){
            label_point_c1.setText(String.valueOf(0));
            app_utils.warningAlertDialog("AVRTISSEMENT","POINT LIMITER A 0");
        }
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
        if (Integer.parseInt(cbt_2_avantage.getText()) < 0){
            cbt_2_avantage.setText(String.valueOf(0));
            app_utils.warningAlertDialog("AVRTISSEMENT","POINT LIMITER A 0");
        }
    }

    /**
     * Handle Penalite Combattant 2
     */

    public void ajoutPenaliteC2(Integer penalite_point){
        int dernier_penalite = Integer.parseInt(cbt_2_penalite.getText().trim());
        cbt_2_penalite.setText(String.valueOf(dernier_penalite + penalite_point));
        if(Integer.parseInt(cbt_2_penalite.getText()) == 2){
            int avantage_point_cbt1 = Integer.parseInt(cbt_1_avantage.getText())  + 1;
            cbt_1_avantage.setText(String.valueOf(avantage_point_cbt1));
        } else if(Integer.parseInt(cbt_2_penalite.getText()) == 3){
            int point_cbt1 = Integer.parseInt(label_point_c1.getText()) + 2;
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
        if (Integer.parseInt(cbt_2_penalite.getText()) < 0){
            cbt_2_penalite.setText(String.valueOf(0));
            app_utils.warningAlertDialog("AVRTISSEMENT","POINT LIMITER A 0");
        }
    }

    /**
     * Handle Point combattant 2
     */

    public void handleAddPointsCombattant2(Integer point_combattant){
        label_point_c2.setText(String.valueOf(point_combattant + Integer.parseInt(label_point_c2.getText())));
    }

    public void handleDelPointsCombattant2(Integer point_combattant){
        label_point_c2.setText(String.valueOf(Integer.parseInt(label_point_c2.getText()) - point_combattant));
        if (Integer.parseInt(label_point_c2.getText()) < 0){
            label_point_c2.setText(String.valueOf(0));
            app_utils.warningAlertDialog("AVRTISSEMENT","POINT LIMITER A 0");
        }
    }


    /**
     * Animation victoire
     */
    Duration duree_anim = Duration.seconds(1.3);
    private void afficheVictoire(ImageView node, Duration duration) {
        Timeline timeline_victoire = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(node.visibleProperty(), true)),
                new KeyFrame(duration.divide(2), new KeyValue(node.visibleProperty(), false)),
                new KeyFrame(duration, new KeyValue(node.visibleProperty(), true))
        );

        timeline_victoire.setCycleCount(Timeline.INDEFINITE);
        timeline_victoire.play();
        PauseTransition pause = new PauseTransition(Duration.seconds(5.5));
        pause.setOnFinished(e -> timeline_victoire.stop());
        pause.play();
        //timeline_victoire.stop();

    }

    /**
     * Annonce victoire
     * Format : VICTOIRE_COMBATTANT_1 :
     * Ex : SUBMISSION_COMBATTANT_1
     */
    public void annonceVictoire(Victoire cbt_victoire){
        switch (cbt_victoire){
            case SUBMISSION_COMBATTANT_1 -> {
                SUBMISSION_C1.setVisible(true);
                afficheVictoire(SUBMISSION_C1, duree_anim);
                pauseChrono();
            }
            case SUBMISSION_COMBATTANT_2 -> {
                SUBMISSION_C2.setVisible(true);
                afficheVictoire(SUBMISSION_C2, duree_anim);
                pauseChrono();
            }
            case POINT_COMBATTANT_1 -> {
                POINT_C1.setVisible(true);
                afficheVictoire(POINT_C1, duree_anim);
                pauseChrono();
            }
            case POINT_COMBATTANT_2 -> {
                POINT_C2.setVisible(true);
                afficheVictoire(POINT_C2, duree_anim);
                pauseChrono();
            }
            case ABANDON_COMBATTANT_1 -> {
                ABANDON_C1.setVisible(true);
                afficheVictoire(ABANDON_C1, duree_anim);
                pauseChrono();
            }
            case ABANDON_COMBATTANT_2 -> {
                ABANDON_C2.setVisible(true);
                afficheVictoire(ABANDON_C2, duree_anim);
                pauseChrono();
            }
            case DISQUALIFICATION_COMBATTANT_1 -> {
                DISQUALIFIE_C1.setVisible(true);
                afficheVictoire(DISQUALIFIE_C1, duree_anim);
                pauseChrono();
            }
            case DISQUALIFICATION_COMBATTANT_2 -> {
                DISQUALIFIE_C2.setVisible(true);
                afficheVictoire(DISQUALIFIE_C2, duree_anim);
                pauseChrono();
            }
            case MATCH_NULL -> {
                MATCH_NULL_C1.setVisible(true);
                MATCH_NULL_C2.setVisible(true);
                afficheVictoire(MATCH_NULL_C1, duree_anim);
                afficheVictoire(MATCH_NULL_C2, duree_anim);
                pauseChrono();
            }
            //default -> stopChrono();
        }

    }

    /**
     * Capture d'ecran a la fin du match
     * @param Stage
     */
    public void captureResultat(Stage stage){
        if (null != stage && stage.sceneProperty() != null){
            Scene scene_en_cour = stage.getScene();
            int width = (int) scene_en_cour.widthProperty().get();
            int height = (int) scene_en_cour.heightProperty().get();
            WritableImage image = scene_en_cour.snapshot(new WritableImage(width, height));
            File file_image = new File(java.time.LocalDate.now()+"-DATA.png");
            System.out.println("IMAGE : " + file_image);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file_image);
                app_utils.successAlertDialog("SUCCESS","CAPTURE BIEN ENREGISTRER");
            } catch (IOException e) {
                app_utils.erreurAlertDialog("ERREUR","ERREUR DU CAPTURE");
                e.printStackTrace();
            }
        }
    }

    /**
     * Reset ScoreBoard pour match suivant
     */
    String default_value = String.valueOf(0);
    public boolean resetScoreBoard(){
        label_combattant_1.setText("");
        label_club_cbt1.setText("");
        cbt_1_avantage.setText(default_value);
        cbt_1_penalite.setText(default_value);
        label_point_c1.setText(default_value);

        SUBMISSION_C1.visibleProperty().setValue(false);
        SUBMISSION_C1.managedProperty().setValue(false);
        SUBMISSION_C1.setVisible(false);
        SUBMISSION_C2.visibleProperty().setValue(false);
        SUBMISSION_C2.setVisible(false);

        DISQUALIFIE_C1.setVisible(false);
        DISQUALIFIE_C2.setVisible(false);

        POINT_C1.setVisible(false);
        POINT_C2.setVisible(false);

        MATCH_NULL_C1.setVisible(false);
        MATCH_NULL_C2.setVisible(false);

        ABANDON_C1.setVisible(false);
        ABANDON_C2.setVisible(false);

        label_combattant_2.setText("");
        label_club_cbt2.setText("");
        cbt_2_avantage.setText(default_value);
        cbt_2_penalite.setText(default_value);
        label_point_c2.setText(default_value);

        resetTimer();
        stopChrono();
        label_time.setVisible(false);
        //label_time.setText(formatTime(00,00));
        return true;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
