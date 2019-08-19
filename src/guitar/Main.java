package guitar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

// Event Handler vars
double SceneX;
double SceneY;
double TranslateX;
double TranslateY;

public static void main(String[] args) {

launch(args);

}

@Override
public void start(Stage stage) throws FileNotFoundException {

// Stage vars
int stageHeight = 400;
int stageWidth = 1000;
int imageHeight = 200;

// Guitar vars
int frets = 12;
double stdFretboardLength = 25.40; // Total length of guitar neck
double constant = 17.817; // Constant used for calculating frets
double length = stdFretboardLength;
double fretPosition = 0;
double[] fretLocation = new double[frets];
double stringStartY = stageHeight / 2 - imageHeight / 2;
double stringEndY = stageHeight / 2 - imageHeight / 2;

// Create Pane
Pane pane1 = new Pane();
pane1.setBackground(null);

// wrap the scene contents in a pannable scroll pane.
ScrollPane scrollPane = createScrollPane(pane1);
scrollPane.setBackground(null);

// Create Scene
Scene scene = new Scene(scrollPane, stageWidth, stageHeight, Color.BLACK);
scene.setFill(Color.BLACK);
stage.setMinWidth(stageWidth - 300);
stage.setMinHeight(stageHeight - 100);
stage.setMaxHeight(stageHeight);
stage.setMaxWidth(stageWidth + 205);

// bind the preferred size of the scroll area to the size of the scene.
scrollPane.prefWidthProperty().bind(scene.widthProperty());
scrollPane.prefHeightProperty().bind(scene.widthProperty());

// center the scroll contents.
scrollPane.setHvalue(scrollPane.getHmin() + (scrollPane.getHmax() - scrollPane.getHmin()) / 2);
scrollPane.setVvalue(scrollPane.getVmin() + (scrollPane.getVmax() - scrollPane.getVmin()) / 2);

// Creates fretboard and sets properties
Rectangle fretBoard = new Rectangle();
fretBoard.setX(0);
fretBoard.setY(stageHeight / 2 - imageHeight / 2);
fretBoard.setWidth(stageWidth);
fretBoard.setHeight(imageHeight);
fretBoard.setFill(Color.CHOCOLATE);

fretBoard.setOnMousePressed(fretBoardOnMousePressed);
fretBoard.setOnMouseDragged(fretBoardOnMouseDragged);

// Create a tile pane
Image image = new Image(new FileInputStream(".\\maple fretboard.jpg"));

// Setting the image view
ImageView imageView = new ImageView(image);

// Setting the position of the image
imageView.setX(0);
imageView.setY(stageHeight / 2 - imageHeight / 2);

// setting the fit height and width of the image view
imageView.setFitHeight(200);
imageView.setFitWidth(stageWidth + 270);

ColorAdjust colorAdjust = new ColorAdjust();
colorAdjust.setBrightness(-.2);
colorAdjust.setSaturation(0.3);
colorAdjust.setContrast(-0.1);

imageView.setEffect(colorAdjust);
pane1.getChildren().add(imageView);

// Creates guitar Nut and sets properties
Line Nut = new Line();
Nut.setStroke(Color.ANTIQUEWHITE);
Nut.setStartX(0.0);
Nut.setEndX(0.0);
Nut.setStartY(stageHeight / 2 - imageHeight / 2);
Nut.setEndY(stageHeight / 2 + imageHeight / 2);
Nut.setScaleX(20);

// Calculates and stores fret locations up to 12th fret
for (int i = 0; i < fretLocation.length; i++) {
fretPosition += length / constant;
length -= length / constant;
int num = i;
System.out.print("fretPosition: " + num);
System.out.printf(" = [ %.4f - %.4f ] = %.4f\n", stdFretboardLength, length, fretPosition);
fretLocation[i] = fretPosition;
}

double inlay3Pos = (fretLocation[2] - ((fretLocation[2] - fretLocation[1]) / 2)) * 100;
double inlay5Pos = (fretLocation[4] - ((fretLocation[4] - fretLocation[3]) / 2)) * 100;
double inlay7Pos = (fretLocation[6] - ((fretLocation[6] - fretLocation[5]) / 2)) * 100;
double inlay9Pos = (fretLocation[8] - ((fretLocation[8] - fretLocation[7]) / 2)) * 100;
double inlay12Pos = (fretLocation[11] - ((fretLocation[11] - fretLocation[10]) / 2)) * 100;

// Creates INLAYS for fretboard
Circle circle3 = new Circle(15, Color.DARKSEAGREEN);
circle3.relocate(inlay3Pos - circle3.getRadius(), (stageHeight / 2 - circle3.getRadius()));
Circle circle5 = new Circle(15, Color.ALICEBLUE);
circle5.relocate(inlay5Pos - circle5.getRadius(), (stageHeight / 2 - circle5.getRadius()));
Circle circle7 = new Circle(15, Color.ALICEBLUE);
circle7.relocate(inlay7Pos - circle7.getRadius(), (stageHeight / 2 - circle7.getRadius()));
Circle circle9 = new Circle(15, Color.ALICEBLUE);
circle9.relocate(inlay9Pos - circle9.getRadius(), (stageHeight / 2 - circle9.getRadius()));
Circle circle12a = new Circle(15, Color.ALICEBLUE);
circle12a.relocate(inlay12Pos - circle12a.getRadius(), (stageHeight / 2 - circle12a.getRadius() - 44));
Circle circle12b = new Circle(15, Color.ALICEBLUE);
circle12b.relocate(inlay12Pos - circle12b.getRadius(), (stageHeight / 2 - circle12b.getRadius() + 44));


// FRETBOARD DECOR
Polygon polygon = new Polygon();
polygon.getPoints().addAll(new Double[]{
   305.0, 160.0,
   305.0, 240.0,
   375.0, 270.0,
   375.0, 130.0
   });
polygon.setFill(Color.CORNSILK);
polygon.setStroke(Color.BLACK);
polygon.setStrokeWidth(.5);



pane1.getChildren().addAll(polygon, circle3, circle5, circle7, circle9, circle12a, circle12b, Nut);

// Creates # of frets with set properties and adds to Pane
for (int i = 0; i < fretLocation.length; i++) {
Line fret = new Line();
fret.setStroke(Color.SILVER);
fret.setStartX((fretLocation[i]) * 100);
fret.setEndX((fretLocation[i]) * 100);
fret.setStartY(stageHeight / 2 - imageHeight / 2);
fret.setEndY(stageHeight / 2 + imageHeight / 2);
fret.setScaleX(5);
pane1.getChildren().add(fret);
}

// Creates strings with set properties and adds to Pane
for (int i = 0; i < 6; i++) {
Line string = new Line();
string.setStroke(Color.GOLD);
string.setStartX(0);
string.setEndX(fretLocation[11] * 100);
string.setStartY(stringStartY);
string.setEndY(stringEndY);
string.setLayoutY(20 + i * ((imageHeight - 5) / 6));
string.setScaleY(5 + .5 * i);

DropShadow shadow = new DropShadow();
shadow.setOffsetX(11.0);
shadow.setOffsetY(1.0);
shadow.setHeight(1);
shadow.setColor(Color.BLACK);
shadow.setRadius(.5);

string.setEffect(shadow);

pane1.getChildren().add(string);

}

double[] noteLocation = new double[frets];
noteLocation[0] = fretLocation[0] / 2;
noteLocation[1] = fretLocation[1] - (fretLocation[1] - fretLocation[0]) / 2;
noteLocation[2] = fretLocation[2] - (fretLocation[2] - fretLocation[1]) / 2;
noteLocation[3] = fretLocation[3] - (fretLocation[3] - fretLocation[2]) / 2;
noteLocation[4] = fretLocation[4] - (fretLocation[4] - fretLocation[3]) / 2;
noteLocation[5] = fretLocation[5] - (fretLocation[5] - fretLocation[4]) / 2;
noteLocation[6] = fretLocation[6] - (fretLocation[6] - fretLocation[5]) / 2;
noteLocation[7] = fretLocation[7] - (fretLocation[7] - fretLocation[6]) / 2;
noteLocation[8] = fretLocation[8] - (fretLocation[8] - fretLocation[7]) / 2;
noteLocation[9] = fretLocation[9] - (fretLocation[9] - fretLocation[8]) / 2;
noteLocation[10] = fretLocation[10] - (fretLocation[10] - fretLocation[9]) / 2;
noteLocation[11] = fretLocation[11] - (fretLocation[11] - fretLocation[10]) / 2;

ArrayList<Circle> noteArray = new ArrayList<>();
// Creates # of NoteBubbles with set properties and adds to Pane
for (int i = 0; i < noteLocation.length; i++) {
Circle bubble = new Circle(12, Color.GREEN);
double bubbleX = noteLocation[i] * 100 - 12;
double bubbleY = stageHeight / 2 - stringStartY + bubble.getRadius() / 2;
bubble.relocate(bubbleX, bubbleY);
bubble.setStroke(Color.BLACK);
pane1.getChildren().add(bubble);
noteArray.add(bubble);
}

// Create a combo box (drop down menu)
ComboBox<String> comboBox = new ComboBox<>();
comboBox.getItems().addAll("Major", "Minor");
comboBox.autosize();
comboBox.setPromptText("Scale");
comboBox.layoutXProperty().bind(scrollPane.hvalueProperty()
.multiply(pane1.widthProperty().subtract(new ScrollPaneViewPortWidthBinding(scrollPane))));
scrollPane.setHvalue(0);
pane1.getChildren().add(comboBox);
// comboBox.addEventHandler(eventType, eventHandler);

stage.setTitle("Guitar Scales");
stage.setScene(scene);
stage.show();

}

private static ArrayList<Circle> MajorScale(ArrayList<Circle> array) {
ArrayList<Circle> temp = new ArrayList<Circle>();
temp.add(array.get(0));
temp.add(array.get(0));
return temp;

}

private static class ScrollPaneViewPortWidthBinding extends DoubleBinding {

private final ScrollPane root;

public ScrollPaneViewPortWidthBinding(ScrollPane root) {
this.root = root;
super.bind(root.viewportBoundsProperty());
}

@Override
protected double computeValue() {
return root.getViewportBounds().getWidth();
}
}

private ScrollPane createScrollPane(Pane layout) {
ScrollPane scroll = new ScrollPane();
scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
scroll.setPannable(true);
scroll.setPrefSize(800, 600);
scroll.setContent(layout);
return scroll;
}

EventHandler<MouseEvent> fretBoardOnMousePressed = new EventHandler<MouseEvent>() {

@Override
public void handle(MouseEvent t) {
SceneX = t.getSceneX();
SceneY = t.getSceneY();
TranslateX = ((Circle) (t.getSource())).getTranslateX();
TranslateY = ((Circle) (t.getSource())).getTranslateY();
}
};

EventHandler<MouseEvent> fretBoardOnMouseDragged = new EventHandler<MouseEvent>() {

@Override
public void handle(MouseEvent t) {
double offsetX = t.getSceneX() - SceneX;
double offsetY = t.getSceneY() - SceneY;
double newTranslateX = TranslateX + offsetX;
double newTranslateY = TranslateY + offsetY;

((Rectangle) t.getSource()).setTranslateX(newTranslateX);
((Rectangle) (t.getSource())).setTranslateY(newTranslateY);
}
};

}
