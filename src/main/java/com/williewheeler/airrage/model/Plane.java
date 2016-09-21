package com.williewheeler.airrage.model;

import java.awt.*;

/**
 * Created by willie on 9/18/16.
 */
public class Plane implements GameObject {
	public static final Dimension ENEMY_SIZE_PX = new Dimension(64, 64);

	private static final int SPEED = 2;

	private int x;
	private int y;

	/**
	 * In radians. 0 degrees is north.
	 */
	private double rotation;

	public Plane(int x, int y) {
		this(x, y, Math.PI);
	}

	public Plane(int x, int y, double rotation) {
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
	}
}
