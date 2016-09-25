package com.williewheeler.airrage.ui;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by willie on 9/4/16.
 */
public class Sprites {
	private static final BufferedImage A6M_ZERO_SHEET = loadImage("sprites/a6m-zero-white.png");
	private static final BufferedImage F6F_HELLCAT_SHEET = loadImage("sprites/f6f-hellcat.png");

	public static final BufferedImage[] A6M_ZERO = new BufferedImage[] {
			A6M_ZERO_SHEET.getSubimage(0, 0, 64, 64),
			A6M_ZERO_SHEET.getSubimage(64, 0, 64, 64)
	};

	public static final BufferedImage[] F6F_HELLCAT = new BufferedImage[] {
			F6F_HELLCAT_SHEET.getSubimage(0, 0, 64, 64)
	};

	private static BufferedImage loadImage(String filename) {
		try {
			return ImageIO.read(ClassLoader.getSystemResourceAsStream(filename));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
