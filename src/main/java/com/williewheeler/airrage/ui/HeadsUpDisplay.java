package com.williewheeler.airrage.ui;

import com.williewheeler.airrage.Config;
import com.williewheeler.airrage.model.GameState;
import com.williewheeler.airrage.model.gameobj.Player;

import java.awt.*;

/**
 * Created by willie on 9/25/16.
 */
public class HeadsUpDisplay {
	private static final Dimension MY_SIZE = new Dimension(Config.VIEWPORT_SIZE_PX.width, 32);

	// This should be set to the max player health.
	private static final int HEALTH_METER_NUM_CELLS = 10;

	private static final int HEALTH_METER_CELL_WIDTH = 6;
	private static final int HEALTH_METER_CELL_HEIGHT = 12;
	private static final int HEALTH_METER_BORDER = 2;
	private static final int HEALTH_METER_GAP = 2;

	private static final int HEALTH_METER_WIDTH =
			2 * HEALTH_METER_BORDER + HEALTH_METER_GAP + HEALTH_METER_NUM_CELLS * (HEALTH_METER_CELL_WIDTH + HEALTH_METER_GAP);

	private static final int HEALTH_METER_HEIGHT =
			2 * HEALTH_METER_BORDER + 2 * HEALTH_METER_GAP + HEALTH_METER_CELL_HEIGHT;

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
		final int shiftX = (MY_SIZE.width - HEALTH_METER_WIDTH - 10);
		final int shiftY = ((MY_SIZE.height - HEALTH_METER_HEIGHT) / 2);
		final int borderPlusGap = HEALTH_METER_BORDER + HEALTH_METER_GAP;

		g.translate(shiftX, shiftY);

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, HEALTH_METER_WIDTH, HEALTH_METER_HEIGHT);

		g.setColor(Color.BLACK);
		g.fillRect(HEALTH_METER_BORDER, HEALTH_METER_BORDER, HEALTH_METER_WIDTH - borderPlusGap, HEALTH_METER_HEIGHT - borderPlusGap);

		Player player = gameState.getPlayer();
		for (int i = 0; i < player.getHealth(); i++) {
			Color cellColor;
			double percentage = (i + 1) / (double) HEALTH_METER_NUM_CELLS;
			if (percentage <= 0.31) {
				cellColor = Color.RED;
			} else if (percentage <= 0.71) {
				cellColor = Color.YELLOW;
			} else {
				cellColor = Color.GREEN;
			}
			g.setColor(cellColor);

			final int x = borderPlusGap + i * (HEALTH_METER_CELL_WIDTH + HEALTH_METER_GAP);
			final int y = borderPlusGap;
			g.fillRect(x, y, HEALTH_METER_CELL_WIDTH, HEALTH_METER_CELL_HEIGHT);
		}

		g.translate(-shiftX, -shiftY);
	}
}
