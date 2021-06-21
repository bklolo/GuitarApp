
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Tuner {
// Audio vars
boolean stopCapture = false;
ByteArrayOutputStream byteArrayOutputStream;
AudioFormat audioFormat;
// A TargetDataLine receives audio data from a mixer. Commonly, the mixer has captured audio data from 
// a port such as a microphone; it might process or mix this captured audio before placing the data in 
// the target data line's buffer. The TargetDataLine interface provides methods for reading the data 
// from the target data line's buffer and for determining how much data is currently available for reading.
TargetDataLine targetDataLine;		// a mixer output containing the mixture of the streamed input sounds
AudioInputStream audioInputStream;
// A SourceDataLine receives audio data for playback. It provides methods for writing data to the source 
// data line's buffer for playback, and for determining how much data the line is prepared to receive without blocking.
SourceDataLine sourceDataLine;		// a mixer input that accepts a real-time stream of audio data
byte tempBuffer[] = new byte[500];

// Tuner vars
int noteTextSize = 100;
Button tunerStart, tunerStop;

// TODO try out TarsosDSP library for implementing tuner
// TODO dissect code to figure out how to create tuner
// TODO LineUnavailableException thrown when Start->Stop->Start tuner
// TODO tuner should display letter of note closest to frequency of note being picked up by the microphone.

public Tuner(Pane pane, int stageHeight, int stageWidth){
	
	Text noteText = new Text();
	noteText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, noteTextSize));
	noteText.setX(stageWidth/2);
	noteText.setY(stageHeight/2);
//	setNoteText(); where to use?
	noteText.setText("C");
	
	
	Text sharpText = new Text();
	sharpText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
	sharpText.setX(stageWidth/2 + 75);
	sharpText.setY(stageHeight/2 - 20);
//	setNoteText(); where to use?
	sharpText.setText("#");
	
	
	tunerStart = new Button("Start");
	tunerStart.setOnAction(e -> captureAudio());
	tunerStart.setLayoutX(stageWidth/2 - 50);
	tunerStart.setLayoutY(stageHeight/2 + 50);
	
	tunerStop = new Button("Stop");
	tunerStop.setOnAction(e -> stopCapture = true);
	tunerStop.setLayoutX(stageWidth/2 + 75);
	tunerStop.setLayoutY(stageHeight/2 + 50);
	
	
	pane.getChildren().addAll(noteText, sharpText, tunerStart, tunerStop);
	
}
private AudioFormat getAudioFormat() {
    float sampleRate = 8000.0F;
    int sampleSizeInBits = 16;
    int channels = 1;
    boolean signed = true;
    boolean bigEndian = true;
    return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
}

void captureAudio() {
    try {

        Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();    //get available mixers
        System.out.println("Available mixers:");
        Mixer mixer = null;
        for (int cnt = 0; cnt < mixerInfo.length; cnt++) {
            System.out.println(cnt + " " + mixerInfo[cnt].getName());
            mixer = AudioSystem.getMixer(mixerInfo[cnt]);

            Line.Info[] lineInfos = mixer.getTargetLineInfo();
            if (lineInfos.length >= 1 && lineInfos[0].getLineClass().equals(TargetDataLine.class)) {
                System.out.println(cnt + " Mic is supported!");
                break;
            }
        }

        audioFormat = getAudioFormat();     //get the audio format
       
        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
        targetDataLine = (TargetDataLine) mixer.getLine(dataLineInfo);
        targetDataLine.open(audioFormat);
        targetDataLine.start();

        DataLine.Info dataLineInfo1 = new DataLine.Info(SourceDataLine.class, audioFormat);
        sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo1);
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();

        Thread captureAndPlayThread = new captureAndPlayThread();   //thread to capture and play audio
        captureAndPlayThread.start();

    } catch (LineUnavailableException e) {
        System.out.println(e);
        System.exit(0);
    }
}

class captureAndPlayThread extends Thread {

    @Override
    public void run() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        stopCapture = false;
        try {
            int readCount;
            while (!stopCapture) {
                readCount = targetDataLine.read(tempBuffer, 0, tempBuffer.length);  //capture sound into tempBuffer
                if (readCount > 0) {
                    byteArrayOutputStream.write(tempBuffer, 0, readCount);
                    sourceDataLine.write(tempBuffer, 0, 500);   			//playing audio available in tempBuffer
                    for(int i = 0; i < tempBuffer.length; i++){
                    	//System.out.println("tempbuff value at " + i + ": " + tempBuffer[i]);
                    	System.out.print(tempBuffer[i]);
                    }
                }
            }
            byteArrayOutputStream.close();
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }
}

}