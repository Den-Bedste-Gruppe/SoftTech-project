package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.MineSweeperGame;

public class CustomGameController {

	@FXML
	private TextField customHeight, customWidth, customBombs;
	
	@FXML
	private Label bombLabel, sizeLabel;
	
	@FXML
	private RadioButton classic, corona, minecraft;
	
	@FXML
	private Scene customScene;

	private int savedHeight, savedWidth, savedBombs;
	private String selectedTheme = "classic";
	
	public void initialize() {
		switch (selectedTheme) {
		case "classic": 
			classic.setSelected(true);
			break;
		
		case "corona": 
			corona.setSelected(true);
			break;
		
		case "minecraft":
			minecraft.setSelected(true);
			break;
		}
		
	}

	public void themeSelecter(ActionEvent e) {
		System.out.println(e);
		selectedTheme = "" + ((RadioButton) e.getSource()).getId();

	}
	public void createCustomGame(ActionEvent event) throws IOException {
		bombLabel.setVisible(false);
		sizeLabel.setVisible(false);
		savedHeight = Integer.parseInt(customHeight.getText());
		savedWidth = Integer.parseInt(customWidth.getText());
		savedBombs = Integer.parseInt(customBombs.getText());
		if (savedBombs >= savedHeight * savedWidth) {
			bombLabel.setVisible(true);
			if (savedHeight < 4 || savedWidth < 4 || savedHeight > 100 || savedWidth > 100) {
				sizeLabel.setVisible(true);
			}
			return;
		}
		
		if (savedHeight < 4 || savedWidth < 4 || savedHeight > 100 || savedWidth > 100) {
			sizeLabel.setVisible(true);
			if(savedBombs >= savedHeight * savedWidth) {
				bombLabel.setVisible(true);
			}
			return;
		}
		MineSweeperController.setGame(new MineSweeperGame(savedWidth, savedHeight, savedBombs));
		MineSweeperController.setTheme(selectedTheme);
		Scene game = FXMLLoader.load(Main.class.getResource("../views/standardGame.fxml"));
	
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(game);
		stage.getScene().getStylesheets().add("public/css/buttonStyle.css");

		stage.setHeight(600);
		stage.setWidth(800);

		stage.show();

	}

	public void menu(ActionEvent event) throws IOException {
		Scene tableViewScene = FXMLLoader.load(Main.class.getResource("../views/menu.fxml"));

		Stage stage = (Stage) customScene.getWindow();
		stage.setScene(tableViewScene);
		stage.setHeight(600);
		stage.setWidth(800);
		// stage.getScene().getStylesheets().add("public/css/buttonStyle.css.css");
		stage.show();
	}

	public void close() {
		System.out.println("Closed!");
		Stage stage = (Stage) customScene.getWindow();
		stage.close();
	}
}
