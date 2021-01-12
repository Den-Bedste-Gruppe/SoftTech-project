import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Tile {
	private int marker;
	private boolean shown;
	private int x,y;
	private boolean hasBomb;
    private boolean isOpen = false;
    
	
    
    
	
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
	
	public int getAdjBombs() {
		return 0;
	}
	
	public void incAdjBombs() {
		return;
	}
	
}
