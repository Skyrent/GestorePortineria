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
import model.Employee;
import model.EmployeesManager;

public class EmployeesManagerImpl implements EmployeesManager {

    private static final Set<Employee> employees = new HashSet<>();

    public EmployeesManagerImpl() {
    	if(employees.isEmpty())
    		this.getData();
    }

    private void getData() {
        if (EmployeesManagerImpl.employees.isEmpty()) {
            try (Connection connection = DataManager.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT name, surname, username, password, lastAccess FROM Employees")) {
                    while (resultSet.next()) {
                        String name = resultSet.getString("name");
                        String surname = resultSet.getString("surname");
                        String username = resultSet.getString("username");
                        String password = resultSet.getString("password");
                        Optional<String> lastAccess = Optional.ofNullable(resultSet.getString("lastAccess"));
                        Employee employee = new EmployeeImpl(name, surname, username, password, lastAccess);
                        employees.add(employee);
                    }
            } catch (SQLException e) {
                System.out.println("Errore nella lettura del Database: " + e);
            }
        }
    }

    @Override
    public void add(Employee employee) throws SQLException {
        try (Connection connection = DataManager.getConnection();
            PreparedStatement checkStatement = connection.prepareStatement("SELECT COUNT(*) FROM Employees WHERE username = ?")) {
            checkStatement.setString(1, employee.getUsername());
            ResultSet resultSet = checkStatement.executeQuery();
            int count = resultSet.getInt(1);
            resultSet.close();
            checkStatement.close();
            if (count == 0) {
                PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Employees (name, surname, username, password, lastAccess) VALUES (?, ?, ?, ?, ?)");
                insertStatement.setString(1, employee.getName());
                insertStatement.setString(2, employee.getSurname());
                insertStatement.setString(3, employee.getUsername());
                insertStatement.setString(4, employee.getPassword());
                insertStatement.setString(5, employee.getLastAccess());
                insertStatement.executeUpdate();
                insertStatement.close();
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.out.println("Errore nell'aggiunta di un dipendente al database: " + e);
        }
    }

    @Override
    public void update(Employee employee, String lastAccess) throws SQLException {
        try (Connection connection = DataManager.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE Employees SET lastAccess = ? WHERE username = ?")) {
            updateStatement.setString(1, lastAccess);
            updateStatement.setString(2, employee.getUsername());
            updateStatement.executeUpdate();
            employees.stream()
                     .filter(e -> e.getUsername().equals(employee.getUsername()))
                     .forEach(e -> e.setLastAccess(lastAccess));
        } catch (SQLException e) {
            System.out.println("Errore nell'aggiornamento ultimo accesso del dipendente: " + e);
        }
    }

    @Override
    public void remove(String username) throws SQLException {
        try (Connection connection = DataManager.getConnection();
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM Employees WHERE username = ?")) {
            deleteStatement.setString(1, username);
            deleteStatement.executeUpdate();
            deleteStatement.close();
            employees.removeIf(e -> e.getUsername().equals(username));
        } catch (SQLException e) {
            System.out.println("Errore nella rimozione di un dipendente dal database: " + e);
        }
    }

	@Override
	public Set<Employee> getList() {
		return Collections.unmodifiableSet(employees);
	}

	@Override
	public void clear() {
		EmployeesManagerImpl.employees.clear();
	}
}
