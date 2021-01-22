package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import main.Main;
import model.MineSweeperGame;

public class MenuController {

	@FXML
	private Scene menuScene;

	@FXML
	private RadioButton classic, corona, minecraft;

	private static String selectedTheme = "classic";

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
	 * Starts the Minesweeper game with 6x6 grid and 5 bombs and switches scene
	 * @param event. Triggered when pressing "Easy" button
	 * @throws IOException
	 */
	public void createEasyGame(ActionEvent event) throws IOException {

		MineSweeperController.setGame(new MineSweeperGame(6, 6, 5));
		MineSweeperController.setTheme(selectedTheme);
		Scene game = FXMLLoader.load(Main.class.getResource("/views/standardGame.fxml"));

		Stage stage = (Stage) menuScene.getWindow();
		stage.setScene(game);
		stage.getScene().getStylesheets().add("public/css/"+selectedTheme+"/buttonStyle.css");
		stage.setHeight(600);
		stage.setWidth(800);
		stage.show();
	}

	/**
	 * Starts the Minesweeper game with 12x12 grid and 30 bombs and switches scene
	 * @param event. Triggered when pressing "Medium" button
	 * @throws IOException
	 */
	public void createMediumGame(ActionEvent event) throws IOException {
		MineSweeperController.setGame(new MineSweeperGame(12, 12, 30));
		MineSweeperController.setTheme(selectedTheme);
		Scene game = FXMLLoader.load(Main.class.getResource("/views/standardGame.fxml"));

		Stage stage = (Stage) menuScene.getWindow();
		stage.setScene(game);
		stage.getScene().getStylesheets().add("public/css/"+selectedTheme+"/buttonStyle.css");

		stage.setHeight(600);
		stage.setWidth(800);

		stage.show();
	}

	/**
	 * Starts the Minesweeper game with 30x30 grid and 100 bombs and switches scene
	 * @param event. Triggered when pressing "Hard" button
	 * @throws IOException
	 */
	public void createHardGame(ActionEvent event) throws IOException {
		MineSweeperController.setGame(new MineSweeperGame(30, 30, 100));
		MineSweeperController.setTheme(selectedTheme);
		Scene game = FXMLLoader.load(Main.class.getResource("/views/standardGame.fxml"));

		Stage stage = (Stage) menuScene.getWindow();
		stage.setScene(game);
		stage.getScene().getStylesheets().add("public/css/"+selectedTheme+"/buttonStyle.css");

		stage.setHeight(600);
		stage.setWidth(800);

		stage.show();
	}
	
	/**
	 * Switches scene to the Custom Game Menu
	 * @param event. Triggered when pressing "Custom" button
	 * @throws IOException
	 */
	public void setCustomGame(ActionEvent event) throws IOException {
		CustomGameController.setTheme(selectedTheme);
		Scene game = FXMLLoader.load(Main.class.getResource("/views/customGame.fxml"));
		
		Stage stage = (Stage) menuScene.getWindow();
		stage.setScene(game);

		stage.setHeight(600);
		stage.setWidth(800);

		stage.show();
	}

	/**
	 * Menuitem that closes the window. Triggered by "Close"-button in the "Menu"-bar
	 */
	public void close() {
		System.out.println("Closed!");
		Stage stage = (Stage) menuScene.getWindow();
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
