package br.com.shellcode.shellalarm;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {
	private Clip clip;

	public Audio(String fileName) {
		try {
			AudioInputStream sound = AudioSystem
					.getAudioInputStream(this.getClass().getResourceAsStream("/sounds/" + fileName));
			// load the sound into memory (a Clip)
			clip = AudioSystem.getClip();
			clip.open(sound);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException("Sound: Malformed URL: " + e);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
			throw new RuntimeException("Sound: Unsupported Audio File: " + e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Sound: Input/Output Error: " + e);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
		}

		// play, stop, loop the sound clip
	}

	public void play() {
		clip.setFramePosition(0); // Must always rewind!
		clip.start();
	}

	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void stop() {
		clip.stop();
	}
	/*
	static Clip clip;
	static {
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} finally {
			if (clip == null) {
				clip = null;
			}
		}
	}

	public static synchronized void playSound(final String url) {
		new Thread(new Runnable() {
			public void run() {
				try {
					AudioInputStream inputStream = AudioSystem
							.getAudioInputStream(this.getClass().getResourceAsStream("/sounds/" + url));
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}
	*/
}
