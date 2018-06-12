package Neuroshop.Controller;

import Neuroshop.Model.ScreenSize;
import Neuroshop.Model.Widget;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;

import static Neuroshop.Start.primaryStage;

public class MainViewController {

    @FXML
    private Region frameBorder_RE;
    @FXML
    private AnchorPane whiteboardPane_AP;
    @FXML
    private ImageView toolMenuButton_IV;
    @FXML
    private ImageView toolMenuButtonOpen_IV;
    @FXML
    private StackPane toolMenuOpener_ST;
    @FXML
    private VBox toolMenuPane_VB;
    @FXML
    private VBox presetsPane_VB;
    @FXML
    private ImageView TESTOBJECT;
    private ArrayList<Widget> widgetList = new ArrayList<>();
    public static boolean toolMenuIsOpen = false;

    //Für Mouse Events
    private double windowCursorPosX, windowCursorPosY;
    private double sceneOnWindowPosX, sceneOnWindowPosY;
    private double sceneCursorPosX, sceneCursorPosY;
    private double nodeTranslatedX, nodeTranslatedY;
    private double sceneWidth, sceneHeight;

    public MainViewController() {
        primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWN, window -> initialize());
    }

    private void initialize() {

        draggablePrimaryStage(frameBorder_RE);
        fullscreenOnDoubleclick(frameBorder_RE);
        draggableWhiteboardObject(TESTOBJECT);

        draggablePrimaryStage(frameBorder_RE);
        fullscreenOnDoubleclick(frameBorder_RE);
        draggableWhiteboardObject(TESTOBJECT);

        whiteboardPane_AP.setPrefHeight(ScreenSize.screenHeight-30);

        primaryStage.widthProperty().addListener(observable -> {
            toolMenuOpener_ST.setLayoutY(ScreenSize.screenHeight/2-30);
            }
        );
        widgetList.add(new Widget("network", new Image("Neuroshop/Resources/networkThumb.png"))); //TODO Später anders hinzufügen
        widgetList.add(new Widget("result diagram", new Image("Neuroshop/Resources/resultDiagramThumb.png")));
    }

    private void toggleToolMenu() {
        if (!toolMenuIsOpen) {
            Timeline timelineAnimation = new Timeline();
            timelineAnimation.getKeyFrames().addAll(
                    new KeyFrame(new Duration(250), new KeyValue(toolMenuButton_IV.opacityProperty(), 0)),
                    new KeyFrame(new Duration(500), new KeyValue(toolMenuOpener_ST.layoutXProperty(), 300, Interpolator.EASE_BOTH), new KeyValue(toolMenuPane_VB.prefWidthProperty(), 300, Interpolator.EASE_BOTH), new KeyValue(toolMenuButtonOpen_IV.opacityProperty(), 1))
            );
            timelineAnimation.play();
            timelineAnimation.setOnFinished(event -> {
                for (Widget aWidgetList : widgetList) {
                    StackPane sp = new StackPane();
                    sp.setAlignment(Pos.CENTER);
                    draggableToolMenuItem(sp);
                    sp.getChildren().add(aWidgetList.getThumbnail());
                    sp.getChildren().add(aWidgetList.getRectangleBorder());
                    sp.getChildren().add(aWidgetList.getRectangleColor());
                    sp.getChildren().add(aWidgetList.getLabel());
                    toolMenuPane_VB.getChildren().add(sp);
                }
            });
            toolMenuIsOpen = true;
        }
        else {
            Timeline timelineAnimation = new Timeline();
            timelineAnimation.getKeyFrames().addAll(
                    new KeyFrame(new Duration(250), new KeyValue(toolMenuButtonOpen_IV.opacityProperty(), 0)),
                    new KeyFrame(new Duration(500), new KeyValue(toolMenuOpener_ST.layoutXProperty(), 0, Interpolator.EASE_BOTH), new KeyValue(toolMenuPane_VB.prefWidthProperty(), 0, Interpolator.EASE_BOTH), new KeyValue(toolMenuButton_IV.opacityProperty(), 1))
            );
            timelineAnimation.play();

            toolMenuPane_VB.getChildren().clear();
            toolMenuIsOpen = false;
        }
    }

    @FXML
    private void minimize() {
        primaryStage.setIconified(true);
    }

    @FXML
    private void minMax() {
        ScreenSize.toggleFullScreen();
    }

    @FXML
    private void exit() {
        System.exit(0);
    }
    @FXML
    private void toolsMenuButtonMouseEntered() {
        toolMenuButton_IV.setImage(new Image("Neuroshop/Resources/toolsMenuButtonHover.png"));
        toolMenuButtonOpen_IV.setImage(new Image("Neuroshop/Resources/toolsMenuButtonHover.png"));
    }
    @FXML
    private void toolsMenuButtonMouseExited() {
        toolMenuButton_IV.setImage(new Image("Neuroshop/Resources/toolsMenuButton.png"));
        toolMenuButtonOpen_IV.setImage(new Image("Neuroshop/Resources/toolsMenuButton.png"));
    }
    @FXML
    private void toolsMenuButtonMouseAction() { //TODO Button-Hitbox nicht bis zum Rand (generell größer)
        toggleToolMenu();
    }
    @FXML
    private void presetsPaneEntered() {
        presetsPane_VB.setStyle("-fx-background-color: #1f1f1f");
    }
    @FXML
    private void presetsPaneExited() {
        presetsPane_VB.setStyle("-fx-background-color: TRANSPARENT");
    }

    public void draggableWhiteboardObject(Node node) {

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

    public void draggableToolMenuItem(Node node) {

        EventHandler<MouseEvent> onMousePressed =
                event -> {
                    sceneCursorPosX = event.getSceneX();
                    sceneCursorPosY = event.getSceneY();
                    nodeTranslatedX = node.getTranslateX();
                    nodeTranslatedY = node.getTranslateY();
                    sceneWidth = node.getScene().getWidth();
                    sceneHeight = node.getScene().getHeight()-30; //-30 weil die obere Leiste 30 Pixel groß ist
                };

        EventHandler<MouseEvent> onMouseDragged = //TODO duplicated code
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

                    if (MainViewController.toolMenuIsOpen & MouseInfo.getPointerInfo().getLocation().x > 300) {
                        //sp.getChildren().remove(2, 3);
                    }
                };

        EventHandler<MouseEvent> onMouseReleased =
                event -> {
                    if (MainViewController.toolMenuIsOpen & MouseInfo.getPointerInfo().getLocation().x < 300) {
                        node.setTranslateX(0);
                        node.setTranslateY(0);
                    }
                    else {
                        System.out.println(node.getBoundsInParent());
                    }
                };

        node.setOnMousePressed(onMousePressed);
        node.setOnMouseDragged(onMouseDragged);
        node.setOnMouseReleased(onMouseReleased);
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
