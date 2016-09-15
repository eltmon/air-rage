package com.williewheeler.airrage.model;

import com.williewheeler.airrage.Config;

/**
 * Created by willie on 9/11/16.
 */
public class GameState {

	// Tile coords. Row 0 is at the bottom of the map.
	private int[][] gameMap;

	// Current progress y in game coords. y = 0 is at the bottom of the map.
	// Note that this isn't the same as the plane y.
	private int progressY;

	private int playerX;
	private int playerYOffset;

	public GameState() {
		this.gameMap = GameMaps.getGameMap();
		this.progressY = 0;
		this.playerX = (Config.MAP_SIZE_PX.width - Config.TILE_SIZE_PX.width) / 2;
		this.playerYOffset = (Config.MAX_PLANE_Y_OFFSET - Config.MIN_PLANE_Y_OFFSET) / 2;
	}

	public int[][] getGameMap() {
		return gameMap;
	}

	public int getProgressY() {
		return progressY;
	}

	public void incrementProgressY(int distance) {
		this.progressY += distance;
	}

	public int getPlayerX() {
		return playerX;
	}

	public int getPlayerYOffset() { return playerYOffset; }

	public void movePlaneUp(int distance) {
		this.playerYOffset += distance;
		if (playerYOffset > Config.MAX_PLANE_Y_OFFSET) {
			this.playerYOffset = Config.MAX_PLANE_Y_OFFSET;
		}
	}

	public void movePlaneDown(int distance) {
		this.playerYOffset -= distance;
		if (playerYOffset < Config.MIN_PLANE_Y_OFFSET) {
			this.playerYOffset = Config.MIN_PLANE_Y_OFFSET;
		}
	}

	public void movePlaneLeft(int distance) {
		this.playerX -= distance;
		if (playerX < 0) {
			this.playerX = 0;
		}
	}

	public void movePlaneRight(int distance) {
		this.playerX += distance;
		int limit = Config.MAP_SIZE_PX.width - Config.PLAYER_SIZE_PX.width - 1;
		if (playerX > limit) {
			this.playerX = limit;
		}
	}
}
