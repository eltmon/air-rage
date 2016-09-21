package com.williewheeler.airrage.ui.renderer;

import com.williewheeler.airrage.model.gameobj.GameObject;

import java.awt.*;

/**
 * Created by willie on 9/20/16.
 */
public interface Renderer {

	/**
	 * We expect the graphics context to be translated to the NW corner of the game object.
	 *
	 * @param g2
	 * @param gameObject
	 */
	void paint(Graphics2D g2, GameObject gameObject);
}
