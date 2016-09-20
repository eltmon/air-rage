package com.williewheeler.airrage.model.trigger;

import com.williewheeler.airrage.model.GameState;

/**
 * Created by willie on 9/19/16.
 */
public interface Trigger {

	boolean canFire(GameState gameState);

	void fireTrigger(GameState gameState);
}
