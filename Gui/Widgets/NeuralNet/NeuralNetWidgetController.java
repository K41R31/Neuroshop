package Neuroshop.Gui.Widgets.NeuralNet;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;

public class NeuralNetWidgetController {

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

    public void draggableNeuron(Node node) {
        EventHandler<MouseEvent> onMousePressed =
                event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        sceneCursorPosX = event.getSceneX();
                        sceneCursorPosY = event.getSceneY();
                        nodeTranslatedX = node.getTranslateX();
                        nodeTranslatedY = node.getTranslateY();

//                        vGridInput.setStyle("-fx-border-color: BLACK");
//                        vGridOutput.setStyle("-fx-border-color: BLACK");
                    }
                };

        EventHandler<MouseEvent> onMouseDragged =
                event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        double offsetX = event.getSceneX() - sceneCursorPosX;
                        double offsetY = event.getSceneY() - sceneCursorPosY;
                        double newTranslateX = nodeTranslatedX + offsetX;
                        double newTranslateY = nodeTranslatedY + offsetY;
                        node.setTranslateX(newTranslateX);
                        node.setTranslateY(newTranslateY);
                    }
                };

        EventHandler<MouseEvent> onMouseReleased =
                event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        node.setTranslateX(0);
                        node.setTranslateY(0);
//                        vGridInput.setStyle("-fx-border-color: TRANSPARENT");
//                        vGridOutput.setStyle("-fx-border-color: TRANSPARENT");
                    }
                };
        node.setOnMousePressed(onMousePressed);
        node.setOnMouseDragged(onMouseDragged);
        node.setOnMouseReleased(onMouseReleased);
    }
}
