package model;

import java.sql.SQLException;
import java.util.Set;

public interface DataManager<T> {

    void add(T item) throws SQLException;

    void updateLastAccess(T identifier, String lastAccess) throws SQLException;

    void remove(String identifier) throws SQLException;

    Set<T> getList();
    
    void clear();
}
