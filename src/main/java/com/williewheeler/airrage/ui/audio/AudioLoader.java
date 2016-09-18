package com.williewheeler.airrage.ui.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class AudioLoader {

	public Clip loadSoundEffect(String id) {
		String filename = "audio/" + id + ".wav";
		InputStream is = ClassLoader.getSystemResourceAsStream(filename);
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(is);
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			return clip;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (LineUnavailableException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedAudioFileException e) {
			throw new RuntimeException(e);
		}
	}
}
