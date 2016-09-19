package com.williewheeler.airrage;

import com.williewheeler.airrage.model.GameState;
import com.williewheeler.airrage.ui.GamePane;
import com.williewheeler.airrage.ui.InputManager;
import com.williewheeler.airrage.ui.audio.AudioManager;
import com.williewheeler.airrage.ui.audio.AudioLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

/**
 * Created by willie on 9/4/16.
 */
public class AirRage extends JFrame {
	private static final Logger log = LoggerFactory.getLogger(AirRage.class);

	private GameState gameState;
	private GamePane gamePane;
	private InputManager inputManager;
	private AudioManager audioManager;

	public static void main(String[] args) {
		AirRage rage = new AirRage();
		rage.startGame();
	}

	public AirRage() {
		super("-= AirRage =-");
		this.gameState = new GameState();
		this.gamePane = new GamePane(gameState);
		this.inputManager = new InputManager(gameState);
		this.audioManager = new AudioManager(new AudioLoader());

		// FIXME Get rid of this
		gameState.getPlayer().setAudioManager(audioManager);

		getContentPane().add(gamePane);
		addKeyListener(inputManager.getKeyListener());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Config.VIEWPORT_SIZE_PX);
		setResizable(false);
	}

	public void startGame() {
		setVisible(true);
		startGameLoop();
	}

	private void startGameLoop() {

		// TODO Figure out how to make this continuous
		audioManager.playSoundEffect("airplane+spita", true);

		while (true) {
			long startMs = System.currentTimeMillis();
			gameState.updateState();
			repaint();
			long duration = System.currentTimeMillis() - startMs;
			long sleepMs = Config.TARGET_FRAME_DURATION - duration;
			GameUtil.sleep(Math.max(0, sleepMs));
		}
	}
}
