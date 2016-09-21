package com.williewheeler.airrage.ui.renderer;

import com.williewheeler.airrage.Config;
import com.williewheeler.airrage.model.GameObject;

import java.awt.*;

// FIXME Not really a game object...

/**
 * Created by willie on 9/20/16.
 */
public class TileRenderer implements Renderer {
	private Color color;

	public TileRenderer(Color color) {
		this.color = color;
	}

	@Override
	public void paint(Graphics g, GameObject gameObject) {
		g.setColor(color);
		g.fillRect(0, 0, Config.TILE_SIZE_PX.width, Config.TILE_SIZE_PX.height);
	}
}
