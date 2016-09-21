package com.williewheeler.airrage.ui;

import com.williewheeler.airrage.Config;
import com.williewheeler.airrage.model.*;
import com.williewheeler.airrage.model.level.Level;
import com.williewheeler.airrage.model.level.Tiles;
import com.williewheeler.airrage.ui.renderer.*;
import com.williewheeler.airrage.ui.renderer.Renderer;
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

	// Tiles
	private Renderer grassTileRenderer = new TileRenderer(new Color(154, 212, 68));
	private Renderer seaTileRenderer = new TileRenderer(new Color(42, 135, 181));

	// Game objects
	private Renderer playerRenderer = new ImageRenderer(Sprites.A6M_ZERO);
	private Renderer enemyPlaneRenderer = new ImageRenderer(Sprites.F6F_HELLCAT);
	private Renderer missileRenderer = new MissileRenderer();

	public GamePane(GameState gameState) {
		this.gameState = gameState;
	}

	@Override
	public void paint(Graphics g) {
		paintTiles(g);
		paintEnemyPlanes(g);
		paintPlayer(g);
		paintPlayerMissiles(g);
	}

	private void paintTiles(Graphics g) {
		Player player = gameState.getPlayer();
		int progressY = player.getProgressY();

		Level level = gameState.getLevel();
		int[][] gameMap = level.getGameMap();

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
						grassTileRenderer.paint(g, null);
						break;
					case Tiles.SEA:
						seaTileRenderer.paint(g, null);
						break;
				}
				g.translate(-tileScreenX, -tileScreenY);
			}
		}
	}

	private void paintEnemyPlanes(Graphics g) {
		List<Plane> planes = gameState.getPlanes();
		for (Plane plane : planes) {
			paint(g, plane, enemyPlaneRenderer);
		}
	}

	private void paintPlayer(Graphics g) {
		paint(g, gameState.getPlayer(), playerRenderer);
	}

	private void paintPlayerMissiles(Graphics g) {
		List<PlayerMissile> missiles = gameState.getPlayerMissiles();
		for (PlayerMissile missile : missiles) {
			paint(g, missile, missileRenderer);
		}
	}

	private void paint(Graphics g, GameObject gameObject, Renderer renderer) {

		// TODO We will want to this into the renderer, because we'll need to rotate the image around the game object
		// centroid. [WLW]

		Dimension mySize = getSize();
		Player player = gameState.getPlayer();

		int shiftX = gameObject.getX();

		int offsetY = gameObject.getY() - player.getProgressY();
		int shiftY = mySize.height - gameObject.getHeight() - offsetY;

		g.translate(shiftX, shiftY);
		renderer.paint(g, gameObject);
		g.translate(-shiftX, -shiftY);
	}
}
