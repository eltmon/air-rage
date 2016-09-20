package com.williewheeler.airrage.model;

import com.williewheeler.airrage.Config;

/**
 * Created by willie on 9/19/16.
 */
public class Formations {

	// https://en.wikipedia.org/wiki/Finger-four
	public static void fingerFour(GameState gameState) {
		Player player = gameState.getPlayer();
		int progressY = player.getProgressY();
		int topY = progressY + Config.VIEWPORT_SIZE_PX.height;

		Plane flightLeader = new Plane(400, topY);
		Plane flightWingman = new Plane(500, topY + 80);
		Plane elementLeader = new Plane(300, topY + 80);
		Plane elementWingman = new Plane(200, topY + 160);

		gameState.addPlane(flightLeader);
		gameState.addPlane(flightWingman);
		gameState.addPlane(elementLeader);
		gameState.addPlane(elementWingman);
	}
}
