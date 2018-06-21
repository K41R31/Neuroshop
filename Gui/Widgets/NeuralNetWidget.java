package Neuroshop.Gui.Widgets;

import Neuroshop.ScreenSize;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;
import javafx.util.Duration;

public class NeuralNetWidget {

    private VBox mainPane;
    private double sceneCursorPosX, sceneCursorPosY;
    private double nodeTranslatedX, nodeTranslatedY;
    private boolean menuIsOpen = false;
    private boolean menuIsBusy = false;
    private StackPaneMenu vGridMenu;
    private HGrid vGridContainer;

    public NeuralNetWidget() {
        //Unterste Pane auf die die Splines kommen
        mainPane = new VBox();
        mainPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
        mainPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
        //HBox in der die eizelnen VBoxes sind
        vGridContainer = new HGrid();
        VGrid vGridInput = new VGrid(120, 300, 40);
        VGrid vGridOutput = new VGrid(120, 300, 40);
        vGridMenu = new StackPaneMenu();

        vGridInput.addCircle(20);
        vGridInput.addCircle(20);
        vGridOutput.addCircle(20);

        mainPane.getChildren().add(vGridContainer);
        vGridContainer.getChildren().add(vGridInput);
        vGridContainer.getChildren().add(vGridOutput);

        VGrid columnGrid; //Muss mach dem Anzeigen auseführt werden (sonst sind die Koordinaten 0)
        for (int c = 0; c < vGridContainer.getChildren().size()-1; c++) {
            columnGrid = (VGrid)vGridContainer.getChildren().get(c);
            for (int r = 0; r < columnGrid.getChildren().size(); r++) {
                Circle neuron = (Circle)columnGrid.getChildren().get(r);

            }
        }
    }

    class StackPaneMenu extends StackPane {
        StackPaneMenu() {
            setStyle("-fx-background-color: #222222");
            setOpacity(0);
            setMinWidth(Region.USE_PREF_SIZE);
            setMinHeight(Region.USE_PREF_SIZE);
            setPrefWidth(0);
            setPrefHeight(0);
            setMaxWidth(Region.USE_PREF_SIZE);
            setMaxHeight(Region.USE_PREF_SIZE);
        }
    }

    class HGrid extends HBox {
        HGrid() {
            setPrefWidth(Region.USE_COMPUTED_SIZE);
            setPrefHeight(Region.USE_COMPUTED_SIZE);
        }
    }

    class VGrid extends VBox {
        VGrid(double prefWidth, double prefHeigth, double spacing) {
            setMinWidth(Region.USE_COMPUTED_SIZE);
            setMinHeight(Region.USE_COMPUTED_SIZE);
            setPrefWidth(prefWidth);
            setPrefHeight(prefHeigth);
            setMaxWidth(Region.USE_COMPUTED_SIZE);
            setMaxHeight(Region.USE_COMPUTED_SIZE);
            setSpacing(spacing);
            setAlignment(Pos.CENTER);
        }
        private void addCircle(double radius) { //20
            Circle circle = new Circle(radius);
            circle.setStrokeWidth(0);
            circle.setFill(Color.WHITE);
            draggableNeuron(circle);
            getChildren().add(circle);
        }
        private void removeCircle(Circle circle) {
            int index = getChildren().indexOf(circle);
            getChildren().remove(index);
        }
    }

    class Splines extends QuadCurve {

    }

    private void toggleMenu() {
        if (!menuIsOpen) {
            Bounds bounds = mainPane.getBoundsInParent();

            if (bounds.getMaxY() < ScreenSize.height - 70) {
                mainPane.getChildren().add(vGridMenu);
                vGridContainer.toFront();
                vGridMenu.setPrefWidth(100);
                vGridMenu.setPrefHeight(100);
            }

            Timeline openMenuAnimation = new Timeline();
            openMenuAnimation.getKeyFrames().addAll(
                    new KeyFrame(new Duration(100), new KeyValue(vGridMenu.opacityProperty(), 0.5, Interpolator.EASE_BOTH))
            );
            openMenuAnimation.play();
            menuIsOpen = true;
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
                        toggleMenu();
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

    public int getIndex() { return 0; }
    public VBox getMainPane() {
        return mainPane;
    }
}
