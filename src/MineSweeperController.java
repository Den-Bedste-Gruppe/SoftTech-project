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
		

		if(currTile.isShown()) {
			return;
			//add code for the autosolving related to flags, ie check if flags in area = num of adj bombs, then clear
		}
		
		tileMarker = currTile.getMarker();
		//for changing markers
		if(e.getButton() == MouseButton.SECONDARY) {
			switch(tileMarker) {
			//flagene vil altid være de sidste childnotes i panen, så man kan bare modificere sidste element i .getChildren()
			case 0:
				game.incFlagCounter(1);
				//add flag pic to stackpane
			case 1:
				game.incFlagCounter(-1);
				//replace flag with questionmark
			case 2:
				//remove questionmark
			}
			
			currTile.incMarker();
			return;
		}
		
		//Different actions depending on if there is flag or questionmark
		switch(tileMarker) {
		case 0:
			unmarkedTile();
		case 1:
			flaggedTile();
		case 2:
			questionTile();
		}
		
	}
	
	
	private void unmarkedTile() {
		if(!(currTile instanceof SafeTile)) {
			//add some other exit option?
			close();
		}
		
		
		
	}
	
	private void revealTile(int x, int y) {
		game.getTile(x, y).setShown();
		
		
	}
	
	//super grimt loop ja, men tror det er nødvendigt, det er den rekursive solver
	//er hoved metode som står for det meste når man klikker på et felt som ikke er vist
	private void zeroSolver(int x, int y) {
		Tile tempTile;
		revealTile(x,y);
		int tempX, tempY;
		for(int i = -1; i <= 1; i++) {
			tempY = y + i;
			if(tempY >= 0 && tempY < game.getHeight()) {
				for(int j = -1; j <= 1; j++) {
					tempX = x + j;
					if(tempX >= 0 && tempX < game.getWidth()) {
						tempTile = game.getTile(tempX, tempY);
						if(tempTile instanceof SafeTile) {
							if(tempTile.getAdjBombs() == 0) {
								zeroSolver(tempX, tempY);
							}
						}
					}
				}
			}
		}
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
