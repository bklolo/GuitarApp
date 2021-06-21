
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
	private int chosenStringNote;
	private float stringYPos;
	private float bubbleX;
	private String[] arrayFromChosenStringNote;
	private ComboBox<String> guitarStringCBox = new ComboBox<String>();
	private HashMap<String, Integer> notesInKey = new HashMap<>();
	private Pane notesPane = new Pane();
	private String selectedKeyItem = "";
	
	GuitarString(Pane pane, Pane guitarStringsPane, int stageHeight, int imageHeight, 
					int imageWidth, int stringCount, float[] noteXPos, int index, 
					HashMap<String, Integer> notesInKey, String[] chromaticScale, String selectedKeyItem) {
		Line guitarString = new Line();
		notesPane.setMouseTransparent(true);																// disables mouse events for pane
		this.selectedKeyItem = selectedKeyItem;
		this.notesInKey = notesInKey;
		this.xLayout = 800 - index * 100;																	// the X position value of each guitarString's cBox
		this.stringYPos = stageHeight / 2 - imageHeight / 2;												// the starting Y position for the set of all guitarStrings
		this.offset = 20 + index * ((imageHeight - 5) / stringCount);										// the starting Y position, plus offset, of strings from the first string of the set
		stringProperties(guitarString, imageWidth, index, offset);											// adjust guitarString properties
		rootNoteSelector(chromaticScale, index, xLayout, yLayout);											// create a cBox used to select root note of each string
		chosenStringNote = guitarStringCBox.getSelectionModel().getSelectedIndex();							// index of this cBoxes currently selected note
		arrayFromChosenStringNote = newChromArray(chromaticScale, chosenStringNote);						// array of chromatic notes, beginning from current cBoxes selected index
		drawNotes(guitarStringsPane, notesInKey, chromaticScale, noteXPos, selectedKeyItem);
		pane.getChildren().add(guitarStringCBox);
		guitarStringsPane.getChildren().addAll(guitarString, notesPane);
		
		// GuitarString CBox Event Handler
		guitarStringCBox.setOnAction(new EventHandler<ActionEvent>() {										// update note bubbles per combo box selected index
			@Override
			public void handle(ActionEvent e) {
				notesPane.getChildren().clear();															// clear notesPane before redrawing
				chosenStringNote = guitarStringCBox.getSelectionModel().getSelectedIndex();					// store selected guitarString cBox index
				arrayFromChosenStringNote = newChromArray(chromaticScale, chosenStringNote);				// create new String array beginning from selected note
				drawNotes(guitarStringsPane, notesInKey, chromaticScale, noteXPos, GuitarString.this.selectedKeyItem);		// GuitarString.this.selectedKeyItem used because EventHandler
			}});																											// is the same as creating a new subclass
	
	}
	// changes the base note of the guitar string (0th/12th fret)
	public void drawNotes(Pane guitarStringsPane, HashMap<String, Integer> notesInKey, 
							String[] chromaticScale, float[] noteXPos, String itemFromKeyCBox) {	
		Color color = null;
		for (int i = 0; i < chromaticScale.length; i++) {													// iterates through Map and turns off noteBubbles not in scale
			bubbleX = noteXPos[i == 0 ? 11 : i - 1] * 100;													// draw notebubble at fret 12 when i = 0, draw at i-1 otherwise
			if (notesInKey.get(arrayFromChosenStringNote[i]) != 0) {										// if the value in HashMap isn't zero
				if(!arrayFromChosenStringNote[i].equals(itemFromKeyCBox)){									// if the note isn't the tonic to the key, create and color ALICEBLUE
					color = Color.LIGHTSTEELBLUE;
				}
				
				else{																						// if the note is the tonic to the chosen key, color it ORANGE
					color = Color.ORANGE;
				}
				
				NoteBubble bubble = new NoteBubble(offset, bubbleX, stringYPos, 
													arrayFromChosenStringNote[i], color);
				notesPane.getChildren().addAll(bubble, bubble.getText());
			}
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
			guitarStringCBox.getItems().add(notesArray[i]);
		}
		if (stringNumber == 0 || stringNumber == 5) {
			guitarStringCBox.getSelectionModel().select(notesArray[7]);		// e
		} else if (stringNumber == 1) {													// 	String[] chromaticScale = { "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G",  "G#" };
			guitarStringCBox.getSelectionModel().select(notesArray[2]);		// b		//	String[] chromaticScale = { "0", "A#", "2", "C", "4",  "D", "6",  "E", "8", "F#", "10", "G#" };
		} else if (stringNumber == 2) {
			guitarStringCBox.getSelectionModel().select(notesArray[10]);	// g 
		} else if (stringNumber == 3) {
			guitarStringCBox.getSelectionModel().select(notesArray[5]); 	// d
		} else if (stringNumber == 4) {
			guitarStringCBox.getSelectionModel().select(notesArray[0]);		// a
		}
		guitarStringCBox.autosize();
		guitarStringCBox.setPromptText(String.valueOf(stringNumber)); // default displayed text
		guitarStringCBox.setLayoutX(xLayout);
		guitarStringCBox.setLayoutY(yLayout);
		guitarStringCBox.setVisibleRowCount(5);
	}

	// guitar string properties
	private void stringProperties(Line guitarstring, int imageWidth, int index, int offset) {
		guitarstring.setScaleY(2.5 + index / 2);
		guitarstring.setLayoutY(offset);
//		guitarstring.setStroke(Color.rgb(255, 175, 51));
		Color stringColor = null;
		if(index <= 2){
			stringColor = Color.CORNSILK;
		} else{
			stringColor = Color.LIGHTGOLDENRODYELLOW;
		}
		guitarstring.setStroke(stringColor);
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

	public void setNotesInKey(Pane guitarStringsPane, HashMap<String, Integer> hm, 
								String[] chromaticScale, float[] noteXPos, String selectedKeyItem) {
		this.notesInKey = hm;
		this.selectedKeyItem = selectedKeyItem;
		drawNotes(guitarStringsPane, notesInKey, chromaticScale, noteXPos, selectedKeyItem);
	}
	
	public Pane getNotesPane(){
		return this.notesPane;
	}
	
	public void setGuitarStringCBoxItem(String item) {
		this.guitarStringCBox.getSelectionModel().select(item);
	}

}
