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

	private static int fretCount = 12;
	private static int stringCount = 6;
	int selectedKeysIndex;
	private float[] fretLocations;
	private float[] noteXPos;
	String[] chromaticScale = { "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#" };
	String[] musicalScales = { "Major", "Minor" };
	String selectedScalesItem;
	HashMap<String, Integer> hm = new HashMap<>();
	int[] usedScaleArray = new int[8];
	int[] minorScale = {0,1,0,1,1,0,1,1};
	int[] majorScale = {0,1,1,0,1,1,1,0};
	String[] chosenKeyChromatic;
	String[] notesInKey = new String[8];
	ArrayList<GuitarString> guitarStringList = new ArrayList<>();

	Guitar(Pane pane, Pane notesPane, int stageHeight, int stageWidth, int imageHeight, int imageWidth,
			String imageDir) {
		// Fretboard class: wood image, frets, nut, inlays, calculations, etc.
		Fretboard fretboard = new Fretboard(pane, stageHeight, imageHeight, imageWidth, fretCount, imageDir);
		fretLocations = fretboard.getFretArray();

		// use fret location intervals to position note bubbles (halfway between
		// each fret)
		noteXPos = notePosition(fretLocations, fretCount);

		ComboBox<String> scale = CreateBox("Scale", musicalScales, 450, 5, 0);
		selectedScalesItem = scale.getSelectionModel().getSelectedItem();
		Label scaleLabel = new Label("Scale");
		scaleLabel.setTextFill(Color.AZURE);
		scaleLabel.setLayoutX(450 - 30);

		ComboBox<String> key = CreateBox("Key", chromaticScale, 650, 5, 3);
		selectedKeysIndex = key.getSelectionModel().getSelectedIndex();
		Label keyLabel = new Label("Key");
		keyLabel.setTextFill(Color.AZURE);
		keyLabel.setLayoutX(650 - 30);

		chosenKeyChromatic = GuitarString.newChromArray(chromaticScale, selectedKeysIndex);
		
		// METHOD CALL: calculate HashMap contents based on CBoxes, pass to
		 updateViewableNotes();

		// Create each guitar string
		createGuitarStrings(pane, notesPane, stageHeight, imageHeight, imageWidth);

		// update note bubbles per selected Scale cBox item
		scale.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// clear notesPane before redrawing
				notesPane.getChildren().clear();
				selectedScalesItem = scale.getSelectionModel().getSelectedItem();
				// update hashmap values and replace existing GuitarString maps with 
				updateAndReplaceHash();
			}});
		// update note bubbles per selected Key cBox item
		key.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// clear notesPane before redrawing
				notesPane.getChildren().clear();
				// store selected Key cBox index
				selectedKeysIndex = key.getSelectionModel().getSelectedIndex();
				// create new String array beginning from selected index
				chosenKeyChromatic = GuitarString.newChromArray(chromaticScale, selectedKeysIndex);
				// update hashmap values and replace existing GuitarString maps with 
				updateAndReplaceHash();
			}});
		
		// add nodes to Pane
		pane.getChildren().addAll(scale, scaleLabel, key, keyLabel);
	}

	private void createGuitarStrings(Pane pane, Pane notesPane, int stageHeight, int imageHeight, int imageWidth) {
		for (int i = 0; i < stringCount; i++) {
			GuitarString guitarString = new GuitarString(pane, notesPane, stageHeight, imageHeight, imageWidth, stringCount,
					noteXPos, i, hm, chromaticScale);
			guitarStringList.add(guitarString);
			pane.getChildren().add(guitarString);
		}
	}
	
	private void updateAndReplaceHash() {
		// update HashMap values
		updateViewableNotes();
		// replace all GuitarString's HashMap with new one
		for(GuitarString g : guitarStringList){
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