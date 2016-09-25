package com.williewheeler.airrage.ui.renderer;

import com.williewheeler.airrage.model.gameobj.GameObject;

import java.awt.*;

/**
 * Created by willie on 9/20/16.
 */
public class MissileRenderer implements Renderer {
	private Color outerColor;
	private Color innerColor;

	public MissileRenderer(Color outerColor, Color innerColor) {
		this.outerColor = outerColor;
		this.innerColor = innerColor;
	}

	@Override
	public void paint(Graphics2D g2, GameObject gameObject, int frameIndex) {
		g2.setColor(outerColor);
		g2.fillOval(0, 0, 9, 14);

		g2.setColor(innerColor);
		g2.fillOval(2, 2, 5, 10);
	}
}
