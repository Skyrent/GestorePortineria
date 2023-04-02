package model.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DataManager;

public class CreateDatabase {
    public static void create() {

        try (Connection connection = DataManager.getConnection()) {

        	PreparedStatement employeeStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Employees (" +
            		 												   		  "id INTEGER PRIMARY KEY, " +
            		 												   		  "name TEXT(50), " +
            		 												   		  "surname TEXT(50), " +
            		 												   		  "username TEXT(50) UNIQUE, " +
            		 												   		  "password TEXT(50), " +
            		 												   		  "lastAccess TEXT(50))");
        	employeeStatement.execute();
        	employeeStatement.close();

            PreparedStatement countStatement = connection.prepareStatement("SELECT COUNT(*) FROM Employees");
            ResultSet resultSet = countStatement.executeQuery();

            if (resultSet.getInt(1) == 0) {
                PreparedStatement inserteEmployeeStmt = connection.prepareStatement("INSERT OR IGNORE INTO Employees (name, surname, username, password, lastAccess) " +
                                                                           			"VALUES (?, ?, ?, ?, ?)");
                inserteEmployeeStmt.setString(1, "admin");
                inserteEmployeeStmt.setString(2, "admin");
                inserteEmployeeStmt.setString(3, "admin");
                inserteEmployeeStmt.setString(4, "admin");
                inserteEmployeeStmt.setString(5, null);
                inserteEmployeeStmt.execute();
                inserteEmployeeStmt.close();
            }

            resultSet.close();
            countStatement.close();

            PreparedStatement keyStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Keys (" +
            															 "id INTEGER PRIMARY KEY, " +
            															 "tag TEXT(50) UNIQUE, " +
            															 "holder TEXT(50), " +
                                                              			 "lastAccess TEXT(50))");
            keyStatement.execute();
            keyStatement.close();

            PreparedStatement textStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Text (" +
            															  "id INTEGER PRIMARY KEY, " +
            															  "text TEXT(1000), " +
            															  "date TEXT(50))");
            textStatement.execute();
            textStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
