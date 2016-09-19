package com.williewheeler.airrage.ui;

import com.williewheeler.airrage.Config;
import com.williewheeler.airrage.model.*;
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
		paintEnemies(g);
		paintPlayer(g);
		paintPlayerMissiles(g);
	}

	private void paintTiles(Graphics g) {
		Player player = gameState.getPlayer();
		int[][] gameMap = gameState.getGameMap();
		int progressY = player.getProgressY();

		// mySize gives us the actual viewport height, not the frame height. (The frame height includes chrome.)
		Dimension mySize = getSize();

		// Determine view bounds in game coords.
		int viewYLower = progressY;
		int viewYUpper = viewYLower + mySize.height + Config.TILE_SIZE_PX.height;

		// Determine corresponding view bounds in tile coords.
		int tileRowLower = viewYLower / TILE_SIZE_PX.height;
		int tileRowUpper = viewYUpper / TILE_SIZE_PX.height + 1;

		int viewportX = Math.max(player.getX() + (Player.PLAYER_SIZE_PX.width - mySize.width) / 2, 0);
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

	private void paintEnemies(Graphics g) {
		Player player = gameState.getPlayer();
		Dimension mySize = getSize();
		int playerYOffset = player.getYOffset();
		Plane enemy = gameState.getEnemy();
		int enemyYOffset = enemy.getY() - player.getProgressY();
		int shiftX = enemy.getX();
		int shiftY = mySize.height - Config.ENEMY_SIZE_PX.height / 2 - enemyYOffset;

		g.translate(shiftX, shiftY);
		Sprites.paintEnemy(g, enemy);
		g.translate(-shiftX, -shiftY);
	}

	private void paintPlayer(Graphics g) {
		Player player = gameState.getPlayer();
		Dimension mySize = getSize();
		int playerYOffset = player.getYOffset();
		int yOffset = mySize.height - Player.PLAYER_SIZE_PX.height - playerYOffset;
		g.translate(player.getX(), yOffset);
		Sprites.paintPlayer(g);
		g.translate(-player.getX(), -yOffset);
	}

	private void paintPlayerMissiles(Graphics g) {
		Player player = gameState.getPlayer();
		Dimension mySize = getSize();
		List<PlayerMissile> missiles = gameState.getPlayerMissiles();
		for (PlayerMissile missile : missiles) {
			int missileYOffset = missile.getY() - player.getProgressY();
			int shiftX = missile.getX();
			int shiftY = mySize.height - 10 - missileYOffset;
			g.translate(shiftX, shiftY);

			g.setColor(Color.RED);
			g.fillOval(0, 0, 9, 14);

			g.setColor(Color.ORANGE);
			g.fillOval(2, 2, 5, 10);

			g.translate(-shiftX, - shiftY);
		}
	}
}
