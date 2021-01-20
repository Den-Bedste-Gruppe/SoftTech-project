import java.util.ArrayList;
import java.util.Random;

public class MineSweeperGame {
	private boolean done, won;
	private Tile[][] board;
	private int height, width, flagCounter, numOfBombs, rounds, fieldsToWin;
	private Random rand = new Random();
	private ArrayList<int[]> test = new ArrayList<int[]>();
	
	public MineSweeperGame(int height, int width, int numOfBombs) {
		this.height = height;
		this.width = width;
		System.out.println(height + " " + width);
		this.numOfBombs = numOfBombs;
		fieldsToWin = height * width - numOfBombs;
		
		board = new Tile[height][width];

		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				board[y][x] = new SafeTile();
				test.add(new int[]{x, y});
			}
		}
	}
	
	public void showTile(Tile tile){
		tile.setShown();
		fieldsToWin--;
		if(fieldsToWin == 0) {
			won = true;
			done = true;
		}
	}
	
	public Tile getTile(int x, int y) {
		if (x < 0 || x > width) return null;
		if (y < 0 || y > height) return null;
		return board[x][y];
	}
	
	public int getRounds() {
		return rounds;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getFlagCounter() {
		return flagCounter;
	}
	
	public void incRounds () {
		rounds++;
	}
	
	public int getNumOfBombs() {
		return numOfBombs;
	}

	public boolean isDone() {
		return done;
	}
	public void setDone() {
		done = true;
	}
	 
	public boolean isWon() {
		return won;
	}
	
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
	
	public void placeBombs(int startX, int startY) {
//		for (int i = 0; i < numOfBombs; i++) {
//			int ranIndex = new Random().nextInt(test.size());
//			
//			int[] cords = test.get(ranIndex);
//			int x = cords[0];
//			int y = cords[1];
//			
////			board[x][y] = new BombTile();
////			incrementNeighbor(x, y);
//			
//			test.remove(ranIndex);
//		}
		int bombsPlaced = 0;
		int x, y;
		Tile currTile;
		System.out.println(startX+"-"+startY);
		while(bombsPlaced < numOfBombs) {
			x = rand.nextInt(height);
			y = rand.nextInt(width);
			currTile = board[x][y];
			// Should it not be (x == startX && y == startY)
			if(!(x == startX && y == startY) && (currTile instanceof SafeTile)) { 
				board[x][y] = new Tile();
				bombsPlaced++;
				incNeighbours(x, y);

			}
		}
		System.out.println("Bombs placed");
	}
	
	public void incFlagCounter(int upOrNot) {
		flagCounter += upOrNot;
	}
	
	//for testing
	/*
	public String toString() {
		String s = "";
		for(int y = 0; y < width; y++) {
			for(int x = 0; x < width; x++) {
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