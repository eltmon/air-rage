package com.williewheeler.airrage.model.gameobj;

/**
 * Created by willie on 9/18/16.
 */
public interface GameObject {

	int getX();

	int getY();

	int getWidth();

	int getHeight();

	double getRotation();

	int getTtl();

	void updateState(int frameIndex);
}
