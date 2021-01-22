package model;

//MH og PH var hovedansvarlige for denne del.
public abstract class Tile {
	private int marker = 0;
	private boolean shown;
	
	/**
	 * 
	 * @return if the tile is shown
	 */
	public boolean isShown() {
		return shown;
	}
	
	/**
	 * 
	 * @return which marker is shown. 0 is none, 1 is flag, and 2 is question mark
	 */
	public int getMarker() {
		return marker;
	}
	
	/**
	 * Increments the tile's marker by 1 then the remainder by division with 3
	 */
	public void incMarker() {
		marker = (marker + 1) % 3;
	}
	
	/**
	 * Sets the tile to being shown (true)
	 */
	public void setShown() {
		shown = true;
	}
	
	/**
	 * 
	 * @return number of adjacent bombs
	 */
	public int getAdjBombs() {
		return 0;
	}
	
	/**
	 * Increments the adjBombs field
	 */
	public void incAdjBombs() {
		return;
	}
}