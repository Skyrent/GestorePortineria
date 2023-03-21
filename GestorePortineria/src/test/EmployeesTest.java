package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import model.*;
import model.implementation.*;

class EmployeesTest {

	EmployeesManager em = new EmployeesManagerImpl();
	Employee employee1 = new EmployeeImpl("Paolo", "Rossi", "Paolo.Rossi", "password123", null);
	
	
	
	@Test
	void test() {
		assertTrue(!em.getList().isEmpty());
	}

}
