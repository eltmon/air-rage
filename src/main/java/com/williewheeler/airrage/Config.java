package com.williewheeler.airrage;

import java.awt.*;

/**
 * Created by willie on 9/11/16.
 */
public class Config {

	/** Target frames per second */
	public static final int TARGET_FPS = 60;

	/** Duration of a single frame */
	public static final int TARGET_FRAME_DURATION = 1000 / TARGET_FPS;

	/** Map progress: pixels per frame */
	public static final int PROGRESS_SPEED = 2;

	/** Horizontal speed: pixels per frame */
	public static final int PLANE_SPEED = 5;

	/** Map size in tiles */
	public static final Dimension MAP_SIZE = new Dimension(30, 1000);

	/** Viewport size in pixels */
	public static final Dimension VIEWPORT_SIZE_PX = new Dimension(800, 800);

	/** Tile size in pixels */
	public static final Dimension TILE_SIZE_PX = new Dimension(40, 40);

	/** Map size in pixels */
	public static final Dimension MAP_SIZE_PX =
			new Dimension(MAP_SIZE.width * TILE_SIZE_PX.width, MAP_SIZE.height * TILE_SIZE_PX.height);
}
