package org.app.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.app.model.Competition;
import org.app.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompetitionService {

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();

    public Service<List<Competition>> getCompetitionData(){
        Service<List<Competition>> competition_service = new Service(){
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected List<Competition> call() throws Exception {
                        String liste_competition_query = "SELECT * FROM competition";
                        List<Competition> liste_competition = new ArrayList<>();
                        try {
                            preparedStatement = connection.prepareStatement(liste_competition_query);
                            resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                Competition competition = new Competition(
                                        resultSet.getInt("id_cpt"),
                                        resultSet.getString("nom_competition"),
                                        resultSet.getString("date_debut"),
                                        resultSet.getString("date_fin"),
                                        resultSet.getString("lieu_competition")
                                );
                                liste_competition.add(competition);
                            }
                        } catch (SQLException sqlException){
                            sqlException.printStackTrace();
                        }
                        return liste_competition;
                    }
                };
            }
        };
        return competition_service;
    }

    public Service<Integer> getLastCompetitionId(){
        return new Service<Integer>() {
            @Override
            protected Task<Integer> createTask() {
                return new Task<Integer>() {
                    @Override
                    protected Integer call() throws Exception {
                        String last_id_competition_query = "SELECT seq FROM sqlite_sequence WHERE name = 'competition'";
                        int _return_value = -1;
                        try {
                            preparedStatement = connection.prepareStatement(last_id_competition_query);
                            resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()){
                                _return_value = resultSet.getInt("seq");
                            }
                        } catch (SQLException sqlException){
                            sqlException.printStackTrace();
                        }
                        return _return_value;
                    }
                };
            }
        };
    }

    public Service<Boolean> addNewCompetition(Competition competition){
        return new Service<Boolean>(){
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>(){
                    @Override
                    protected Boolean call() throws Exception {
                        String ajout_competition_query = "INSERT INTO competition (nom_competition, date_debut, date_fin, lieu_competition) VALUES (?,?,?,?)";
                        try {
                            preparedStatement = connection.prepareStatement(ajout_competition_query);
                            preparedStatement.setString(1, competition.getNom_competition());
                            preparedStatement.setString(2, competition.getDate_debut());
                            preparedStatement.setString(3, competition.getDate_fin());
                            preparedStatement.setString(4, competition.getLieu_competition());
                            preparedStatement.executeUpdate();
                            return true;
                        } catch (SQLException sqlException){
                            sqlException.printStackTrace();
                        }
                        return false;
                    }
                };
            }
        };
    }
}
