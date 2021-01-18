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

		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				board[i][j] = new SafeTile(i, j);
			}
		}
	}
	
	public void showTile(Tile tile){
		tile.setShown();
		fieldsToWin--;
		if(fieldsToWin==0) {
			won = true;
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
						board[tempY][tempX].incAdjBombs();
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
			if(!(x == startX && y == startY) && (currTile instanceof SafeTile)) { 
				board[x][y] = new Tile();
				bombsPlaced++;
				incNeighbours(y, x);

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
		for(int i = 0; i <= height; i++) {
			for(int j = 0; j < width; j++) {
				if(board[i][j] instanceof SafeTile) {
					s += board[i][j].getAdjBombs();
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