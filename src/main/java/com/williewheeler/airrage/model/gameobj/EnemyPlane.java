package com.williewheeler.airrage.model.gameobj;

import com.williewheeler.airrage.Config;
import com.williewheeler.airrage.model.GameState;

import java.awt.*;

/**
 * Created by willie on 9/18/16.
 */
public class EnemyPlane implements GameObject {
	public static final Dimension ENEMY_SIZE_PX = new Dimension(64, 64);

	private static final int SPEED = 2;
	private static final int FIRE_PERIOD = Config.TARGET_FPS;

	private GameState gameState;
	private int x;
	private int y;

	/**
	 * In radians. 0 degrees is north.
	 */
	private double rotation;

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

	@Override
	public void updateState(int frameIndex) {
		this.y -= SPEED;

		// For now, enemies just fire like crazy.
		if (frameIndex % FIRE_PERIOD == 0) {
			fireGuns();
		}
	}

	private void fireGuns() {
		// TODO Remove hardcodes, and the coords should be the missile centroids
		// Also player coords need to be centroid as well
		int missileY = getY() + ENEMY_SIZE_PX.height + 24;
		gameState.addEnemyMissile(new EnemyMissile(x + 7, missileY));
		gameState.addEnemyMissile(new EnemyMissile(x + 45, missileY));
	}
}
