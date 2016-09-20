package com.williewheeler.airrage.model;

import com.williewheeler.airrage.Config;

// http://www.combataircraft.com/en/Formations/

/**
 * Created by willie on 9/19/16.
 */
public class Formations {

	public static void battleSpread(GameState gameState) {
		Player player = gameState.getPlayer();
		int progressY = player.getProgressY();
		int topY = progressY + Config.VIEWPORT_SIZE_PX.height;

		gameState.addPlane(new Plane(300, topY));
		gameState.addPlane(new Plane(400, topY));
		gameState.addPlane(new Plane(500, topY));
		gameState.addPlane(new Plane(600, topY));
	}

	// Finger-four, aka fingertip
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

	public static void fluidTwo(GameState gameState) {
		Player player = gameState.getPlayer();
		int progressY = player.getProgressY();
		int topY = progressY + Config.VIEWPORT_SIZE_PX.height;

		gameState.addPlane(new Plane(400, topY));
		gameState.addPlane(new Plane(600, topY));
	}

	public static void trail(GameState gameState) {
		Player player = gameState.getPlayer();
		int progressY = player.getProgressY();
		int topY = progressY + Config.VIEWPORT_SIZE_PX.height;

		gameState.addPlane(new Plane(400, topY));
		gameState.addPlane(new Plane(400, topY + 80));
		gameState.addPlane(new Plane(400, topY + 160));
	}

	// TODO Wall, but looks like a battle spread?
	// Oh, there are two above and two below.

	public static void eschelon(GameState gameState) {
		Player player = gameState.getPlayer();
		int progressY = player.getProgressY();
		int topY = progressY + Config.VIEWPORT_SIZE_PX.height;

		gameState.addPlane(new Plane(600, topY));
		gameState.addPlane(new Plane(520, topY + 80));
		gameState.addPlane(new Plane(440, topY + 160));
		gameState.addPlane(new Plane(360, topY + 240));
	}

	public static void fluidFour(GameState gameState) {
		Player player = gameState.getPlayer();
		int progressY = player.getProgressY();
		int topY = progressY + Config.VIEWPORT_SIZE_PX.height;

		gameState.addPlane(new Plane(300, topY + 80));
		gameState.addPlane(new Plane(400, topY));
		gameState.addPlane(new Plane(500, topY));
		gameState.addPlane(new Plane(600, topY + 80));
	}

	// TODO Route

	// TODO Ladder

	public static void box(GameState gameState) {
		Player player = gameState.getPlayer();
		int progressY = player.getProgressY();
		int topY = progressY + Config.VIEWPORT_SIZE_PX.height;

		gameState.addPlane(new Plane(400, topY));
		gameState.addPlane(new Plane(400, topY + 160));
		gameState.addPlane(new Plane(600, topY));
		gameState.addPlane(new Plane(600, topY + 160));
	}
}
