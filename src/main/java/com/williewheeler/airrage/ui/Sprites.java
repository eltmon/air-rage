package com.williewheeler.airrage.ui;

import com.williewheeler.airrage.Config;

import java.awt.*;

/**
 * Created by willie on 9/4/16.
 */
public class Sprites {

	public static void paintPlane(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Config.TILE_SIZE_PX.width, Config.TILE_SIZE_PX.height);
	}

	public static void paintLand(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, Config.TILE_SIZE_PX.width, Config.TILE_SIZE_PX.height);
	}

	public static void paintSea(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, Config.TILE_SIZE_PX.width, Config.TILE_SIZE_PX.height);
	}
}
