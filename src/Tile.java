public class Tile {
	private int marker = 0;
	private boolean shown;
    
	
    
    public int[] getCoords() {
    	int[] array = {0, 0};
    	return array;
    }
	
	public boolean isShown() {
		return shown;
	}
	
	public int getMarker() {
		return marker;
	}
	
	public void incMarker() {
		marker=(marker + 1) % 3;
	}
	
	public void setShown() {
		shown = true;
	}
	
	public int getAdjBombs() {
		return 0;
	}
	
	public void incAdjBombs() {
		return;
	}
	
	public boolean getFlag() {
		return false;
	}
	
	public void setFlag() {
		return;
	}
	
}
