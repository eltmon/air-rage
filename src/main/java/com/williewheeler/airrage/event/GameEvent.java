package com.williewheeler.airrage.event;

/**
 * Created by willie on 9/19/16.
 */
public class GameEvent {
	public static final String PLAYER_FIRED = "playerFired";

	private String type;

	public GameEvent(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
