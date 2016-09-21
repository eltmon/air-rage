package com.williewheeler.airrage.ui.renderer;

import com.williewheeler.airrage.model.gameobj.GameObject;

import java.awt.*;

/**
 * Created by willie on 9/20/16.
 */
public interface Renderer {

	void paint(Graphics g, GameObject gameObject);
}
