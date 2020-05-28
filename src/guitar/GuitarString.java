package src.guitar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class GuitarString extends Line 
{

	private float stringYPos;
	private int offset;
	String[] chromaticScale = { "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
	String[] rootNoteArray;
	String[] strings = {"E", "A", "D", "G", "B", "E"};
	private ComboBox<String> stringBaseNoteCBox = new ComboBox<String>();
	int xLayout, yLayout;
	int indexOfSelectedBaseNote;
	String[] selectedBaseNoteChromatic;
	private Pane pane;
	
	//TODO: every rootNote selection needs its own array
	
	GuitarString(Pane pane, int stageHeight, int imageHeight, int imageWidth, int stringCount, 
			float[] noteXPos, int index) 
	{
		this.pane = pane;
		Line guitarstring = new Line();		// why can't i use "this" instead of creating new Line? class extends Line...
		xLayout = index * 100 + 300;
		yLayout = 50;
		stringYPos = stageHeight / 2 - imageHeight / 2;
		
		// offset value of strings from top of fretboard
		offset = 20 + index * ((imageHeight - 5) / stringCount);
		
		StringProperties(guitarstring, imageWidth, index, offset);
		
		// Combo Box to select root note of string
		rootNoteSelector(chromaticScale, index, xLayout, yLayout);
		
		// get index of selected note
		indexOfSelectedBaseNote = stringBaseNoteCBox.getSelectionModel().getSelectedIndex();
		
		// create new String array beginning from selected note
		selectedBaseNoteChromatic = newChromArray(chromaticScale, indexOfSelectedBaseNote);
		
		// update note bubbles per combo box selected index
		stringBaseNoteCBox.setOnAction(new EventHandler<ActionEvent>()
		{
		    @Override public void handle(ActionEvent e)
		    {
				indexOfSelectedBaseNote = stringBaseNoteCBox.getSelectionModel().getSelectedIndex();
				
				// create new String array beginning from selected note
				selectedBaseNoteChromatic = newChromArray(chromaticScale, indexOfSelectedBaseNote);
				System.out.println("String base note: " + returnCBoxString());

		    }
		});
		
		pane.getChildren().add(stringBaseNoteCBox);
	}
	
	// creates a new chromatic scale array of notes, starting from selectedIndex
	public String[] newChromArray(String[] array, int startingIndex){
		
		String[] newArray = new String[13];
		
		for(int i = 0; i < array.length; i++){
			
		    int pointer = (i + startingIndex) % array.length;
		    newArray[i] = array[pointer];
		    
		}
		newArray[12] = newArray[0];
		
		return newArray;
	}
	
	// each string gets a combo box
	private void rootNoteSelector(String[] notesArray, int stringNumber, int xLayout, int yLayout){
		
		for(int i = 0; i < notesArray.length; i++){
			stringBaseNoteCBox.getItems().add(notesArray[i]);
		}
		
		if(stringNumber == 0 || stringNumber == 5){
			stringBaseNoteCBox.getSelectionModel().select(notesArray[7]);
		}
		else if(stringNumber == 1){
			stringBaseNoteCBox.getSelectionModel().select(notesArray[0]);
		}
		else if(stringNumber == 2){
			stringBaseNoteCBox.getSelectionModel().select(notesArray[5]);
		}
		else if(stringNumber == 3){
			stringBaseNoteCBox.getSelectionModel().select(notesArray[10]);
		}
		else if(stringNumber == 4){
			stringBaseNoteCBox.getSelectionModel().select(notesArray[2]);
		}
		
		stringBaseNoteCBox.autosize();
		stringBaseNoteCBox.setPromptText(String.valueOf(stringNumber));	// default displayed text
		stringBaseNoteCBox.setLayoutX(xLayout);
		stringBaseNoteCBox.setLayoutY(yLayout);
		stringBaseNoteCBox.setVisibleRowCount(5);
		
	}

	// guitar string properties
	private void StringProperties(Line guitarstring, int imageWidth, 
			int index, int offset) 
	{
		guitarstring.setScaleY(2.5 + index/2);
		guitarstring.setLayoutY(offset);
		guitarstring.setStroke(Color.rgb(255, 175, 51));
		guitarstring.setStartX(0);
		guitarstring.setEndX(imageWidth);
		guitarstring.setStartY(stringYPos);
		guitarstring.setEndY(stringYPos);
		guitarstring.toBack();
			shadow(guitarstring);
			pane.getChildren().add(guitarstring);

	}

	// guitar string shadow properties
	private DropShadow shadow(Line line) {
		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(1.0);
		shadow.setHeight(1);
		shadow.setColor(Color.BLACK);
		shadow.setRadius(.5);
		line.setEffect(shadow);
		return shadow;
	}

	// returns index of this Cbox
	public int returnCBoxIndex(){
		return stringBaseNoteCBox.getSelectionModel().getSelectedIndex();
	}

	// returns note of this Cbox
	public String returnCBoxString(){
		return stringBaseNoteCBox.getSelectionModel().getSelectedItem();
	}
	
}
