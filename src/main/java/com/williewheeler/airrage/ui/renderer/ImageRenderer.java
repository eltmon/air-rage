package com.williewheeler.airrage.ui.renderer;

import com.williewheeler.airrage.model.gameobj.GameObject;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by willie on 9/20/16.
 */
public class ImageRenderer implements Renderer {
	private Image image;

	public ImageRenderer(Image image) {
		this.image = image;
	}

	@Override
	public void paint(Graphics2D g2, GameObject gameObject) {
		int halfWidth = gameObject.getWidth() / 2;
		int halfHeight = gameObject.getHeight() / 2;

		AffineTransform xform = new AffineTransform();
		xform.rotate(gameObject.getRotation(), halfWidth, halfHeight);

		g2.drawImage(image, xform, null);
	}
}
