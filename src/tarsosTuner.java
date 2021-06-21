
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Vector;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Mixer.Info;
import be.tarsos.dsp.AudioDispatcher;
import javafx.scene.Group;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class tarsosTuner {
	
	AudioDispatcher dispatcher;
	Mixer mixer = null;
	Text pitchText, noteText;
	ToggleGroup tg;
	Group buttonGroup;
	int i = 10;
	public tarsosTuner(Pane pane, int stageWidth, int stageHeight){
		
		buttonGroup = new Group();
		tg = new ToggleGroup();
		pitchText = new Text();
		noteText = new Text();
	
		for(Mixer.Info info : getMixerInfo(false, true)){
			RadioButton button = new RadioButton();
			button.setLayoutX(stageWidth/3);
			button.setLayoutY(stageHeight/3 + i);
			button.setText(toLocalString(info));
			button.setText(info.toString());
			button.setToggleGroup(tg);
			buttonGroup.getChildren().add(button);
			i += 25;
		}
		// attempt to use setInput for ToggleGroup listener (broke code)
//		tg.selectedToggleProperty().addListener((ChangeListener<? super Toggle>) setInput);
		
		pitchText.setLayoutX(stageWidth/2);
		pitchText.setLayoutY(stageHeight/2);
		noteText.setLayoutX(stageWidth/2 + 100);
		noteText.setLayoutY(stageHeight/2 + 100);
		pane.getChildren().addAll(pitchText, noteText, buttonGroup);

	}
    
	private ActionListener setInput = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			for(Mixer.Info info : getMixerInfo(false, true)){
				if(arg0.getActionCommand().equals(info.toString())){
					Mixer newValue = AudioSystem.getMixer(info);
//					firePropertyChange("mixer", mixer, newValue);
					mixer = newValue;
					break;
				}
			}
		}
	};
	
	public static Vector<Mixer.Info> getMixerInfo(
			final boolean supportsPlayback, final boolean supportsRecording) {
		final Vector<Mixer.Info> infos = new Vector<Mixer.Info>();
		final Mixer.Info[] mixers = AudioSystem.getMixerInfo();
		for (final Info mixerinfo : mixers) {
			if (supportsRecording
					&& AudioSystem.getMixer(mixerinfo).getTargetLineInfo().length != 0) {
				// Mixer capable of recording audio if target LineWavelet length != 0
				infos.add(mixerinfo);
			} else if (supportsPlayback
					&& AudioSystem.getMixer(mixerinfo).getSourceLineInfo().length != 0) {
				// Mixer capable of audio play back if source LineWavelet length != 0
				infos.add(mixerinfo);
			}
		}
		return infos;
	}
	
	public static String toLocalString(Object info)
	{
		if(!isWindows())
			return info.toString();
		String defaultEncoding = Charset.defaultCharset().toString();
		try
		{
			return new String(info.toString().getBytes("windows-1252"), defaultEncoding);
		}
		catch(UnsupportedEncodingException ex)
		{
			return info.toString();
		}
	}
	
	private static String OS = null;
	
	public static String getOsName()
	{
		if(OS == null)
			OS = System.getProperty("os.name");
	    return OS;
	}
	
	public static boolean isWindows()
	{
	   return getOsName().startsWith("Windows");
	}
	
	private void processPitch(float pitchInHz) {
		// TODO Auto-generated method stub
		pitchText.setText("" + pitchInHz);

	    if(pitchInHz >= 110 && pitchInHz < 123.47) {
	        //A
	        noteText.setText("A");
	    }
	    else if(pitchInHz >= 123.47 && pitchInHz < 130.81) {
	        //B
	        noteText.setText("B");
	    }
	    else if(pitchInHz >= 130.81 && pitchInHz < 146.83) {
	        //C
	        noteText.setText("C");
	    }
	    else if(pitchInHz >= 146.83 && pitchInHz < 164.81) {
	        //D
	        noteText.setText("D");
	    }
	    else if(pitchInHz >= 164.81 && pitchInHz <= 174.61) {
	        //E
	        noteText.setText("E");
	   }
	    else if(pitchInHz >= 174.61 && pitchInHz < 185) {
	        //F
	        noteText.setText("F");
	    }
	    else if(pitchInHz >= 185 && pitchInHz < 196) {
	        //G
	        noteText.setText("G");
	    }
	}
	
}
