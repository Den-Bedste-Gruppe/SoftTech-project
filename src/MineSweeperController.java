import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
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
	
	private Button button;

	public static void setGame(MineSweeperGame mgame) {
		game = mgame;
	}

	public void initialize() {
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
	
	
	
	public void SimulateClick(int x, int y) {
		currTile = game.getTile(x, y);
		//String s = "" + currTile.getCoords()[0] + " . " + currTile.getCoords()[1];
		String s = "#" + currTile.getCoords()[0] + " . " + currTile.getCoords()[1];
		System.out.println(s);
		//System.out.println(MineSweeperApplication.window.getScene());
		button = ((Button)board.lookup(s));
		//System.out.println(button.getId());
		unmarkedTile(coords[0], coords[1]);
	}
	
	public void TileClicked(MouseEvent e) {
		button = ((Button)e.getSource());
		System.out.println("CLICKED " + button.getId());
		//getting coordinates from button clicked, and tile model from that location
		String[] coordString = new String[2];
		coordString = button.getId().split(" . ");
		coords[0] = Integer.parseInt(coordString[0]);
		coords[1] = Integer.parseInt(coordString[1]);
		currTile = game.getTile(coords[0], coords[1]);
		
		if(game.getRounds() == 0) {
			game.placeBombs(coords[0], coords[1]);
			game.incRounds();
			revealTile(coords[0], coords[1]);
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
			bombLabel.setText(""+game.getFlagCounter());
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
			System.out.println("Bomb");
			//close();
			//return;
		}
		coords[0] = x;
		coords[1] = y;
		revealTile(coords[0], coords[1]);
		if(game.isWon()) {
			System.out.println("Win");
			//close();
		}
		
	}
	
	private void revealTile(int x, int y) {
		currTile = game.getTile(x, y);
		game.showTile(currTile);		
		System.out.println(button.getId());
		button.getStyleClass().add("bombs-" + currTile.getAdjBombs());
		/*
		if (currTile.getAdjBombs() != 0) {
			button.setText("" + currTile.getAdjBombs());
		}
		*/
		button.setText("" + currTile.getAdjBombs());
		if (currTile.getAdjBombs() == 0) {
			System.out.println("Test");
			zeroSolver(coords[0], coords[1]);
		}
	}
	
	
	private void zeroSolver(int x, int y) {
		System.out.println("ZERO");
		currTile = game.getTile(x, y);
		coords[0] = currTile.getCoords()[0];
		coords[1] = currTile.getCoords()[1];
		
		
		if(x < 0 || y < 0 || x >= game.getWidth() || y >= game.getHeight() || currTile.getAdjBombs() > 0){
			System.out.println("1");
			return;
		} else if (currTile.getFlag()) {
			System.out.println("A");
			return;
		} else {
			currTile.setFlag();
			revealTile(coords[0], coords[1]);
			System.out.println("2");
			SimulateClick(x - 1, y);
			SimulateClick(x + 1, y);
			SimulateClick(x, y - 1);
			SimulateClick(x, y + 1);
			//Recursion
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
/*




private void zeroSolver() {
	System.out.println("1");
	
	if (currTile.getFlag()) {
		System.out.println("A");
		return;
	}
	
	if (currTile.getAdjBombs() == 0) {
		currTile.setFlag();
		revealTile();
		System.out.println("2");
		int counter = 0;
		int tempX, tempY;
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				System.out.println("3");
				tempX = coords[0] + i;
				tempY = coords[1] + j;
				if((i == 0 && j == 0) || tempX > game.getWidth() - 1 || tempY > game.getHeight() - 1 || tempX < 0 || tempY < 0 ) {
					System.out.println("B");
					continue;	
				} else {
					System.out.println("4");
					//SimulateClick();
					if (!game.getTile(tempX, tempY).getFlag()) {
						//adjTiles[counter] = game.getTile(tempX, tempY);
						currTile = game.getTile(tempX, tempY);
						counter++;
						zeroSolver();
					}
				}
			}
		}
		
		counter = 0;
		for (int i = 0; i < adjTiles.length; i++) {
			System.out.println("5");
			currTile = adjTiles[i];
			
			int[] currCoords = new int[2];
			currCoords[0] = currTile.getCoords()[0];
			currCoords[1] = currTile.getCoords()[1];
			
			coords[0] = currTile.getCoords()[0];
			coords[1] = currTile.getCoords()[1];
			SimulateClick();
		}
	
		
	}
			
}

*/