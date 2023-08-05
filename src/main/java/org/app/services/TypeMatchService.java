package org.app.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.app.model.Categorie;
import org.app.model.Clubs;
import org.app.model.TypeMatch;
import org.app.model.TypeMatchModel;
import org.app.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TypeMatchService {

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();

    /**
     * Get all type match
     * @return service - type_match
     */
    public Service<List<TypeMatchModel>> getTypeMatchData(){
        Service<List<TypeMatchModel>> type_match_service = new Service<>() {
            @Override
            protected Task<List<TypeMatchModel>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<TypeMatchModel> call() {
                        String liste_type_match_query = "SELECT id, nom_type_match FROM type_match";
                        List<TypeMatchModel> tm_data = new ArrayList<>();
                        try {
                            preparedStatement = connection.prepareStatement(liste_type_match_query);
                            resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                TypeMatchModel type_match_data = new TypeMatchModel(
                                        resultSet.getInt("id"),
                                        resultSet.getString("nom_type_match")
                                );
                                tm_data.add(type_match_data);
                            }
                        } catch (SQLException sqlException) {
                            sqlException.printStackTrace();
                        }
                        return tm_data;
                    }
                };
            }
        };
        return type_match_service;
    }

    /**
     * Get last ID
     */
    public Service<Integer> getLastTypeMatchId(){
        return new Service<Integer>() {
            @Override
            protected Task<Integer> createTask() {
                return new Task<Integer>() {
                    @Override
                    protected Integer call() throws Exception {
                        String last_typematch_id_query = "SELECT seq FROM sqlite_sequence WHERE name = 'type_match'";
                        int _return_value = -1;
                        try {
                            preparedStatement = connection.prepareStatement(last_typematch_id_query);
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

    /**
     * Add new type match
     */
    public Service<Boolean> addNewTypeMatch(TypeMatchModel typeMatchModel){
        return new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        String ajout_type_match_query = "INSERT INTO type_match (nom_type_match) VALUES(?)";
                        try {
                            preparedStatement = connection.prepareStatement(ajout_type_match_query);
                            preparedStatement.setString(1, typeMatchModel.getNom_type_match());
                            preparedStatement.executeUpdate();
                            return true;
                        } catch (SQLException sqlException) {
                            sqlException.printStackTrace();
                        }
                        return false;
                    }
                };
            }
        };
    }

    /**
     * Delete type match
     */
    public Service<Boolean> deleteTypeMatch(TypeMatchModel typeMatchModel){
        return new Service<Boolean>(){
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>(){
                    @Override
                    protected Boolean call() throws Exception {
                        String delete_type_match_query = "DELETE FROM type_match WHERE id = ?";
                        try{
                            preparedStatement = connection.prepareStatement(delete_type_match_query);
                            preparedStatement.setInt(1,typeMatchModel.getId());
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
