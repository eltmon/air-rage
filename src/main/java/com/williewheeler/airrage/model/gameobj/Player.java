package com.williewheeler.airrage.model.gameobj;

import com.williewheeler.airrage.Config;
import com.williewheeler.airrage.GameUtil;
import com.williewheeler.airrage.event.GameEvent;
import com.williewheeler.airrage.model.GameState;

import java.awt.*;
import java.util.Random;

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
	private static final int PUFF_PERIOD = Config.TARGET_FPS / 15;

	/** Fire period in frames. */
	private static final int FIRE_PERIOD = Config.TARGET_FPS / 10;

	private GameState gameState;
	private int x;

	/** This is the macro y */
	private int progressY;

	/** This is the micro y */
	private int yOffset;

	private double rotation = 0.0;

	private int health = 10;

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

	@Override
	public double getRotation() {
		return rotation;
	}

	@Override
	public int getTtl() {
		return -1;
	}

	public int getHealth() {
		return health;
	}

	public void decrementHealth(int amount) {
		this.health = Math.max(0, health - amount);
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

		if (frameIndex % PUFF_PERIOD == 0) {
			if (health <= 3) {
				createPuffOfSmoke(5, 0);
			} else if (health <= 7) {
				createPuffOfSmoke(5, 66);
			} else {
				createPuffOfSmoke(1, 255);
			}
		}

		if (health > 0) {
			updateStateForLivingPlayer(frameIndex);
		} else {
			updateStateForDeadPlayer(frameIndex);
		}
	}

	private void updateStateForLivingPlayer(int frameIndex) {
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

	private void updateStateForDeadPlayer(int frameIndex) {
		rotation += 0.1;

		if (frameIndex % PUFF_PERIOD == 0) {
			int brightness = GameUtil.RANDOM.nextInt(66) + 40;
			createPuffOfSmoke(5, brightness);
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
		final int lowerBound = PLAYER_SIZE_PX.width / 2;
		this.x -= MOVEMENT_SPEED;
		if (x < lowerBound) {
			this.x = lowerBound;
		}
	}

	private void moveRight() {
		final int upperBound = Config.MAP_SIZE_PX.width - PLAYER_SIZE_PX.width / 2 - 1;
		this.x += MOVEMENT_SPEED;
		if (x > upperBound) {
			this.x = upperBound;
		}
	}

	private void createPuffOfSmoke(int radius, int brightness) {
		Random random = GameUtil.RANDOM;
		double adjRot = rotation + Math.PI / 2;
		int rotX = (int) (x + 20 * Math.cos(adjRot)) + random.nextInt(4) - 2;
		int rotY = (int) (getY() + 20 * Math.sin(adjRot)) + random.nextInt(4) - 2;
		PuffOfSmoke puff = new PuffOfSmoke(rotX, rotY, radius, brightness);
		gameState.addPuffOfSmoke(puff);
	}

	private void fireGuns() {
		int missileY = getY() + 12;
		gameState.addPlayerMissile(new PlayerMissile(x - 16, missileY));
		gameState.addPlayerMissile(new PlayerMissile(x + 14, missileY));

		GameEvent event = new GameEvent(GameEvent.PLAYER_FIRED);
		gameState.fireGameEvent(event);
	}
}
