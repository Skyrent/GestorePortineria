package controller;

import java.sql.SQLException;
import application.Main;
import javafx.fxml.FXML;
import model.*;
import model.implementation.EmployeesManagerImpl;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LogInController {
	
    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Text wrongLogIn;

    private DataManager<Employee> employees = new EmployeesManagerImpl();
    
    
    @FXML
    public void userLogIn(ActionEvent event) throws SQLException, IOException {
		if (employees.getList().stream()
							 	.filter(x -> x.canLogIn(this.username.getText(), 
											 			this.password.getText()))
							 	.anyMatch(x -> true))
    		changeScene();
    	else
     		wrongLogIn.setText("Credenziali non valide!");
 	}
 	
	private void changeScene() throws IOException {
 		Main m = new Main();
 			 m.changeScene("/view/MainPage.fxml");
 	}
  
}