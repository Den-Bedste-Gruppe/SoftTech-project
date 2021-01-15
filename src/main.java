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
	
		MineSweeperController.setGame(new MineSweeperGame(4,4, 1));
		primaryStage = FXMLLoader.load(main.class.getResource("grid2.fxml"));
		primaryStage.getScene().getStylesheets().add("buttonStyle.css");
		

		primaryStage.show();
		
		
	}
}
