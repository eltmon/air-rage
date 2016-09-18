package com.williewheeler.airrage.ui;

import com.williewheeler.airrage.Config;
import com.williewheeler.airrage.model.GameState;
import com.williewheeler.airrage.model.PlayerMissile;
import com.williewheeler.airrage.model.Tiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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

		// Assume that the viewport starts off in the correct world location.
		// Our task is to transform objects from world coords to view coords.
		paintTiles(g);
		paintPlayer(g);
		paintPlayerMissiles(g);
	}

	private void paintTiles(Graphics g) {
		int[][] gameMap = gameState.getGameMap();
		int progressY = gameState.getProgressY();

		// mySize gives us the actual viewport height, not the frame height. (The frame height includes chrome.)
		Dimension mySize = getSize();

		// Determine view bounds in game coords.
		int viewYLower = progressY;
		int viewYUpper = viewYLower + mySize.height + Config.TILE_SIZE_PX.height;

		// Determine corresponding view bounds in tile coords.
		int tileRowLower = viewYLower / TILE_SIZE_PX.height;
		int tileRowUpper = viewYUpper / TILE_SIZE_PX.height + 1;

		int viewportX = Math.max(gameState.getPlayerX() + (Config.PLAYER_SIZE_PX.width - mySize.width) / 2, 0);
		viewportX = Math.min(viewportX, Config.MAP_SIZE_PX.width - mySize.width - 1);
		g.translate(-viewportX, 0);

		int tileColLower = viewportX / Config.TILE_SIZE_PX.width;
		int tileColUpper = tileColLower + mySize.width / Config.TILE_SIZE_PX.width;

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
	}

	private void paintPlayer(Graphics g) {
		Dimension mySize = getSize();
		int playerYOffset = gameState.getPlayerYOffset();
		int yOffset = mySize.height - Config.PLAYER_SIZE_PX.height - playerYOffset;
		g.translate(gameState.getPlayerX(), yOffset);
		Sprites.paintPlane(g);
		g.translate(-gameState.getPlayerX(), -yOffset);
	}

	private void paintPlayerMissiles(Graphics g) {
		Dimension mySize = getSize();
		g.setColor(Color.RED);
		List<PlayerMissile> missiles = gameState.getPlayerMissiles();
		for (PlayerMissile missile : missiles) {
			int shiftX = missile.getX() - 10;
			int shiftY = gameState.getProgressY() - missile.getY() - 10;
			g.translate(shiftX, shiftY);
			g.fillOval(0, 0, 20, 20);
			g.translate(-shiftX, - shiftY);
		}
	}
}
