package com.williewheeler.airrage.model;

import com.williewheeler.airrage.Config;
import com.williewheeler.airrage.event.GameEvent;
import com.williewheeler.airrage.event.GameListener;
import com.williewheeler.airrage.model.level.Level;
import com.williewheeler.airrage.model.trigger.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;

/**
 * Created by willie on 9/11/16.
 */
public class GameState {
	private static final Logger log = LoggerFactory.getLogger(GameState.class);

	private List<GameListener> gameListeners = new LinkedList<>();

	private Player player;
	private Level level;

	private List<Plane> planes = new LinkedList<>();
	private List<PlayerMissile> playerMissiles = new LinkedList<>();

	private int frameIndex;

	public GameState(Level level) {
		this.level = level;
		this.player = new Player(this);
		this.frameIndex = 0;
	}

	public void addGameListener(GameListener listener) {
		gameListeners.add(listener);
	}

	public Player getPlayer() {
		return player;
	}

	public Level getLevel() {
		return level;
	}

	public List<Plane> getPlanes() { return planes; }

	public void addPlane(Plane plane) { planes.add(plane); }

	public List<PlayerMissile> getPlayerMissiles() {
		return playerMissiles;
	}

	public void addPlayerMissile(PlayerMissile missile) {
		playerMissiles.add(missile);
	}

	/**
	 * Advance the game state forward one frame.
	 */
	public void updateState() {
		player.updateState(frameIndex);
		updateEnemies();
		updateMissiles();
		fireTriggers();
		this.frameIndex = (frameIndex + 1) % Config.TARGET_FPS;
	}

	void fireGameEvent(GameEvent event) {
		for (GameListener listener : gameListeners) {
			listener.handleGameEvent(event);
		}
	}

	private void updateEnemies() {
		ListIterator<Plane> it = planes.listIterator();
		while (it.hasNext()) {
			Plane plane = it.next();
			plane.updateState(frameIndex);
		}
	}

	private void updateMissiles() {
		ListIterator<PlayerMissile> it = playerMissiles.listIterator();
		while (it.hasNext()) {
			PlayerMissile missile = it.next();
			missile.updateState(frameIndex);
			if (missile.getTtl() == 0) {
				it.remove();
			}
		}
	}

	private void fireTriggers() {
		Queue<Trigger> triggers = level.getTriggers();
		if (triggers.peek() != null && triggers.peek().canFire(this)) {
			Trigger trigger = triggers.poll();
			trigger.fireTrigger(this);
		}
	}
}
