package com.williewheeler.airrage.ui;

import com.williewheeler.airrage.Config;
import com.williewheeler.airrage.model.GameState;
import com.williewheeler.airrage.model.Tiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

import static com.williewheeler.airrage.Config.TILE_SIZE_PX;
import static com.williewheeler.airrage.Config.VIEWPORT_SIZE_PX;

/**
 * Created by willie on 9/4/16.
 */
public class GamePane extends JComponent {
	private static final Logger log = LoggerFactory.getLogger(GamePane.class);

	private GameState gameState;

	public GamePane(GameState gameState) {
		this.gameState = gameState;
	}

	@Override
	public void paint(Graphics g) {
		paintTiles(g);
	}

	private void paintTiles(Graphics g) {
		int[][] gameMap = gameState.getGameMap();
		int progressY = gameState.getProgressY();

		// Determine view bounds in game coords.
		int viewYLower = progressY;
		int viewYUpper = viewYLower + VIEWPORT_SIZE_PX.height;

		// Determine corresponding view bounds in tile coords.
		int tileRowLower = viewYLower / TILE_SIZE_PX.height;
		int tileRowUpper = viewYUpper / TILE_SIZE_PX.height + 1;

		int viewportX = Math.max(gameState.getPlaneX() + (Config.TILE_SIZE_PX.width - Config.VIEWPORT_SIZE_PX.width) / 2, 0);
		viewportX = Math.min(viewportX, Config.VIEWPORT_SIZE_PX.width / 2 - 1);
		g.translate(-viewportX, 0);

		int tileColLower = viewportX / Config.TILE_SIZE_PX.width;
		// FIXME Remove this hardcode (20)
		int tileColUpper = tileColLower + 20;

//		log.debug("viewYLower={}, viewYUpper={}, tileRowLower={}, tileRowUpper={}",
//				viewYLower, viewYUpper, tileRowLower, tileRowUpper);

		for (int i = tileRowLower; i <= tileRowUpper; i++) {
			int tileScreenY = VIEWPORT_SIZE_PX.height - i * TILE_SIZE_PX.height + progressY;
			for (int j = tileColLower; j <= tileColUpper; j++) {
				int tileScreenX = j * TILE_SIZE_PX.width;
				g.translate(tileScreenX, tileScreenY);
				int tile = gameMap[i][j];
				switch (tile) {
					case Tiles.LAND:
						Sprites.paintLand(g);
						break;
					case Tiles.SEA:
						Sprites.paintSea(g);
						break;
				}
				g.translate(-tileScreenX, -tileScreenY);
			}
		}

		g.translate(gameState.getPlaneX(), 600);
		Sprites.paintPlane(g);
		g.translate(-gameState.getPlaneX(), -600);
	}
}
