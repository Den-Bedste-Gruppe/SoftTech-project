
public class Tile {
	private int marker;
	private boolean shown;
	
	
	public boolean isShown() {
		return shown;
	}
	
	public int getMarker() {
		return marker;
	}
	
	public void incMarker() {
		marker++;
	}
	
	public void setShown() {
		shown = true;
	}
	
	
}
