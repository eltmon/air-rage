package com.williewheeler.airrage.model.trigger;

import com.williewheeler.airrage.model.GameState;

/**
 * Created by willie on 9/19/16.
 */
public abstract class ProgressTrigger implements Trigger {
	private int progressY;

	/**
	 * @param progressY progressY value that fires the trigger
	 */
	public ProgressTrigger(int progressY) {
		this.progressY = progressY;
	}

	@Override
	public boolean canFire(GameState gameState) {
		return gameState.getPlayer().getProgressY() >= progressY;
	}
}
