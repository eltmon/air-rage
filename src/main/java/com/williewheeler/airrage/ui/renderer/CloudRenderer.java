package com.williewheeler.airrage.ui.renderer;

import com.williewheeler.airrage.model.gameobj.Cloud;
import com.williewheeler.airrage.model.gameobj.GameObject;

import java.awt.*;

/**
 * Created by willie on 9/25/16.
 */
public class CloudRenderer implements Renderer {
	private static final Color FILL_COLOR = new Color(255, 255, 255, 40);

	@Override
	public void paint(Graphics2D g2, GameObject gameObject, int frameIndex) {
		Cloud cloud = (Cloud) gameObject;
		g2.setColor(FILL_COLOR);
		g2.fillOval(0, 0, cloud.getWidth(), cloud.getHeight());
	}
}
