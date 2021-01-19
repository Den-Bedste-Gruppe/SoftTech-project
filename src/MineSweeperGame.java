import java.util.Random;

public class MineSweeperGame {
	private boolean done, won;
	private Tile[][] board;
	private int height, width, flagCounter, numOfBombs, rounds, fieldsToWin;
	private Random rand = new Random();
	
	
	
	public MineSweeperGame(int height, int width, int numOfBombs) {
		this.height = height;
		this.width = width;
		this.numOfBombs = numOfBombs;
		fieldsToWin = height*width - numOfBombs;
		
		board = new Tile[height][width];

		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				board[x][y] = new SafeTile();
			}
		}
	}
	
	public void showTile(Tile tile){
		tile.setShown();
		fieldsToWin--;
		if(fieldsToWin==0) {
			won = true;
			done = true;
		}
	};
	
	public Tile getTile(int x, int y) {
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
			if(tempY >= 0 && tempY < height) {
				for(int j = -1; j <= 1; j++) {
					tempX = x + j;
					if(tempX >= 0 && tempX < width) {
						board[tempX][tempY].incAdjBombs();
					}
				}
			}
		}
	}
	
	public void placeBombs(int startX, int startY) {
		int bombsPlaced = 0;
		int x, y;
		Tile currTile;
		System.out.println(startX+"-"+startY);
		while(bombsPlaced < numOfBombs) {
			x = rand.nextInt(width);
			y = rand.nextInt(height);
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
	public String toString() {
		String s = "";
		for(int y = height - 1; y >= 0; y--) {
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
}