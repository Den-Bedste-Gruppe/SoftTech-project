import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Node;

public class menuController {

	@FXML
	private Button close;
	
	public void createEasyGame (ActionEvent event) throws IOException {
		MineSweeperController.setGame(new MineSweeperGame(5,5, 5));
		Scene game = FXMLLoader.load(main.class.getResource("grid2.fxml"));
		
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(game);
		stage.getScene().getStylesheets().add("buttonStyle.css");
		stage.show();
	}
	
	public void createMediumGame (ActionEvent event) throws IOException {
		MineSweeperController.setGame(new MineSweeperGame(12,12, 30));
		Scene game = FXMLLoader.load(main.class.getResource("grid2.fxml"));
		
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(game);
		stage.getScene().getStylesheets().add("buttonStyle.css");
		stage.show();
		stage.setHeight(600);
		stage.setWidth(800);
	}
	
	public void createHardGame (ActionEvent event) throws IOException {
		MineSweeperController.setGame(new MineSweeperGame(30,30, 100));
		Scene game = FXMLLoader.load(main.class.getResource("grid2.fxml"));
		
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(game);
		stage.getScene().getStylesheets().add("buttonStyle.css");
		stage.show();
		stage.setHeight(600);
		stage.setWidth(800);
	}
	
	public void close() {
		System.out.println("Closed!");
		Stage stage = (Stage) close.getScene().getWindow();
		stage.close();
	}
}
