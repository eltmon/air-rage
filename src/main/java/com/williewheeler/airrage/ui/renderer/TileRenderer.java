package com.williewheeler.airrage.ui.renderer;

import com.williewheeler.airrage.Config;
import com.williewheeler.airrage.model.gameobj.GameObject;

import java.awt.*;

// FIXME Not really a game object. We may want to treat tiles differently than game objects
// in that rotation isn't an issue here, so we may want to avoid all the unnecessary translation.

/**
 * Created by willie on 9/20/16.
 */
public class TileRenderer implements Renderer {
	private Color color;

	public TileRenderer(Color color) {
		this.color = color;
	}

	@Override
	public void paint(Graphics2D g2, GameObject gameObject) {
		g2.setColor(color);
		g2.fillRect(0, 0, Config.TILE_SIZE_PX.width, Config.TILE_SIZE_PX.height);
	}
}
