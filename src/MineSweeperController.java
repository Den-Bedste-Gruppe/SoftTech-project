import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;
import javafx.scene.input.*;

public class MineSweeperController {

	@FXML
	private GridPane board;
	
	@FXML
	private Label bombLabel;
	
	@FXML
	private Label gameOver;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	private static MineSweeperGame game;
	
	private int[] coords = new int[2];
	
	private Button[][] btnArray;
	
	public static void setGame(MineSweeperGame mgame) {
		game = mgame;
	}

	public void initialize() {

		bombLabel.setText("Flag: "+game.getFlagCounter() + "/" + game.getNumOfBombs());
		btnArray = new Button[game.getHeight()][game.getWidth()];
		for (int i = 0; i < game.getHeight(); i++) {
			for (int j = 0; j < game.getWidth(); j++) {
				Button btn = new Button("");
				btnArray[i][j] = btn;
				btn.setMaxSize(50, 50);
				btn.setMinSize(50, 50);
				board.add(btn, i, j);
				btn.setId(i + " . " + j);
				btn.setOnMouseClicked(e -> TileClicked(e));
			}
		}
	}
	

	public void TileClicked(MouseEvent e) {
		Button button;
		if(game.isDone()) {
			return;
		}
		button = ((Button)e.getSource());
		//getting cordinates from button clicked, and tile model from that location
		String[] coordString = new String[2];
		coordString = button.getId().split(" . ");
		int x = Integer.parseInt(coordString[0]);
		int y = Integer.parseInt(coordString[1]);

		Tile currTile = game.getTile(x, y);
		Button btn = btnArray[x][y];
		
		
		if(game.getRounds() == 0) {
			game.placeBombs(coords[0], coords[1]);
			System.out.println(game);
		}

		
		//in case the tile is already revealed
		if(currTile.isShown()) {
			return;
			//add code for the autosolving related to flags, ie check if flags in area = num of adj bombs, then clear
		}
		
		game.incRounds();
		
		int tileMarker = currTile.getMarker();
		//for changing markers
		if(e.getButton() == MouseButton.SECONDARY) {
			switch(tileMarker) {
			//flagene vil altid være de sidste childnotes i panen, så man kan bare modificere sidste element i .getChildren()
			case 0:
				game.incFlagCounter(1);
				ImageView flag = new ImageView(new Image("images/flag.png"));
				flag.setFitHeight(30);
				flag.setFitWidth(30);
				button.setGraphic(flag);
//				button.setText("F");
				break;
			case 1:
				game.incFlagCounter(-1);
				ImageView qMark = new ImageView(new Image("images/qMark.png"));
				qMark.setFitHeight(20);
				qMark.setFitWidth(20);
				button.setGraphic(qMark);
//				button.setText("?");
				break;
			case 2:
				button.setGraphic(null);
				//button.setText("");
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
		Tile currTile = game.getTile(x, y);
		Button currBtn = btnArray[x][y];
		revealTile(currTile, currBtn);
		if(!(currTile instanceof SafeTile)) {
			gameOver(currBtn);
			return;
		}

		if(game.isWon()) {
			System.out.println("you won!");
			gameOver.setText("WINNER WINNER CHICKEN DINNER: Want to try again?");
			gameOver.setVisible(true);
			//close();
		}
		
		if(currTile.getAdjBombs()==0) {
			zeroSolver(x, y);
		}
		
	}
	
	private void revealTile(Tile currTile, Button btn) {
		game.showTile(currTile);
		btn.getStyleClass().add("bombs-"+currTile.getAdjBombs());
		btn.getStyleClass().add("shown");
		if (currTile.getAdjBombs() != 0) {
			btn.setText("" + currTile.getAdjBombs());	
		}
		
		
		
	}
	
	//super grimt loop ja, men tror det er nødvendigt, det er den rekursive solver
	//er hoved metode som står for det meste når man klikker på et felt som ikke er vist
	
	private void zeroSolver(int x, int y) {
		Tile currTile = game.getTile(x, y);
		int tempX, tempY;
		for(int i = -1; i <= 1; i++) {
			tempY = y + i;
			if(tempY >= 0 && tempY < game.getHeight()) {
				for(int j = -1; j <= 1; j++) {
					tempX = x + j;
					if(tempX >= 0 && tempX < game.getWidth()) {
						currTile = game.getTile(tempX, tempY);
						if(!currTile.isShown() && !(currTile.getMarker()==1)) {
							unmarkedTile(tempX, tempY);
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
		return ;

	}
	
	public void gameOver(Button button) {
//		ImageView iv = new ImageView(new Image("images/mine.png"));
		ImageView iv = new ImageView(new Image("images/corona.jpg"));
//		ImageView iv = new ImageView(new Image("images/mine.png"));
		iv.setFitHeight(40);
		iv.setFitWidth(40);
		button.setGraphic(iv);
		//add some other exit option?
		gameOver.setText("GAME OVER: Try again?");
		gameOver.setVisible(true);
		game.setDone();
		//close();
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
