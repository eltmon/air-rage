package com.williewheeler.airrage.ui.audio;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by willie on 9/18/16.
 */
public class AudioPlayer {
	private Sequencer sequencer;

	public AudioPlayer() {
		try {
			this.sequencer = MidiSystem.getSequencer();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void play(String id) {
		if (sequencer == null) {
			throw new IllegalStateException("sequencer can't be null");
		}

		try {
			InputStream is = ClassLoader.getSystemResourceAsStream(path(id));
//			Sequence sequence = MidiSystem.getSequence(is);
			sequencer.open();
			sequencer.setSequence(is);
			sequencer.start();
			// TODO Do I need to close the sequencer, or do I keep reusing it?
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String path(String id) {
		return "audio/music/" + id + ".mid";
	}
}
