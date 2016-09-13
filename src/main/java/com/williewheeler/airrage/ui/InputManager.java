package com.williewheeler.airrage.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Reads user input and makes them available as user intents.
 *
 * Created by willie on 9/11/16.
 */
public class InputManager {
	private KeyListener keyListener;

	/** Does user intend to move left? */
	private boolean moveLeftIntent;

	/** Does user intend to move right? */
	private boolean moveRightIntent;

	public InputManager() {
		this.keyListener = new KeyHandler();
	}

	public KeyListener getKeyListener() {
		return keyListener;
	}

	public boolean getMoveLeftIntent() {
		return moveLeftIntent;
	}

	public boolean getMoveRightIntent() {
		return moveRightIntent;
	}

	private class KeyHandler implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int code = e.getKeyCode();
			if (code == KeyEvent.VK_LEFT) {
				moveLeftIntent = true;
			} else if (code == KeyEvent.VK_RIGHT) {
				moveRightIntent = true;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int code = e.getKeyCode();
			if (code == KeyEvent.VK_LEFT) {
				moveLeftIntent = false;
			} else if (code == KeyEvent.VK_RIGHT) {
				moveRightIntent = false;
			}
		}
	}
}
