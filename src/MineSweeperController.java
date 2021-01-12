import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MineSweeperController {

	@FXML
	private GridPane board;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	private static MineSweeperGame game;

	public MineSweeperController() {
		game = new MineSweeperGame(10, 10, 10);
	}

	public MineSweeperController(int hight, int width, int bombs) {
		game = new MineSweeperGame(hight, width, bombs);
	}

	public void initialize() {
		for (int i = 1; i <= game.getHeight(); i++) {
			for (int j = 1; j <= game.getWidth(); j++) {
				Button btn = new Button("");
				btn.setMaxSize(50, 50);
				btn.setMinSize(50, 50);
				board.add(btn, i, j);
				btn.setId("" + i + "." + j);
				btn.setOnAction(e -> TicClick(e));
			}
		}
	}

	public void TicClick(ActionEvent e) {
		//System.out.println("Success");
		System.out.println(((Control)e.getSource()).getId());
	}
	public void close() {
		System.out.println("Closed!");
		Stage stage = (Stage) board.getScene().getWindow();
		stage.close();
	}

}
