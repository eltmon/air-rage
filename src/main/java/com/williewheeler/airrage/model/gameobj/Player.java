package com.williewheeler.airrage.model.gameobj;

import com.williewheeler.airrage.Config;
import com.williewheeler.airrage.event.GameEvent;
import com.williewheeler.airrage.model.GameState;

import java.awt.*;

/**
 * Created by willie on 9/18/16.
 */
public class Player implements GameObject {

	// FIXME Make private
	public static final Dimension PLAYER_SIZE_PX = new Dimension(64, 64);

	private static final int PROGRESS_SPEED = 2;
	private static final int MOVEMENT_SPEED = 5;
	private static final int MIN_PLAYER_Y_OFFSET = 20;
	private static final int MAX_PLAYER_Y_OFFSET = 400;

	/** Fire period in frames. */
	private static final int FIRE_PERIOD = Config.TARGET_FPS / 10;
//	private static final int FIRE_PERIOD = 1;

	private GameState gameState;
	private int x;

	/** This is the macro y */
	private int progressY;

	/** This is the micro y */
	private int yOffset;

	private boolean moveUpIntent;
	private boolean moveDownIntent;
	private boolean moveLeftIntent;
	private boolean moveRightIntent;
	private boolean fireIntent;

	public Player(GameState gameState) {
		this.gameState = gameState;
		this.x = (Config.MAP_SIZE_PX.width - Config.TILE_SIZE_PX.width) / 2;
		this.progressY = 0;
		this.yOffset = (MAX_PLAYER_Y_OFFSET - MIN_PLAYER_Y_OFFSET) / 2;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return progressY + yOffset;
	}

	@Override
	public int getWidth() { return PLAYER_SIZE_PX.width; }

	@Override
	public int getHeight() { return PLAYER_SIZE_PX.height; }

	public int getProgressY() {
		return progressY;
	}

	public int getYOffset() {
		return yOffset;
	}

	@Override
	public double getRotation() {
		return 0;
	}

	@Override
	public int getTtl() {
		return -1;
	}

	public void setMoveUpIntent(boolean moveUpIntent) {
		this.moveUpIntent = moveUpIntent;
	}

	public void setMoveDownIntent(boolean moveDownIntent) {
		this.moveDownIntent = moveDownIntent;
	}

	public void setMoveLeftIntent(boolean moveLeftIntent) {
		this.moveLeftIntent = moveLeftIntent;
	}

	public void setMoveRightIntent(boolean moveRightIntent) {
		this.moveRightIntent = moveRightIntent;
	}

	public void setFireIntent(boolean fireIntent) {
		this.fireIntent = fireIntent;
	}

	@Override
	public void updateState(int frameIndex) {

		// TODO Maybe this belongs with the global game state instead of in the player, since it triggers events
		// external to the player.
		this.progressY += Player.PROGRESS_SPEED;

		if (moveUpIntent) {
			moveUp();
		}
		if (moveDownIntent) {
			moveDown();
		}
		if (moveLeftIntent) {
			moveLeft();
		}
		if (moveRightIntent) {
			moveRight();
		}
		if (fireIntent) {
			if (frameIndex % FIRE_PERIOD == 0) {
				fireGuns();
			}
		}
	}

	private void moveUp() {
		this.yOffset += MOVEMENT_SPEED;
		if (yOffset > MAX_PLAYER_Y_OFFSET) {
			this.yOffset = MAX_PLAYER_Y_OFFSET;
		}
	}

	private void moveDown() {
		this.yOffset -= MOVEMENT_SPEED;
		if (yOffset < MIN_PLAYER_Y_OFFSET) {
			this.yOffset = MIN_PLAYER_Y_OFFSET;
		}
	}

	private void moveLeft() {
		this.x -= MOVEMENT_SPEED;
		if (x < 0) {
			this.x = 0;
		}
	}

	private void moveRight() {
		this.x += MOVEMENT_SPEED;
		int limit = Config.MAP_SIZE_PX.width - PLAYER_SIZE_PX.width - 1;
		if (x > limit) {
			this.x = limit;
		}
	}

	private void fireGuns() {

		// TODO Remove hardcodes, and the coords should be the missile centroids
		// Also player coords need to be centroid as well
		int missileY = getY() + PLAYER_SIZE_PX.height - 24;
		gameState.addPlayerMissile(new PlayerMissile(x + 7, missileY));
		gameState.addPlayerMissile(new PlayerMissile(x + 45, missileY));

		GameEvent event = new GameEvent(GameEvent.PLAYER_FIRED);
		gameState.fireGameEvent(event);
	}
}
