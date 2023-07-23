package org.app.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {

    private static Connection connection = null;

    static {
        // TODO : utiliser sqlite
        try {
            String url_db = "jdbc:sqlite::resource:db-desktop-app.db";
            connection = DriverManager.getConnection(url_db);
            Logger.getLogger(connection.getClass().getName()).log(Level.INFO, "[+] DB CONNECT SUCCESS", url_db);
        } catch (SQLException exception) {
            Logger.getLogger(connection.getClass().getName()).log(Level.SEVERE, "[-] DATABASE ERROR", exception.getMessage());
        }
    }

    public static Connection getConnection(){
        return connection;
    }
}
