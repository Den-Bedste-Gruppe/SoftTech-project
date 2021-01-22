package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.MineSweeperGame;

public class CustomGameController {
	//EK var hovedansvarlig for denne kode

	@FXML
	private TextField customHeight, customWidth, customBombs;
	
	@FXML
	private Label bombLabel, sizeLabel;
	
	@FXML
	private RadioButton classic, corona, minecraft;
	
	@FXML
	private Scene customScene;

	private int savedHeight, savedWidth, savedBombs;
	private static String selectedTheme;
	
	/**
	 * Initializes the program by setting the Radio Buttons corresponding to the current theme
	 */
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
	
	/**
	 * Sets the selected theme chosen with the Radio Buttons
	 * @param e. The event sent by the Radio Button
	 */
	public void themeSelecter(ActionEvent e) {
		selectedTheme = "" + ((RadioButton) e.getSource()).getId();
	}
	
	/**
	 * Starts the Minesweeper game with the given size and number of bombs given in the custom game menu and switches scene. Triggered when pressing "Create Game" button
	 * @throws IOException
	 */
	public void createCustomGame() throws IOException {
		bombLabel.setVisible(false);
		sizeLabel.setVisible(false);
		savedHeight = Integer.parseInt(customHeight.getText());
		savedWidth = Integer.parseInt(customWidth.getText());
		savedBombs = Integer.parseInt(customBombs.getText());
		
		if (savedBombs >= savedHeight * savedWidth) {
			bombLabel.setText("To many bombs. Must be less than (width x height)");
			bombLabel.setVisible(true);
			if (savedHeight < 4 || savedWidth < 4 || savedHeight > 100 || savedWidth > 100) {
				sizeLabel.setVisible(true);
			}
			if (savedBombs <0) {
				bombLabel.setText("Amount of bombs must be a positive integer");
				bombLabel.setVisible(true);
			}
			return;
		}
		
		if (savedHeight < 4 || savedWidth < 4 || savedHeight > 100 || savedWidth > 100) {
			sizeLabel.setVisible(true);
			if(savedBombs >= savedHeight * savedWidth) {
				bombLabel.setText("To many bombs. Must be less than (width x height)");
				bombLabel.setVisible(true);
			}
			if (savedBombs <0) {
				bombLabel.setText("Amount of bombs must be a positive integer");
				bombLabel.setVisible(true);
			}
			return;
		}
		if (savedBombs <0) {
			bombLabel.setText("Amount of bombs must be a positive integer");
			bombLabel.setVisible(true);
			return;
		}
		MineSweeperController.setGame(new MineSweeperGame(savedWidth, savedHeight, savedBombs));
		MineSweeperController.setTheme(selectedTheme);
		Scene game = FXMLLoader.load(Main.class.getResource("/views/standardGame.fxml"));
	
		Stage stage = (Stage) customScene.getWindow();
		stage.setScene(game);
		stage.getScene().getStylesheets().add("public/css/"+selectedTheme+"/buttonStyle.css");
		// Views dosen't update on Linux when you don't set size, until you move the view.
		// To fix that we need to update the view somehow. This was the solution...
		stage.setX(stage.getX());
		stage.show();
	}

	/**
	 * Menuitem that allows the user to return to the main menu scene. Triggered by "Menu"-button in the "Menu"-bar
	 * @throws IOException
	 */
	public void menu() throws IOException {
		MenuController.setTheme(selectedTheme);
		Scene tableViewScene = FXMLLoader.load(Main.class.getResource("/views/menu.fxml"));
		Stage stage = (Stage) customScene.getWindow();
		stage.setScene(tableViewScene);
		// Views dosen't update on Linux when you don't set size, until you move the view.
		// To fix that we need to update the view somehow. This was the solution...
		stage.setX(stage.getX());
		stage.show();
	}

	/**
	 * Menuitem that closes the window. Triggered by "Close"-button in the "Menu"-bar
	 */
	public void close() {
		System.out.println("Closed!");
		Stage stage = (Stage) customScene.getWindow();
		stage.close();
	}
	
	/**
	 * Sets the variable selectedTheme to a given theme
	 * @param theme. Chosen theme
	 */
	public static void setTheme(String theme) {
		selectedTheme = theme;
	}
}
