package com.williewheeler.airrage.model;

import com.williewheeler.airrage.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by willie on 9/11/16.
 */
public class GameState {
	private static final Logger log = LoggerFactory.getLogger(GameState.class);

	/**
	 * Tile coords. Row 0 is at the bottom of the map.
	 */
	private int[][] gameMap;

	/**
	 * Current progress y in game coords. y = 0 is at the bottom of the map. Note that this isn't the same as the player
	 * y.
	 */
	private int progressY;

	private int playerX;
	private int playerYOffset;

	private List<PlayerMissile> playerMissiles = new LinkedList<>();

	private boolean moveUpIntent, moveDownIntent, moveLeftIntent, moveRightIntent, fireIntent;

	private int frameCount;

	public GameState() {
		this.gameMap = GameMaps.getGameMap();
		this.progressY = 0;
		this.playerX = (Config.MAP_SIZE_PX.width - Config.TILE_SIZE_PX.width) / 2;
		this.playerYOffset = (Config.MAX_PLAYER_Y_OFFSET - Config.MIN_PLAYER_Y_OFFSET) / 2;
		this.frameCount = 0;
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

	public int getPlayerY() {
		return progressY + playerYOffset;
	}

	public int getPlayerYOffset() {
		return playerYOffset;
	}

	public List<PlayerMissile> getPlayerMissiles() {
		return playerMissiles;
	}

	public boolean getMoveUpIntent() {
		return moveUpIntent;
	}

	public void setMoveUpIntent(boolean moveUpIntent) {
		this.moveUpIntent = moveUpIntent;
	}

	public boolean getMoveDownIntent() {
		return moveDownIntent;
	}

	public void setMoveDownIntent(boolean moveDownIntent) {
		this.moveDownIntent = moveDownIntent;
	}

	public boolean getMoveLeftIntent() {
		return moveLeftIntent;
	}

	public void setMoveLeftIntent(boolean moveLeftIntent) {
		this.moveLeftIntent = moveLeftIntent;
	}

	public boolean getMoveRightIntent() {
		return moveRightIntent;
	}

	public void setMoveRightIntent(boolean moveRightIntent) {
		this.moveRightIntent = moveRightIntent;
	}

	public boolean getFireIntent() {
		return fireIntent;
	}

	public void setFireIntent(boolean fireIntent) {
		this.fireIntent = fireIntent;
	}

	public void movePlaneUp(int distance) {
		this.playerYOffset += distance;
		if (playerYOffset > Config.MAX_PLAYER_Y_OFFSET) {
			this.playerYOffset = Config.MAX_PLAYER_Y_OFFSET;
		}
	}

	public void movePlaneDown(int distance) {
		this.playerYOffset -= distance;
		if (playerYOffset < Config.MIN_PLAYER_Y_OFFSET) {
			this.playerYOffset = Config.MIN_PLAYER_Y_OFFSET;
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

	public void fireGuns() {
		PlayerMissile missile = new PlayerMissile(playerX, getPlayerY(), 300);
		playerMissiles.add(missile);
	}

	/**
	 * Advance the game state forward one frame.
	 */
	public void updateState() {
		purgeMissiles();
		incrementProgressY(Config.PROGRESS_SPEED);
		processUserInputs();
		this.frameCount = (frameCount + 1) % Config.TARGET_FPS;
	}

	private void processUserInputs() {
		if (moveUpIntent) {
			movePlaneUp(Config.PLAYER_SPEED);
		}
		if (moveDownIntent) {
			movePlaneDown(Config.PLAYER_SPEED);
		}
		if (moveLeftIntent) {
			movePlaneLeft(Config.PLAYER_SPEED);
		}
		if (moveRightIntent) {
			movePlaneRight(Config.PLAYER_SPEED);
		}
		if (fireIntent) {
			if (frameCount % Config.PLAYER_FIRE_PERIOD == 0) {
				fireGuns();
			}
		}
	}

	private void purgeMissiles() {
		ListIterator<PlayerMissile> it = playerMissiles.listIterator();
		while (it.hasNext()) {
			PlayerMissile missile = it.next();
			missile.updateMissileState();
			if (missile.getTtl() == 0) {
				it.remove();
			}
		}
	}
}
