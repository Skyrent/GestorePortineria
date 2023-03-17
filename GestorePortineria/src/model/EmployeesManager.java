package model;


import java.sql.SQLException;
import java.util.Set;


public interface EmployeesManager extends DataManager<Employee> {
		
	@Override
    public void add(Employee employee) throws SQLException;
            
    @Override
	public Set<Employee> getList();
    
    public void update(Employee employee, String lastAccess) throws SQLException;
    
    void remove(String username) throws SQLException;

	public void clear();

}
