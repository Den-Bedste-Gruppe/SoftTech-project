import java.io.IOException;

import javafx.application.Application;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.input.*;

public class MinesweeperGameSimple extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	private Button button = new Button();
	Stage window;

	@Override
	public void start(Stage primaryStage) throws IOException {
		window = primaryStage;
		primaryStage.setTitle("Close?");
		Parent root = FXMLLoader.load(MinesweeperGameSimple.class.getResource("view.fxml"));
		this.button.setText("->   Close   <-");
		this.button.setOnAction(e -> {
			System.out.println("Closed!");
			window.close();
		});
		
		StackPane root1 = new StackPane();
		root1.getChildren().add(this.button);
		Scene scene = new Scene(root1, 500, 350);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
}
