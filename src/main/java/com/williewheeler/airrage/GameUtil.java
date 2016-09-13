package com.williewheeler.airrage;

/**
 * Created by willie on 9/11/16.
 */
public class GameUtil {

	public static void sleep(long duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
