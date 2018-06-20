package Neuroshop.Gui.WidgetMenu;

import Neuroshop.Model.WidgetsModel;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.awt.*;
import java.util.ArrayList;

public class WidgetMenuController { //TODO dragged widget index of und int index an Widgetsloader

    @FXML
    private HBox widgetMenuRootPane;
    @FXML
    private VBox widgetMenuPane;
    @FXML
    private ImageView openerIcon;
    private boolean menuIsOpen;
    private ArrayList<StackPane> previewWidgetList;
    private WidgetsModel model;

    //Für Mouse Events
    private double sceneCursorPosX, sceneCursorPosY;
    private double nodeTranslatedX, nodeTranslatedY;
    private double sceneWidth, sceneHeight;

    @FXML
    private void initialize() {
        previewWidgetList = new ArrayList<>();
        menuIsOpen = false;
        AnchorPane.setTopAnchor(widgetMenuRootPane, (double)0);
        AnchorPane.setLeftAnchor(widgetMenuRootPane, (double)0);
        AnchorPane.setBottomAnchor(widgetMenuRootPane, (double)0);
    }

    @FXML
    private void toggleMenu() { //Wenn das Menu wieder geschlossen wird bevor es die Animation beendet hat, werden die Widgets nicht entfernt
        if (!menuIsOpen) {
            Timeline openMenuAnimation = new Timeline();
            openMenuAnimation.getKeyFrames().addAll(
                    new KeyFrame(new Duration(200), new KeyValue(openerIcon.scaleXProperty(), -1, Interpolator.EASE_BOTH)),
                    new KeyFrame(new Duration(200), new KeyValue(widgetMenuPane.prefWidthProperty(), 300, Interpolator.EASE_BOTH))
            );
            openMenuAnimation.play();
            openMenuAnimation.setOnFinished(event -> {
                this.previewWidgetList = model.getPreviewWidgets();
                for (StackPane processPane: previewWidgetList) { //Pane mit Widgets füllen
                    widgetMenuPane.getChildren().add(processPane);
                    draggablePreview(processPane);
                }
            });
            widgetMenuPane.setVisible(true);
            menuIsOpen = true;
        } else {
            Timeline closeMenuAnimation = new Timeline();
            closeMenuAnimation.getKeyFrames().addAll(
                    new KeyFrame(new Duration(200), new KeyValue(openerIcon.scaleXProperty(), 1, Interpolator.EASE_BOTH)),
                    new KeyFrame(new Duration(200), new KeyValue(widgetMenuPane.prefWidthProperty(), 0, Interpolator.EASE_BOTH))
            );
            closeMenuAnimation.play();
            closeMenuAnimation.setOnFinished(event ->
                    widgetMenuPane.setVisible(false)
            );
            widgetMenuPane.getChildren().clear();
            menuIsOpen = false;
        }
    }

    private void draggablePreview(StackPane sp) {

        EventHandler<MouseEvent> onMousePressed =
                event -> {
                    sceneCursorPosX = event.getSceneX();
                    sceneCursorPosY = event.getSceneY();
                    nodeTranslatedX = sp.getTranslateX();
                    nodeTranslatedY = sp.getTranslateY();
                    sceneWidth = sp.getScene().getWidth();
                    sceneHeight = sp.getScene().getHeight()-30; //-30 weil die obere Leiste 30 Pixel groß ist

                    model.setDraggPreview(sp);
                    widgetMenuPane.getChildren().remove(sp);
                };

        EventHandler<MouseEvent> onMouseDragged =
                event -> {
                    double offsetX = event.getSceneX() - sceneCursorPosX;
                    double offsetY = event.getSceneY() - sceneCursorPosY;
                    double newTranslateX = nodeTranslatedX + offsetX;
                    double newTranslateY = nodeTranslatedY + offsetY;

                    //Collider, der das Objekt stoppt falls es an eine Wand stößt
                    if ((sp.getBoundsInParent().getMinX() > 0 || newTranslateX > sp.getTranslateX()) & (sp.getBoundsInParent().getMaxX() < sceneWidth || newTranslateX < sp.getTranslateX())) sp.setTranslateX(newTranslateX);
                    if ((sp.getBoundsInParent().getMinY() > 0 || newTranslateY > sp.getTranslateY()) & (sp.getBoundsInParent().getMaxY() < sceneHeight || newTranslateY < sp.getTranslateY())) sp.setTranslateY(newTranslateY);
                    //Behebt einen Glitch, bei dem das Objekt durch schnelles bewegen durch die Wand gezogen werden kann, indem es genau so weit zurück transliert wird, wie es durch geglitcht ist.
                    if (sp.getBoundsInParent().getMinX() < 0) sp.setTranslateX(newTranslateX-sp.getBoundsInParent().getMinX());
                    else if (sp.getBoundsInParent().getMaxX() > sceneWidth) sp.setTranslateX(newTranslateX-(sp.getBoundsInParent().getMaxX()-sceneWidth));
                    if (sp.getBoundsInParent().getMinY() < 0) sp.setTranslateY(newTranslateY-sp.getBoundsInParent().getMinY());
                    else if (sp.getBoundsInParent().getMaxY() > sceneHeight) sp.setTranslateY(newTranslateY-(sp.getBoundsInParent().getMaxY()-sceneHeight));

                    if (menuIsOpen & MouseInfo.getPointerInfo().getLocation().x > 300) {
                        toggleMenu();
                    } else if (!menuIsOpen & sp.getBoundsInParent().getMinX() <= 30) {
                        toggleMenu();
                    }
                };

        EventHandler<MouseEvent> onMouseReleased =
                event -> {
                    if (!menuIsOpen & MouseInfo.getPointerInfo().getLocation().x > 300) {
                        model.removeDraggPreview();
                        model.addWidgetToWhiteboard(previewWidgetList.indexOf(sp));
                    } else if (menuIsOpen & MouseInfo.getPointerInfo().getLocation().x < 300) {
                        widgetMenuPane.getChildren().add(sp);
                        model.removeDraggPreview();
                        sp.setTranslateX(0);
                        sp.setTranslateY(0);
                    }
                };

        sp.setOnMousePressed(onMousePressed);
        sp.setOnMouseDragged(onMouseDragged);
        sp.setOnMouseReleased(onMouseReleased);
    }

    public void initModel(WidgetsModel model) {
        this.model = model;
    }
}
