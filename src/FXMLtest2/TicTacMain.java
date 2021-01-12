package FXMLtest2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.image.*;


public class TicTacMain extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	Stage window;
	Image mine = new Image("file:images/mine.png");
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("FXMLTesting");
		
		Parent root = FXMLLoader.load(getClass().getResource("TicTacView2.fxml"));
		Scene scene = new Scene(root);
		window.setScene(scene);
		window.show();
	}

}
