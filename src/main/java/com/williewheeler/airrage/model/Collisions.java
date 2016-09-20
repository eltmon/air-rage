package com.williewheeler.airrage.model;

/**
 * Created by willie on 9/19/16.
 */
public class Collisions {

	public static boolean collision(GameObject o1, GameObject o2) {
		// TODO For right now just decide whether the objects are "close". But really we want to check whether their
		// bounding boxes (or spheres, however we do it) intersect.
		int xDiff = o1.getX() - o2.getX();
		int yDiff = o1.getY() - o2.getY();
		double dist = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
		return dist < 50;
	}
}
