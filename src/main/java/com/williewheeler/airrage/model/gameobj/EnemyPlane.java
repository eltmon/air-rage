package com.williewheeler.airrage.model.gameobj;

import com.williewheeler.airrage.Config;
import com.williewheeler.airrage.GameUtil;
import com.williewheeler.airrage.model.GameState;

import java.awt.*;
import java.util.Random;

/**
 * Created by willie on 9/18/16.
 */
public class EnemyPlane implements GameObject {
	public static final Dimension ENEMY_SIZE_PX = new Dimension(64, 64);

	public static final int STATE_DAMAGED = 1 << 0;
	public static final int STATE_SPINNING = 1 << 1;

	private static final int SPEED = 2;
	private static final int FIRE_PERIOD = Config.TARGET_FPS / 3;

	private GameState gameState;
	private int x;
	private int y;

	/** State flags */
	private int stateFlags = 0;

	/**
	 * In radians. 0 degrees is north.
	 */
	private double rotation;

	private boolean firingMood = true;

	public EnemyPlane(GameState gameState, int x, int y) {
		this(gameState, x, y, Math.PI);
	}

	public EnemyPlane(GameState gameState, int x, int y, double rotation) {
		this.gameState = gameState;
		this.x = x;
		this.y = y;
		this.rotation = rotation;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getWidth() { return ENEMY_SIZE_PX.width; }

	@Override
	public int getHeight() { return ENEMY_SIZE_PX.height; }

	@Override
	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	@Override
	public int getTtl() {
		return -1;
	}

	public int getStateFlags() {
		return stateFlags;
	}

	public void setStateFlags(int stateFlags) {
		this.stateFlags = stateFlags;
	}

	@Override
	public void updateState(int frameIndex) {
		this.y -= SPEED;

		if ((stateFlags & STATE_DAMAGED) == 0) {
			updateStateForUndamagedPlane(frameIndex);
		} else {
			updateStateForDamagedPlane(frameIndex);
		}
	}

	private void updateStateForUndamagedPlane(int frameIndex) {
		// For now, enemies just fire like crazy.
		if (frameIndex % FIRE_PERIOD == 0) {
			Random random = GameUtil.random();
			if (firingMood) {
				if (random.nextDouble() < 0.1) {
					firingMood = false;
				}
			} else {
				if (random.nextDouble() < 0.2) {
					firingMood = true;
				}
			}

			if (firingMood) {
				fireGuns();
			}
		}
	}

	private void updateStateForDamagedPlane(int frameIndex) {
		if ((stateFlags & STATE_SPINNING) > 0) {
			this.rotation += 0.2;
		}

		if (frameIndex % 5 == 0) {
			Random random = GameUtil.random();
			double adjRot = rotation - Math.PI / 2;
			int rotX = (int) (x + 10 * Math.cos(adjRot)) + random.nextInt(4) - 2;
			int rotY = (int) (y + 10 * Math.sin(adjRot)) + random.nextInt(4) - 2;
			PuffOfSmoke puff = new PuffOfSmoke(rotX, rotY, 5);
			gameState.addPuffOfSmoke(puff);
		}
	}

	private void fireGuns() {
		int missileY = getY() - 16;
		gameState.addEnemyMissile(new EnemyMissile(x - 20, missileY));
		gameState.addEnemyMissile(new EnemyMissile(x + 20, missileY));
	}
}
