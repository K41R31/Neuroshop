package Neuroshop.Widgets;

import Neuroshop.Models.WidgetContainerModel;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;

public class PreviewWidget extends StackPane {

    private WidgetContainerModel widgetContainerModel;
    private Text label;
    private ImageView thumbnail;
    private Rectangle rectangleBorder;

    //Für Mouse Events
    private double sceneCursorPosX, sceneCursorPosY;
    private double nodeTranslatedX, nodeTranslatedY;
    private double sceneWidth, sceneHeight;

    public PreviewWidget(String name, Image thumbnail, WidgetContainerModel widgetContainerModel) throws IOException {
        this.widgetContainerModel = widgetContainerModel;
        this.thumbnail = new ImageView(thumbnail);
        this.label = new Text(name);
        this.rectangleBorder = new Rectangle(250, 200);
        label.setFont(new Font("Caviar Dreams", 35));
        label.setFill(Color.WHITE);
        label.setOpacity(0);
        label.setDisable(true);
        rectangleBorder.setStrokeWidth(2);
        rectangleBorder.setStroke(Color.BLACK);
        rectangleBorder.setFill(Color.TRANSPARENT);
        rectangleBorder.setFill(Color.web("#222222"));
        rectangleBorder.setOpacity(0);
        rectangleBorder.setOnMouseEntered(event -> {
            rectangleBorder.setOpacity(0.7);
            label.setOpacity(1);
        });
        rectangleBorder.setOnMouseExited(event -> {
            rectangleBorder.setOpacity(0);
            label.setOpacity(0);
        });
        rectangleBorder.setOnMousePressed(event -> {
            rectangleBorder.setOpacity(0);
            label.setOpacity(0);
        });
        this.setId(name);
        this.getChildren().addAll(this.thumbnail, this.rectangleBorder, this.label);
        draggablePreview(this);
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

                    widgetContainerModel.changeWidgetStateById(node.getId(), 1);
                    widgetContainerModel.setBufferedWidget(node);
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

                    if (widgetContainerModel.getWidgetMenuIsOpen() & !widgetContainerModel.getMenuIsBusy() & MouseInfo.getPointerInfo().getLocation().x > 300) {
                        widgetContainerModel.toggleWidgetMenu();
                    } else if (!widgetContainerModel.getWidgetMenuIsOpen() & !widgetContainerModel.getMenuIsBusy() & node.getBoundsInParent().getMinX() <= 30) {
                        widgetContainerModel.toggleWidgetMenu();
                    }
                };

        EventHandler<MouseEvent> onMouseReleased =
                event -> {
                    if (MouseInfo.getPointerInfo().getLocation().x > 300) {
                        widgetContainerModel.changeWidgetStateById(node.getId(), 1);
                        widgetContainerModel.addWidgetToWhiteboard(node.getId(), false);
                        node.setTranslateX(0);
                        node.setTranslateY(0);
                    } else if (widgetContainerModel.getWidgetMenuIsOpen() & MouseInfo.getPointerInfo().getLocation().x < 300) {
                        widgetContainerModel.changeWidgetStateById(node.getId(), 0);
                        widgetContainerModel.addWidgetToMenu();
                        widgetContainerModel.clearBufferedWidget();
                        node.setTranslateX(0);
                        node.setTranslateY(0);
                    }
                };

        node.setOnMousePressed(onMousePressed);
        node.setOnMouseDragged(onMouseDragged);
        node.setOnMouseReleased(onMouseReleased);
    }
}
