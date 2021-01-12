import javafx.application.Application;
import javafx.event.*;
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
	public void start(Stage primaryStage) {
		window = primaryStage;
		primaryStage.setTitle("Close?");
		
		this.button.setText("->   Close   <-");
		this.button.setOnAction(e -> {
			System.out.println("Closed!");
			window.close();
		});
		
		StackPane root = new StackPane();
		root.getChildren().add(this.button);
		Scene scene = new Scene(root, 500, 350);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
}