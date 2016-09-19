package com.williewheeler.airrage.model;

/**
 * Created by willie on 9/18/16.
 */
public interface GameObject {

	int getX();

	int getY();

	double getRotation();

	int getTtl();

	void updateState(int frameIndex);
}
