package game6.client.sound;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.sound.sampled.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.OpenALException;
import org.lwjgl.util.WaveData;

import com.jcraft.oggdecoder.OggData;
import com.jcraft.oggdecoder.OggDecoder;

class ALBufferBank {

	public static HashMap<String, ALBuffer> buffers;
	private static OggDecoder oggDecoder = new OggDecoder();

	static {
		buffers = new HashMap<String, ALBuffer>();
	}

	/** reads a sound file into a buffer and adds it to the buffer bank */
	public static void addSound(String filename) throws IOException, LWJGLException, OpenALException {
		// create OpenAL context, if not already done
		SoundManager.createAL();

		File file = new File("res/sound/" + filename);

		int id = ALHelper.genBuffer();

		// read file into buffer
		String format = getExtension(file);
		switch (format) {
		case "wav":
			setWaveFile(id, file);
			break;
		case "ogg":
			setVorbisFile(id, file);
			break;
		case "mp3":
			setMpegFile(id, file);
			break;
		default:
			System.out.println("did not recognize extension for: " + file.getName() + ", allowed extensions: .mp3 .wav .ogg");
			throw new IOException();
		}

		int size = ALHelper.getBufferSize(id);
		int channels = ALHelper.getBufferChannels(id);
		buffers.put(file.getName(), new ALBuffer(id, size, channels));
	}

	/** Returns the ALBuffer for the corresponding file. If it hasn't been loaded yet, it gets loaded. */
	public static ALBuffer getSound(String filename) throws OpenALException, IOException, LWJGLException {
		if (!buffers.containsKey(filename))
			addSound(filename);
		return buffers.get(filename);
	}

	public static ALBuffer[] getBuffers(String[] filenames) throws OpenALException, IOException, LWJGLException {
		ALBuffer[] buffers = new ALBuffer[filenames.length];
		for (int i = 0; i < buffers.length; i++) {
			buffers[i] = getSound(filenames[i]);
		}
		return buffers;
	}

	/** reads given .wav wave file into given buffer */
	private static void setWaveFile(int bufferID, File file) throws FileNotFoundException {
		WaveData waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(file)));
		ALHelper.setBuffer(bufferID, waveFile.format, waveFile.data, waveFile.samplerate);
		waveFile.dispose();
	}

	/** reads given .ogg vorbis file into given buffer */
	private static void setVorbisFile(int bufferID, File file) throws IOException {
		// Decode OGG into PCM
		InputStream inputStream = new FileInputStream(file);
		OggData oggData = oggDecoder.getData(inputStream);

		// Load PCM data into buffer
		ALHelper.setBuffer(bufferID, oggData.channels > 1 ? AL10.AL_FORMAT_STEREO16 : AL10.AL_FORMAT_MONO16, oggData.data, oggData.rate);

		inputStream.close();
	}

	/** reads given .mp3 file into given buffer */
	private static void setMpegFile(int bufferID, File file) {
		AudioInputStream in = null;
		try {
			in = AudioSystem.getAudioInputStream(file);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
			return;
		}
		AudioInputStream din = null;
		AudioFormat baseFormat = in.getFormat();
		AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
				baseFormat.getSampleRate(),
				16,
				baseFormat.getChannels(),
				baseFormat.getChannels() * 2,
				baseFormat.getSampleRate(),
				false);
		din = AudioSystem.getAudioInputStream(decodedFormat, in);
		ByteBuffer buffer = null;
		try {
			buffer = ByteBuffer.allocateDirect(din.available());
			int b;
			while ((b = din.read()) != -1)
				buffer.putInt(b);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		ALHelper.setBuffer(bufferID, AL10.AL_FORMAT_STEREO16, buffer, (int) decodedFormat.getSampleRate());
	}

	/** Returns the lowercase file extension of the given File (for example "ogg")*/
	public static String getExtension(File file) {
		String[] parts = file.getName().split("\\.");
		if (parts.length <= 1)
			return null;
		return parts[parts.length - 1].toLowerCase();
	}

	/** Frees all buffers (unloads all data from the sound device) */
	public static void clear() {
		for (int i = 0; i < buffers.size(); i++) {
			if (buffers.get(i) != null)
				buffers.get(i).destroy();
		}
	}

}
