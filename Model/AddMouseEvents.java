package Neuroshop.Model;

import Neuroshop.Controller.MainViewController;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.awt.*;
import static Neuroshop.Start.primaryStage;

public class AddMouseEvents { //TODO Vielleicht komplett in den Controller

    private double windowCursorPosX, windowCursorPosY;
    private double sceneOnWindowPosX, sceneOnWindowPosY;
    private double sceneCursorPosX, sceneCursorPosY;
    private double nodeTranslatedX, nodeTranslatedY;
    private double sceneWidth, sceneHeight;
    private RemoveWidget removeWidget;

    public AddMouseEvents() {
        removeWidget = new RemoveWidget();
    }

    public void draggableWhiteboardOject(Node node) {

        EventHandler<MouseEvent> onMousePressed =
                event -> {
                    sceneCursorPosX = event.getSceneX();
                    sceneCursorPosY = event.getSceneY();
                    nodeTranslatedX = node.getTranslateX();
                    nodeTranslatedY = node.getTranslateY();
                    sceneWidth = node.getScene().getWidth();
                    sceneHeight = node.getScene().getHeight()-30; //-30 weil die obere Leiste 30 Pixel groß ist
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
                    //Behebt einen glitch, bei dem das Objekt durch schnelles bewegen durch die Wand gezogen werden kann, indem es genau so weit zurück transliert wird, wie es durch geglitcht ist.
                    if (node.getBoundsInParent().getMinX() < 0) node.setTranslateX(newTranslateX-node.getBoundsInParent().getMinX());
                    else if (node.getBoundsInParent().getMaxX() > sceneWidth) node.setTranslateX(newTranslateX-(node.getBoundsInParent().getMaxX()-sceneWidth));
                    if (node.getBoundsInParent().getMinY() < 0) node.setTranslateY(newTranslateY-node.getBoundsInParent().getMinY());
                    else if (node.getBoundsInParent().getMaxY() > sceneHeight) node.setTranslateY(newTranslateY-(node.getBoundsInParent().getMaxY()-sceneHeight));
                };

        node.setOnMousePressed(onMousePressed);
        node.setOnMouseDragged(onMouseDragged);
    }

    public void draggablePrimaryStage(Node node) {

        EventHandler<MouseEvent> onMousePressed =
                event -> {
                    windowCursorPosX = MouseInfo.getPointerInfo().getLocation().x;
                    windowCursorPosY = MouseInfo.getPointerInfo().getLocation().y;
                    sceneOnWindowPosX = primaryStage.getX();
                    sceneOnWindowPosY = primaryStage.getY();
                };

        EventHandler<MouseEvent> onMouseDragged =
                event -> {
                    double offsetX = MouseInfo.getPointerInfo().getLocation().x - windowCursorPosX;
                    double offsetY = MouseInfo.getPointerInfo().getLocation().y - windowCursorPosY;
                    double newPosX = sceneOnWindowPosX + offsetX;
                    double newPosY = sceneOnWindowPosY + offsetY;

                    if (ScreenSize.isFullscreen) {
                        newPosX = MouseInfo.getPointerInfo().getLocation().x; //TODO Drag and Drop Vollbild zu nicht Vollbild Fehler: X Position muss relativ zu der Cursorposition errechnet werden
                        ScreenSize.toggleFullScreen(); //Wenn das Fenster im Vollbildmodus gedraggt wird, wird es verkleinert
                    }

                    primaryStage.setX(newPosX);
                    primaryStage.setY(newPosY);
                };
        EventHandler<MouseEvent> onMouseReleased =
                event -> {
                    if (MouseInfo.getPointerInfo().getLocation().y == 0) ScreenSize.toggleFullScreen(); //Wenn das Fenster oben losgelassen wird, wird es in den Vollbildmodus gesetzt
                    else if (primaryStage.getY() < 0) primaryStage.setY(0); //Wenn das Fenster höher als 0 losgelassen wird, wird die Höhe auf 0 gesetzt
                    else if (primaryStage.getY() + 30 > ScreenSize.screenHeight - 40) primaryStage.setY(ScreenSize.screenHeight - 70); //Wenn das Fenster in der Taskbar losgelassen wird, wird es drüber gesetzt
                };

        node.setOnMousePressed(onMousePressed);
        node.setOnMouseDragged(onMouseDragged);
        node.setOnMouseReleased(onMouseReleased);
    }

    public void draggableToolMenuItem(StackPane sp) {

        EventHandler<MouseEvent> onMousePressed =
                event -> {
                    sceneCursorPosX = event.getSceneX();
                    sceneCursorPosY = event.getSceneY();
                    nodeTranslatedX = sp.getTranslateX();
                    nodeTranslatedY = sp.getTranslateY();
                    sceneWidth = sp.getScene().getWidth();
                    sceneHeight = sp.getScene().getHeight()-30; //-30 weil die obere Leiste 30 Pixel groß ist
                };

        EventHandler<MouseEvent> onMouseDragged = //TODO duplicated code
                event -> {
                    double offsetX = event.getSceneX() - sceneCursorPosX;
                    double offsetY = event.getSceneY() - sceneCursorPosY;
                    double newTranslateX = nodeTranslatedX + offsetX;
                    double newTranslateY = nodeTranslatedY + offsetY;

                    //Collider, der das Objekt stoppt falls es an eine Wand stößt
                    if ((sp.getBoundsInParent().getMinX() > 0 || newTranslateX > sp.getTranslateX()) & (sp.getBoundsInParent().getMaxX() < sceneWidth || newTranslateX < sp.getTranslateX())) sp.setTranslateX(newTranslateX);
                    if ((sp.getBoundsInParent().getMinY() > 0 || newTranslateY > sp.getTranslateY()) & (sp.getBoundsInParent().getMaxY() < sceneHeight || newTranslateY < sp.getTranslateY())) sp.setTranslateY(newTranslateY);
                    //Behebt einen glitch, bei dem das Objekt durch schnelles bewegen durch die Wand gezogen werden kann, indem es genau so weit zurück transliert wird, wie es durch geglitcht ist.
                    if (sp.getBoundsInParent().getMinX() < 0) sp.setTranslateX(newTranslateX-sp.getBoundsInParent().getMinX());
                    else if (sp.getBoundsInParent().getMaxX() > sceneWidth) sp.setTranslateX(newTranslateX-(sp.getBoundsInParent().getMaxX()-sceneWidth));
                    if (sp.getBoundsInParent().getMinY() < 0) sp.setTranslateY(newTranslateY-sp.getBoundsInParent().getMinY());
                    else if (sp.getBoundsInParent().getMaxY() > sceneHeight) sp.setTranslateY(newTranslateY-(sp.getBoundsInParent().getMaxY()-sceneHeight));

                    if (MainViewController.toolMenuIsOpen & MouseInfo.getPointerInfo().getLocation().x > 300) {
                        removeWidget.removeWidgetFromVBox(sp);
                    }
                };

        EventHandler<MouseEvent> onMouseReleased =
                event -> {
                    if (MainViewController.toolMenuIsOpen & MouseInfo.getPointerInfo().getLocation().x < 300) {
                        sp.setTranslateX(0);
                        sp.setTranslateY(0);
                    }
                    else {
                        System.out.println(sp.getBoundsInParent());
                    }
                };

        sp.setOnMousePressed(onMousePressed);
        sp.setOnMouseDragged(onMouseDragged);
        sp.setOnMouseReleased(onMouseReleased);
}

    public void fullscreenOnDoubleclick(Node node) {

        EventHandler<MouseEvent> onMouseDoubleClicked =
                event -> {

                    if(event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY){
                        ScreenSize.toggleFullScreen();
                    }

                };
        node.setOnMouseClicked(onMouseDoubleClicked);
    }
}
