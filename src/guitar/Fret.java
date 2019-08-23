package src.guitar;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Fret extends Line {

	float constant = 17.817f;
	float fretPosition = 0;
	float fretboardLength = 24.50f;

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

	public float[] calcFrets(int numberOfFrets) {
		
		float[] fretArray = new float[numberOfFrets];
		
		for (int i = 0; i < fretArray.length; i++) {
			System.out.print("fretPosition: " + i);
			System.out.printf(" = [ %.4f", fretboardLength);
			
			fretPosition += fretboardLength / constant;
			fretboardLength -= fretboardLength / constant;
			
			System.out.printf(" - %.4f ] = %.4d\n", fretboardLength, fretPosition);
			
			fretArray[i] = fretPosition;
		}

		return fretArray;
	}

	public float getFretboardLength() {
		return fretboardLength;
	}

	public void setFretboardLength(float fretboardLength) {
		this.fretboardLength = fretboardLength;
	}

}
