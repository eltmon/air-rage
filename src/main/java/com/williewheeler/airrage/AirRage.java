package com.williewheeler.airrage;

import com.williewheeler.airrage.event.GameEvent;
import com.williewheeler.airrage.event.GameListener;
import com.williewheeler.airrage.model.GameState;
import com.williewheeler.airrage.model.level.Levels;
import com.williewheeler.airrage.ui.GamePane;
import com.williewheeler.airrage.ui.InputManager;
import com.williewheeler.airrage.ui.audio.AudioLoader;
import com.williewheeler.airrage.ui.audio.AudioManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by willie on 9/4/16.
 */
public class AirRage extends JFrame {
	private static final Logger log = LoggerFactory.getLogger(AirRage.class);

	private GameState gameState;
	private GamePane gamePane;
	private InputManager inputManager;
	private AudioManager audioManager;
	private GameListener gameListener;

	public static void main(String[] args) {
		AirRage rage = new AirRage();
		rage.startGame();
	}

	public AirRage() {
		super("-= AirRage =-");
		this.gameState = new GameState(Levels.getLevel(0));
		this.gamePane = new GamePane(gameState);
		this.inputManager = new InputManager(gameState);
		this.audioManager = new AudioManager(new AudioLoader());
		this.gameListener = new GameHandler();

		gameState.addGameListener(gameListener);

		getContentPane().add(gamePane);
		addKeyListener(inputManager.getKeyListener());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Config.VIEWPORT_SIZE_PX);
		setResizable(false);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - getSize().width) / 2, (screenSize.height - getSize().height) / 2);
	}

	public void startGame() {
		setVisible(true);
		startGameLoop();
	}

	private void startGameLoop() {
//		GameUtil.sleep(15000);

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

	private final class GameHandler implements GameListener {

		@Override
		public void handleGameEvent(GameEvent event) {
			String type = event.getType();
			if (GameEvent.PLAYER_FIRED.equals(type)) {
				audioManager.playSoundEffect("gunfire", false);
			} else if (GameEvent.PLANE_HIT.equals(type)) {
				audioManager.playSoundEffect("explosion", false);
			}
		}
	}
}
