package org.app.services;

import org.app.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MatchService {

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();


}
