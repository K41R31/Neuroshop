package Neuroshop.Widgets;

import Neuroshop.Models.WidgetContainerModel;
import Neuroshop.Models.WidgetModels.DataManagerWidgetModel;
import Neuroshop.Models.WidgetModels.DiagramWidgetModel;
import Neuroshop.Models.WidgetModels.NeuralNetWidgetModel;
import Neuroshop.Widgets.DataManagerWidget.DataManagerWidgetController;
import Neuroshop.Widgets.DiagramWidget.DiagramWidgetController;
import Neuroshop.Widgets.NeuralNetWidget.NeuralNetController;
import Neuroshop.Widgets.WidgetBorder.WidgetBorderController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.awt.*;
import java.io.IOException;

public class WidgetContainer {

    private FXMLLoader widgetBorderLoader, dataManagerLoader, diagramWidgetLoader, neuralNetWidgetLoader;
    private AnchorPane widgetBorderRoot, dataManagerWidgetRoot, diagramWidgetRoot, neuralNetWidgetRoot;
    private StackPane dataManagerPrevRoot, diagramPrevRoot, neuralNetPrevRoot;

    //Für Mouse Events
    private double sceneCursorPosX, sceneCursorPosY;
    private double nodeTranslatedX, nodeTranslatedY;
    private double sceneWidth, sceneHeight;

    public WidgetContainer() throws IOException {

        widgetBorderLoader = new FXMLLoader(getClass().getResource("WidgetBorder/WidgetBorderView.fxml"));
        widgetBorderRoot = widgetBorderLoader.load();

        dataManagerLoader = new FXMLLoader(getClass().getResource("DataManagerWidget/DataManagerWidgetView.fxml"));
        dataManagerWidgetRoot = dataManagerLoader.load();

        diagramWidgetLoader = new FXMLLoader(getClass().getResource("DiagramWidget/DiagramWidgetView.fxml"));
        diagramWidgetRoot = diagramWidgetLoader.load();

        neuralNetWidgetLoader = new FXMLLoader(getClass().getResource("NeuralNetWidget/NeuralNetView.fxml"));
        neuralNetWidgetRoot = neuralNetWidgetLoader.load();

        dataManagerPrevRoot = new PreviewWidget("Data Manager", new Image("Neuroshop/Ressources/thumbKommtNoch.png"));
        diagramPrevRoot = new PreviewWidget("Diagram", new Image("Neuroshop/Ressources/resultDiagramThumb.png"));
        neuralNetPrevRoot = new PreviewWidget("Neural Net", new Image("Neuroshop/Ressources/netThumb.png"));
        draggablePreview(dataManagerPrevRoot);
    }

    private void draggablePreview(StackPane node) {

        EventHandler<MouseEvent> onMousePressed =
                event -> {
                    sceneCursorPosX = event.getSceneX();
                    sceneCursorPosY = event.getSceneY();
                    nodeTranslatedX = node.getTranslateX();
                    nodeTranslatedY = node.getTranslateY();
                    sceneWidth = node.getScene().getWidth();
                    sceneHeight = node.getScene().getHeight()-30; //-30 weil die obere Leiste 30 Pixel groß ist

                    widgetMenuPane.getChildren().remove(node);

                    model.setDraggPreview(node.getId());
                };

        EventHandler<MouseEvent> onMouseDragged =
                event -> {
                    double offsetX = event.getSceneX() - sceneCursorPosX;
                    double offsetY = event.getSceneY() - sceneCursorPosY;
                    double newTranslateX = nodeTranslatedX + offsetX;
                    double newTranslateY = nodeTranslatedY + offsetY;

                    //Collider, der das Objekt stoppt falls es an eine Wand stößt
                    if ((node.getBoundsInParent().getMinX() > 0 || newTranslateX > node.getTranslateX()) & (node.getBoundsInParent().getMaxX() < sceneWidth || newTranslateX < node.getTranslateX())) node.setTranslateX(newTranslateX);
                    if ((node.getBoundsInParent().getMinY() > 0 || newTranslateY > node.getTranslateY()) & (node.getBoundsInParent().getMaxY() < sceneHeight || newTranslateY < node.getTranslateY())) node.setTranslateY(newTranslateY);
                    //Behebt einen Glitch, bei dem das Objekt durch schnelles bewegen durch die Wand gezogen werden kann, indem es genau so weit zurück transliert wird, wie es durch geglitcht ist.
                    if (node.getBoundsInParent().getMinX() < 0) node.setTranslateX(newTranslateX-node.getBoundsInParent().getMinX());
                    else if (node.getBoundsInParent().getMaxX() > sceneWidth) node.setTranslateX(newTranslateX-(node.getBoundsInParent().getMaxX()-sceneWidth));
                    if (node.getBoundsInParent().getMinY() < 0) node.setTranslateY(newTranslateY-node.getBoundsInParent().getMinY());
                    else if (node.getBoundsInParent().getMaxY() > sceneHeight) node.setTranslateY(newTranslateY-(node.getBoundsInParent().getMaxY()-sceneHeight));

                    if (menuIsOpen & MouseInfo.getPointerInfo().getLocation().x > 300) {
                        toggleMenu();
                    } else if (!menuIsOpen & node.getBoundsInParent().getMinX() <= 30) {
                        toggleMenu();
                    }
                };

        EventHandler<MouseEvent> onMouseReleased =
                event -> {
                    if (MouseInfo.getPointerInfo().getLocation().x > 300) {
                        model.addWidgetToWhiteboard(model.getDraggPreview());
                        model.removeDraggPreview();
                    } else if (menuIsOpen & MouseInfo.getPointerInfo().getLocation().x < 300) {
                        widgetMenuPane.getChildren().add(node);
                        model.removeDraggPreview();
                        node.setTranslateX(0);
                        node.setTranslateY(0);
                    }
                };

        node.setOnMousePressed(onMousePressed);
        node.setOnMouseDragged(onMouseDragged);
        node.setOnMouseReleased(onMouseReleased);
    }

        public void initModel(WidgetContainerModel widgetContainerModel, DataManagerWidgetModel dataManagerWidgetModel, DiagramWidgetModel diagramWidgetModel, NeuralNetWidgetModel neuralNetWidgetModel) {

        //Init Model----------------------------------------------------------------------------------------------------
        WidgetBorderController widgetBorderController = widgetBorderLoader.getController();
        DataManagerWidgetController dataManagerWidgetController = dataManagerLoader.getController();
        DiagramWidgetController diagramWidgetController = diagramWidgetLoader.getController();
        NeuralNetController neuralNetController = neuralNetWidgetLoader.getController();

        widgetBorderController.initModel(widgetContainerModel);
        dataManagerWidgetController.initModel(dataManagerWidgetModel, widgetContainerModel);
        diagramWidgetController.initModel(diagramWidgetModel, widgetContainerModel);
        neuralNetController.initModel(neuralNetWidgetModel, widgetContainerModel);
    }
}
