package com.williewheeler.airrage.model;

import com.williewheeler.airrage.Config;
import com.williewheeler.airrage.event.GameEvent;
import com.williewheeler.airrage.event.GameListener;
import com.williewheeler.airrage.model.gameobj.EnemyMissile;
import com.williewheeler.airrage.model.gameobj.EnemyPlane;
import com.williewheeler.airrage.model.gameobj.Player;
import com.williewheeler.airrage.model.gameobj.PlayerMissile;
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

	private final List<PlayerMissile> playerMissiles = new LinkedList<>();
	private final List<EnemyPlane> enemyPlanes = new LinkedList<>();
	private final List<EnemyMissile> enemyMissiles = new LinkedList<>();

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

	public List<EnemyPlane> getEnemyPlanes() {
		return enemyPlanes;
	}

	public void addEnemyPlane(EnemyPlane plane) {
		enemyPlanes.add(plane);
	}

	public List<PlayerMissile> getPlayerMissiles() {
		return playerMissiles;
	}

	public void addPlayerMissile(PlayerMissile missile) {
		playerMissiles.add(missile);
	}

	public List<EnemyMissile> getEnemyMissiles() {
		return enemyMissiles;
	}

	public void addEnemyMissile(EnemyMissile missile) {
		enemyMissiles.add(missile);
	}

	public void fireGameEvent(GameEvent event) {
		for (GameListener listener : gameListeners) {
			listener.handleGameEvent(event);
		}
	}

	/**
	 * Advance the game state forward one frame.
	 */
	public void updateState() {
		checkForDownedPlayer();
		checkForDownedEnemies();
		player.updateState(frameIndex);
		updatePlayerMissiles();
		updateEnemies();
		updateEnemyMissiles();
		fireTriggers();
		this.frameIndex = (frameIndex + 1) % Config.TARGET_FPS;
	}

	private void checkForDownedPlayer() {
		ListIterator<EnemyMissile> missileIt = enemyMissiles.listIterator();
		while (missileIt.hasNext()) {
			EnemyMissile missile = missileIt.next();
			boolean collision = Collisions.collision(missile, player);
			if (collision) {
				player.setDowned(true);
			}
		}
	}

	private void checkForDownedEnemies() {
		ListIterator<PlayerMissile> missileIt = playerMissiles.listIterator();
		while (missileIt.hasNext()) {
			PlayerMissile missile = missileIt.next();
			ListIterator<EnemyPlane> planeIt = enemyPlanes.listIterator();
			while (planeIt.hasNext()) {
				EnemyPlane plane = planeIt.next();
				boolean collision = Collisions.collision(missile, plane);
				if (collision) {
					missileIt.remove();
					planeIt.remove();
					fireGameEvent(new GameEvent(GameEvent.ENEMY_DOWNED));
				}
			}
		}
	}

	private void updatePlayerMissiles() {
		ListIterator<PlayerMissile> it = playerMissiles.listIterator();
		while (it.hasNext()) {
			PlayerMissile missile = it.next();
			missile.updateState(frameIndex);
			if (missile.getTtl() <= 0) {
				it.remove();
			}
		}
	}

	private void updateEnemies() {
		ListIterator<EnemyPlane> it = enemyPlanes.listIterator();
		while (it.hasNext()) {
			EnemyPlane plane = it.next();
			plane.updateState(frameIndex);
		}
	}

	private void updateEnemyMissiles() {
		ListIterator<EnemyMissile> it = enemyMissiles.listIterator();
		while (it.hasNext()) {
			EnemyMissile missile = it.next();
			missile.updateState(frameIndex);
			if (missile.getTtl() <= 0) {
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
