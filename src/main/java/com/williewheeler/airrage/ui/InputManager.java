package com.williewheeler.airrage.ui;

import com.williewheeler.airrage.model.GameState;
import com.williewheeler.airrage.model.gameobj.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Reads user input and makes them available as user intents.
 *
 * Created by willie on 9/11/16.
 */
public class InputManager {
	private GameState gameState;
	private KeyListener keyListener;

	public InputManager(GameState gameState) {
		this.gameState = gameState;
		this.keyListener = new KeyHandler();
	}

	public KeyListener getKeyListener() {
		return keyListener;
	}

	private class KeyHandler implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			setIntent(e, true);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			setIntent(e, false);
		}

		private void setIntent(KeyEvent e, boolean value) {
			Player player = gameState.getPlayer();
			int code = e.getKeyCode();
			if (code == KeyEvent.VK_UP) {
				player.setMoveUpIntent(value);
			} else if (code == KeyEvent.VK_DOWN) {
				player.setMoveDownIntent(value);
			} else if (code == KeyEvent.VK_LEFT) {
				player.setMoveLeftIntent(value);
			} else if (code == KeyEvent.VK_RIGHT) {
				player.setMoveRightIntent(value);
			} else if (code == KeyEvent.VK_SPACE) {
				player.setFireIntent(value);
			}
		}
	}
}
