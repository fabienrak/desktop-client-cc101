package org.app.utils;

import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
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
            String url_db = "jdbc:sqlite::resource:db-cc101.db";
            connection = DriverManager.getConnection(url_db);
            System.out.println("URL DB : " + url_db);
            Logger.getLogger(connection.getClass().getName()).log(Level.INFO, "[+] DB CONNECT SUCCESS", url_db);
        } catch (SQLException exception) {
            Logger.getLogger(connection.getClass().getName()).log(Level.SEVERE, "[-] DATABASE ERROR", exception.getMessage());
        }
    }

    public static Connection getConnection(){
        return connection;
    }
}
