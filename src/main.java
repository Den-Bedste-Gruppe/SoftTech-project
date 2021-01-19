import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		primaryStage.setTitle("MineSweeper Game");
		//MineSweeperController.setGame(new MineSweeperGame(10,10, 10));
		Scene game = FXMLLoader.load(main.class.getResource("menu.fxml"));
		primaryStage.setScene(game);
	//	primaryStage.getScene().getStylesheets().add("buttonStyle.css");
		
		primaryStage.show();
		
		
	}
}
