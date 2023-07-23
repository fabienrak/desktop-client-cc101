package org.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ScoreboardController {

    @FXML
    private Label label_combattant_1;
    @FXML
    private Label label_combattant_2;
    private Stage ScoreboardStage;

    public void afficheCombattant_1(String combattant_1){
        label_combattant_1.setText(combattant_1);
    }

    public void afficheCombattant_2(String combattant_2){
        label_combattant_2.setText(combattant_2);
    }



}
