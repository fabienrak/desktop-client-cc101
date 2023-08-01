package org.app.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.app.model.Clubs;
import org.app.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClubService {

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();

    /**
     * Service get all club
     * @return service
     */
    public Service<List<Clubs>> getClubData(){
        Service<List<Clubs>> club_service = new Service(){
            @Override
            protected Task<List<Clubs>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<Clubs> call() {
                        String liste_club_query = "SELECT * FROM club";
                        List<Clubs> data_club = new ArrayList<>();
                        try {
                            preparedStatement = connection.prepareStatement(liste_club_query);
                            resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                Clubs nouveau_club = new Clubs(
                                        resultSet.getInt("id_clb"),
                                        resultSet.getString("nom_club"),
                                        resultSet.getString("adresse_club")
                                );
                                data_club.add(nouveau_club);
                            }
                        } catch (SQLException sqlException) {
                            sqlException.printStackTrace();
                        }
                        return data_club;
                    }
                };
            }
        };
        return club_service;
    };

    /**
     * Get last ID registered
     * @return last id registered
     */
    public Service<Integer> getLastIdFromClubTable(){
        return new Service<Integer>(){
            @Override
            protected Task<Integer> createTask() {
                return new Task<Integer>() {
                    @Override
                    protected Integer call() throws Exception {
                        String last_id_query = "SELECT seq FROM sqlite_sequence WHERE name = 'club'";
                        int _return_value = -1;
                        try {
                            preparedStatement = connection.prepareStatement(last_id_query);
                            resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
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

    /**
     * Add new Club
     * @param nouveau_club
     */
    public Service<Boolean> addNewClub(Clubs nouveau_club){
        return new Service<Boolean>(){
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>(){
                    @Override
                    protected Boolean call() throws Exception {
                        String ajout_club_query = "INSERT INTO club (nom_club, adresse_club) VALUES (?,?)";
                        try{
                            preparedStatement = connection.prepareStatement(ajout_club_query);
                            preparedStatement.setString(1, nouveau_club.getNom_club());
                            preparedStatement.setString(2, nouveau_club.getAdresse_club());
                            preparedStatement.executeUpdate();
                            return true;

                        } catch (SQLException sqlException){
                            sqlException.printStackTrace();
                            return false;
                        }
                    }
                };
            }
        };
    }

    /**
     * Delete club
     * @param club
     */
    public Service<Boolean> deleteClub(Clubs club){
        return new Service<Boolean>(){
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>(){
                    @Override
                    protected Boolean call() throws Exception {
                        String delete_club_query = "DELETE FROM club WHERE id_clb = ?";
                        try{
                            preparedStatement = connection.prepareStatement(delete_club_query);
                            preparedStatement.setInt(1,club.getId_club());
                            preparedStatement.executeUpdate();
                            return true;
                        } catch (SQLException sqlException){
                            sqlException.printStackTrace();
                            return false;
                        }
                    }
                };
            }
        };
    }
}
