package com.williewheeler.airrage.ui;

import com.williewheeler.airrage.Config;
import com.williewheeler.airrage.model.GameState;

import java.awt.*;

/**
 * Created by willie on 9/25/16.
 */
public class HeadsUpDisplay {
	private GameState gameState;

	public HeadsUpDisplay(GameState gameState) {
		this.gameState = gameState;
	}

	public void paint(Graphics g) {
		Color color = new Color(0, 0, 0, 150);

		g.setColor(color);
		g.fillRect(0, 0, Config.VIEWPORT_SIZE_PX.width, 40);
	}
}
