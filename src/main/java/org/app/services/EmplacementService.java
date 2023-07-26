package org.app.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.app.model.Emplacement;
import org.app.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmplacementService {

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();

    public Service<List<Emplacement>> getEmplacementDataService() {
        Service<List<Emplacement>> emplacementService = new Service(){
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected List<Emplacement> call() {
                        String liste_emplacement_query = "SELECT * FROM emplacement";
                        List<Emplacement> liste_emplacement = new ArrayList<>();
                        try {
                            preparedStatement = connection.prepareStatement(liste_emplacement_query);
                            resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                Emplacement emplacement = new Emplacement(
                                  resultSet.getInt("id_tatami"),
                                  resultSet.getString("nom_emplacement"),
                                  resultSet.getString("couleur_tatami")
                                );
                                liste_emplacement.add(emplacement);
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        return liste_emplacement;
                    }
                };
            }
        };
        return emplacementService;
    }

    public Service<Integer> getLastEmplacementId(){
        return new Service<Integer>() {
            @Override
            protected Task<Integer> createTask() {
                return new Task<Integer>() {
                    @Override
                    protected Integer call() throws Exception {
                        String last_id_emplacement_query = "SELECT seq FROM sqlite_sequence WHERE name = 'emplacement'";
                        int _return_value = -1;
                        try {
                            preparedStatement = connection.prepareStatement(last_id_emplacement_query);
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

    public Service<Boolean> addNewEmplacement(Emplacement emplacement){
        return new Service<Boolean>(){
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        String ajout_emplacement_query = "INSERT INTO emplacement (nom_emplacement, couleur_tatami) VALUES (?,?)";
                        try {
                            preparedStatement = connection.prepareStatement(ajout_emplacement_query);
                            preparedStatement.setString(1,emplacement.getNom_emplacement());
                            preparedStatement.setString(2,emplacement.getCouleur_tatami());
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
