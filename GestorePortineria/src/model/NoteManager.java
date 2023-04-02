package model;

import java.sql.SQLException;
import java.util.Set;

public interface NoteManager extends DataManager<Note> {

	@Override
    void add(Note item) throws SQLException;

    @Override
    Set<Note> getList();
}
