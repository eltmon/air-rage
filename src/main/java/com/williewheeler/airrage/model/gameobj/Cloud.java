package com.williewheeler.airrage.model.gameobj;

/**
 * Created by willie on 9/25/16.
 */
public class Cloud implements GameObject {
	private int x;
	private int y;
	private int ttl;
	private int radius;
	private int speed;

	public Cloud(int x, int y, int ttl, int radius, int speed) {
		this.x = x;
		this.y = y;
		this.ttl = ttl;
		this.radius = radius;
		this.speed = speed;
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
		return 2 * radius;
	}

	@Override
	public int getHeight() {
		return 2 * radius;
	}

	@Override
	public double getRotation() {
		return 0;
	}

	@Override
	public int getTtl() {
		return ttl;
	}

	@Override
	public void updateState(int frameIndex) {
		this.ttl = Math.max(0, ttl - 1);
		this.y -= speed;
	}

	@Override
	public String toString() {
		return "Cloud{" +
				"x=" + x +
				", y=" + y +
				", ttl=" + ttl +
				", radius=" + radius +
				", speed=" + speed +
				'}';
	}
}
