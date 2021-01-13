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
import javafx.scene.input.*;

public class MineSweeperController {

	@FXML
	private GridPane board;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	private MineSweeperGame game;
	
	private int[] coords = new int[2];
	
	private int tileMarker;
	
	private Tile currTile;

	
	public MineSweeperController(MineSweeperGame game) {
		this.game = game;
	}

	public void initialize() {
		for (int i = 1; i <= game.getHeight(); i++) {
			for (int j = 1; j <= game.getWidth(); j++) {
				Button btn = new Button("");
				btn.setMaxSize(50, 50);
				btn.setMinSize(50, 50);
				board.add(btn, i, j);
				btn.setId("" + i + "." + j);
				btn.setOnMouseClicked(e -> TileClicked(e));
			}
		}
	}
	

	public void TileClicked(MouseEvent e) {
		//getting cordinates from button clicked, and tile model from that location
		String[] coordString = ((Control)e.getSource()).getId().split(".");
		coords[0] = Integer.parseInt(coordString[0]);
		coords[1] = Integer.parseInt(coordString[1]);
		currTile = game.getTile(coords[0], coords[1]);
		
		//for changing markers
		if(e.getButton() == MouseButton.SECONDARY) {
			currTile.incMarker();
		}
		
		//Different actions depending on if there is flag or questionmark
		tileMarker = currTile.getMarker();
		switch(tileMarker) {
		case 0:
			
		}
		
	}
	
	
	private void unmarkedTile() {
		return;
	}
	
	private void flaggedTile() {
		return;
	}
	
	private void questionTile() {
		return;
	}
	
	
	public void close() {
		System.out.println("Closed!");
		Stage stage = (Stage) board.getScene().getWindow();
		stage.close();
	}

}
