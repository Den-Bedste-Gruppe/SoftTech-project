
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

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
	private Button[][] btnArray;
	public static void setGame(MineSweeperGame mgame) {
		game = mgame;
	}

	public void initialize() {
		bombLabel.setText("Flag: " + game.getFlagCounter() + "/" + game.getNumOfBombs());

		btnArray = new Button[game.getHeight()][game.getWidth()];
		for (int x = 0; x < game.getHeight(); x++) {
			for (int y = 0; y < game.getWidth(); y++) {
				Button btn = new Button("");
				btnArray[x][y] = btn;
				btn.setMaxSize(50, 50);
				btn.setMinSize(50, 50);
				board.add(btn, x, y);
				btn.setId(x + " . " + y);
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
		
		
		if(game.getRounds() == 0) {
			game.placeBombs(x, y);
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
				break;
			case 1:
				game.incFlagCounter(-1);
				ImageView qMark = new ImageView(new Image("images/qMark.png"));
				qMark.setFitHeight(20);
				qMark.setFitWidth(20);
				button.setGraphic(qMark);
				break;
			case 2:
				button.setGraphic(null);
				break;
			}
			bombLabel.setText("Flag: " + game.getFlagCounter() + "/" + game.getNumOfBombs());
			currTile.incMarker();
			return;
		}
		
		//Different actions depending on if there is flag or questionmark
		switch(tileMarker) {
		case 0:
			zeroSolver(x, y);
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
			System.out.println("You won!");
			gameOver.setText("Congratulations - you won! Want to try again?");
			gameOver.setVisible(true);
		}
	}
	
	private void revealTile(Tile currTile, Button btn) {
		if (currTile != null && btn != null) {
			game.showTile(currTile);
			btn.getStyleClass().add("bombs-" + currTile.getAdjBombs());
			btn.getStyleClass().add("shown");
			if (currTile.getAdjBombs() != 0) {
				btn.setText("" + currTile.getAdjBombs());	
			}
		}
	}
	
	private void zeroSolver(int x, int y) {
		// Create stack
		Stack<int[]> stack = new Stack<>();
		stack.add(new int[]{x, y});
		

		while (!stack.empty() && !game.isDone()) {
			int[] coords = stack.pop();
			
			int dx = coords[0];
			int dy = coords[1];
			
			if (dx < 0 || dx >= game.getWidth() || dy < 0 || dy >= game.getHeight()) {
				continue;
			} 

			Tile currTile = game.getTile(dx, dy);
			
			if (currTile.isShown()) {
				continue;
			}

			unmarkedTile(dx, dy);
					
			if(currTile.getAdjBombs() == 0 && currTile != null) {

				int[][] checkbox = {
						{dx-1, dy+1}, {dx, dy+1}, {dx+1, dy+1},
						{dx-1, dy}, {dx+1, dy},
						{dx-1, dy-1}, {dx , dy-1}, {dx+1, dy-1}
				};

				for (int i = 0; i < checkbox.length; i++) {
					stack.push(new int[]{checkbox[i][0], checkbox[i][1]});
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
		ImageView iv = new ImageView(new Image("images/oldMine.png"));
//		ImageView iv = new ImageView(new Image("images/corona.jpg"));
//		ImageView iv = new ImageView(new Image("images/mine.png"));
		iv.setFitHeight(40);
		iv.setFitWidth(40);
		button.setGraphic(iv);
		gameOver.setText("GAME OVER: Try again?");
		gameOver.setVisible(true);
		game.setDone();
	}
	
	public void close() {
		System.out.println("Closed!");
		Stage stage = (Stage) board.getScene().getWindow();
		stage.close();
	}

	public void newGame (ActionEvent event) throws IOException {
		Scene tableViewScene = FXMLLoader.load(Main.class.getResource("views/menu.fxml"));
		
		Stage stage = (Stage) board.getScene().getWindow();
		stage.setScene(tableViewScene);
		//stage.getScene().getStylesheets().add("buttonStyle.css");
		stage.setHeight(600);
		stage.setWidth(800);
		stage.setMaximized(false);
		stage.show();
	}
}
