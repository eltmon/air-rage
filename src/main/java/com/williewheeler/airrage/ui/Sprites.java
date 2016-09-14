package com.williewheeler.airrage.ui;

import com.williewheeler.airrage.Config;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * Created by willie on 9/4/16.
 */
public class Sprites {
	private static Image planePlayer;

	static {
		try {
			planePlayer = ImageIO.read(ClassLoader.getSystemResourceAsStream("sprites/plane-player-64x64.png"));
		} catch (IOException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static void paintPlane(Graphics g) {
		g.drawImage(planePlayer, 0, 0, null);
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
