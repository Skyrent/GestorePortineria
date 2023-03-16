package model.implementation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* Classe dedita alla connessione con il database */
public class SQLConnector {
    private static final String url = "jdbc:sqlite:database.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
