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
	private int xLayout;
	private int yLayout = 50;
	private int selectedCBoxIndex;
	private float stringYPos;
	private String[] selectedBaseNoteChromatic;
	private ComboBox<String> stringNoteCBox = new ComboBox<String>();
	private HashMap<String, Integer> notesInKey = new HashMap<>();
	private Pane notesPane = new Pane();

	GuitarString(Pane pane, Pane guitarStringsPane, int stageHeight, int imageHeight, int imageWidth, int stringCount,
			float[] noteXPos, int index, HashMap<String, Integer> notesInKey, String[] chromaticScale) {
		
		Line guitarString = new Line();
		
//		notesPane.setMaxSize(100, 100);
//		notesPane.setStyle("-fx-border-color: red");														// used to determine pane width/height and location
		notesPane.setMouseTransparent(true);															// disables mouse events for pane
		
		this.notesInKey = notesInKey;
		this.xLayout = 800 - index * 100;																	// the X position value of each guitarString's cBox
		this.stringYPos = stageHeight / 2 - imageHeight / 2;												// the starting Y position for the set of all guitarStrings
		this.offset = 20 + index * ((imageHeight - 5) / stringCount);										// the Y position offset of strings from the first string of the set
		
		StringProperties(guitarString, imageWidth, index, offset);											// adjust guitarString properties
		
		rootNoteSelector(chromaticScale, index, xLayout, yLayout);											// create a cBox used to select root note of each string
		
		selectedCBoxIndex = stringNoteCBox.getSelectionModel().getSelectedIndex();							// index of this cBoxes currently selected note
		selectedBaseNoteChromatic = newChromArray(chromaticScale, selectedCBoxIndex);						// array of chromatic notes, beginning from current cBoxes selected index
		stringNoteCBox.setOnAction(new EventHandler<ActionEvent>() {											// update note bubbles per combo box selected index
			@Override
			public void handle(ActionEvent e) {
				notesPane.getChildren().clear();															// clear notesPane before redrawing
				selectedCBoxIndex = stringNoteCBox.getSelectionModel().getSelectedIndex();					// store selected guitarString cBox index
				selectedBaseNoteChromatic = newChromArray(chromaticScale, selectedCBoxIndex);				// create new String array beginning from selected note
				drawNotes(notesInKey, chromaticScale, noteXPos);											// redraw notes
			}});

		drawNotes(notesInKey, chromaticScale, noteXPos);
		pane.getChildren().add(stringNoteCBox);
		guitarStringsPane.getChildren().addAll(guitarString, notesPane);
	}

	public void drawNotes(HashMap<String, Integer> notesInKey, String[] chromaticScale, float[] noteXPos) {	// changes the base note of the guitar string (0th/12th fret)
		for (int i = 0; i < chromaticScale.length; i++) {													// iterates through Map and turns off noteBubbles
			NoteBubble bubble = new NoteBubble(offset, stringYPos);
			bubble.setColor(Color.LIGHTSTEELBLUE);
			bubble.setLayoutX(noteXPos[i == 0 ? 11 : i - 1] * 100);
			if (notesInKey.get(selectedBaseNoteChromatic[i]) == 0) {
				bubble.setVisible(false);
			}
			notesPane.getChildren().add(bubble);
		}
	}
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
			stringNoteCBox.getItems().add(notesArray[i]);
		}
		if (stringNumber == 0 || stringNumber == 5) {
			stringNoteCBox.getSelectionModel().select(notesArray[7]);	// e
		} else if (stringNumber == 1) {										// 	String[] chromaticScale = { "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#" };
			stringNoteCBox.getSelectionModel().select(notesArray[2]);	// b		//	String[] chromaticScale = { "0", "A#", "2", "C", "4", "D", "6", "E", "8", "F#", "10", "G#" };
		} else if (stringNumber == 2) {
			stringNoteCBox.getSelectionModel().select(notesArray[10]); // g 
		} else if (stringNumber == 3) {
			stringNoteCBox.getSelectionModel().select(notesArray[5]); // d
		} else if (stringNumber == 4) {
			stringNoteCBox.getSelectionModel().select(notesArray[0]);	// a
		}
		stringNoteCBox.autosize();
		stringNoteCBox.setPromptText(String.valueOf(stringNumber)); // default displayed text
		stringNoteCBox.setLayoutX(xLayout);
		stringNoteCBox.setLayoutY(yLayout);
		stringNoteCBox.setVisibleRowCount(5);
	}

	// guitar string properties
	private void StringProperties(Line guitarstring, int imageWidth, int index, int offset) {
		guitarstring.setScaleY(2.5 + index / 2);
		guitarstring.setLayoutY(offset);
		guitarstring.setStroke(Color.rgb(255, 175, 51));
		guitarstring.setStartX(0);
		guitarstring.setEndX(imageWidth);
		guitarstring.setStartY(stringYPos);
		guitarstring.setEndY(stringYPos);
		guitarstring.toBack();
		shadow(guitarstring);
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
	
	public Pane getNotesPane(){
		return this.notesPane;
	}

}
