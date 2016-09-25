package com.williewheeler.airrage.model;

import com.williewheeler.airrage.Config;
import com.williewheeler.airrage.event.GameEvent;
import com.williewheeler.airrage.event.GameListener;
import com.williewheeler.airrage.model.gameobj.*;
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

	private CollisionDetector collisionDetector;
	private List<GameListener> gameListeners = new LinkedList<>();

	private Player player;
	private Level level;

	private final List<PlayerMissile> playerMissiles = new LinkedList<>();
	private final List<EnemyPlane> enemyPlanes = new LinkedList<>();
	private final List<EnemyMissile> enemyMissiles = new LinkedList<>();
	private final List<PuffOfSmoke> puffsOfSmoke = new LinkedList<>();
	private final List<Explosion> explosions = new LinkedList<>();

	private int frameIndex;

	public GameState(Level level) {
		this.collisionDetector = new CollisionDetector(this);

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

	public List<PuffOfSmoke> getPuffsOfSmoke() {
		return puffsOfSmoke;
	}

	public void addPuffOfSmoke(PuffOfSmoke puff) {
		puffsOfSmoke.add(puff);
	}

	public List<Explosion> getExplosions() {
		return explosions;
	}

	public void addExplosion(Explosion explosion) {
		explosions.add(explosion);
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
		fireTriggers();

		player.updateState(frameIndex);
		updateAll(playerMissiles);
		updateAll(enemyPlanes);
		updateAll(enemyMissiles);
		updateAll(puffsOfSmoke);
		updateAll(explosions);

		collisionDetector.checkPlayerHit();
		collisionDetector.checkEnemiesHit();

		this.frameIndex = (frameIndex + 1) % Config.TARGET_FPS;
	}

	private <T extends GameObject> void updateAll(List<T> gameObjects) {
		ListIterator<T> it = gameObjects.listIterator();
		while (it.hasNext()) {
			T gameObject = it.next();
			gameObject.updateState(frameIndex);

			// Use == 0, not <= 0, as -1 means infinite TTL
			if (gameObject.getTtl() == 0) {
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
