package com.williewheeler.airrage.model.gameobj;

/**
 * Created by willie on 9/20/16.
 */
public class EnemyMissile implements GameObject {
	private static final int SPEED = 4;
	private static final int TTL = 300;

	// x coord of the center of the missile
	private int x;

	// y coord of the center of the missile
	private int y;

	private int ttl;

	public EnemyMissile(int x, int y) {
		this.x = x;
		this.y = y;
		this.ttl = TTL;
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
	public int getWidth() {
		return 9;
	}

	@Override
	public int getHeight() {
		return 14;
	}

	@Override
	public double getRotation() {
		return 0.0;
	}

	@Override
	public int getTtl() {
		return ttl;
	}

	@Override
	public void updateState(int frameIndex) {
		this.y -= SPEED;
		decrementTtl();
	}

	private void decrementTtl() {
		this.ttl--;
		if (ttl < 0) {
			this.ttl = 0;
		}
	}
}
