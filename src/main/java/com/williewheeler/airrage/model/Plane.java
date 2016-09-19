package com.williewheeler.airrage.model;

/**
 * Created by willie on 9/18/16.
 */
public class Plane {
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
}
