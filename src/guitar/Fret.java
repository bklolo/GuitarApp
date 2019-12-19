package src.guitar;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Fret extends Line {

	private float constant = 17.817f;
	private float fretPosition = 0;
	private float fretboardLength = 24.50f;
	private float[] fretArray;

	Fret(int fretCount, int stageHeight, int imageHeight, Pane pane1) {

		fretArray = calcFrets(fretCount);

		for (int i = 0; i < fretArray.length; i++) {
			Line fret = new Line();
			fret.setStroke(Color.SILVER);
			fret.setStartX((fretArray[i]) * 100);
			fret.setEndX((fretArray[i]) * 100);
			fret.setStartY(stageHeight / 2 - imageHeight / 2);
			fret.setEndY(stageHeight / 2 + imageHeight / 2);
			fret.setScaleX(5);
			pane1.getChildren().add(fret);
		}
	}

	// Returns an array of fret positions based on the number of frets on the
	// guitar fretboard.
	public float[] calcFrets(int numberOfFrets) 
	{

		float[] array = new float[numberOfFrets];
		for (int i = 0; i < array.length; i++) 
		{
			fretPosition += fretboardLength / constant;
			fretboardLength -= fretboardLength / constant;
			array[i] = fretPosition;
		}
		return array;
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
