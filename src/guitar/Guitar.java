package src.guitar;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Guitar {

	private float[] fretLocations;
	private float[] noteXPos;
	private static int fretCount = 12;
	private static int stringCount = 6;
	int[] usedScaleArray = new int[8];
	int[] minorScale = {0,1,0,1,1,0,1,1};
	int[] majorScale = {0,1,1,0,1,1,1,0};
	int selectedKeysIndex;
	String[] chromaticScale = { "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#" };
	String[] musicalScales = { "Major", "Minor" };
	String[] chosenKeyChromatic;
	String[] notesInKey = new String[8];
	String selectedScalesItem;
	ArrayList<GuitarString> guitarStringList = new ArrayList<>();								// holds all GuitarStrings
	HashMap<String, Integer> hm = new HashMap<>();												// stores (String)notes (Integer)values used to display notebubbles on guitar

	Guitar(Pane mainPane, Pane guitarStringsPane, int stageHeight, int stageWidth, int imageHeight, int imageWidth,
			String imageDir) {
		
		Fretboard fretboard = new Fretboard(mainPane, stageHeight, imageHeight, imageWidth, fretCount, imageDir);	// Fretboard class: wood image, frets, nut, inlays, calculations, etc.
		fretLocations = fretboard.getFretArray();
		noteXPos = notePosition(fretLocations, fretCount);										// use fret location intervals to position note bubbles (halfway-point between each fret)
		
		ComboBox<String> scale = CreateBox("Scale", musicalScales, 450, 5, 0);					// Scale combobox used to change the scale to play in
		selectedScalesItem = scale.getSelectionModel().getSelectedItem();						// store the item selected from Scale
		ComboBox<String> key = CreateBox("Key", chromaticScale, 650, 5, 3);						// Key combobox used to change the key to play in
		selectedKeysIndex = key.getSelectionModel().getSelectedIndex();							// store the index selected from Key
		
		Label scaleLabel = new Label("Scale");													// text object to label Scale combobox
		scaleLabel.setTextFill(Color.AZURE);
		scaleLabel.setLayoutX(450 - 30);
		Label keyLabel = new Label("Key");														// text object to label Key combobox
		keyLabel.setTextFill(Color.AZURE);
		keyLabel.setLayoutX(650 - 30);
		
		chosenKeyChromatic = GuitarString.newChromArray(chromaticScale, selectedKeysIndex);		// create chromatic scale beginning at chosen Key 
		
		updateViewableNotes();																	// calculate HashMap contents based on CBoxes, pass to
		
		createGuitarStrings(mainPane, guitarStringsPane, stageHeight, imageHeight, imageWidth);		// Create each guitar string
		
		scale.setOnAction(new EventHandler<ActionEvent>() {										// update note displayed per selected Scale cBox item
			@Override
			public void handle(ActionEvent e) {
				clearEachGuitarStringPane();
				selectedScalesItem = scale.getSelectionModel().getSelectedItem();
				updateAndReplaceHash();															// update hashmap values and replace existing GuitarString maps with 
			}});
		
		key.setOnAction(new EventHandler<ActionEvent>() {										// update note bubbles per selected Key cBox item
			@Override
			public void handle(ActionEvent e) {
				clearEachGuitarStringPane();															// clear each GuitarString's pane (all string change vs single string change)
				selectedKeysIndex = key.getSelectionModel().getSelectedIndex();							// store selected Key cBox index
				chosenKeyChromatic = GuitarString.newChromArray(chromaticScale, selectedKeysIndex);		// create new String array beginning from selected index
				updateAndReplaceHash();																	// update hashmap values and replace existing GuitarString maps with
			}

			});
		
		mainPane.getChildren().addAll(scale, scaleLabel, key, keyLabel, guitarStringsPane);				// add nodes to Pane
	}
	
	// iterate through and clear each GuitarString's pane before redrawing NoteBubbles
	private void clearEachGuitarStringPane() {
		for(GuitarString g : guitarStringList){
			g.getNotesPane().getChildren().clear();
		}
	}

	private void createGuitarStrings(Pane mainPane, Pane guitarStringsPane, int stageHeight, int imageHeight, int imageWidth) {
		for (int i = 0; i < stringCount; i++) {
			GuitarString guitarString = new GuitarString(mainPane, guitarStringsPane, stageHeight, imageHeight, imageWidth, stringCount,
					noteXPos, i, hm, chromaticScale);
			guitarStringList.add(guitarString);
			guitarStringsPane.getChildren().add(guitarString);											// add guitarStrings to guitarStringPane
		}
	}
	
	private void updateAndReplaceHash() {
		updateViewableNotes();																	// calculate HashMap contents based on CBoxes, pass to
		for(GuitarString g : guitarStringList){													// update each GuitarString's HashMap based on new combobox selections
			g.setNotesInKey(hm, chromaticScale, noteXPos);
		}
	}
	
	private void updateViewableNotes() {
		// fill HashMap with chromatic scale of GuitarString, based on string base note, and set noteDisplay to 0
		for (int i = 0; i < chromaticScale.length; i++) {
			hm.put(chromaticScale[i], 0);
		}

		// determines which scale is currently selected
		if (selectedScalesItem == "Major") {
			usedScaleArray = majorScale;
		} else if (selectedScalesItem == "Minor") {
			usedScaleArray = minorScale;
		}

		// keep only notes of chosen chromatic scale by setting those
		// noteDisplays to 1
		int step = 0;
		// replace HashMap value with 1 if the note is part of the chosen scale
		for (int i = 0; i < usedScaleArray.length; i++) { 	// {0,1,2,3,4,5,6,7}
			step += usedScaleArray[i]; 						// Major: {0,1,1,0,1,1,1,0} 
			// step holds the sum of steps from root note ([E + 1 = F], [E + 5 = A], etc)
			notesInKey[i] = chosenKeyChromatic[i + step]; 	// {C, C#, D, D#, E, F, F#, G, G#, A, A#, B, C}
			// notesInKey now holds String value of all notes that should be viewed on guitar string
			hm.put(notesInKey[i], 1);
		} // note:i+step {C:0+(0+0), D:1+(0+1), E:2+(1+1), F:3+(2+0), G:4+(2+1),
			// A:5+(3+1),
			// B:6+(4+1), C:7+(5+0)}

		// at this point notesInKey holds all notes that should be viewed on
		// guitar string
		// and HashMap has stored 1 for each note to be viewed

	}

	// returns calculated locations of NoteBubbles from 1st - 12th frets
	public static float[] notePosition(float[] fretLocation, int fretCount) {
		float[] noteLocation = new float[fretCount];
		noteLocation[0] = fretLocation[0] / 2;

		for (int i = 0; i < fretLocation.length - 1; i++) {
			noteLocation[i + 1] = fretLocation[i + 1] - (fretLocation[i + 1] - fretLocation[i]) / 2;
		}
		return noteLocation;
	}

	public <T> ComboBox<T> CreateBox(String name, T[] array, int xLayout, int visibleRows, int selectionIndex) {

		// musical Key combo box (full chromatic)
		ComboBox<T> box = new ComboBox<>();
		
		for (int i = 0; i < array.length; i++) {
			box.getItems().add(array[i]);
		}
		box.autosize();
		box.setPromptText(name);
		box.setLayoutX(xLayout);
		box.setVisibleRowCount(visibleRows);
		box.getSelectionModel().select(selectionIndex);

		return box;
	}

}