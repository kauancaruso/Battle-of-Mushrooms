package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	Clip clip;
	URL soundURL[] = new URL[30];

	public Sound() {

		soundURL[0] = getClass().getResource("/sound/01.-Ground-Theme.wav");
		soundURL[1] = getClass().getResource("/sound/smb_warning.wav");
		soundURL[2] = getClass().getResource("/sound/smb_powerup.wav");
		soundURL[3] = getClass().getResource("/sound/smb_pause.wav");
		soundURL[4] = getClass().getResource("/sound/smb_mariodie.wav");
		soundURL[5] = getClass().getResource("/sound/smb_kick.wav");
		soundURL[6] = getClass().getResource("/sound/smb_gameover.wav");
		soundURL[7] = getClass().getResource("/sound/smb_over.wav");
		soundURL[8] = getClass().getResource("/sound/smb_1-up.wav");
		soundURL[9] = getClass().getResource("/sound/smw_lost_a_life.wav");
		soundURL[10] = getClass().getResource("/sound/smb_coin.wav");
		soundURL[11] = getClass().getResource("/sound/smb_bump.wav");
		soundURL[12] = getClass().getResource("/sound/damagehit.wav");
		soundURL[13] = getClass().getResource("/sound/damagemonster.wav");
		soundURL[14] = getClass().getResource("/sound/swingweapon.wav");
		soundURL[15] = getClass().getResource("/sound/levelup.wav");
	}

	public void setFile(int i) {

		try {

			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);

		} catch (Exception e) {

		}
	}

	public void play() {

		clip.start();
	}

	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void stop() {
		clip.stop();
	}
}