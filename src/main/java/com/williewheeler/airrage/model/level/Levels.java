package com.williewheeler.airrage.model.level;

import com.williewheeler.airrage.model.Formations;
import com.williewheeler.airrage.model.GameState;
import com.williewheeler.airrage.model.trigger.ProgressTrigger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by willie on 9/19/16.
 */
public class Levels {
	private static List<Level> levels = new ArrayList<>();

	static {
		levels.add(level1());
	}

	public static Level getLevel(int index) {
		return levels.get(index);
	}

	private static Level level1() {
		Level level = new Level();
		level.setGameMap(GameMaps.getGameMap());

		// TODO Might use Java 8 lamdbas here instead.

		level.addTrigger(new ProgressTrigger(200) {
			@Override
			public void fireTrigger(GameState gameState) {
				Formations.fingerFour(gameState);
			}
		});

		level.addTrigger(new ProgressTrigger(700) {
			@Override
			public void fireTrigger(GameState gameState) {
				Formations.battleSpread(gameState);
			}
		});

		// FIXME Should allow two triggers at the same y.
		level.addTrigger(new ProgressTrigger(750) {
			@Override
			public void fireTrigger(GameState gameState) {
				gameState.setCloudy(true);
			}
		});

		level.addTrigger(new ProgressTrigger(1200) {
			@Override
			public void fireTrigger(GameState gameState) {
				Formations.fluidTwo(gameState);
			}
		});

		level.addTrigger(new ProgressTrigger(1700) {
			@Override
			public void fireTrigger(GameState gameState) {
				Formations.trail(gameState);
			}
		});

		level.addTrigger(new ProgressTrigger(2200) {
			@Override
			public void fireTrigger(GameState gameState) {
				Formations.eschelon(gameState);
			}
		});

		level.addTrigger(new ProgressTrigger(2300) {
			@Override
			public void fireTrigger(GameState gameState) {
				gameState.setCloudy(false);
			}
		});

		level.addTrigger(new ProgressTrigger(2700) {
			@Override
			public void fireTrigger(GameState gameState) {
				Formations.fluidFour(gameState);
			}
		});

		level.addTrigger(new ProgressTrigger(3200) {
			@Override
			public void fireTrigger(GameState gameState) {
				Formations.box(gameState);
			}
		});

		level.addTrigger(new ProgressTrigger(3800) {
			@Override
			public void fireTrigger(GameState gameState) {
				Formations.fingerFour(gameState);
			}
		});

		return level;
	}
}
