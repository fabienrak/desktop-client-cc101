package org.app.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.app.model.Categorie;
import org.app.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorieService {

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();

    public Service<List<Categorie>> getCategorieData(){
        Service<List<Categorie>> club_service = new Service(){
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() {
                        String liste_categorie_query = "SELECT id_cp, categorie, nom_categorie, poids_max, age_max FROM categorie_poids";
                        List<Categorie> data_categorie = new ArrayList<>();
                        try {
                            preparedStatement =  connection.prepareStatement(liste_categorie_query);
                            resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                Categorie categorie = new Categorie(
                                        resultSet.getInt("id_cp"),
                                        resultSet.getString("categorie"),
                                        resultSet.getString("nom_categorie"),
                                        resultSet.getInt("age_max"),
                                        resultSet.getInt("poids_max")
                                );
                                data_categorie.add(categorie);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return data_categorie;
                    }
                };
            }
        };
        return club_service;
    }

    public Service<Integer> getLastCategorieId(){
        return new Service<Integer>() {
            @Override
            protected Task<Integer> createTask() {
                return new Task<Integer>() {
                    @Override
                    protected Integer call() throws Exception {
                        String last_categorie_id_query = "SELECT seq FROM sqlite_sequence WHERE name = 'categorie_poids'";
                        int _return_value = -1;
                        try {
                            preparedStatement = connection.prepareStatement(last_categorie_id_query);
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

    public Service<Boolean> addNewCategorie(Categorie categorie){
        return new Service<Boolean>(){
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        String ajout_categorie_query = "INSERT INTO categorie_poids (categorie, nom_categorie, poids_max, age_max) VALUES (?,?,?,?)";
                        try {
                            preparedStatement = connection.prepareStatement(ajout_categorie_query);
                            preparedStatement.setString(1, categorie.getNom_category());
                            preparedStatement.setString(2, categorie.getNom_sous_category());
                            preparedStatement.setInt(3, categorie.getPoids_max());
                            preparedStatement.setInt(4, categorie.getAge_max());
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
