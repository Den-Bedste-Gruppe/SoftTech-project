package model;

import java.util.ArrayList;
import java.util.Random;

public class MineSweeperGame {
	private boolean done, won;
	private Tile[][] board;
	private int height, width, flagCounter, numOfBombs, rounds, fieldsToWin;
	private ArrayList<int[]> canPlaceBombs = new ArrayList<int[]>();
	
	public MineSweeperGame(int height, int width, int numOfBombs) {
		this.height = height;
		this.width = width;
		this.numOfBombs = numOfBombs;
		fieldsToWin = height * width - numOfBombs;
		
		board = new Tile[height][width];

		for(int x = 0; x < height; x++) {
			for(int y = 0; y < width; y++) {
				board[x][y] = new SafeTile();
				canPlaceBombs.add(new int[]{x, y});
			}
		}
	}
	
	/**
	 * Sets a given tile to shown in the model and checks if the game is won
	 * @param tile. The Tile being shown
	 */
	public void showTile(Tile tile){
		tile.setShown();
		fieldsToWin--;
		if(fieldsToWin == 0) {
			won = true;
			done = true;
		}
	}
	
	/**
	 * 
	 * @param x. Vertical coordinate of the tile the method executes on
	 * @param y. Horizontal coordinate of the tile the method executes on
	 * @return tile at given coordinates
	 */
	public Tile getTile(int x, int y) {
		if (x < 0 || x >= height) return null;
		if (y < 0 || y >= width) return null;
		return board[x][y];
	}
	
	/**
	 * 
	 * @return number of rounds played
	 */
	public int getRounds() {
		return rounds;
	}
	
	/**
	 * 
	 * @return height of game board
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * 
	 * @return width of game board
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * 
	 * @return number of flags placed in total
	 */
	public int getFlagCounter() {
		return flagCounter;
	}
	
	/**
	 * Increments counter of rounds by 1
	 */
	public void incRounds () {
		rounds++;
	}
	
	/**
	 * 
	 * @return number of bombs in total
	 */
	public int getNumOfBombs() {
		return numOfBombs;
	}

	/**
	 * 
	 * @return a boolean if the game is over or not
	 */
	public boolean isDone() {
		return done;
	}
	
	/**
	 * Sets the game to being over
	 */
	public void setDone() {
		done = true;
	}
	
	/**
	 * 
	 * @return a boolean if the game has been won or not
	 */
	public boolean isWon() {
		return won;
	}
	
	/**
	 * Increments the adjBombs counter on all adjacent tiles
	 * @param x. Vertical coordinate of the tile the method executes on
	 * @param y. Horizontal coordinate of the tile the method executes on
	 */
	private void incNeighbours(int x, int y) {
		int tempX, tempY;
		for(int i = -1; i <= 1; i++) {
			tempY = y + i;
			if(tempY >= 0 && tempY < width) {
				for(int j = -1; j <= 1; j++) {
					tempX = x + j;
					if(tempX >= 0 && tempX < height) {
						board[tempX][tempY].incAdjBombs();
					}
				}
			}
		}
	}
	
	/**
	 * Places all the bombs when the game is started. Bombs cannot be placed on the tile with coordinates (startX, startY)
	 * @param startX. Vertical coordinate of the first tile clicked
	 * @param startY. Horizontal coordinate of the first tile clicked
	 */
	public void placeBombs(int startX, int startY) {
		for (int i = 0; i < numOfBombs; i++) {
			int ranIndex = new Random().nextInt(canPlaceBombs.size());
			
			int[] cords = canPlaceBombs.get(ranIndex);
			int x = cords[0];
			int y = cords[1];
			
			if (x == startX && y == startY) {
				i--;
				canPlaceBombs.remove(ranIndex);
				continue;
			}
			
			board[x][y] = new BombTile();
			incNeighbours(x, y);
			
			canPlaceBombs.remove(ranIndex);
		}
	}
	
	/**
	 * Increments or decrements the flag counter by 1
	 * @param upOrDown. Either +1 or -1
	 */
	public void incFlagCounter(int upOrDown) {
		flagCounter += upOrDown;
	}
	
	/*
	//for testing
	public String toString() {
		String s = "";
		for(int y = 0; y < width; y++) {
			for(int x = 0; x < height; x++) {
				if(board[x][y] instanceof SafeTile) {
					s += board[x][y].getAdjBombs();
				} else {
					s += "B";
				}
				s += "|";
			}
			s += "\n";
		}
		return s;
	}
	*/
}