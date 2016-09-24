package com.williewheeler.airrage.model;

import com.williewheeler.airrage.Config;
import com.williewheeler.airrage.GameUtil;
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
		checkPlayerHit();
		checkEnemiesHit();
		player.updateState(frameIndex);
		updateAll(playerMissiles);
		updateAll(enemyPlanes);
		updateAll(enemyMissiles);
		updateAll(puffsOfSmoke);
		updateAll(explosions);
		fireTriggers();
		this.frameIndex = (frameIndex + 1) % Config.TARGET_FPS;
	}

	private void checkPlayerHit() {
		ListIterator<EnemyMissile> missileIt = enemyMissiles.listIterator();
		while (missileIt.hasNext()) {
			EnemyMissile missile = missileIt.next();
			boolean collision = Collisions.collision(missile, player);
			if (collision) {
				player.setDowned(true);
			}
		}
	}

	private void checkEnemiesHit() {
		ListIterator<PlayerMissile> missileIt = playerMissiles.listIterator();
		while (missileIt.hasNext()) {
			PlayerMissile missile = missileIt.next();
			ListIterator<EnemyPlane> planeIt = enemyPlanes.listIterator();
			while (planeIt.hasNext()) {
				EnemyPlane plane = planeIt.next();
				boolean collision = Collisions.collision(missile, plane);
				if (collision) {
					missileIt.remove();
					int stateFlags = plane.getStateFlags();
					stateFlags |= EnemyPlane.STATE_DAMAGED;
					double d = GameUtil.RANDOM.nextDouble();
					if (d < 0.05) {
						stateFlags |= EnemyPlane.STATE_SPINNING;
					} else if (d < 0.25) {
						stateFlags |= EnemyPlane.STATE_DESTROYED;
						plane.setTtl(0);

						// TODO Move this code
						int numFireballs = GameUtil.RANDOM.nextInt(5) + 5;
						for (int i = 0; i < numFireballs; i++) {
							int x = plane.getX() + GameUtil.RANDOM.nextInt(20) - 10;
							int y = plane.getY() + GameUtil.RANDOM.nextInt(20) - 10;
							int radius = GameUtil.RANDOM.nextInt(10) + 10;
							int alpha = GameUtil.RANDOM.nextInt(50) + 150;
							explosions.add(new Explosion(x, y, radius, alpha));
						}
					}
					plane.setStateFlags(stateFlags);
					fireGameEvent(new GameEvent(GameEvent.ENEMY_HIT));
				}
			}
		}
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
