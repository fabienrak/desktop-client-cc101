package org.app.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.app.model.Combattants;
import org.app.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CombattantService {

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();

    /**
     * Get List combattant
     * @return list_combattant
     */
    public Service<List<Combattants>> getCombattantsData() {
        Service<List<Combattants>> combattant_service = new Service(){
            @Override
            protected Task<List<Combattants>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<Combattants> call() {
                        String liste_combattant_query = "SELECT id_cb, nom, prenom, date_naissance, genre, poids, grade, nom_club, categorie FROM combattant INNER JOIN club ON combattant.club_id=id_clb INNER JOIN categorie_poids ON combattant.categorie_id=id_cp";
                        List<Combattants> liste_combattants = new ArrayList<>();

                        try {
                            preparedStatement = connection.prepareStatement(liste_combattant_query);
                            resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                Combattants combattant = new Combattants(
                                        resultSet.getInt("id_cb"),
                                        resultSet.getString("nom"),
                                        resultSet.getString("prenom"),
                                        resultSet.getString("date_naissance"),
                                        resultSet.getString("genre"),
                                        resultSet.getInt("poids"),
                                        resultSet.getString("grade"),
                                        resultSet.getString("nom_club"),
                                        resultSet.getString("categorie"));
                                liste_combattants.add(combattant);
                            }

                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        return liste_combattants;
                    }
                };
            }
        };
        return combattant_service;
    };

    /**
     * Get last ID combattant registered
     * @return last_id_combattant
     */
    public Service<Integer> getLastCombattantId(){
        return new Service<Integer>() {
            @Override
            protected Task<Integer> createTask() {
                return new Task<Integer>() {
                    @Override
                    protected Integer call() throws Exception {
                        String last_id_combattant_query = "SELECT seq FROM sqlite_sequence WHERE name = 'combattant'";
                        int _return_value = -1;
                        try {
                            preparedStatement = connection.prepareStatement(last_id_combattant_query);
                            resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                _return_value = resultSet.getInt("seq");
                            }
                        } catch (SQLException sqlException) {
                            sqlException.printStackTrace();
                        }
                        return _return_value;
                    }
                };
            }
        };
    }

    /**
     * Add new Combattant
     * @param combattants
     * @return nouveau_combattant
     */
    public Service<Boolean> addNewCombattant(Combattants combattants){
        return new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() {
                        String ajout_combattant_query = "INSERT INTO combattant (nom, prenom, date_naissance, genre, poids, grade, club_id, categorie_id) VALUES(?,?,?,?,?,?,?,?)";

                        try {
                            preparedStatement = connection.prepareStatement(ajout_combattant_query);
                            preparedStatement.setString(1, combattants.getNom_combattant());
                            preparedStatement.setString(2, combattants.getPrenom_combattant());
                            preparedStatement.setString(3, combattants.getDate_naissance_combattant());
                            preparedStatement.setString(4, combattants.getGenre_combattant());
                            preparedStatement.setInt(5, combattants.getPoids_combattant());
                            preparedStatement.setString(6, combattants.getGrade_combattant());
                            preparedStatement.setInt(7, combattants.getId_club_combattant());
                            preparedStatement.setInt(8, combattants.getId_category_combattant());

                            preparedStatement.executeUpdate();
                            return true;
                        } catch (SQLException sqlException) {
                            sqlException.printStackTrace();
                            return false;
                        }
                    }
                };
            }
        };
    }

    /**
     * Delete combattant
     * @param combattants
     * @return deleted_combattant
     */
    public Service<Boolean> deleteCombattant(Combattants combattants){
        return new Service<>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<>() {
                    @Override
                    protected Boolean call() {
                        String delete_combattant_query = "DELETE FROM combattant WHERE id_cb = ?";
                        try {
                            preparedStatement = connection.prepareStatement(delete_combattant_query);
                            preparedStatement.setInt(1, combattants.getId_combattant());
                            preparedStatement.executeUpdate();
                            return true;
                        } catch (SQLException sqlException) {
                            sqlException.printStackTrace();
                            return false;
                        }
                    }
                };
            }
        };
    }
}
