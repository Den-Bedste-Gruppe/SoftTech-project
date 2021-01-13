import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Tile {
	private int marker = 0;
	private boolean shown;
    
	
    
    
	
	public boolean isShown() {
		return shown;
	}
	
	public int getMarker() {
		return marker;
	}
	
	public void incMarker() {
		marker=(marker+1)%3;
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
