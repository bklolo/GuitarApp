package src.guitar;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class NoteBubble extends Circle {

	private static double radius = 15;
	private static Color color = Color.GREEN;
	private float bubbleX, bubbleY;
	private float yPos;
	private float[] noteLocation;

	NoteBubble(Pane pane, int imageHeight, int stageHeight, float[] fretArray, int fretCount) {

//		super(radius, color);
		noteLocation = notePosition(fretArray, fretCount);

		for (int i = 0; i < noteLocation.length; i++) {
			Circle circle = new Circle(radius, color);
			circle.setFill(color);
			circle.setStroke(Color.PINK);

			yPos = stageHeight / 2 - imageHeight / 2;

			bubbleX = noteLocation[i] * 100;
			bubbleY = (float) (stageHeight / 2 - yPos + getRadius() / 2);

			circle.setCenterY(bubbleY);
			circle.setCenterX(bubbleX);

			pane.getChildren().add(circle);
		}
	}

	public Color getColor() {
		return color;
	}

	public float getyPos() {
		return yPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}

	public static float[] notePosition(float[] fretArray, int frets) {
		float[] noteLocation = new float[frets];
		noteLocation[0] = fretArray[0] / 2;
		noteLocation[1] = fretArray[1] - (fretArray[1] - fretArray[0]) / 2;
		noteLocation[2] = fretArray[2] - (fretArray[2] - fretArray[1]) / 2;
		noteLocation[3] = fretArray[3] - (fretArray[3] - fretArray[2]) / 2;
		noteLocation[4] = fretArray[4] - (fretArray[4] - fretArray[3]) / 2;
		noteLocation[5] = fretArray[5] - (fretArray[5] - fretArray[4]) / 2;
		noteLocation[6] = fretArray[6] - (fretArray[6] - fretArray[5]) / 2;
		noteLocation[7] = fretArray[7] - (fretArray[7] - fretArray[6]) / 2;
		noteLocation[8] = fretArray[8] - (fretArray[8] - fretArray[7]) / 2;
		noteLocation[9] = fretArray[9] - (fretArray[9] - fretArray[8]) / 2;
		noteLocation[10] = fretArray[10] - (fretArray[10] - fretArray[9]) / 2;
		noteLocation[11] = fretArray[11] - (fretArray[11] - fretArray[10]) / 2;

		return noteLocation;
	}

}
