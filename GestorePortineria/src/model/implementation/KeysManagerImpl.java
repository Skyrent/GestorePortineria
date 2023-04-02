package model.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import model.DataManager;
import model.Key;
import model.KeysManager;

public class KeysManagerImpl implements KeysManager {

	private	static final Set<Key> keys = new HashSet<>();

    public KeysManagerImpl() {
    	if(keys.isEmpty())
    		this.getData();
    }

    private void getData() {
        if (KeysManagerImpl.keys.isEmpty()) {
			try (Connection connection = DataManager.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT tag, holder, lastAccess FROM Keys")) {
				while (resultSet.next()) {
					String tag = resultSet.getString("tag");
					String holder = resultSet.getString("holder");
					Optional<String> lastAccess = Optional.ofNullable(resultSet.getString("lastAccess"));
					Key key = new KeyImpl(tag, holder, lastAccess);
					keys.add(key);
				}
			} catch (SQLException e) {
			System.out.println("Errore nella lettura del Database: " + e);
			}
        }
    }

    @Override
    public void add(Key key) throws SQLException {
        try (Connection connection = DataManager.getConnection();
            PreparedStatement checkStatement = connection.prepareStatement("SELECT COUNT(*) FROM Keys WHERE tag = ?")) {
            checkStatement.setString(1, key.getTag());
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.getInt(1) == 0) {
                PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Keys (tag, holder, lastAccess) VALUES (?, ?, ?)");
                insertStatement.setString(1, key.getTag());
                insertStatement.setString(2, key.getHolder());
                insertStatement.setString(3, key.getLastAccess());
                insertStatement.executeUpdate();
                insertStatement.close();
                keys.add(key);
            }
            resultSet.close();
            checkStatement.close();
        } catch (SQLException e) {
            System.out.println("Errore nell'aggiunta di una chiave al database: " + e);
        }
    }

    @Override
    public void update(Key key) throws SQLException {
        try (Connection connection = DataManager.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE Keys SET holder = ?, lastAccess = ? WHERE tag = ?")) {
            updateStatement.setString(1, key.getHolder());
            updateStatement.setString(2, key.getLastAccess());
            updateStatement.setString(3, key.getTag());
            updateStatement.executeUpdate();
            keys.forEach(e -> {
            				   if(e.getTag().equals(key.getTag()))
            				   e.setHolder(key.getHolder());
				   			   });
        } catch (SQLException e) {
            System.out.println("Errore nell'aggiornamento di una chiave nel database: " + e);
        }
    }

	@Override
	public void remove(String key) throws SQLException {
        try (Connection connection = DataManager.getConnection();
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM Keys WHERE tag = ?")) {
            deleteStatement.setString(1, key);
            deleteStatement.executeUpdate();
            keys.removeIf(k -> k.getTag().equals(key));
        } catch (SQLException e) {
            System.out.println("Errore nella rimozione di una chiave dal database: " + e);
        }
    }

	@Override
	public Set<Key> getList() {
		return Collections.unmodifiableSet(keys);
	}

	@Override
	public void clear() {
		keys.clear();
	}

}
