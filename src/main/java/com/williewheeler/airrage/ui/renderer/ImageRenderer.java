package com.williewheeler.airrage.ui.renderer;

import com.williewheeler.airrage.GameUtil;
import com.williewheeler.airrage.model.gameobj.EnemyPlane;
import com.williewheeler.airrage.model.gameobj.GameObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

/**
 * Created by willie on 9/20/16.
 */
public class ImageRenderer implements Renderer {
	private Image image;

	public ImageRenderer(Image image) {
		this.image = image;
	}

	@Override
	public void paint(Graphics2D g2, GameObject gameObject) {

		// FIXME Remove hardcode
		if (gameObject instanceof EnemyPlane) {
			paintEnemyPlane(g2, (EnemyPlane) gameObject);
			return;
		}

		int halfWidth = gameObject.getWidth() / 2;
		int halfHeight = gameObject.getHeight() / 2;

		AffineTransform xform = new AffineTransform();
		xform.rotate(gameObject.getRotation(), halfWidth, halfHeight);

		g2.drawImage(image, xform, null);
	}

	private void paintEnemyPlane(Graphics2D g2, EnemyPlane enemyPlane) {
		int halfWidth = enemyPlane.getWidth() / 2;
		int halfHeight = enemyPlane.getHeight() / 2;

		AffineTransform xform = new AffineTransform();
		xform.rotate(enemyPlane.getRotation(), halfWidth, halfHeight);

		g2.drawImage(image, xform, null);

		Random random = GameUtil.random();
		int stateFlags = enemyPlane.getStateFlags();

		if ((stateFlags & EnemyPlane.STATE_DAMAGED) > 0) {
			int numFireballs = random.nextInt(10);
			for (int i = 0; i < numFireballs; i++) {
				int x = random.nextInt(enemyPlane.getWidth());
				int y = random.nextInt(enemyPlane.getHeight());
				int size = random.nextInt(10) + 10;
				g2.setColor(Color.RED);
				g2.fillOval(x, y, size, size);
				g2.setColor(Color.ORANGE);
				g2.fillOval(x + 2, y + 2, size - 4, size - 4);
			}
			int numPuffs = random.nextInt(6);
			for (int i = 0; i < numPuffs; i++) {
				int x = random.nextInt(enemyPlane.getWidth());
				int y = random.nextInt(enemyPlane.getHeight());
				int size = random.nextInt(10) + 10;
				g2.setColor(Color.DARK_GRAY);
				g2.fillOval(x, y, size, size);
			}
		}
	}
}
