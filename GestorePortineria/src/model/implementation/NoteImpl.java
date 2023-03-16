package model.implementation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Note;


public class NoteImpl implements Note {
	
	private static String note;
	
	public NoteImpl() {
		this.getData();
	}
	
    private void getData() {
    
			try (Connection connection = SQLConnector.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT text FROM Text")) {
				while (resultSet.next()) {
					String text = resultSet.getString("text");
					NoteImpl.note = text;
				}
			} catch (SQLException e) {
			System.out.println("Errore nella lettura del Database: " + e); 
			}
        
    }

    public void updateNote(String note) throws SQLException {
    	try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
    		 PreparedStatement updateStatement = connection.prepareStatement("UPDATE Text SET text = ?")) {
    		    updateStatement.setString(1, note);
    		    updateStatement.executeUpdate();
    		    NoteImpl.note = note;
    		} catch (SQLException e) {
    		    System.out.println("Errore nel salvataggio delle note al database: " + e);
    		}

   	}
    
    public String getNote() {
    	return NoteImpl.note;
    }
}
