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

import java.util.*;

/**
 * Created by willie on 9/11/16.
 */
public class GameState {
	private static final Logger log = LoggerFactory.getLogger(GameState.class);

	private CollisionDetector collisionDetector;
	private List<GameListener> gameListeners = new LinkedList<>();

	private Player player;
	private Level level;
	private int frameIndex;

	private final List<PlayerMissile> playerMissiles = new LinkedList<>();
	private final List<EnemyPlane> enemyPlanes = new LinkedList<>();
	private final List<EnemyMissile> enemyMissiles = new LinkedList<>();
	private final List<PuffOfSmoke> puffsOfSmoke = new LinkedList<>();
	private final List<Explosion> explosions = new LinkedList<>();
	private final List<Cloud> clouds = new LinkedList<>();

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

	public int getFrameIndex() {
		return frameIndex;
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

	public List<Cloud> getClouds() {
		return clouds;
	}

	public void addCloud(Cloud cloud) {
		clouds.add(cloud);
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
		addClouds();

		player.updateState(frameIndex);
		updateAll(playerMissiles);
		updateAll(enemyPlanes);
		updateAll(enemyMissiles);
		updateAll(puffsOfSmoke);
		updateAll(explosions);
		updateAll(clouds);

		collisionDetector.checkPlayerHit();
		collisionDetector.checkEnemiesHit();

		this.frameIndex = (frameIndex + 1) % Config.TARGET_FPS;
	}

	private void fireTriggers() {
		Queue<Trigger> triggers = level.getTriggers();
		if (triggers.peek() != null && triggers.peek().canFire(this)) {
			Trigger trigger = triggers.poll();
			trigger.fireTrigger(this);
		}
	}

	private void addClouds() {
		boolean cloudy = player.getProgressY() > 1200 && player.getProgressY() < 2800;
		double prCloud = (cloudy ? 0.05 : 0.001);

		Random random = GameUtil.RANDOM;
		if (random.nextDouble() < prCloud) {

			// 2 = low, 5 = high
			int altitude = random.nextInt(4) + 2;

			int anchorX = random.nextInt(Config.MAP_SIZE_PX.width);
			int anchorY = player.getProgressY() + 1500;
			int numPuffs = random.nextInt(20) + 10;
			for (int i = 0; i < numPuffs; i++) {
				int offsetX = altitude * (random.nextInt(50) - 25);
				int offsetY = altitude * (random.nextInt(30) - 15);
				int x = anchorX + offsetX;
				int y = anchorY + offsetY;
				int ttl = 1500;
				int radius = 5 * altitude * (random.nextInt(7) + 3);
				int speed = random.nextInt(altitude * 3) + 1;
				addCloud(new Cloud(x, y, ttl, radius, speed));
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
}
