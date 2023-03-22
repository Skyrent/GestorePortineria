package model.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import model.DataManager;
import model.Note;
import model.NoteManager;

public class NoteManagerImpl implements NoteManager {

	private static final Set<Note> notes = new HashSet<>();
	
	public NoteManagerImpl() {
		if (NoteManagerImpl.notes.isEmpty())
			this.getData();
	}
	
    private void getData() {
		try (Connection connection = DataManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT text, date FROM Text")) {
			while (resultSet.next()) {
				String text = resultSet.getString("text");
				String date = resultSet.getString("date");
				Note note = new NoteImpl(text, date);
				notes.add(note);
			}
		} catch (SQLException e) {
		System.out.println("Errore nella lettura del Database: " + e); 
		}
    }
    
	@Override
	public void add(Note note) throws SQLException {
        try (Connection connection = DataManager.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Text (text, date) VALUES (?, ?)")) {
                insertStatement.setString(1, note.getNote());
                insertStatement.setString(2, note.getCreationDate());
                insertStatement.executeUpdate();
                insertStatement.close();
                notes.add(note);
        } catch (SQLException e) {
            System.out.println("Errore nell'aggiunta di una chiave al database: " + e);
        }
        		
	}

	@Override
	public Set<Note> getList() {
		return NoteManagerImpl.notes;
	}

}
