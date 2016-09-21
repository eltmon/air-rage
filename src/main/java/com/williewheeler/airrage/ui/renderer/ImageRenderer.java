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
	public void paint(Graphics g, GameObject gameObject) {
		AffineTransform xform = new AffineTransform();
		xform.rotate(gameObject.getRotation());
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image, xform, null);
	}
}
