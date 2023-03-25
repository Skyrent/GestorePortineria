package controller;

import java.sql.SQLException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.DataManager;
import model.Employee;
import model.implementation.EmployeeImpl;
import model.implementation.EmployeesManagerImpl;

public class AddEmployeeControl {

	    @FXML
	    private TextField txtName;

	    @FXML
	    private TextField txtPassword;

	    @FXML
	    private TextField txtSurname;

	    @FXML
	    private TextField txtUsername;
	    
	    @FXML
	    private Text wrongNewUser;

	    private DataManager<Employee> employeesManager = new EmployeesManagerImpl();
	    
	    @FXML
	    void addUser(ActionEvent event) throws SQLException {
	    	if (!txtName.getText().isBlank() || 
	    		!txtSurname.getText().isBlank() || 
	    		!txtUsername.getText().isBlank() || 
	    		!txtPassword.getText().isBlank()) {
	    		employeesManager.add(new EmployeeImpl(txtName.getText(),
	    											  txtSurname.getText(), 
	    	    									  txtUsername.getText(),
	    	    									  txtPassword.getText(),									  
	    	    									  Optional.empty()));
	    	    txtName.getScene().getWindow().hide();
	    	}
	    	else
	    		wrongNewUser.setText("Errore: dati mancanti!");
	}
}
