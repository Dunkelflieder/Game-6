package game6.client.sound;

import java.util.ArrayList;
import java.util.List;

public class SoundContext {

	private List<SoundContext> subContexts;
	private List<Sound> sounds;

	private SoundContext parent;

	private float pitch = 1f;
	private float gain = 1f;

	public SoundContext() {
		this(null);
	}

	public SoundContext(SoundContext parent) {
		this.subContexts = new ArrayList<>();
		this.sounds = new ArrayList<>();
		this.parent = parent;
	}

	public SoundContext createSubContext() {
		SoundContext subContext = new SoundContext(this);
		subContexts.add(subContext);
		return subContext;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
		updatePitch();
	}

	public float getPitch() {
		return pitch;
	}

	public void setGain(float gain) {
		this.gain = gain;
		updateGain();
	}

	public float getGain() {
		return gain;
	}

	public float getTotalPitch() {
		if (parent != null) {
			return parent.getTotalPitch() * pitch;
		}
		return pitch;
	}

	public float getTotalGain() {
		if (parent != null) {
			return parent.getTotalGain() * gain;
		}
		return gain;
	}

	private void updateGain() {
		for (Sound sound : sounds) {
			sound.updateGain();
		}
		for (SoundContext context : subContexts) {
			context.updateGain();
		}
	}

	private void updatePitch() {
		for (Sound sound : sounds) {
			sound.updatePitch();
		}
		for (SoundContext context : subContexts) {
			context.updatePitch();
		}
	}

	public Sound createSound(String filename) {
		return createSound(new String[] { filename });
	}

	public Sound createSound(String[] filenames) {
		Sound sound = new Sound(this, filenames);
		sounds.add(sound);
		return sound;
	}

}
