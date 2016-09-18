package com.williewheeler.airrage.model;

/**
 * Created by willie on 9/14/16.
 */
public class PlayerMissile {

	// x coord of the center of the missile
	private int x;

	// y coord of the center of the missile
	private int y;

	private int ttl;

	// TODO Add direction vector
	public PlayerMissile(int x, int y, int ttl) {
		this.x = x;
		this.y = y;
		this.ttl = ttl;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getTtl() {
		return ttl;
	}

	public void updateMissileState() {
		// TODO Adjust location based on initial direction vector
		this.y += 15;
		decrementTtl();
	}

	private void decrementTtl() {
		this.ttl--;
		if (ttl < 0) {
			this.ttl = 0;
		}
	}
}
