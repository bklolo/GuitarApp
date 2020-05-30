package src.guitar;

import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class GuitarString extends Line {
	
	private int offset;
	private int xLayout, yLayout;
	private int selectedCBoxIndex;
	private float stringYPos;
	private String[] selectedBaseNoteChromatic;
	private ComboBox<String> rootNote = new ComboBox<String>();
	private HashMap<String, Integer> notesInKey = new HashMap<>();
	private Pane notesPane;

	GuitarString(Pane pane, Pane notesPane, int stageHeight, int imageHeight, int imageWidth, int stringCount,
			float[] noteXPos, int index, HashMap<String, Integer> notesInKey, String[] chromaticScale) {

		Line guitarstring = new Line();
		this.notesInKey = notesInKey;
		// the Pane that holds all notebubbles
		this.notesPane = notesPane;
		// the X position value of each guitarString's cBox
		this.xLayout = 800 - index * 100;
		// the Y position value of each guitarString's cBox
		this.yLayout = 50;
		// the starting Y position for the set of all guitarStrings
		this.stringYPos = stageHeight / 2 - imageHeight / 2;
		// the Y position offset of strings from the first string of the set
		this.offset = 20 + index * ((imageHeight - 5) / stringCount);
		// adjust guitarString properties
		StringProperties(pane, guitarstring, imageWidth, index, offset);
		// create a cBox used to select root note of each string
		rootNoteSelector(chromaticScale, index, xLayout, yLayout);
		// index of this cBoxes currently selected note
		selectedCBoxIndex = rootNote.getSelectionModel().getSelectedIndex();
		// array of chromatic notes, beginning from current cBoxes selected index 
		selectedBaseNoteChromatic = newChromArray(chromaticScale, selectedCBoxIndex);
		
		// update note bubbles per combo box selected index
		rootNote.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// clear notesPane before redrawing
				notesPane.getChildren().clear();
				// store selected guitarString cBox index
				selectedCBoxIndex = rootNote.getSelectionModel().getSelectedIndex();
				// create new String array beginning from selected note
				selectedBaseNoteChromatic = newChromArray(chromaticScale, selectedCBoxIndex);
				// redraw notes
				drawNotes(notesInKey, chromaticScale, noteXPos);
			}});

		drawNotes(notesInKey, chromaticScale, noteXPos);
		pane.getChildren().add(rootNote);
	}

	// changes the base note of the guitar string (0th/12th fret)
	public void drawNotes(HashMap<String, Integer> notesInKey, String[] chromaticScale, float[] noteXPos) {
		// iterates through Map and turn on/off noteBubbles
		for (int i = 0; i < chromaticScale.length; i++) {
			NoteBubble bubble = new NoteBubble(offset, stringYPos);
			bubble.setColor(Color.LIGHTSTEELBLUE);
			bubble.setLayoutX(noteXPos[i == 0 ? 11 : i - 1] * 100);
			if (notesInKey.get(selectedBaseNoteChromatic[i]) == 0) {
				bubble.setVisible(false);
			}
			notesPane.getChildren().add(bubble);
		}
	}
	
	// STATIC for Guitar.java to access
	// rearranges the chromatic array to begin with the currently selected note
	public static String[] newChromArray(String[] array, int startingIndex) {
		String[] newArray = new String[13];
		for (int i = 0; i < array.length; i++) {
			int pointer = (i + startingIndex) % array.length;
			newArray[i] = array[pointer];
		}
		newArray[12] = newArray[0];
		return newArray;
	}

	// changes cbox prompt text based on which guitar string it is associated with
	private void rootNoteSelector(String[] notesArray, int stringNumber, int xLayout, int yLayout) {
		for (int i = 0; i < notesArray.length; i++) {
			rootNote.getItems().add(notesArray[i]);
		}
		if (stringNumber == 0 || stringNumber == 5) {
			rootNote.getSelectionModel().select(notesArray[7]);	// e
		} else if (stringNumber == 1) {										// 	String[] chromaticScale = { "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#" };
			rootNote.getSelectionModel().select(notesArray[2]);	// b		//	String[] chromaticScale = { "0", "A#", "2", "C", "4", "D", "6", "E", "8", "F#", "10", "G#" };
		} else if (stringNumber == 2) {
			rootNote.getSelectionModel().select(notesArray[10]); // g 
		} else if (stringNumber == 3) {
			rootNote.getSelectionModel().select(notesArray[5]); // d
		} else if (stringNumber == 4) {
			rootNote.getSelectionModel().select(notesArray[0]);	// a
		}
		rootNote.autosize();
		rootNote.setPromptText(String.valueOf(stringNumber)); // default displayed text
		rootNote.setLayoutX(xLayout);
		rootNote.setLayoutY(yLayout);
		rootNote.setVisibleRowCount(5);
	}

	// guitar string properties
	private void StringProperties(Pane pane, Line guitarstring, int imageWidth, int index, int offset) {
		guitarstring.setScaleY(2.5 + index / 2);
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

	public void setNotesInKey(HashMap<String, Integer> hm, String[] chromaticScale, float[] noteXPos) {
		this.notesInKey = hm;
		drawNotes(notesInKey, chromaticScale, noteXPos);
	}

}
