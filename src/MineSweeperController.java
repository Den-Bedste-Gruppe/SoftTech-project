import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class MineSweeperController implements Initializable  {

	
	@FXML
	private GridPane board;
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
	
	public void initialize(URL location, ResourceBundle resources) {
		MineSweeperGame game = new MineSweeperGame(50, 20, 10);
	
		for(int i = 0; i <= game.getHeight() ; i++) {
			for (int j = 0; j <=game.getWidth() ; j++) {
				
				
				Button btn = new Button("");
				btn.setMaxSize(50, 50);
				btn.setMinSize(20, 20);
				board.add(btn, i, j);
		
				
				
			}
			
		}
	
		
	}
	
	
}
