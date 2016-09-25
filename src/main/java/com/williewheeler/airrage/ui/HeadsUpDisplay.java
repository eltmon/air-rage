package com.williewheeler.airrage.ui;

import com.williewheeler.airrage.Config;
import com.williewheeler.airrage.model.GameState;

import java.awt.*;

/**
 * Created by willie on 9/25/16.
 */
public class HeadsUpDisplay {
	private static final Dimension MY_SIZE = new Dimension(Config.VIEWPORT_SIZE_PX.width, 32);
	private static final Dimension HEALTH_METER_SIZE = new Dimension(134, 26);
	private static final Color BG_COLOR = new Color(0, 0, 0, 150);

	private GameState gameState;

	public HeadsUpDisplay(GameState gameState) {
		this.gameState = gameState;
	}

	public void paint(Graphics g) {
		paintBackground(g);
		paintHealthMeter(g);
	}

	private void paintBackground(Graphics g) {
		g.setColor(BG_COLOR);
		g.fillRect(0, 0, Config.VIEWPORT_SIZE_PX.width, MY_SIZE.height);
	}

	private void paintHealthMeter(Graphics g) {
		int shiftX = (MY_SIZE.width - HEALTH_METER_SIZE.width - 10);
		int shiftY = ((MY_SIZE.height - HEALTH_METER_SIZE.height) / 2);

		g.translate(shiftX, shiftY);

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, HEALTH_METER_SIZE.width, HEALTH_METER_SIZE.height);

		g.setColor(Color.BLACK);
		g.fillRect(2, 2, HEALTH_METER_SIZE.width - 4, HEALTH_METER_SIZE.height - 4);

		// TODO
		g.setColor(Color.GREEN);
		for (int i = 0; i < 8; i++) {
			g.fillRect(4 + i * 16, 4, 14, HEALTH_METER_SIZE.height - 8);
		}

		g.translate(-shiftX, -shiftY);
	}
}
