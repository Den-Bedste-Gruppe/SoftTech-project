import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class customGameController {

	@FXML
	private TextField customHeight, customWidth, customBombs;
	@FXML
	private Label bombLabel, sizeLabel;
	
	@FXML
	private Scene customScene;

	private int savedHeight, savedWidth, savedBombs;

	
	public void createCustomGame(ActionEvent event) throws IOException {
		bombLabel.setVisible(false);
		sizeLabel.setVisible(false);
		savedHeight = Integer.parseInt(customHeight.getText());
		savedWidth = Integer.parseInt(customWidth.getText());
		savedBombs = Integer.parseInt(customBombs.getText());
		if (savedBombs >= savedHeight*savedWidth) {
			bombLabel.setVisible(true);
			if (savedHeight <4 || savedWidth <4 || savedHeight>100 || savedWidth>100) {
				sizeLabel.setVisible(true);
			}
			return;
		}
		
		if (savedHeight <4 || savedWidth <4 || savedHeight>100 || savedWidth>100) {
			sizeLabel.setVisible(true);
			if(savedBombs>=savedHeight*savedWidth) {
				bombLabel.setVisible(true);
			}
			return;
		}
		MineSweeperController.setGame(new MineSweeperGame(savedWidth, savedHeight, savedBombs));
		Scene game = FXMLLoader.load(main.class.getResource("grid2.fxml"));

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(game);
		stage.getScene().getStylesheets().add("buttonStyle.css");

		stage.setHeight(600);
		stage.setWidth(800);

		stage.show();

	}

	public void menu(ActionEvent event) throws IOException {
		Scene tableViewScene = FXMLLoader.load(main.class.getResource("menu.fxml"));

		Stage stage = (Stage) customScene.getWindow();
		stage.setScene(tableViewScene);
		stage.setHeight(500);
		stage.setWidth(700);
		// stage.getScene().getStylesheets().add("buttonStyle.css");
		stage.show();
	}

	public void close() {
		System.out.println("Closed!");
		Stage stage = (Stage) customScene.getWindow();
		stage.close();
	}
}
