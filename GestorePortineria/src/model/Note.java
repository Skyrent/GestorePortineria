package model;

import java.sql.SQLException;

public interface Note {
	
    void updateNote(String note) throws SQLException;
    
    String getNote();

}
