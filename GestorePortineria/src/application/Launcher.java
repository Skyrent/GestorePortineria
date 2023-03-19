package application;
	
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.implementation.CreateDatabase;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Launcher extends Application {
	
	private static Stage stage;	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			stage.setResizable(false);
			changeScene("/view/LogIn.fxml");
			stage.setTitle("Gestore Portineria");
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void changeScene(String fxml) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(fxml));
		stage.setScene(new Scene(root));
		stage.getScene().setRoot(root);
		stage.getScene().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
	} 
	
	public void launchApp() throws Exception {
			CreateDatabase.create();
		 	launch();
	}
}