package main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		primaryStage.setTitle("MineSweeper Game");
		Scene game = FXMLLoader.load(Main.class.getResource("../views/menu.fxml"));

		primaryStage.setScene(game);
		primaryStage.setHeight(600);
		primaryStage.setWidth(800);

		primaryStage.show();
	}
}
