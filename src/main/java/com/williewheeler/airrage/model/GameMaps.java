package com.williewheeler.airrage.model;

import com.williewheeler.airrage.Config;

import java.util.Random;

import static com.williewheeler.airrage.Config.MAP_SIZE;

/**
 * Created by willie on 9/4/16.
 */
public class GameMaps {

	public static int[][] getGameMap() {
		Random random = new Random();
		int[][] gameMap = new int[MAP_SIZE.height][MAP_SIZE.width];
		for (int i = 0; i < MAP_SIZE.height; i++) {
			for (int j = 0; j < MAP_SIZE.width; j++) {
//				double distFromCenter = (double)(Math.abs(MAP_SIZE.width / 2 - j)) / MAP_SIZE.width;
				double distFromCenter = Math.abs(0.5 * Config.MAP_SIZE.width - j) / (double) Config.MAP_SIZE.width;
				if (random.nextDouble() < distFromCenter) {
					gameMap[i][j] = Tiles.LAND;
				} else {
					gameMap[i][j] = Tiles.SEA;
				}
			}
		}
		return gameMap;
	}
}
