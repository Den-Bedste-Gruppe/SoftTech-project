import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	Stage window;

	@Override
	public void start(Stage primaryStage) throws IOException {
	
		MineSweeperController.setGame(new MineSweeperGame(10, 10, 5));
		primaryStage = FXMLLoader.load(main.class.getResource("grid2.fxml"));
		primaryStage.getScene().getStylesheets().add("buttonStyle.css");
		

		primaryStage.show();
		
		
	}
}
