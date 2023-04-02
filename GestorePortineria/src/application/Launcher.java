package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.implementation.CreateDatabase;

public class Launcher extends Application {
public static Stage stage;

	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			changeScene("/view/LogIn.fxml");
			stage.setTitle("Gestore Portineria");
			stage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void changeScene(String fxml) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(fxml));
		stage.setScene(new Scene(root));
		stage.getScene().setRoot(root);
		stage.getScene().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
 		Launcher.stage.setResizable(false);

	}

	public void launchApp() {
			CreateDatabase.create();
		 	launch();
	}
}