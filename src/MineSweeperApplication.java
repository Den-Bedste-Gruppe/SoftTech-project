import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class MineSweeperApplication extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	static Stage window;

	@Override
	public void start(Stage primaryStage) throws IOException {
		window = primaryStage;
		MineSweeperController.setGame(new MineSweeperGame(10, 10, 20));
		window = FXMLLoader.load(MineSweeperApplication.class.getResource("grid2.fxml"));
		window.getScene().getStylesheets().add("buttonStyle.css");
		
		window.show();
		
		
	}
}
