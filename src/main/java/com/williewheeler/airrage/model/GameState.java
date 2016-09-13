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

	private int planeX;

	public GameState() {
		this.gameMap = GameMaps.getGameMap();
		this.progressY = 0;
		this.planeX = (Config.MAP_SIZE_PX.width - Config.TILE_SIZE_PX.width) / 2;
	}

	public int[][] getGameMap() {
		return gameMap;
	}

	public void setGameMap(int[][] gameMap) {
		this.gameMap = gameMap;
	}

	public int getProgressY() {
		return progressY;
	}

	public void incrementProgressY(int distance) {
		this.progressY += distance;
	}

	public int getPlaneX() {
		return planeX;
	}

	public void setPlaneX(int planeX) {
		this.planeX = planeX;
	}

	public void movePlaneLeft(int distance) {
		this.planeX -= distance;
		if (planeX < 0) {
			this.planeX = 0;
		}
	}

	public void movePlaneRight(int distance) {
		this.planeX += distance;
		int limit = Config.MAP_SIZE_PX.width - Config.TILE_SIZE_PX.width - 1;
		if (planeX > limit) {
			this.planeX = limit;
		}
	}
}
