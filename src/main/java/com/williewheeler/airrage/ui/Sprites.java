package com.williewheeler.airrage.ui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * Created by willie on 9/4/16.
 */
public class Sprites {
	public static final Image A6M_ZERO = loadImage("sprites/a6m-zero.png");
	public static final Image F6F_HELLCAT = loadImage("sprites/f6f-hellcat.png");

	private static Image loadImage(String filename) {
		try {
			return ImageIO.read(ClassLoader.getSystemResourceAsStream(filename));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
