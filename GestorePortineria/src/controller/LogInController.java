package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Launcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.EmployeesManager;
import model.implementation.EmployeesManagerImpl;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LogInController implements Initializable {
	
    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Text wrongLogIn;

    private EmployeesManager employees;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	employees = new EmployeesManagerImpl();
    }
    
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
 	
	private void changeScene() throws IOException  {
 		Launcher app = new Launcher();
 		Launcher.stage.setResizable(true);
 		app.changeScene("/view/MainPage.fxml"); 	
 		Launcher.stage.setResizable(false);
 	}	
}