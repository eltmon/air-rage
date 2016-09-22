package com.williewheeler.airrage;

import java.util.Random;

/**
 * Created by willie on 9/11/16.
 */
public class GameUtil {
	private static final Random RANDOM = new Random();

	public static Random random() {
		return RANDOM;
	}

	public static void sleep(long duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
