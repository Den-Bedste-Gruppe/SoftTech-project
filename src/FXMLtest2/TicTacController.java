package FXMLtest2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;



public class TicTacController {
	
    @FXML
    private GridPane Board;    
	
	public void initialize() {
		for(int i = 0; i <= 5; i++) {
			for(int j = 0; j <= 5; j++) {
				
				if(i < 1 && j < 1) {
					continue;
				}
				
				ImageView iv = new ImageView(new Image("file:/C:/Users/Lucas/EclipseFiles/Test/src/FXMLtest/images/mine.png"));
				iv.setFitHeight(50);
				iv.setFitWidth(50);
				
				Button btn = new Button("", iv);
				btn.setMaxSize(50, 50);
				btn.setId("" + i + "." + j);
				btn.setOnAction(e -> TicClick(e));
				
				Board.add(btn, i, j);
			}
		}
	}
	
	public void TicClick(ActionEvent e) {
		//System.out.println("Success");
		System.out.println(((Control)e.getSource()).getId());
	}
}
