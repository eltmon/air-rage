package com.williewheeler.airrage.model;

/**
 * Created by willie on 9/14/16.
 */
public class PlayerMissile implements GameObject {
	private static final int SPEED = 15;
	private static final int TTL = 300;

	// x coord of the center of the missile
	private int x;

	// y coord of the center of the missile
	private int y;

	private int ttl;

	// TODO Add direction vector
	public PlayerMissile(int x, int y) {
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
	public double getRotation() {
		return 0.0;
	}

	@Override
	public int getTtl() {
		return ttl;
	}

	@Override
	public void updateState(int frameIndex) {
		// TODO Adjust location based on initial direction vector
		this.y += SPEED;
		decrementTtl();
	}

	private void decrementTtl() {
		this.ttl--;
		if (ttl < 0) {
			this.ttl = 0;
		}
	}
}
