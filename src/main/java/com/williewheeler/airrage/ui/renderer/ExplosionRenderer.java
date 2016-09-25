package com.williewheeler.airrage.ui.renderer;

import com.williewheeler.airrage.model.gameobj.Explosion;
import com.williewheeler.airrage.model.gameobj.GameObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * Created by willie on 9/24/16.
 */
public class ExplosionRenderer implements Renderer {
	private static final Logger log = LoggerFactory.getLogger(ExplosionRenderer.class);

	@Override
	public void paint(Graphics2D g2, GameObject gameObject, int frameIndex) {
		Explosion explosion = (Explosion) gameObject;
//		log.debug("Painting explosion: {}", explosion);
		int alpha = explosion.getAlpha();

		g2.setColor(new Color(255, 0, 0, alpha));
//		g2.setColor(Color.RED);
		g2.fillOval(0, 0, explosion.getWidth(), explosion.getHeight());

		g2.setColor(new Color(255, 255, 0, alpha));
//		g2.setColor(Color.ORANGE);
		g2.fillOval(2, 2, explosion.getWidth() - 4, explosion.getHeight() - 4);
	}
}
