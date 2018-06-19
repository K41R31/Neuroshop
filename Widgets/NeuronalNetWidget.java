package Neuroshop.Widgets;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;

public class NeuronalNetWidget {
    private AnchorPane mainPane;
    private double sceneCursorPosX, sceneCursorPosY;
    private double nodeTranslatedX, nodeTranslatedY;

    public NeuronalNetWidget() {
        //Unterste Pane auf die die Splines kommen
        mainPane = new AnchorPane();
        mainPane.setOnKeyPressed(event -> {
            System.out.println(event.getCode());
        });
        mainPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
        mainPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
        //HBox in der die eizelnen VBoxes sind
        HGrid hGrid = new HGrid();
        VGrid vGridInput = new VGrid(120, 300, 40);
        VGrid vGridOutput = new VGrid(120, 300, 40);
        VGrid vGridMenu = new VGrid();

        vGridInput.addCircle(20);
        vGridInput.addCircle(20);
        vGridOutput.addCircle(20);

        mainPane.getChildren().add(hGrid);
        hGrid.getChildren().add(vGridInput);
        hGrid.getChildren().add(vGridOutput);
        hGrid.getChildren().add(vGridMenu);

        VGrid columnGrid; //Muss mach dem Anzeigen auseführt werden (sonst sind die Koordinaten 0)
        for (int c = 0; c < hGrid.getChildren().size()-1; c++) {
            columnGrid = (VGrid)hGrid.getChildren().get(c);
            for (int r = 0; r < columnGrid.getChildren().size(); r++) {
                Circle neuron = (Circle)columnGrid.getChildren().get(r);

            }
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
        VGrid() {
            setStyle("-fx-background-color: #222222");
            setOpacity(0);
            setMinWidth(Region.USE_PREF_SIZE);
            setMinHeight(Region.USE_PREF_SIZE);
            setPrefWidth(20);
            setPrefHeight(300);
            setMaxWidth(Region.USE_PREF_SIZE);
            setMaxHeight(Region.USE_PREF_SIZE);
            setSpacing(20);
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
                        //Ist true, wenn ein gedraggtes Neuron weiter nach rechts gezogen wurde als das Widget groß ist
                        if (node.getParent().getBoundsInParent().getMinX() + node.getBoundsInParent().getMaxX() == node.getParent().getParent().getBoundsInParent().getMaxX()) {
                            //Ins Menu gedraggt
                        }
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

    public AnchorPane getMainPane() {
        return mainPane;
    }
}
