package view.ranking;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import application.Main;
import controller.SoundController;
import controller.sound.Sound;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import model.ranking.PlayerStats;
import view.menu.MainMenuViewController;

public class RankingViewController extends BorderPane {

	@FXML
	private TableView<Stats> table_ranking;

	@FXML
	private TableColumn<Stats, Integer> col_rank;

	@FXML
	private TableColumn<Stats, String> col_name;

	@FXML
	private TableColumn<Stats, String> col_time;

	@FXML
	private TableColumn<Stats, Integer> col_victoryPoints;

	@FXML
	private TableColumn<Stats, Integer> col_conflictPoints;

	@FXML
	private TableColumn<Stats, Integer> col_losePoints;

	@FXML
	private TableColumn<Stats, Integer> col_coins;

	@FXML
	private Button btn_back;
	
	@FXML
	private Button btn_mute;

	@FXML
	private ImageView img_music;
	
	public RankingViewController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ranking/Ranking.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		btn_back.setOnAction(e -> { Main.getSWController().getSoundController().play(Sound.BUTTON_CLICK);
			Main.primaryStage.getScene().setRoot(new MainMenuViewController());
		});
		
		col_rank.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getRank()));
		col_name.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPlayerStats().getName()));
		col_time.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPlayerStats().getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))));
		col_victoryPoints.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPlayerStats().getVictoryPoints()));
		col_conflictPoints.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPlayerStats().getConflictPoints()));
		col_losePoints.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPlayerStats().getLosePoints()));
		col_coins.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPlayerStats().getCoins()));
		
		col_rank.setReorderable(false);
		col_name.setReorderable(false);
		col_time.setReorderable(false);
		col_victoryPoints.setReorderable(false);
		col_conflictPoints.setReorderable(false);
		col_losePoints.setReorderable(false);
		col_coins.setReorderable(false);
		
		table_ranking.setEditable(false);
		
		SoundController.addMuteFunction(btn_mute, img_music);

		ArrayList<Stats> list = new ArrayList<>();
		int rank = 1;
		for (PlayerStats stats : Main.getSWController().getRanking().getStats()) {
			list.add(new Stats(stats, rank));
			rank++;
		}
		table_ranking.setSelectionModel(null);
		table_ranking.setItems(FXCollections.observableArrayList(list));
		col_rank.setSortType(SortType.ASCENDING);
	}

	private static class Stats{
		private PlayerStats stats;
		private int rank;

		private Stats(PlayerStats stats, int rank) {
			this.stats = stats;
			this.rank = rank;
		}

		public int getRank() {
			return rank;
		}

		public PlayerStats getPlayerStats() {
			return stats;
		}
	}

}
