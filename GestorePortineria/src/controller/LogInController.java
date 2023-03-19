package controller;

import java.sql.SQLException;
import application.Launcher;
import javafx.fxml.FXML;
import model.EmployeesManager;
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

    private EmployeesManager employees = new EmployeesManagerImpl();
    
    
    @FXML
    public void userLogIn(ActionEvent event) throws Exception {
		if (employees.getList().stream()
							 	.filter(x -> x.canLogIn(this.username.getText(), 
											 			this.password.getText()))
							 	.anyMatch(x -> true))
    		changeScene();
    	else
     		wrongLogIn.setText("Credenziali non valide!");
 	}
 	
	private void changeScene() throws Exception {
 		Launcher app = new Launcher();
 			 app.changeScene("/view/MainPage.fxml");
 	}
  
}