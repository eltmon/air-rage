package com.williewheeler.airrage.model;

/**
 * Created by willie on 9/18/16.
 */
public class Plane implements GameObject {
	private int x;
	private int y;

	/**
	 * In radians. 0 degrees is north.
	 */
	private double rotation;

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
		this.y -= 2;
	}
}
