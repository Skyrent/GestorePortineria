package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;

public interface DataManager<T> {
	
    static final String url = "jdbc:sqlite:database.db";

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
    
    void add(T item) throws SQLException;

    Set<T> getList();
    
}
