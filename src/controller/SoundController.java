package controller;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

import application.Main;
import application.Utils;
import controller.sound.Sound;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundController {
	/** mute sound */
	private boolean mute = false;
	/** list of media players */
	private ArrayList<SoundPlayer> players;
	/**
	 * create new Sound Controller
	 */
	public SoundController() {
		players = new ArrayList<SoundPlayer>();
	}

	/**
	 * plays sound
	 * @param sound 	name of sound
	 */
	
	public void play(Sound sound) {
		play(sound, false);
	}

	/**
	 * stops sound
	 * @param sound 	name of sound
	 */
	
	public void stop(Sound sound) {
		SoundPlayer remove = null;
		for(SoundPlayer player : players) {
			if(player.getSound() == sound) {
				System.out.println("SOUND STOP "+sound.name());
				
				player.stop();
				remove = player;
				break;
			}
		}
		
		if(remove != null)players.remove(remove);
	}

	/**
	 * plays sound with/without loop
	 * @param sound 	name of sound
	 */
	public void play(Sound sound,boolean loop) {
		SoundPlayer player = new SoundPlayer(sound);
		System.out.println("SOUND PLAY "+sound.name());
		if(loop)
			player.setLoop();
		else
			player.setAutoRemove();
		if (!mute)
			player.play();
		players.add(player);
	}
	
	/**
	 * mutes or unmutes sound
	 * @return true if muted
	 */
	public boolean mute() {
		if (mute)
			for (SoundPlayer player : players)
				player.play();
		else
			for (SoundPlayer player: players)
				player.stop();
		return (mute ^= true);
	}
	/**
	 * @return true if muted
	 */
	public boolean isMuted() {
		return mute;
	}
	/**
	 * @param btn 		adds mute button
	 * @param imgv		adds mute button image
	 */
	public static void addMuteFunction(Button btn, ImageView imgv) {
		btn.setOnAction(event -> {
			Main.getSWController().getSoundController().mute();
			updateMuteIcon(imgv);
		});
		updateMuteIcon(imgv);
	}
	/**
	 * @param imgv		updates mute button image
	 */
	public static void updateMuteIcon(ImageView imgv) {
		if (Main.getSWController().getSoundController().isMuted())
			imgv.setImage(new Image(SoundController.class.getResourceAsStream("../view/images/musicoff.png")));
		else
			imgv.setImage(new Image(SoundController.class.getResourceAsStream("../view/images/music.png")));
	}
	
	private class SoundPlayer{
		private MediaPlayer player;
		private int index;
		private String[] filenames;
		private Sound sound;
		
		public SoundPlayer(Sound sound) {
			this.sound = sound;
			this.filenames = sound.getSoundFilenames();
			this.index = Utils.randInt(0, this.filenames.length-1);
			this.player = new MediaPlayer(new Media(new File(Main.SOUNDS_PATH + this.filenames[this.index] + ".mp3").toURI().toString()));
		}
		
		public void setAutoRemove() {
			SoundPlayer tthis = this;
			player.setOnEndOfMedia(() -> players.remove(tthis));
		}
		
		public boolean setLoop() {
			if(filenames.length > 1)
				player.setOnEndOfMedia(() -> {
					this.index++;
					if(this.filenames.length == this.index)this.index = 0;
					this.player.stop();
					this.player = new MediaPlayer(new Media(new File(Main.SOUNDS_PATH + this.filenames[this.index] + ".mp3").toURI().toString()));
					this.player.play();
				});
			else 
				player.setCycleCount(MediaPlayer.INDEFINITE);
			return filenames.length > 1;
		}
		
		public Sound getSound() {
			return this.sound;
		}
		
		public void stop() {
			this.player.stop();
			players.remove(this);
		}
		
		public void play() {
			this.player.play();
		}
	}
}