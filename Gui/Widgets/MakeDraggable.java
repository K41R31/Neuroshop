package Neuroshop.Gui.Widgets;

import Neuroshop.Models.WidgetContainerModel;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class MakeDraggable {

    private double sceneCursorPosX, sceneCursorPosY;
    private double nodeTranslatedX, nodeTranslatedY;
    private double sceneWidth, sceneHeight;

    public MakeDraggable(WidgetContainerModel widgetContainerModel, Node node) {

        EventHandler<MouseEvent> onMousePressed =
                event -> {
                    sceneCursorPosX = event.getSceneX();
                    sceneCursorPosY = event.getSceneY();
                    nodeTranslatedX = node.getTranslateX();
                    nodeTranslatedY = node.getTranslateY();
                    sceneWidth = node.getScene().getWidth();
                    sceneHeight = node.getScene().getHeight()-30; //-30 weil die obere Leiste 30 Pixel groß ist

                    node.setOpacity(0.5);
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

                    if (widgetContainerModel.menusAreActive()) {
                        if (!widgetContainerModel.getWidgetMenuIsOpen() & node.getBoundsInParent().getMinX() < 20) {
                            widgetContainerModel.toggleWidgetMenu();
                        } else if (widgetContainerModel.getWidgetMenuIsOpen() & node.getBoundsInParent().getMinX() > 150) {
                            widgetContainerModel.toggleWidgetMenu();
                        }
                    }
                };

        EventHandler<MouseEvent> onMouseReleased =
                event -> {
                    if (widgetContainerModel.menusAreActive() & widgetContainerModel.getWidgetMenuIsOpen() & node.getBoundsInParent().getMinX() < 300) {
                        widgetContainerModel.toggleWidgetMenu();
                        widgetContainerModel.changeWidgetStateById(node.getId(), 0);
                        widgetContainerModel.removeWidgetFromWhiteboard(node.getId());
                        node.setTranslateX(0);
                        node.setTranslateY(0);
                    }
                    node.setOpacity(1);
                };

        node.setOnMousePressed(onMousePressed);
        node.setOnMouseDragged(onMouseDragged);
        node.setOnMouseReleased(onMouseReleased);
    }
}
