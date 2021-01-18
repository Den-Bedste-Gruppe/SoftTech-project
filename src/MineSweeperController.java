import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.*;

public class MineSweeperController {

	@FXML
	private GridPane board;
	
	@FXML
	private Label bombLabel;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	private static MineSweeperGame game;
	
	private int[] coords = new int[2];
	
	private int tileMarker;

	private Tile currTile;
	private StackPane stackPane;
	private Button button;
	private Label flag ;
	public static void setGame(MineSweeperGame mgame) {
		game = mgame;
	}

	public void initialize() {

		bombLabel.setText("Flag: "+game.getFlagCounter() + "/" + game.getNumOfBombs());

		for (int i = 0; i < game.getHeight(); i++) {
			for (int j = 0; j < game.getWidth(); j++) {
				Button btn = new Button("");
				btn.setMaxSize(50, 50);
				btn.setMinSize(50, 50);
				board.add(btn, i, j);
				btn.setId(i + " . " + j);
				btn.setOnMouseClicked(e -> TileClicked(e));
			
			}
		}
	}
	

	public void TileClicked(MouseEvent e) {
		button = ((Button)e.getSource());
		//getting cordinates from button clicked, and tile model from that location
		String[] coordString = new String[2];
		coordString = button.getId().split(" . ");
		coords[0] = Integer.parseInt(coordString[0]);
		coords[1] = Integer.parseInt(coordString[1]);
		currTile = game.getTile(coords[0], coords[1]);
		
		if(game.getRounds() == 0) {
			game.placeBombs(coords[0], coords[1]);
			game.incRounds();
			revealTile();
			System.out.println(game);
			return;
		}

		
		//in case the tile is already revealed
		if(currTile.isShown()) {
			return;
			//add code for the autosolving related to flags, ie check if flags in area = num of adj bombs, then clear
		}
		
		game.incRounds();
		
		tileMarker = currTile.getMarker();
		//for changing markers
		if(e.getButton() == MouseButton.SECONDARY) {
			switch(tileMarker) {
			//flagene vil altid være de sidste childnotes i panen, så man kan bare modificere sidste element i .getChildren()
			case 0:
				game.incFlagCounter(1);
				button.setText("F");
				break;
			case 1:
				game.incFlagCounter(-1);
				button.setText("?");
				break;
			case 2:
				button.setText("");
				break;
			}
			bombLabel.setText("Flag: " + game.getFlagCounter() + "/" + game.getNumOfBombs());
			currTile.incMarker();
			return;
		}
		
		//Different actions depending on if there is flag or questionmark
		switch(tileMarker) {
		case 0:
			unmarkedTile(coords[0], coords[1]);
			return;
		case 1:
			flaggedTile();
			return;
		case 2:
			questionTile();
			return;
		}
		
	}
	
	
	private void unmarkedTile(int x, int y) {
		if(!(currTile instanceof SafeTile)) {
			//add some other exit option?
			close();
			return;
		}
		revealTile();
		if(game.isWon()) {
			System.out.println("you won!");
			close();
		}
		
	}
	
	private void revealTile() {

		game.showTile(currTile);
		button.getStyleClass().add("bombs-"+currTile.getAdjBombs());
		button.setText("" + currTile.getAdjBombs());
		
		
	}
	
	//super grimt loop ja, men tror det er nødvendigt, det er den rekursive solver
	//er hoved metode som står for det meste når man klikker på et felt som ikke er vist
	/*
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
							//den autorevealer også felter med spørgsmåltegn hvis de er clean
							if(tempTile.getAdjBombs() == 0 && !tempTile.isShown() && !(tempTile.getMarker()==1)) {
								zeroSolver(tempX, tempY);
							}
						}
					}
				}
			}
		}
	}
	*/
	
	
	private void flaggedTile() {
		return;
	}
	
	private void questionTile() {
		return ;

	}
	
	
	public void close() {
		System.out.println("Closed!");
		Stage stage = (Stage) board.getScene().getWindow();
		stage.close();
	}

	public void newGame (ActionEvent event) throws IOException {
		Scene tableViewScene = FXMLLoader.load(main.class.getResource("menu.fxml"));
		
		Stage stage = (Stage) board.getScene().getWindow();
		stage.setScene(tableViewScene);
		//stage.getScene().getStylesheets().add("buttonStyle.css");
		stage.show();
	}
	
	
}
