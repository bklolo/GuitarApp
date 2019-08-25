package src.guitar;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Fret extends Line {

	float constant = 17.817f;
	float fretPosition = 0;
	float fretboardLength = 24.50f;
	float[] fretArray;


	Fret(int numberOfFrets, int stageHeight, int imageHeight, Pane pane1) {
		
		float[] array = calcFrets(numberOfFrets);
		
		for (int i = 0; i < array.length; i++) {
			Line fret = new Line();
			fret.setStroke(Color.SILVER);
			fret.setStartX((array[i]) * 100);
			fret.setEndX((array[i]) * 100);
			fret.setStartY(stageHeight / 2 - imageHeight / 2);
			fret.setEndY(stageHeight / 2 + imageHeight / 2);
			fret.setScaleX(5);
			pane1.getChildren().add(fret);
		}
	}
	/** Returns an array of fret positions based on the number of frets
	 * on the guitar fretboard.	
	*/
	
	public float[] calcFrets(int numberOfFrets) {
		
		fretArray = new float[numberOfFrets];
		System.out.println(fretArray.length);
		for (int i = 0; i < fretArray.length; i++) {

			System.out.println("fretposition " + i + " = " + fretPosition);
			fretPosition += fretboardLength / constant;
			fretboardLength -= fretboardLength / constant;
			fretArray[i] = fretPosition;
		}
		return fretArray;
	}
	
	public float[] getFretArray() {
		return fretArray;
	}
	public void setFretArray(float[] fretArray) {
		this.fretArray = fretArray;
	}

	public float getFretboardLength() {
		return fretboardLength;
	}

	public void setFretboardLength(float fretboardLength) {
		this.fretboardLength = fretboardLength;
	}

}
