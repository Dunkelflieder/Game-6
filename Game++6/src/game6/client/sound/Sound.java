package game6.client.sound;

import java.io.IOException;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.OpenALException;

public class Sound {

	public static final int PRIORITY_DELETE = 0;
	public static final int PRIORITY_LOW = 1;
	public static final int PRIORITY_MODERATE = 2;
	public static final int PRIORITY_HIGH = 3;

	private ALSource source = null;
	private ALBuffer[] buffers;
	private int selectedBuffer = 0;
	public int priority = PRIORITY_MODERATE;

	private float posX, posY, posZ, velX, velY, velZ;
	private float gain = 1f;
	private float pitch = 1f;
	private boolean looping = false;
	private float offset = 0f;
	private Random random = new Random();
	private SoundContext context;

	Sound(SoundContext context, String[] filenames) {
		try {
			this.buffers = ALBufferBank.getBuffers(filenames);
		} catch (OpenALException | IOException | LWJGLException e) {
			System.out.println("Error creating buffer");
			e.printStackTrace();
		}
		this.context = context;
		this.posX = 0;
		this.posY = 0;
		this.posZ = 0;
		this.velX = 0;
		this.velY = 0;
		this.velZ = 0;
		this.looping = false;
		this.gain = 1f;
		this.pitch = 1f;
	}

	void setUncoupled() {
		stop();
		source = null;
	}

	public void randomizeBuffer() {
		selectBuffer(random.nextInt(buffers.length));
	}

	public void nextBuffer() {
		if (selectedBuffer + 1 == buffers.length) {
			selectBuffer(0);
		} else {
			selectBuffer(selectedBuffer + 1);
		}
	}

	public void play() {
		getSource().play();
	}

	public void stop() {
		getSource().stop();
	}

	public void pause() {
		getSource().pause();
	}

	public boolean isStopped() {
		return getSource().isStopped();
	}

	public boolean isPaused() {
		return getSource().isPaused();
	}

	public boolean isPlaying() {
		return getSource().isPlaying();
	}

	ALSource getSource() {
		if (source == null) {
			source = SoundManager.getFreeSource(priority);
			source.couple(this);
			initSource();
		}
		return source;
	}

	void initSource() {
		selectBuffer(selectedBuffer);
		setGain(gain);
		setLooping(looping);
		setOffset(offset);
		setPitch(pitch);
		setPosition(posX, posY, posZ);
		setVelocity(velX, velY, velZ);
		if (isPlaying())
			play();
		if (isPaused())
			pause();
		if (isStopped())
			stop();
	}

	ALBuffer[] getBuffers() {
		return buffers;
	}

	ALBuffer getSelectedBuffer() {
		return buffers[selectedBuffer];
	}

	void selectBuffer(int buffer) {
		this.selectedBuffer = buffer;
		getSource().setBuffer(buffers[selectedBuffer]);
	}

	public float getPositionX() {
		return posX;
	}

	public float getPositionY() {
		return posY;
	}

	public float getPositionZ() {
		return posZ;
	}

	public void setPosition(float posX, float posY, float posZ) {
		getSource().setPosition(posX, posY, posZ);
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	public float getVelocityX() {
		return velX;
	}

	public float getVelocityY() {
		return velY;
	}

	public float getVelocityZ() {
		return velZ;
	}

	public void setVelocity(float velX, float velY, float velZ) {
		getSource().setVelocity(velX, velY, velZ);
		this.velX = velX;
		this.velY = velY;
		this.velZ = velZ;
	}

	public float getGain() {
		return gain;
	}

	public void setGain(float gain) {
		this.gain = gain;
		updateGain();
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
		updatePitch();
	}

	public void updateGain() {
		getSource().setGain(context.getTotalGain() * gain);
	}
	
	public void updatePitch() {
		getSource().setPitch(context.getTotalPitch() * pitch);
	}


	public float getOffset() {
		return offset;
	}

	public void setOffset(float offset) {
		getSource().setOffset(offset);
		this.offset = offset;
	}

	public boolean isLooping() {
		return looping;
	}

	public void setLooping(boolean looping) {
		getSource().setLooping(looping);
		this.looping = looping;
	}

	public void randomizePitch(float f) {
		setPitch((1 - 0.5f * f) + random.nextFloat() * f);
	}

}
