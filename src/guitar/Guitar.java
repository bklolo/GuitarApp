package src.guitar;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.beans.property.ReadOnlyObjectProperty;
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
	private int[] usedScaleArray = new int[8];
	private int[] majorScale = {0,1,1,0,1,1,1,0};
	private int[] minorScale = {0,1,0,1,1,0,1,1};
	private int[] dorianScale = {0,1,0,1,1,1,0,1};
	private int[] majorPent = {0,1,1,2,1,2};
	private int[] minorPent = {0,2,1,1,2,1};
	private int[] bluesScale = {0,2,1,0,0,2,1};
	private static int selectedKeyIndex;
	private String[] chromaticScale = { "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#" };
	private String[] scales = { "Major", "Minor", "Dorian", "MPent", "mPent", "Blues"};
	private String[] presetTunings = {"Standard", "DADGAD", "Open G", "Open D", "C6", "Modal D"};
	private String[] tuningStandard = {"E","B","G","D","A","E"};										// GuitarStrings are currently created beginning with high E
	private String[] DADGAD = {"D","A","G","D","A","D"};
	private String[] chosenKeyChromaticScale;
	private String[] notesInKey = new String[8];
	private String selectedScalesItem;
	private String selectedKeyItem;
	private ArrayList<GuitarString> guitarStringList = new ArrayList<>();								// holds all GuitarString objects
	private HashMap<String, Integer> hm = new HashMap<>();												// stores (String)notes, (Integer)values used to display notebubbles on guitar
	private Pane mainPane = new Pane();
	private Pane guitarStringsPane = new Pane();
	private ComboBox<String> scale = new ComboBox<>();
	private ComboBox<String> key = new ComboBox<>();
	private ComboBox<String> tunings = new ComboBox<>();
	
	
	Guitar(Pane mainPane, Pane guitarStringsPane, int stageHeight, int stageWidth, int imageHeight, int imageWidth,
			String imageDir) {
		this.mainPane = mainPane;
		this.guitarStringsPane = guitarStringsPane;
		int cBoxOrigin = imageWidth/2;
		Fretboard fretboard = new Fretboard(mainPane, stageHeight, imageHeight, imageWidth, fretCount, imageDir);	// Fretboard class: wood image, frets, nut, inlays, calculations, etc.
		fretLocations = fretboard.getFretArray();
		noteXPos = notePosition(fretLocations, fretCount);												// use fret location intervals to position note bubbles (halfway-point between each fret)
		
		scale = CreateBox("Scale", scales, cBoxOrigin, 5, 0);											// Scale combobox used to change the scale to play in
		selectedScalesItem = scale.getSelectionModel().getSelectedItem();								// store the item selected from Scale
		
		key = CreateBox("Key", chromaticScale, cBoxOrigin+200, 5, 3);												// Key combobox used to choose the key to play in
		selectedKeyIndex = key.getSelectionModel().getSelectedIndex();									// store the index selected from Key
		selectedKeyItem = key.getSelectionModel().getSelectedItem();									// store the item selected from Key
		
		tunings = CreateBox("Tunings", presetTunings, cBoxOrigin-200, 5, 0);							// Tunings combobox used to choose a preset guitar tuning
		
		Label scaleLabel = new Label("Scale");															// label Scale combobox
		scaleLabel.setTextFill(Color.AZURE);
		scaleLabel.setLayoutX(cBoxOrigin - 30);
		Label keyLabel = new Label("Key");																// label Key combobox
		keyLabel.setTextFill(Color.AZURE);
		keyLabel.setLayoutX(cBoxOrigin+170);
		
		Label tuningsLabel = new Label("Preset Tuning");												// label Tuning combobox
		tuningsLabel.setTextFill(Color.AZURE);
		tuningsLabel.setLayoutX(cBoxOrigin - 275);
		
		chosenKeyChromaticScale = GuitarString.newChromArray(chromaticScale, selectedKeyIndex);			// create chromatic scale beginning at chosen Key 
		
		updateViewableNotesInHashMap();																	// calculate HashMap contents based on CBoxes, pass to
		
		createGuitarStrings(stageHeight, imageHeight, imageWidth);										// Create each guitar string
		
		scale.setOnAction(new EventHandler<ActionEvent>() {												// update note displayed per selected Scale cBox item
			@Override
			public void handle(ActionEvent e) {
				clearEachGuitarStringPane();
				selectedScalesItem = scale.getSelectionModel().getSelectedItem();
				updateAndReplaceHash();																	// update hashmap values and replace existing GuitarString maps with
			}});
		
		key.setOnAction(new EventHandler<ActionEvent>() {												// update note bubbles per selected Key cBox item
			@Override
			public void handle(ActionEvent e) {
				clearEachGuitarStringPane();															// clear each GuitarString's pane (all string change vs single string change)
				selectedKeyIndex = key.getSelectionModel().getSelectedIndex();							// store selected Key cBox index
				selectedKeyItem = key.getSelectionModel().getSelectedItem();
				chosenKeyChromaticScale = GuitarString.newChromArray(chromaticScale, selectedKeyIndex);	// create new String array beginning from selected index
				updateAndReplaceHash();																	// update hashmap values and replace existing GuitarString maps with
			}
			});
		tunings.setOnAction(new EventHandler<ActionEvent>() {												// update note bubbles per selected Tuning cBox item
			@Override
			public void handle(ActionEvent e) {
				String selectedTuningItem = tunings.getSelectionModel().getSelectedItem();
				GuitarString currentString;
				
				if(selectedTuningItem.equals(presetTunings[0])){
					for(int i = 0; i < tuningStandard.length; i++){
						currentString = guitarStringList.get(i);
						currentString.setGuitarStringCBoxItem(tuningStandard[i]);
					}
				}
				else if(selectedTuningItem.equals(presetTunings[1])){
					for(int i = 0; i < tuningStandard.length; i++){
						currentString = guitarStringList.get(i);
						currentString.setGuitarStringCBoxItem(DADGAD[i]);
					}
				}
			}
			});

		mainPane.getChildren().addAll(scale, scaleLabel, key, keyLabel, tunings, tuningsLabel, guitarStringsPane);				// add nodes to Pane
	}
	
	// iterate through and clear each GuitarString's pane before redrawing NoteBubbles
	private void clearEachGuitarStringPane() {
		for(GuitarString g : guitarStringList){
			g.getNotesPane().getChildren().clear();
		}
	}

	private void createGuitarStrings(int stageHeight, int imageHeight, int imageWidth) {
		for (int i = 0; i < stringCount; i++) {
			GuitarString guitarString = new GuitarString(mainPane, guitarStringsPane, stageHeight, imageHeight, imageWidth, stringCount,
					noteXPos, i, hm, chromaticScale, selectedKeyItem);
			guitarStringList.add(guitarString);
			guitarStringsPane.getChildren().add(guitarString);											// add guitarStrings to guitarStringPane
		}
	}
	// update this HashMap and replace each of the GuitarString's HashMaps with it
	private void updateAndReplaceHash() {
		updateViewableNotesInHashMap();																	// calculate HashMap contents based on CBoxes, pass to
		for(GuitarString g : guitarStringList){															// update each GuitarString's HashMap based on new combobox selections
			g.setNotesInKey(guitarStringsPane, hm, chromaticScale, noteXPos, selectedKeyItem);
		}
	}
	// fill HashMap with chromatic scale of GuitarString,
	private void updateViewableNotesInHashMap() {
		for (int i = 0; i < chromaticScale.length; i++) {												
			hm.put(chromaticScale[i], 0);																// based on string base note, and set noteDisplay to 0
		}
		if (selectedScalesItem == "Major") {
			usedScaleArray = majorScale;																// determine which scale is currently selected
		} else if (selectedScalesItem == "Minor") {
			usedScaleArray = minorScale;
		}else if (selectedScalesItem == "Dorian") {
			usedScaleArray = dorianScale;
		}else if (selectedScalesItem == "MPent") {
			usedScaleArray = majorPent;
		}else if (selectedScalesItem == "mPent") {
			usedScaleArray = minorPent;
		}else if (selectedScalesItem == "Blues") {
			usedScaleArray = bluesScale;
		}

		int step = 0;
		for (int i = 0; i < usedScaleArray.length; i++) {								// step holds the sum of steps from root note ([E + 1 = F], [E + 5 = A], etc)
			step += usedScaleArray[i]; 													// Major steps: {0, 1, 1, 0, 1, 1, 1, 0}								 
			notesInKey[i] = chosenKeyChromaticScale[i + step]; 							// note = i + (step): {C:0+(0+0)=0, D:1+(0+1)=2, E:2+(1+1)=4, F:3+(2+0)=5, G:4+(2+1)=7, A:5+(3+1)=9, B:6+(4+1)=11, C:7+(5+0)=12}			
			hm.put(notesInKey[i], 1);
		}	
		// at this point notesInKey holds all notes that should be viewed on guitar string and HashMap has value of 1 for each note to be viewed

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

	public String getSelectedKeyItem(){
		return this.selectedKeyItem;
	}
}