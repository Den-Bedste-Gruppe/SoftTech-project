package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.Main;
import model.BombTile;
import model.MineSweeperGame;
import model.Tile;

public class MineSweeperController {

	@FXML
	private Scene gameScene;
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

	@FXML
	private Label timerLabel;
	
	private Timer timer;
	private static String selectedTheme;
	private static MineSweeperGame game;
	private Button[][] btnArray;
	public static void setGame(MineSweeperGame mgame) {
		game = mgame;
	}

	/**
	 * Initializes the board setting game labels and creating the Button grid.
	 */
	public void initialize() {
		bombLabel.setText("Flag: " + game.getFlagCounter() + "/" + game.getNumOfBombs());
		timerLabel.setText("Time: " + 0);
		board.getStyleClass().add("grid");
		btnArray = new Button[game.getHeight()][game.getWidth()];
		for (int x = 0; x < game.getHeight(); x++) {
			for (int y = 0; y < game.getWidth(); y++) {
				Button btn = new Button("");
				btnArray[x][y] = btn;
				btn.setMaxSize(50, 50);
				btn.setMinSize(50, 50);
				//Add Button to the board (GridPane)
				board.add(btn, x, y);
				btn.setId(x + " . " + y);
				btn.setOnMouseClicked(e -> TileClicked(e));
			}
		}
	}
	
	/**
	 * Starts the timer that shows how long you have currently spent on the game
	 */
	private void startTimer() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			int currTime = 0;
			@Override
			public void run() {
				javafx.application.Platform.runLater(new Runnable () {
					@Override
					public void run() {
						currTime++;
						timerLabel.setText("Time: " + currTime);
					}
				});
			}
		}, 1000, 1000);
	}

	/**
	 * Handles the game logic when the users clicks on a tile
	 * @param e. Triggered when any button on the board is clicked
	 */
	
	public void TileClicked(MouseEvent e) {
		Button button;
		if(game.isDone()) {
			return;
		}
		button = ((Button)e.getSource());
		//getting coordinates from button clicked, and tile model from that location
		String[] coordString = new String[2];
		coordString = button.getId().split(" . ");
		int x = Integer.parseInt(coordString[0]);
		int y = Integer.parseInt(coordString[1]);
		Tile currTile = game.getTile(x, y);
		
		
		if(game.getRounds() == 0) {
			game.placeBombs(x, y);
			// For testing.
			//System.out.println(game);
			startTimer();
		}
		
		game.incRounds();
		
		//in case the tile is already revealed
		if(currTile.isShown()) {
			flagSolver(x,y);
			return;
		}
		
		int tileMarker = currTile.getMarker();
		//for changing markers between blank, flag, and question mark
		if(e.getButton() == MouseButton.SECONDARY) {
			switch(tileMarker) {
			case 0:
				game.incFlagCounter(1);
				ImageView flag = new ImageView(new Image("public/images/"+selectedTheme+"/flag.png"));
				flag.setFitHeight(30);
				flag.setFitWidth(30);
				button.setGraphic(flag);
				break;
			case 1:
				game.incFlagCounter(-1);
				ImageView qMark = new ImageView(new Image("public/images/"+selectedTheme+"/qMark.png"));
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
		
		//Calls unmarkedTile if the tile does not have a flag or question mark
		if (tileMarker == 0) {
			unmarkedTile(x, y, false);
		}
	}
	
	/**
	 * Used for already revealed tiles. If the number of adjacent bombs is equal to number of adjacent flags ALL adjacent tiles are revealed
	 * @param x. Vertical coordinate of the tile the method executes on
	 * @param y. Horizontal coordinate of the tile the method executes on
	 */
	private void flagSolver(int x, int y) {
		int flagsNear = 0;
		int tempX, tempY;
		Tile tempTile;
		for(int i = -1; i <= 1; i++) {
			tempY = y + i;
			for(int j = -1; j <= 1; j++) {
				tempX = x + j;
				if(!(tempX == x && tempY == y)) {
					tempTile = game.getTile(tempX, tempY);
					if(!(tempTile == null) && tempTile.getMarker() == 1) {
						flagsNear++;
					}
				}
			}
		}
		
		if(flagsNear == game.getTile(x, y).getAdjBombs()) {
			for(int i = -1; i <= 1; i++) {
				tempY = y + i;
				for(int j = -1; j <= 1; j++) {
					tempX = x + j;
					if(!(tempX == x && tempY == y)) {
						tempTile = game.getTile(tempX, tempY);
						if(!(tempTile == null) && !(tempTile.getMarker() == 1)) {
							unmarkedTile(tempX, tempY, false);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Handles the logic when trying to reveal an tile not yet shown.
	 * @param x. Vertical coordinate of the tile the method executes on
	 * @param y. Horizontal coordinate of the tile the method executes on
	 * @param solving. Boolean telling whether or not the ZeroSolver algorithm is running.
	 */
	private void unmarkedTile(int x, int y, boolean solving) {
		if(!solving) {
			zeroSolver(x,y);
			return;
		}
		
		Tile currTile = game.getTile(x, y);
		Button currBtn = btnArray[x][y];
		revealTile(currTile, currBtn);
		if(currTile instanceof BombTile) {
			timer.cancel();
			gameOver(currBtn);
			return;
		}

		if(game.isWon()) {
			timer.cancel();
			System.out.println("You won!");
			gameOver.setText("Congratulations - you won! Want to try again?");
			gameOver.setVisible(true);
		}
	}
	
	/**
	 * Visually reveals the tile by styling corresponding button in the grid
	 * @param currTile. Current tile selected
	 * @param btn. Button from the view for the model's corresponding tile
	 */
	private void revealTile(Tile currTile, Button btn) {
		if (currTile != null && btn != null) {
			game.showTile(currTile);
			btn.getStyleClass().add("bombs-" + currTile.getAdjBombs());
			btn.getStyleClass().add("shown");
			btn.setGraphic(null);
			if (currTile.getAdjBombs() != 0) {
				btn.setText("" + currTile.getAdjBombs());	
			}
		}
	}
	
	/**
	 * Algorithm for revealing all adjacent tiles whenever a blank tile is revealed
	 * @param x. Vertical coordinate of the tile the method executes on
	 * @param y. Horizontal coordinate of the tile the method executes on
	 */
	private void zeroSolver(int x, int y) {
		// Create stack
		Stack<int[]> stack = new Stack<>();
		stack.add(new int[]{x, y});
		

		while (!stack.empty() && !game.isDone()) {
			int[] coords = stack.pop();
			
			int dx = coords[0];
			int dy = coords[1];
			
			if (dx < 0 || dx >= game.getHeight() || dy < 0 || dy >= game.getWidth()) {
				continue;
			} 

			Tile currTile = game.getTile(dx, dy);
			
			if (currTile.isShown()) {
				continue;
			}

			unmarkedTile(dx, dy, true);
					
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
	
	/**
	 * Called when a mine is clicked. Ends the game and styles 'button' with a mine
	 * @param button. The Button clicked that contains a mine
	 */
	private void gameOver(Button button) {
		ImageView iv = new ImageView(new Image("public/images/"+selectedTheme+"/mine.png"));
		iv.setFitHeight(40);
		iv.setFitWidth(40);
		button.setGraphic(iv);

		gameOver.setText("GAME OVER: Try again?");
		gameOver.setVisible(true);

		game.setDone();
	}
	
	/**
	 * Menuitem that closes the window. Triggered by "Close"-button in the "Menu"-bar
	 */
	public void close() {
		System.out.println("Closed!");
		Stage stage = (Stage)gameScene.getWindow();
		stage.close();
	}

	/**
	 * Menuitem that returns to the main menu.
	 * @throws IOException
	 */
	public void newGame () throws IOException {
		MenuController.setTheme(selectedTheme);
		Scene tableViewScene = FXMLLoader.load(Main.class.getResource("/views/menu.fxml"));
		Stage stage = (Stage)gameScene.getWindow();
		stage.setScene(tableViewScene);
		stage.show();
	}
	
	/**
	 * Sets the variable selectedTheme to a given theme
	 * @param theme. Chosen theme
	 */
	public static void setTheme(String theme) {
		selectedTheme = theme;
	}
}