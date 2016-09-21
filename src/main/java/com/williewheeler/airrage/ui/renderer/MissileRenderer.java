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
	public void paint(Graphics g, GameObject gameObject) {
		g.setColor(outerColor);
		g.fillOval(0, 0, 9, 14);

		g.setColor(innerColor);
		g.fillOval(2, 2, 5, 10);
	}
}
