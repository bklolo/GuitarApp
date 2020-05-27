package src.guitar;

import javafx.scene.layout.Pane;

public class Guitar{

	private static int fretCount = 12;
	private static int stringCount = 6;
	private float[] fretLocation;
	private float[] noteXPos;

	Guitar(Pane pane, int stageHeight, int stageWidth, int imageHeight, int imageWidth, String imageDir) 
	{
		// Fretboard class: wood image, frets, nut, inlays, calculations, etc.
		Fretboard fretboard = new Fretboard(pane, stageHeight, imageHeight, imageWidth, fretCount, imageDir);
		fretLocation = fretboard.getFretArray();

		// use fret location intervals to position note bubbles (halfway between each fret)
		noteXPos = notePosition(fretLocation, fretCount);
		
		// Create each guitar string
		for(int i = 0; i < stringCount; i++){
			GuitarString guitarString = new GuitarString(pane, stageHeight, imageHeight, imageWidth, 
					stringCount, noteXPos, i);
			pane.getChildren().add(guitarString);
		}
	}

	// returns calculated locations of NoteBubbles from 1st - 12th frets
	public static float[] notePosition(float[] fretLocation, int fretCount){
		float[] noteLocation = new float[fretCount];
		noteLocation[0] = fretLocation[0] / 2;
		
		for(int i = 0; i < fretLocation.length - 1; i++){
			noteLocation[i + 1] = fretLocation[i + 1] - (fretLocation[i + 1] - fretLocation[i]) / 2;
		}
		return noteLocation;
	}
}
