package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Main;
import model.MineSweeperGame;
import javafx.scene.Node;

public class MenuController {
	
	@FXML
	private Scene menuScene;

	public void createEasyGame (ActionEvent event) throws IOException {

		MineSweeperController.setGame(new MineSweeperGame(6, 6, 5));

		Scene game = FXMLLoader.load(Main.class.getResource("../views/standardGame.fxml"));
		

		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(game);
		stage.getScene().getStylesheets().add("public/css/buttonStyle.css");
		stage.setHeight(600);
		stage.setWidth(800);
		stage.show();
	}
	
	public void createMediumGame (ActionEvent event) throws IOException {
		MineSweeperController.setGame(new MineSweeperGame(12, 12, 30));
		Scene game = FXMLLoader.load(Main.class.getResource("../views/standardGame.fxml"));
		
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(game);
		stage.getScene().getStylesheets().add("public/css/buttonStyle.css");
		
		stage.setHeight(600);
		stage.setWidth(800);

		stage.show();
	}
	
	public void createHardGame (ActionEvent event) throws IOException {
		MineSweeperController.setGame(new MineSweeperGame(30, 30, 100));
		Scene game = FXMLLoader.load(Main.class.getResource("../views/standardGame.fxml"));
		
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(game);
		stage.getScene().getStylesheets().add("public/css/buttonStyle.css");
		
		stage.setHeight(600);
		stage.setWidth(800);

		stage.show();
	}

	public void setCustomGame(ActionEvent event) throws IOException {
		Scene game = FXMLLoader.load(Main.class.getResource("../views/customGame.fxml"));
		
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(game);
		stage.setHeight(600);
		stage.setWidth(800);
		stage.show();
		
	}
	
	public void close() {
		System.out.println("Closed!");
		Stage stage = (Stage) menuScene.getWindow();
		stage.close();
	}
	
}
