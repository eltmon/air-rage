package com.williewheeler.airrage.model;

import com.williewheeler.airrage.Config;
import com.williewheeler.airrage.event.GameEvent;

import java.awt.*;

import static com.williewheeler.airrage.Config.TARGET_FPS;

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
	private static final int PLAYER_FIRE_PERIOD = TARGET_FPS / 10;
//	private static final int PLAYER_FIRE_PERIOD = 1;

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

		// TODO Externalize this trigger
		if (progressY == 200) {
			Formations.fingerFour(gameState);
		} else if (progressY == 700) {
			Formations.battleSpread(gameState);
		} else if (progressY == 1200) {
			Formations.fluidTwo(gameState);
		} else if (progressY == 1700) {
			Formations.trail(gameState);
		} else if (progressY == 2200) {
			Formations.eschelon(gameState);
		} else if (progressY == 2700) {
			Formations.fluidFour(gameState);
		} else if (progressY == 3200) {
			Formations.box(gameState);
		}

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
			if (frameIndex % PLAYER_FIRE_PERIOD == 0) {
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
