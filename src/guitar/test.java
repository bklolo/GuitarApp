package src.guitar;

import java.util.HashMap;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//	Tuesday, May 26: 
//	refactoring: implemented a method to create Combo Boxes
// 	refactoring: moved Map and Note Bubble code into "Scales" event handler method (still need to figure out a way for all handlers to rearrange note bubbles)
//	created Group to use group.getChildren().clear() to prevent note bubbles from being drawn on top of one another

// TODO: guitarString needs own HashMap, 
//		 need to calculate hm values before passing to guitarString hm, 
//		 when scale/key changes, need to calc new hm and send to guitarString, tell string to update notebubbles
//		 create method to display notebubbles within guitarString class

public class test extends Application 
{
	
	//stage vars
	int stageHeight = 400;
	int stageWidth = 1235;
	int imageHeight = 200;
	int imageWidth = stageWidth;
	Group group = new Group();
	Pane pane = new Pane();
	Pane notesPane = new Pane();
	
	//guitar string vars
	int fretCount = 12;
	int stringCount = 6;
	int[] minorScale = {0,1,0,1,1,0,1,1};
	int[] majorScale = {0,1,1,0,1,1,1,0};
	String imageDir = ".\\maple fretboard.jpg";
	String[] chromaticScale = { "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#" };
	
	// combo box vars
	int selectedKeysIndex;
	int[] usedScaleArray = new int[8];
	String[] musicalScales = {"Major", "Minor"};
	
	// guitar vars
	float[] fretLocations;
	float[] noteX;
	
	// test vars
	String[] chosenKeyChromatic;
	String[] notesInKey = new String[8];
	String selectedScalesItem;
	HashMap<String, Integer> hm = new HashMap<String, Integer>();		// HashMap<Note, T/F> used to turn notes on/off

	public static void main(String[] args) 
	{ 
		launch(args);
	}

	@Override
	public void start(Stage stage) 
	{

		notesPane.setStyle("-fx-border-color: white");	// used to determine pane width/height and location
		notesPane.setMaxSize(50, 50);
		notesPane.autosize();
		notesPane.setMouseTransparent(true);	// disables mouse events for pane
		group.getChildren().addAll(pane, notesPane);
		Scene scene = new Scene(group);
		Fretboard fretboard = new Fretboard(pane, stageHeight, imageHeight, imageWidth, fretCount, imageDir);
		pane.getChildren().add(fretboard);
		fretLocations = fretboard.getFretArray();
		noteX = Guitar.notePosition(fretLocations, fretCount);
		// Scales combo box (only Major and Minor so far)
		ComboBox<String> scale = CreateBox("Scale", musicalScales, 450, 5, 0);
		// store currently selected scale mode Major/Minor (String)
		selectedScalesItem = scale.getSelectionModel().getSelectedItem();
		// musical Key combo box (full chromatic)
		ComboBox<String> key = CreateBox("Key", chromaticScale, 650, 5, 3);
		// store currently selected item index (int)
		selectedKeysIndex = key.getSelectionModel().getSelectedIndex();
		// create guitarString
		GuitarString guitarString = new GuitarString(pane, notesPane, stageHeight, imageHeight, imageWidth, 
															stringCount, noteX, 0, hm);	//index 0
		// store selected guitarstring combo box index
		int stringBaseNote = guitarString.returnCBoxIndex();
		
		System.out.println("stringBaseNote: " + stringBaseNote);
		
		// chromatic scale array that begins with the chosen key from combobox (String[])
		chosenKeyChromatic = guitarString.newChromArray(chromaticScale, selectedKeysIndex);

		// update note bubbles per combo box selected index
		scale.setOnAction(new EventHandler<ActionEvent>(){
		    @Override public void handle(ActionEvent e){
		    	selectedScalesItem = scale.getSelectionModel().getSelectedItem();
		    	notesPane.getChildren().clear();
		    	updateViewableNotes();
		    }
		});
		
		key.setOnAction(new EventHandler<ActionEvent>()
		{
		    @Override public void handle(ActionEvent e)
		    {
				selectedKeysIndex = key.getSelectionModel().getSelectedIndex();
				// create new String array beginning from selected note
				chosenKeyChromatic = GuitarString.newChromArray(chromaticScale, selectedKeysIndex);
				System.out.println("selected key: "+key.getValue());
		    }
		});

		pane.getChildren().addAll(scale, key);
		
		scene.setFill(Color.BLACK);
		stage.setMinWidth(stageWidth + 8);
		stage.setMinHeight(stageHeight);
		stage.setMaxHeight(stageHeight);
		stage.setMaxWidth(stageWidth);
		stage.setTitle("Guitar Scales");
		stage.setScene(scene);
		stage.show();

	}
	
	private void updateViewableNotes() {
		// fill HashMap with chromatic scale of Guitar String, based on string base note, set noteDisplay = 0
		for(int i = 0; i < chromaticScale.length; i++){
			hm.put(chromaticScale[i], 0);
		}
		
		// if selected scale is major, use Major scale, otherwise use Minor scale
		if(selectedScalesItem == "Major"){usedScaleArray = majorScale;}
									 else{usedScaleArray = minorScale;}
		
		// keep only notes of chosen chromatic scale by setting those noteDisplays to 1
		int step = 0;
		for(int i = 0; i < usedScaleArray.length; i++){	// 	  		{0,1,2,3,4,5,6,7}							
			step += usedScaleArray[i];					// Major:	{0,1,1,0,1,1,1,0}								// step holds the sum of steps from root note ([E + 1 = F], [E + 5 = A], etc)
			notesInKey[i] = chosenKeyChromatic[i + step];		// {0C,C#,2D,D#,4E,F,6F#,G,8G#,A,10A#,B,12C}		// notesInKey holds String value of all notes that should be viewed on guitar string
			hm.put(notesInKey[i], 1);						
		}	// note:i+step {C:0+(0+0), D:1+(0+1), E:2+(1+1), F:3+(2+0), G:4+(2+1), A:5+(3+1), 
			// B:6+(4+1), C:7+(5+0)}
		
		// at this point notesInKey holds all notes that should be viewed on guitar string
		// and HashMap has stored 1 for each note to be viewed

// from here		
		int stringYPos = stageHeight / 2 - imageHeight / 2;
		int offset = 20;
		// iterates through Map and turn on/off noteBubbles
		for(int i = 0; i < chromaticScale.length; i++){
			NoteBubble bubble = new NoteBubble(offset, stringYPos);
			bubble.setLayoutX(noteX[i == 0 ? 11 : i-1] * 100);			
			if(hm.get(chromaticScale[i]) == 0){
				bubble.setVisible(false);
			}

//			if(hm.get(selectedBaseNoteChromatic[i]) == 0){	// replace if statement in forloop for Guitar class
//				bubble.setVisible(false);
//			}
			
			notesPane.getChildren().add(bubble);

		}
// to here goes in GuitarString
		
	}
	
	public <T> ComboBox<T> CreateBox(String name, T[] array, int xLayout, int visibleRows, int selectionIndex){
		
//		// musical Key combo box (full chromatic)
		ComboBox<T> box = new ComboBox<>();
		
		for(int i = 0; i < array.length; i++){
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