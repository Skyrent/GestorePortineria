package model;

import java.sql.SQLException;
import java.util.Set;

public interface KeysManager extends DataManager<Key> {

	@Override
    void add(Key key) throws SQLException;

	@Override
	Set<Key> getList();

    void update(Key key) throws SQLException;

    void remove(String username) throws SQLException;

    void clear();
}
