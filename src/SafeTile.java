
public class SafeTile extends Tile {
	private int adjBombs;
	private boolean flag = false;
	
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
