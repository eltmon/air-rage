package com.williewheeler.airrage.model.level;

import com.williewheeler.airrage.model.trigger.Trigger;

import java.util.LinkedList;
import java.util.Queue;

// NOTE This is a *mutable* data structure. Specifically we remove triggers as they fire.
// But I'm thinking that we probably want to avoid making this mutable. For example maybe we should
// copy the triggers into the game state. No biggie for now. [WLW]

/**
 * Created by willie on 9/19/16.
 */
public class Level {

	/**
	 * Tile coords. Row 0 is at the bottom of the map.
	 */
	private int[][] gameMap;

	private Queue<Trigger> triggers = new LinkedList<>();

	public int[][] getGameMap() {
		return gameMap;
	}

	public void setGameMap(int[][] gameMap) {
		this.gameMap = gameMap;
	}

	public void addTrigger(Trigger trigger) {
		triggers.add(trigger);
	}

	public Queue<Trigger> getTriggers() {
		return triggers;
	}
}
