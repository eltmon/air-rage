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

	private static final int SPEED = 2;
	private static final int FIRE_PERIOD = Config.TARGET_FPS / 3;
	private static final int PUFF_PERIOD = Config.TARGET_FPS / 15;

	private GameState gameState;
	private int x;
	private int y;

	/** State flags */
	private int planeState = 0;

	/**
	 * In radians. 0 degrees is north.
	 */
	private double rotation;

	private int ttl;

	private boolean firingMood = true;

	public EnemyPlane(GameState gameState, int x, int y) {
		this(gameState, x, y, Math.PI);
	}

	public EnemyPlane(GameState gameState, int x, int y, double rotation) {
		this.gameState = gameState;
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		this.ttl = -1;
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
		return ttl;
	}

	public void setTtl(int ttl) {
		this.ttl = ttl;
	}

	public int getPlaneState() {
		return planeState;
	}

	public void setPlaneState(int planeState) {
		this.planeState = planeState;
	}

	@Override
	public void updateState(int frameIndex) {
		this.y -= SPEED;

		if ((planeState & GameObjectStates.STATE_DAMAGED) == 0) {
			updateStateForUndamagedPlane(frameIndex);
		} else {
			updateStateForDamagedPlane(frameIndex);
		}
	}

	/**
	 * This is the update method for undamaged planes.
	 *
	 * @param frameIndex
	 */
	private void updateStateForUndamagedPlane(int frameIndex) {
		decideWhetherToFire(frameIndex);

		if (frameIndex % PUFF_PERIOD == 0) {
			createPuffOfSmoke(1, 255);
		}
	}

	/**
	 * This is the update method for damaged planes.
	 *
	 * @param frameIndex
	 */
	private void updateStateForDamagedPlane(int frameIndex) {
		if ((planeState & GameObjectStates.STATE_SPINNING) > 0) {
			this.rotation += 0.1;
		}

		if (frameIndex % PUFF_PERIOD == 0) {
			int brightness = GameUtil.RANDOM.nextInt(66) + 40;
			createPuffOfSmoke(5, brightness);
		}
	}

	private void decideWhetherToFire(int frameIndex) {
		if (frameIndex % FIRE_PERIOD == 0) {
			Random random = GameUtil.RANDOM;
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

	private void createPuffOfSmoke(int radius, int brightness) {
		Random random = GameUtil.RANDOM;
		double adjRot = -rotation + Math.PI / 2;
		int noiseX = random.nextInt(4) - 2;
		int noiseY = random.nextInt(4) - 2;
		int engineX = (int) (20 * Math.cos(adjRot));
		int engineY = (int) (20 * Math.sin(adjRot));
		int puffX = x + engineX + noiseX;
		int puffY = y + engineY + noiseY;
		PuffOfSmoke puff = new PuffOfSmoke(puffX, puffY, radius, brightness);
		gameState.addPuffOfSmoke(puff);
	}

	private void fireGuns() {
		int missileY = getY() - 16;
		gameState.addEnemyMissile(new EnemyMissile(x - 20, missileY));
		gameState.addEnemyMissile(new EnemyMissile(x + 20, missileY));
	}
}
