package com.williewheeler.airrage.ui.renderer;

import com.williewheeler.airrage.model.GameObject;

import java.awt.*;

/**
 * Created by willie on 9/20/16.
 */
public class MissileRenderer implements Renderer {

	@Override
	public void paint(Graphics g, GameObject gameObject) {
		g.setColor(Color.RED);
		g.fillOval(0, 0, 9, 14);

		g.setColor(Color.ORANGE);
		g.fillOval(2, 2, 5, 10);
	}
}
