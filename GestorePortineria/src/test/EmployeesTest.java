package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.Employee;
import model.EmployeesManager;
import model.implementation.EmployeeImpl;
import model.implementation.EmployeesManagerImpl;


class EmployeesTest {

	private EmployeesManager employeesManager;
	private Employee employee1, employee2;
	
    @BeforeEach
    void setUp() {
    	if(DatabaseTools.isPresent())
    		DatabaseTools.delete();
    	DatabaseTools.create();
        employeesManager = new EmployeesManagerImpl();
        employee1 = new EmployeeImpl("Paolo", "Rossi", "Paolo.Rossi", "password123", Optional.empty());
        employee2 = new EmployeeImpl("Silvia", "Verdi", "Silvia.Verdi", "password123", Optional.empty());
    }
	
	@Test
	void testEmployee() {
		assertTrue(employee1.getUsername().equals("Paolo.Rossi"));
		assertTrue(employee1.canLogIn(employee1.getUsername(), "password123"));
		assertTrue(employee1.logged());
		assertFalse(employee2.logged());
	}
    
    @Test
    void testAdd() {
    	assertTrue(DatabaseTools.isPresent());
        try {
			employeesManager.add(employee1);
		} catch (SQLException e) {
			 fail("Errore in EmployeesManager in testAdd");
		}
        Set<Employee> expected = new HashSet<>(employeesManager.getList());
        assertTrue(expected.contains(employee1));
        boolean b = false;
		try {
        	employeesManager.getList().add(employee2);     	
        } catch (UnsupportedOperationException e) {
        	b = true;
        }
        assertTrue(b);
    }
    
    @Test
    void testUpdate () {
    	DatabaseTools.isPresent();
    	try {
			employeesManager.add(new EmployeeImpl("Mario", "Rossi", "Mario.Rossi", "password123", Optional.of("21/03 11:15")));
		} catch (SQLException e) {
			 fail("Errore EmployeesManager in testUpdate");
		}
    	Employee test = employeesManager.getList().stream().filter(e -> e.getUsername().equals("Mario.Rossi")).findFirst().get();
    	assertEquals(test, new EmployeeImpl("Mario", "Rossi", "Mario.Rossi", "password123", Optional.of("21/03 11:15")));
    }
    
    @Test
    void testRemove() {
    	DatabaseTools.isPresent();
    	try {
        employeesManager.add(employee1);
        employeesManager.remove("Paolo.Rossi");
    	} catch (SQLException e) {
			 fail("Errore EmployeesManager in testRemove");
		}
        Set<Employee> expected = new HashSet<>(employeesManager.getList());
        assertFalse(expected.contains(employee1));
    }
}
