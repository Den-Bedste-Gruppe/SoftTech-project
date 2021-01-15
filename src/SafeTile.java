
public class SafeTile extends Tile {
	private int adjBombs;
	private boolean flag = false;
	private int[] tileCoords;
	
	public SafeTile(int x, int y) {
		tileCoords = new int[] {x, y};
	}
	
	public int[] getCoords() {
		return tileCoords;
	}
	
	@Override
	public int getAdjBombs() {
		return adjBombs;
	}
	
	public boolean getFlag() {
		return flag;
	}
	
	public void setFlag() {
		flag = true;
	}
	
	@Override
	public void incAdjBombs() {
		adjBombs++;
	}
}
