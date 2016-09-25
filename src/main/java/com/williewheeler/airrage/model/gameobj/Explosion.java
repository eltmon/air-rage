package com.williewheeler.airrage.model.gameobj;

/**
 * Created by willie on 9/24/16.
 */
public class Explosion implements GameObject {
	private int x;
	private int y;
	private int radius;

	/** 0-255. 0 is fully transparent */
	private int alpha;

	public Explosion(int x, int y, int radius, int alpha) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.alpha = alpha;
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
		return radius;
	}

	public int getAlpha() {
		return alpha;
	}

	@Override
	public void updateState(int frameIndex) {
		if (frameIndex % 3 == 0) {
			this.radius = Math.max(0, radius - 1);
			this.alpha = Math.max(0, alpha - 5);
		}
	}

	@Override
	public String toString() {
		return "Explosion{" +
				"x=" + x +
				", y=" + y +
				", radius=" + radius +
				", alpha=" + alpha +
				'}';
	}
}
