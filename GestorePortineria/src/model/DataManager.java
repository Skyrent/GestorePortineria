package model;

import java.sql.SQLException;
import java.util.Set;

public interface DataManager<T> {

    void add(T item) throws SQLException;

    Set<T> getList();
    
}
