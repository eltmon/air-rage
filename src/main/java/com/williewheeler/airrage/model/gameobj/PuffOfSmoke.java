package com.williewheeler.airrage.model.gameobj;

import com.williewheeler.airrage.GameUtil;

/**
 * Created by willie on 9/23/16.
 */
public class PuffOfSmoke implements GameObject {
	private static final int DEFAULT_BRIGHTNESS = 66;
	private static final int DEFAULT_ALPHA = 100;

	private int x;
	private int y;
	private int radius;
	private int brightness;
	private int alpha;

	public PuffOfSmoke(int x, int y, int radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
//		this.brightness = DEFAULT_BRIGHTNESS;

		this.brightness = GameUtil.random().nextInt(66) + 40;
		this.alpha = DEFAULT_ALPHA;
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
		// When alpha drops to 0, poof! :)
		return alpha;
	}

	public int getRadius() {
		return radius;
	}

	public int getBrightness() {
		return brightness;
	}

	public int getAlpha() {
		return alpha;
	}

	@Override
	public void updateState(int frameIndex) {
		if (frameIndex % 3 == 0) {
			this.radius += 1;
			this.alpha = Math.max(0, alpha - 5);
		}
	}
}

