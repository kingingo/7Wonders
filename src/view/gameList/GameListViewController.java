package view.gameList;

import java.io.IOException;

import application.Main;
import controller.SevenWondersController;
import controller.SoundController;
import controller.sound.Sound;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.gameboard.GameBoardViewController;
import view.menu.MainMenuViewController;

public class GameListViewController extends BorderPane {

	@FXML
	private ImageView img_music;

	@FXML
	private Button btn_back;
	
	@FXML
	private Button btn_mute;

	@FXML
	private VBox vbox_gameList;
	
	public GameListViewController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameList/GameList.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		btn_back.setOnAction(e -> {Main.getSWController().getSoundController().play(Sound.BUTTON_CLICK); Main.primaryStage.getScene().setRoot(new MainMenuViewController());});
		
		String[] games = Main.getSWController().getIOController().listGameFiles();
		for (String game: games) {
			Button button = new Button(game);
			button.setOnAction(e -> Main.primaryStage.getScene().setRoot(new GameBoardViewController())); // TODO game �bergeben
			
			SoundController.addMuteFunction(btn_mute, img_music);vbox_gameList.getChildren().add(button);
		}
		
		SoundController.addMuteFunction(btn_mute, img_music);
	}

}
