import javax.sound.sampled.*;
import java.io.*;
import java.util.*;	

class AudioRecorder implements Runnable {
	File wavFile = getFileName();
 
    // format of audio file
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
 
    // the line from which audio data is captured
    TargetDataLine line;

	File getFileName() {
		File wav;
		wav = new File("test.wav");
		return wav;
	}

	AudioFormat getAudioFormat() {
		float sampleRate = 16000;
		int sampleSizeInBits = 8;
		int channels = 2;
		boolean signed = true;
		boolean bigEndian = true;
		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
											 channels, signed, bigEndian);
		return format;
	}

	public double volumeRMS(double[] raw) {
		double sum = 0d;

		if (raw.length == 0) {
			return sum;
		} else {
			for (int i = 0; i < raw.length; i++) {
				sum += raw[i];
			}
		}

		double average = sum / raw.length;
		double sumMeanSquare = 0d;

		for (int i = 0; i < raw.length; i++) {
			sumMeanSquare += Math.pow(raw[i] - average, 2d);
		}

		double averageMeanSquare = sumMeanSquare / raw.length;
		double rootMeanSquare = Math.sqrt(averageMeanSquare);

		return rootMeanSquare;
	}

	void start() {
		try {
			AudioFormat format = getAudioFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
 
			// checks if system supports the data line
			if (!AudioSystem.isLineSupported(info)) {
				System.out.println("Line not supported");
				System.exit(0);
			}

			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();   // start capturing
 
			System.out.println("Start capturing...");
 
			AudioInputStream ais = new AudioInputStream(line);
 
			System.out.println("Start recording...");
 
			// start recording
			AudioSystem.write(ais, fileType, wavFile);
 
		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	void finish() {
		line.stop();
		line.close();
		System.out.println("Finished");
	}

	void noiseFilter() {
		
	}
}