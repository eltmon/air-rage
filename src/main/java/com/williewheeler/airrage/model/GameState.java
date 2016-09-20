package com.williewheeler.airrage.model;

import com.williewheeler.airrage.Config;
import com.williewheeler.airrage.event.GameEvent;
import com.williewheeler.airrage.event.GameListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by willie on 9/11/16.
 */
public class GameState {
	private static final Logger log = LoggerFactory.getLogger(GameState.class);

	private List<GameListener> gameListeners = new LinkedList<>();

	/**
	 * Tile coords. Row 0 is at the bottom of the map.
	 */
	private int[][] gameMap;

	private Player player;

	private List<PlayerMissile> playerMissiles = new LinkedList<>();

	private int frameIndex;

	// TODO Temporary
	private Plane enemy;

	public GameState() {
		this.gameMap = GameMaps.getGameMap();
		this.player = new Player(this);
		this.frameIndex = 0;

		// TODO Temporary
		this.enemy = new Plane(400, 1600, Math.PI);
	}

	public void addGameListener(GameListener listener) {
		gameListeners.add(listener);
	}

	// FIXME Remove this
	public Plane getEnemy() {
		return enemy;
	}

	public int[][] getGameMap() {
		return gameMap;
	}

	public Player getPlayer() {
		return player;
	}

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
		this.frameIndex = (frameIndex + 1) % Config.TARGET_FPS;
	}

	void fireGameEvent(GameEvent event) {
		for (GameListener listener : gameListeners) {
			listener.handleGameEvent(event);
		}
	}

	private void updateEnemies() {
		enemy.updateState(frameIndex);
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
}
