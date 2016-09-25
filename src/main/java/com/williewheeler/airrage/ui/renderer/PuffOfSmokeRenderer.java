package com.williewheeler.airrage.ui.renderer;

import com.williewheeler.airrage.model.gameobj.GameObject;
import com.williewheeler.airrage.model.gameobj.PuffOfSmoke;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * Created by willie on 9/23/16.
 */
public class PuffOfSmokeRenderer implements Renderer {
	private static final Logger log = LoggerFactory.getLogger(PuffOfSmokeRenderer.class);

	@Override
	public void paint(Graphics2D g2, GameObject gameObject, int frameIndex) {
		PuffOfSmoke puff = (PuffOfSmoke) gameObject;

		int brightness = puff.getBrightness();
		int alpha = puff.getAlpha();
		Color color = new Color(brightness, brightness, brightness, alpha);

		g2.setColor(color);
		g2.fillOval(0, 0, puff.getWidth(), puff.getHeight());
	}
}
