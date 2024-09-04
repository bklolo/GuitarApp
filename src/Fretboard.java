
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Fretboard extends Line {

	private static float constant = 17.817f;
	private static float fretPosition = 0;
	private static float fretboardLength = 24.50f;
	private float[] fretArray;
	private String imageDir = "maplefretboard.jpg";
	
	Fretboard(Pane pane, int stageHeight, int imageHeight, int imageWidth, 
				int fretCount){
		this.fretArray = calcFrets(fretCount);
		// applies an image to act as the fretboard
		FretboardImage(pane, stageHeight, imageHeight, imageWidth, imageDir);
		Frets(pane, stageHeight, imageHeight);
		// creates 3,5,7,9,12 inlays on fretboard
		Inlays(pane, stageHeight, fretCount);
		Nut(pane, stageHeight, imageHeight);
	}

	public void Frets(Pane pane, int stageHeight, int imageHeight){
		for (int i = 0; i < fretArray.length; i++) {
			Line fret = new Line();
			fret.setStroke(Color.SILVER);
			fret.setStartX((fretArray[i]) * 100);
			fret.setEndX((fretArray[i]) * 100);
			fret.setStartY(stageHeight / 2 - imageHeight / 2);
			fret.setEndY(stageHeight / 2 + imageHeight / 2);
			fret.setScaleX(5);
			pane.getChildren().add(fret);
		}
	}
	
	private void Inlays(Pane pane, int stageHeight, int fretCount) 
	{
		Inlay inlay3 = new Inlay(fretArray, 2, 15, Color.ALICEBLUE, stageHeight);
		Inlay inlay5 = new Inlay(fretArray, 4, 15, Color.ALICEBLUE, stageHeight);
		Inlay inlay7 = new Inlay(fretArray, 6, 15, Color.ALICEBLUE, stageHeight);
		Inlay inlay9 = new Inlay(fretArray, 8, 15, Color.ALICEBLUE, stageHeight);
		Inlay inlay12a = new Inlay(fretArray, 11, 15, Color.ALICEBLUE, stageHeight - 74);
		Inlay inlay12b = new Inlay(fretArray, 11, 15, Color.ALICEBLUE, stageHeight + 74);
		pane.getChildren().addAll(inlay3, inlay5, inlay7, inlay9, inlay12a, inlay12b);
	}
	
	private void Nut(Pane pane, int stageHeight, int imageHeight){
		Line Nut = new Line();
		Nut.setStroke(Color.ANTIQUEWHITE);
		Nut.setStartX(0.0);
		Nut.setEndX(0.0);
		Nut.setStartY(stageHeight / 2 - imageHeight / 2);
		Nut.setEndY(stageHeight / 2 + imageHeight / 2);
		Nut.setScaleX(20);
		pane.getChildren().add(Nut);
	}

	// Returns an array of fret positions
	public float[] calcFrets(int numberOfFrets){

		float[] array = new float[numberOfFrets];
		for (int i = 0; i < array.length; i++){
			fretPosition += fretboardLength / constant;
			fretboardLength -= fretboardLength / constant;
			array[i] = fretPosition;
		}
		return array;
	}
	
	// Generates the background for the guitar fretboard
	private Image FretboardImage(Pane pane, int stageHeight, int imageHeight, int imageWidth, String jpg) 
	{
		//InputStream stream = this.getClass().getClassLoader().getResourceAsStream(jpg);
		Image image = null;
		ImageView imageView = null;
		try
		{
			image = new Image(jpg);
			imageView = new ImageView(image);
		}
		catch(Exception e){
			System.out.println("Bad image");
			return null;
		}
		
		imageView.setX(0);
		imageView.setY(stageHeight / 2 - imageHeight / 2);
		imageView.setFitHeight(200);
		imageView.setFitWidth(imageWidth);
		// Overall color palette
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setBrightness(-0.2);
		colorAdjust.setSaturation(0.3);
		colorAdjust.setContrast(-0.1);
		imageView.setEffect(colorAdjust);
		imageView.toBack();
		pane.getChildren().add(imageView);
		return image;
	}
	
	public float[] getFretArray(){
		return fretArray;
	}
	public float getFretboardLength(){
		return fretboardLength;
	}

}
