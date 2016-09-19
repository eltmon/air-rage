package com.williewheeler.airrage.ui;

import com.williewheeler.airrage.Config;
import com.williewheeler.airrage.model.Plane;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;

/**
 * Created by willie on 9/4/16.
 */
public class Sprites {
	private static Image playerSprite;
	private static Image enemySprite;

	private static final Color GRASSY_GREEN = new Color(154, 212, 68);
	private static final Color SEA_BLUE = new Color(42, 135, 181);

	static {
		playerSprite = loadImage("sprites/zero-white.png");
		enemySprite = loadImage("sprites/f6f-hellcat.png");
	}

	public static void paintPlayer(Graphics g) {
		g.drawImage(playerSprite, 0, 0, null);
	}

	public static void paintEnemy(Graphics g, Plane enemy) {
		AffineTransform xform = new AffineTransform();
		xform.rotate(enemy.getRotation());
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(enemySprite, xform, null);
	}

	public static void paintLand(Graphics g) {
		g.setColor(GRASSY_GREEN);
		g.fillRect(0, 0, Config.TILE_SIZE_PX.width, Config.TILE_SIZE_PX.height);
	}

	public static void paintSea(Graphics g) {
		g.setColor(SEA_BLUE);
		g.fillRect(0, 0, Config.TILE_SIZE_PX.width, Config.TILE_SIZE_PX.height);
	}

	private static Image loadImage(String filename) {
		try {
			return ImageIO.read(ClassLoader.getSystemResourceAsStream(filename));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
