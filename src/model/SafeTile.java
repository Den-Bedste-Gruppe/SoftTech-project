package model;
//MH og PH var hovedansvarlige for denne del.
public class SafeTile extends Tile {
	private int adjBombs;
	
	@Override
	public int getAdjBombs() {
		return adjBombs;
	}
	
	@Override
	public void incAdjBombs() {
		adjBombs++;
	}
}