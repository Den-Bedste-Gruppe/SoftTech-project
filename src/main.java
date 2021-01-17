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
		window = primaryStage;
		MineSweeperController.setGame(new MineSweeperGame(10, 10, 5));
		window = FXMLLoader.load(main.class.getResource("grid2.fxml"));
		window.getScene().getStylesheets().add("buttonStyle.css");
		
		window.show();
		
		
	}
}
