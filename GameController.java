package controller;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.application.Application;
import javafx.stage.Stage;
import view.GameOverView;
import view.GameView;
import view.StartView;

public class GameController extends Application {
	private Clip music;
	private Clip slashingSound;
	private Clip explosionSound;
	private Clip deadlyExplosionSound;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		StartView startView = new StartView(primaryStage);
		GameView gameView = new GameView(primaryStage);
		GameOverView gameOverView = new GameOverView(primaryStage);
		startView.setGameViewScene(gameView);
		gameView.setStartViewScene(startView);
		gameView.setGameOverView(gameOverView);
		gameOverView.setStartView(startView);
		gameOverView.setGameView(gameView);
		
		
		startView.prepareStartView();
		primaryStage.setScene(startView.getStartViewScene());
		primaryStage.setTitle("FRUIT NINJA");
		primaryStage.show();
		primaryStage.setMinHeight(750);
		primaryStage.setMinWidth(1124);
		primaryStage.setMaxHeight(750);
		primaryStage.setMaxWidth(1124);
		primaryStage.setResizable(false);
		loadAudio();
		music.loop(Clip.LOOP_CONTINUOUSLY);
		
		gameView.setDeadlyExplosionSound(deadlyExplosionSound);
		gameView.setExplosionSound(explosionSound);
		gameView.setSlashingSound(slashingSound);
		startView.setSlashingSound(slashingSound);
		
		
	}
	
	private void loadAudio() {
		try {
			music = AudioSystem.getClip();
			music.open(AudioSystem.getAudioInputStream(new File("Music.wav").getAbsoluteFile()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			slashingSound = AudioSystem.getClip();
			slashingSound.open(AudioSystem.getAudioInputStream(new File("slash.wav").getAbsoluteFile()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			explosionSound = AudioSystem.getClip();
			explosionSound.open(AudioSystem.getAudioInputStream(new File("Bomb Sound.wav").getAbsoluteFile()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			deadlyExplosionSound = AudioSystem.getClip();
			deadlyExplosionSound.open(AudioSystem.getAudioInputStream(new File("Deadly Bomb Sound.wav").getAbsoluteFile()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
