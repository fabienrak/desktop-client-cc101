package org.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private Button btn_menu_club;
    @FXML
    private Button btn_menu_categorie;
    @FXML
    private Button btn_menu_combattant;
    @FXML
    private Button btn_menu_competition;
    @FXML
    private Button btn_menu_emplacement;
    @FXML
    private Button btn_open_scoreboard;
    @FXML
    private VBox content_home;
    @FXML
    private AnchorPane content_pane;
    private Stage stage;
    @FXML
    private void sceneCombattant(ActionEvent actionEvent) throws IOException {
        Node node_source = (Node) actionEvent.getSource();
        stage = (Stage) node_source.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/combattant/combattants.fxml"));
        stage.setTitle("GESTION COMBATTANT");
        content_pane.getChildren().removeAll();
        content_pane.getChildren().setAll(parent);
    }

    @FXML
    private void sceneCategory(ActionEvent actionEvent) throws IOException {
        Node node_source = (Node) actionEvent.getSource();
        stage = (Stage) node_source.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/categories/categorie.fxml"));
        stage.setTitle("GESTION CATEGORIES");
        content_pane.getChildren().removeAll();
        content_pane.getChildren().setAll(parent);
    }

    @FXML
    private void sceneCompetition(ActionEvent actionEvent) throws IOException {
        Node node_source = (Node) actionEvent.getSource();
        stage = (Stage) node_source.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/competition/competition.fxml"));
        stage.setTitle("GESTION COMPETITION");
        content_pane.getChildren().removeAll();
        content_pane.getChildren().setAll(parent);
    }

    @FXML
    private void sceneClub(ActionEvent actionEvent) throws IOException {
        Node node_source = (Node) actionEvent.getSource();
        stage = (Stage) node_source.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/club/club.fxml"));
        stage.setTitle("GESTION CLUB");
        content_pane.getChildren().removeAll();
        content_pane.getChildren().setAll(parent);
    }

    @FXML
    private void sceneEmplacement(ActionEvent actionEvent) throws IOException {
        Node node_source = (Node) actionEvent.getSource();
        stage = (Stage) node_source.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/emplacement/emplacement.fxml"));
        stage.setTitle("GESTION EMPLACEMENT");
        content_pane.getChildren().removeAll();
        content_pane.getChildren().setAll(parent);
    }

    @FXML
    private void sceneScoreboardManager(ActionEvent actionEvent) throws IOException {
        Node node_source = (Node) actionEvent.getSource();
        stage = (Stage) node_source.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/scoreboard/scoreboard-manager.fxml"));
        stage.setTitle("SCOREBOARD");
        content_pane.getChildren().removeAll();
        content_pane.getChildren().setAll(parent);
    }

    @FXML
    private void openScoreBoard(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/scoreboard/scoreboard.fxml"));
            Scene scoreboardScene = new Scene(fxmlLoader.load(), 1050, 650);
            Stage scoreboardStage = new Stage();
            ScoreboardController scoreboardController = fxmlLoader.getController();
            fxmlLoader.setController(scoreboardController);

            scoreboardStage.setScene(scoreboardScene);
            scoreboardStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
