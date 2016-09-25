package com.williewheeler.airrage.model;

import com.williewheeler.airrage.GameUtil;
import com.williewheeler.airrage.event.GameEvent;
import com.williewheeler.airrage.model.gameobj.*;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by willie on 9/19/16.
 */
public class CollisionDetector {
	private GameState gameState;

	public CollisionDetector(GameState gameState) {
		this.gameState = gameState;
	}

	public void checkPlayerHit() {
		List<EnemyMissile> enemyMissiles = gameState.getEnemyMissiles();
		Player player = gameState.getPlayer();

		ListIterator<EnemyMissile> missileIt = enemyMissiles.listIterator();
		while (missileIt.hasNext()) {
			EnemyMissile missile = missileIt.next();
			boolean collision = collision(missile, player);
			if (collision) {
				player.setDowned(true);
			}
		}
	}

	public void checkEnemiesHit() {
		List<PlayerMissile> playerMissiles = gameState.getPlayerMissiles();
		List<EnemyPlane> enemyPlanes = gameState.getEnemyPlanes();

		ListIterator<PlayerMissile> missileIt = playerMissiles.listIterator();
		while (missileIt.hasNext()) {
			PlayerMissile missile = missileIt.next();
			ListIterator<EnemyPlane> planeIt = enemyPlanes.listIterator();
			while (planeIt.hasNext()) {
				EnemyPlane plane = planeIt.next();
				boolean collision = CollisionDetector.collision(missile, plane);
				if (collision) {
					missileIt.remove();
					int stateFlags = plane.getPlaneState();
					stateFlags |= GameObjectStates.STATE_DAMAGED;
					double d = GameUtil.RANDOM.nextDouble();
					if (d < 0.05) {
						stateFlags |= GameObjectStates.STATE_SPINNING;
					} else if (d < 0.25) {
						stateFlags |= GameObjectStates.STATE_DESTROYED;
						plane.setTtl(0);

						// TODO Move this code
						int numFireballs = GameUtil.RANDOM.nextInt(5) + 5;
						for (int i = 0; i < numFireballs; i++) {
							int x = plane.getX() + GameUtil.RANDOM.nextInt(20) - 10;
							int y = plane.getY() + GameUtil.RANDOM.nextInt(20) - 10;
							int radius = GameUtil.RANDOM.nextInt(10) + 10;
							int alpha = GameUtil.RANDOM.nextInt(50) + 150;
							gameState.addExplosion(new Explosion(x, y, radius, alpha));
						}
					}
					plane.setPlaneState(stateFlags);
					gameState.fireGameEvent(new GameEvent(GameEvent.ENEMY_HIT));
				}
			}
		}
	}

	private static boolean collision(GameObject o1, GameObject o2) {
		// TODO For right now just decide whether the objects are "close". But really we want to check whether their
		// bounding boxes (or spheres, however we do it) intersect.
		int xDiff = o1.getX() - o2.getX();
		int yDiff = o1.getY() - o2.getY();
		double dist = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
		return dist < 32;
	}
}
