package com.williewheeler.airrage.model;

import com.williewheeler.airrage.Config;
import com.williewheeler.airrage.model.gameobj.EnemyPlane;
import com.williewheeler.airrage.model.gameobj.Player;

// http://www.combataircraft.com/en/Formations/

/**
 * Created by willie on 9/19/16.
 */
public class Formations {

	public static void battleSpread(GameState gameState) {
		Player player = gameState.getPlayer();
		int progressY = player.getProgressY();

		// Use an offset to give puffs of smoke time to form
		final int offset = 200;
		int topY = progressY + Config.VIEWPORT_SIZE_PX.height + offset;

		gameState.addEnemyPlane(new EnemyPlane(gameState, 300, topY));
		gameState.addEnemyPlane(new EnemyPlane(gameState, 400, topY));
		gameState.addEnemyPlane(new EnemyPlane(gameState, 500, topY));
		gameState.addEnemyPlane(new EnemyPlane(gameState, 600, topY));
	}

	// Finger-four, aka fingertip
	// https://en.wikipedia.org/wiki/Finger-four
	public static void fingerFour(GameState gameState) {
		Player player = gameState.getPlayer();
		int progressY = player.getProgressY();
		int topY = progressY + Config.VIEWPORT_SIZE_PX.height;

		gameState.addEnemyPlane(new EnemyPlane(gameState, 400, topY));
		gameState.addEnemyPlane(new EnemyPlane(gameState, 500, topY + 80));
		gameState.addEnemyPlane(new EnemyPlane(gameState, 300, topY + 80));
		gameState.addEnemyPlane(new EnemyPlane(gameState, 200, topY + 160));
	}

	public static void fluidTwo(GameState gameState) {
		Player player = gameState.getPlayer();
		int progressY = player.getProgressY();
		int topY = progressY + Config.VIEWPORT_SIZE_PX.height;

		gameState.addEnemyPlane(new EnemyPlane(gameState, 400, topY));
		gameState.addEnemyPlane(new EnemyPlane(gameState, 600, topY));
	}

	public static void trail(GameState gameState) {
		Player player = gameState.getPlayer();
		int progressY = player.getProgressY();
		int topY = progressY + Config.VIEWPORT_SIZE_PX.height;

		gameState.addEnemyPlane(new EnemyPlane(gameState, 400, topY));
		gameState.addEnemyPlane(new EnemyPlane(gameState, 400, topY + 80));
		gameState.addEnemyPlane(new EnemyPlane(gameState, 400, topY + 160));
	}

	// TODO Wall, but looks like a battle spread?
	// Oh, there are two above and two below.

	public static void eschelon(GameState gameState) {
		Player player = gameState.getPlayer();
		int progressY = player.getProgressY();
		int topY = progressY + Config.VIEWPORT_SIZE_PX.height;

		gameState.addEnemyPlane(new EnemyPlane(gameState, 600, topY));
		gameState.addEnemyPlane(new EnemyPlane(gameState, 520, topY + 80));
		gameState.addEnemyPlane(new EnemyPlane(gameState, 440, topY + 160));
		gameState.addEnemyPlane(new EnemyPlane(gameState, 360, topY + 240));
	}

	public static void fluidFour(GameState gameState) {
		Player player = gameState.getPlayer();
		int progressY = player.getProgressY();
		int topY = progressY + Config.VIEWPORT_SIZE_PX.height;

		gameState.addEnemyPlane(new EnemyPlane(gameState, 300, topY + 80));
		gameState.addEnemyPlane(new EnemyPlane(gameState, 400, topY));
		gameState.addEnemyPlane(new EnemyPlane(gameState, 500, topY));
		gameState.addEnemyPlane(new EnemyPlane(gameState, 600, topY + 80));
	}

	// TODO Route

	// TODO Ladder

	public static void box(GameState gameState) {
		Player player = gameState.getPlayer();
		int progressY = player.getProgressY();
		int topY = progressY + Config.VIEWPORT_SIZE_PX.height;

		gameState.addEnemyPlane(new EnemyPlane(gameState, 400, topY));
		gameState.addEnemyPlane(new EnemyPlane(gameState, 400, topY + 160));
		gameState.addEnemyPlane(new EnemyPlane(gameState, 600, topY));
		gameState.addEnemyPlane(new EnemyPlane(gameState, 600, topY + 160));
	}
}
