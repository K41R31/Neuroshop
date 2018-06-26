package Neuroshop.Widgets.NeuralNetWidget;

import Neuroshop.Models.WidgetContainerModel;
import Neuroshop.Models.WidgetModels.NeuralNetWidgetModel;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;

import java.util.Observable;
import java.util.Observer;

public class NeuralNetController implements Observer {

    private NeuralNetWidgetModel neuralNetWidgetModel;
    private WidgetContainerModel widgetContainerModel;

    @FXML
    private AnchorPane splinesPane;
    @FXML
    private StackPane menuPane;
    @FXML
    private HBox vBoxContainer;
    private double sceneCursorPosX, sceneCursorPosY;
    private double nodeTranslatedX, nodeTranslatedY;
    private boolean deleteFieldIsShowing;
    @FXML
    private void initialize() {
    }

    public void drawSplines() {
        VBox endPane, startPane;
        Circle endCircle, startCircle;
        double startX, startY, endX, endY, controlX1, controlX2;
        Bounds startPaneBounds, endPaneBounds, startCircleBounds, endCircleBounds;
        for (int c = 1; c < vBoxContainer.getChildren().size(); c++) { //2. Spalte
            endPane = (VBox)vBoxContainer.getChildren().get(c);
            startPane = (VBox)vBoxContainer.getChildren().get(c-1);
            for (int x = 0; x < endPane.getChildren().size(); x++) { //2. Spalte 1. Neuron
                endCircle = (Circle)endPane.getChildren().get(x);
                for (int r = 0; r < startPane.getChildren().size(); r++) { //1. Spalte 1. Neuron
                    startCircle = (Circle)startPane.getChildren().get(r);

                    startPaneBounds = startPane.getBoundsInParent();
                    endPaneBounds = endPane.getBoundsInParent();
                    startCircleBounds = startCircle.getBoundsInParent();
                    endCircleBounds = endCircle.getBoundsInParent();
                    startX = startPaneBounds.getMinX() + startCircleBounds.getMinX() + 20;
                    startY = startCircleBounds.getMinY() + 20;
                    endX = endPaneBounds.getMinX() + endCircleBounds.getMinX() + 20;
                    endY = endCircleBounds.getMinY() + 20;
                    controlX1 = startX + 50;
                    controlX2 = endX - 50;

                    System.out.println("startX: "+startX+", startY: "+startY+", controlX1: "+controlX1+", controlY1: "+startX+", controlX2: "+controlX1+", controlY2: "+endY+", endX: "+endX+", endY: "+endY);
                    CubicCurve spline = new CubicCurve(startX, startY, controlX1, startY, controlX2, endY, endX, endY);
                    spline.setLayoutX(0);
                    spline.setLayoutY(0);
                    spline.setFill(Color.TRANSPARENT);
                    spline.setStroke(Color.BLACK);
                    splinesPane.getChildren().add(spline);
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
    }

    public void initModel(NeuralNetWidgetModel neuralNetWidgetModel,WidgetContainerModel widgetContainerModel) {
        this.neuralNetWidgetModel = neuralNetWidgetModel;
        this.widgetContainerModel = widgetContainerModel;
    }
}

/**     private int numberOfInputs;  TODO: Entsprechend der Anzahl der Input Spalten (InputColumns)
 private int numberOfOutputs; TODO: Entsprechend der Output Spalten (Output Columns)
 private int[] inputColumns; TODO: Wird in einer Tabelle oder so angegeben, man muss festlegen können welche Spalte relevant ist
 private int[] outputColumns; TODO: Das Selbe wie oben

 private DataNormalization dataNormType; TODO: Auswahlmenü für Funktion DataNormalization (Funktionen: MinMax oder ZScore)

 private int dataPercentage; TODO: Schieberegler für Anteil des Datensatzes zum Festlegen wieviel Prozent als Trainings und Testdatensatz verwendet werden (0% - 100%)
 private int[] numberOfHiddenNeurons; TODO: Anzahl der Neuronen im HiddenLayer (0 - offen) ++ Entspricht dann der Anzahl die beim Aufbauen aufs Whiteboard gezogen werden
 TODO: Wird als Array eingegeben {3, 4, 3}. Erster Layer hat 3, zweiter 4 und der dritte wieder 3
 private IActivationFunction[] actFnc; TODO: Hier wird die Aktivierungsfunktion für die jeweiligen HiddenLayer angegeben. Ebenfalls in Reihenfolge wie im obigen Array
 private Linear outputActFnc; TODO: Wird gesondert behandelt, da immer letzter Layer im Netz, bekommt eine eigenen Aktivierungsfunktion. In unserem Fall immer "LINEAR"

 private double minOverallError; TODO: Kleiner Zahlenbereich, sollte immer 0,0... sein, aber nicht zu tief da sich das Netz sonst tot läuft. Am besten so 0,0...
 private double learningRate; TODO: 0.000001 bis maximal 10. Wobei kleinere Zahlenbereiche zwischen 0.0001 bis maximalst 1 sinnvoller sind.
 private double momentumRate; TODO: Können wir auch einfach auf fix 0.7 setzen. Wird benötigt in der Backpropagation. Muss nicht zwingend verändert werden.
 private int iterations; TODO: Von 1 bis offen, wir können nicht wissen wieviele Durchläufe benötigt werden.
 *
 */
