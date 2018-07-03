package Neuroshop.Gui.Widgets.NeuralNetWidget;

import Neuroshop.Gui.Widgets.MakeDraggable;
import Neuroshop.Models.ANNModel;
import Neuroshop.ANN.Learn.Backpropagation;
import Neuroshop.Models.WidgetContainerModel;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class NeuralNetController extends StackPane implements Observer {

    private ANNModel annModel;
    private Backpropagation bP;
    private WidgetContainerModel widgetContainerModel;
    private ArrayList<ArrayList<ArrayList<Double>>> newWeights;

    @FXML
    private AnchorPane splinesPane;
    @FXML
    private StackPane rootPane;
    @FXML
    private HBox vBoxContainer;
    @FXML
    private VBox inputLayer;
    @FXML
    private HBox hiddenLayer;
    @FXML
    private HBox menuPane;
    @FXML
    private VBox outputLayer;
    private double splineTangent = 50; //TODO um Kurvität von Splines zu ändern
    private double splineWidth = 1;



    private void initNeuralWidget() {
        if (hiddenLayer.getChildren().size() == 0) {
            for (int i = 0; i < annModel.getInputColumns().length; i++) {
                inputLayer.getChildren().add(new Neuron());
                drawSplines();
            }
            for (int i = 0; i < annModel.getOutputColums().length; i++) {
                outputLayer.getChildren().add(new Neuron());
                drawSplines();
            }
            hiddenLayer.getChildren().add(new HiddenLayer());
            updateNeuronsInModel();
        }
    }

    @FXML
    private void drawSplines() {
//        annModel.setNewWeights(newWeights);
        splinesPane.getChildren().clear();
        VBox endPane, startPane;
        Circle endCircle, startCircle;
        double startX, startY, endX, endY, controlX1, controlX2;
        Bounds startPaneBounds, endPaneBounds, startCircleBounds, endCircleBounds;
        for (int c = 0; c < hiddenLayer.getChildren().size(); c++) {
            if (c == 0) startPane = inputLayer;
            else startPane = (VBox)hiddenLayer.getChildren().get(c-1);
            if (c < hiddenLayer.getChildren().size()-1) endPane = (VBox)hiddenLayer.getChildren().get(c);
            else endPane = outputLayer;
            for (int x = 0; x < endPane.getChildren().size(); x++) { //2. Spalte 1. Neuron
                endCircle = (Circle)endPane.getChildren().get(x);
                for (int r = 0; r < startPane.getChildren().size(); r++) { //1. Spalte 1. Neuron
                    startCircle = (Circle)startPane.getChildren().get(r);
                    startPaneBounds = startPane.getBoundsInParent();
                    endPaneBounds = endPane.getBoundsInParent();
                    startCircleBounds = startCircle.getBoundsInParent();
                    endCircleBounds = endCircle.getBoundsInParent();
                    if (c == 0) {
                        startX = startPaneBounds.getMinX() + startCircleBounds.getMinX() + 20;
                        endX = endCircle.getParent().getParent().getBoundsInParent().getMinX() + endPaneBounds.getMinX() + endCircleBounds.getMinX() + 20;
                    } else if (c < hiddenLayer.getChildren().size()-1) {
                        startX = startCircle.getParent().getParent().getBoundsInParent().getMinX() + startPaneBounds.getMinX() + startCircleBounds.getMinX() + 20;
                        endX = endCircle.getParent().getParent().getBoundsInParent().getMinX() + endPaneBounds.getMinX() + endCircleBounds.getMinX() + 20;
                    } else {
                        startX = startCircle.getParent().getParent().getBoundsInParent().getMinX() + startPaneBounds.getMinX() + startCircleBounds.getMinX() + 20;
                        endX = endPaneBounds.getMinX() + endCircleBounds.getMinX() + 20;
                    }
                    startY = startCircleBounds.getMinY() + 20;
                    endY = endCircleBounds.getMinY() + 20;
                    controlX1 = startX + splineTangent;
                    controlX2 = endX - splineTangent;
//                    double lastDeltaWeight = newWeights.get(c).get(x).get(r);
                    CubicCurve spline = new CubicCurve(startX, startY, controlX1, startY, controlX2, endY, endX, endY);
                    spline.setLayoutX(0);
                    spline.setLayoutY(0);
                    spline.setFill(Color.TRANSPARENT);
//                    spline.setStrokeWidth(lastDeltaWeight);
                    spline.setStroke(Color.BLACK);
                    splinesPane.getChildren().add(spline);
                }
            }
        }
    }

    private class Neuron extends Circle {
        Neuron() {
            setFill(Color.WHITE);
            setRadius(20);
        }
    }

    private class OptionsPane extends VBox {
        OptionsPane() {
            setAlignment(Pos.CENTER);
            setMinWidth(USE_PREF_SIZE);
            setPrefWidth(130);
            setPrefHeight(USE_COMPUTED_SIZE);
            setMaxWidth(USE_PREF_SIZE);
            ImageView addNeuronButton = new ImageView(new Image("Neuroshop/Ressources/Assets/addNeuronButton.png"));
            ImageView removeNeuronButton = new ImageView(new Image("Neuroshop/Ressources/Assets/removeNeuronButton.png"));
            addNeuronButton.setPickOnBounds(true);
            removeNeuronButton.setPickOnBounds(true);
            addNeuronButton.setOnMouseClicked(event -> {
                ((HiddenLayer)hiddenLayer.getChildren().get(menuPane.getChildren().indexOf(this)-1)).addNeuron();
                updateNeuronsInModel();
                drawSplines();
            });
            removeNeuronButton.setOnMouseClicked(event -> {
                ((HiddenLayer)hiddenLayer.getChildren().get(menuPane.getChildren().indexOf(this)-1)).removeNeuron();
                drawSplines();
            });
            getChildren().add(addNeuronButton);
            getChildren().add(removeNeuronButton);
        }
    }

    private class HiddenLayer extends VBox {
        HiddenLayer() {
            setMinWidth(USE_PREF_SIZE);
            setPrefWidth(0);
            setPrefHeight(USE_COMPUTED_SIZE);
            setMaxWidth(USE_PREF_SIZE);
            setPadding(new Insets(30, 0, 30, 0));
            setSpacing(30);
            setAlignment(Pos.CENTER);
            ImageView addLayerButton = new ImageView(new Image("Neuroshop/Ressources/Assets/crossButton.png"));
            addLayerButton.setFitWidth(20);
            addLayerButton.setFitHeight(20);
            addLayerButton.setPickOnBounds(true);
            addLayerButton.setOpacity(0.2);
            addLayerButton.setOnMouseEntered(event -> {
                Timeline openMenuAnimation = new Timeline();
                openMenuAnimation.getKeyFrames().addAll(
                        new KeyFrame(new Duration(50), new KeyValue(addLayerButton.fitWidthProperty(), 30, Interpolator.EASE_BOTH),
                                new KeyValue(addLayerButton.fitHeightProperty(), 30, Interpolator.EASE_BOTH),
                                new KeyValue(addLayerButton.opacityProperty(), 1, Interpolator.EASE_BOTH))
                );
                openMenuAnimation.play();
            });
            addLayerButton.setOnMouseExited(event -> {
                Timeline openMenuAnimation = new Timeline();
                openMenuAnimation.getKeyFrames().addAll(
                        new KeyFrame(new Duration(50), new KeyValue(addLayerButton.fitWidthProperty(), 20, Interpolator.EASE_BOTH),
                                new KeyValue(addLayerButton.fitHeightProperty(), 20, Interpolator.EASE_BOTH),
                                new KeyValue(addLayerButton.opacityProperty(), 0.2, Interpolator.EASE_BOTH))
                );
                openMenuAnimation.play();
            });
            addLayerButton.setOnMouseClicked(event -> {
                Timeline openMenuAnimation = new Timeline();
                openMenuAnimation.getKeyFrames().addAll(
                        new KeyFrame(new Duration(100), new KeyValue(this.prefWidthProperty(), 130, Interpolator.EASE_BOTH))
                );
                openMenuAnimation.play();
                this.getChildren().clear();
                hiddenLayer.getChildren().add(new HiddenLayer());
                menuPane.getChildren().add(new OptionsPane());
                drawSplines();
            });
            getChildren().add(addLayerButton);
        }
        void addNeuron() {
            getChildren().add(new Neuron());
        }
        void removeNeuron() {
            if (getChildren().size() > 0) getChildren().remove(0);
        }
    }

    private void updateNeuronsInModel() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < hiddenLayer.getChildren().size()-1; i++) {
            arrayList.add(((VBox)hiddenLayer.getChildren().get(i)).getChildren().size());
        }
        annModel.setNeuronsInHiddenLayer(arrayList);
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((String)arg) {
            case "initNeuralWidget":
                initNeuralWidget();
        }
    }

    public void initModel(ANNModel annModel, WidgetContainerModel widgetContainerModel) {
        this.annModel = annModel;
        this.widgetContainerModel = widgetContainerModel;
        new MakeDraggable(widgetContainerModel, rootPane);
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
