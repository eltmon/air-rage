package com.williewheeler.airrage.ui.audio;

import javax.sound.sampled.Clip;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by willie on 9/18/16.
 */
public class AudioManager {
	private AudioLoader audioLoader;

	private final Map<String, ArrayDeque<Clip>> soundEffects = new HashMap<>();

	public AudioManager(AudioLoader audioLoader) {
		this.audioLoader = audioLoader;

		// Use the # of available processors since this is the most we can run at once anyway.
		int clipsPerId = Runtime.getRuntime().availableProcessors();
		initSoundEffects(clipsPerId);
	}

	public void playSoundEffect(String id, boolean loop) {
		synchronized (soundEffects) {
			ArrayDeque<Clip> buffer = soundEffects.get(id);
			Clip clip = buffer.poll();
			clip.setFramePosition(0);

			if (loop) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}

			clip.start();
			buffer.add(clip);
		}
	}

	// http://stackoverflow.com/questions/27208568/how-to-terminate-anonymous-threads-in-java
	// http://stackoverflow.com/questions/7266042/java-ring-buffer
	private void initSoundEffects(int clipsPerId) {
		// TODO Lazy load
		String[] ids = {
				"airplane+spita", "gunfire"
		};
		// TODO Make this more generic, such as automatically loading all wavs in some directory or manifest. [WLW]
		for (String id : ids) {
			ArrayDeque<Clip> buffer = new ArrayDeque<Clip>();
			for (int i = 0; i < clipsPerId; i++) {
				buffer.add(audioLoader.loadSoundEffect(id));
			}
			soundEffects.put(id, buffer);
		}
	}
}
