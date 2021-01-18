import java.io.IOException;

import javafx.application.Application;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.input.*;

public class main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	private int counter = 0;
	private Button button = new Button();
	Stage window;

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
