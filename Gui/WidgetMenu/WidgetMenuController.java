package Neuroshop.Gui.WidgetMenu;

import Neuroshop.Gui.Widgets.Widget;
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

public class WidgetMenuController { //TODO dragged widget index of und int index an Widgetsloader

    @FXML
    private HBox widgetMenuRootPane;
    @FXML
    private VBox widgetMenuPane;
    @FXML
    private ImageView openerIcon;
    private boolean menuIsOpen;
    private WidgetsModel model;
    private boolean menuIsBusy;

    //Für Mouse Events
    private double sceneCursorPosX, sceneCursorPosY;
    private double nodeTranslatedX, nodeTranslatedY;
    private double sceneWidth, sceneHeight;

    @FXML
    private void initialize() {
        menuIsBusy = false;
        menuIsOpen = false;
        AnchorPane.setTopAnchor(widgetMenuRootPane, (double)0);
        AnchorPane.setLeftAnchor(widgetMenuRootPane, (double)0);
        AnchorPane.setBottomAnchor(widgetMenuRootPane, (double)0);
    }

    @FXML
    private void toggleMenu() {
        if (!menuIsOpen & !menuIsBusy) {
            menuIsBusy = true;
            Timeline openMenuAnimation = new Timeline();
            openMenuAnimation.getKeyFrames().addAll(
                    new KeyFrame(new Duration(200), new KeyValue(openerIcon.scaleXProperty(), -1, Interpolator.EASE_BOTH)),
                    new KeyFrame(new Duration(200), new KeyValue(widgetMenuPane.prefWidthProperty(), 300, Interpolator.EASE_BOTH))
            );
            openMenuAnimation.play();
            openMenuAnimation.setOnFinished(event -> {
                //Alle Widgets von model PrviewWidgetList in previewWidgetList schreiben und Pane mit Previews füllen
                StackPane widgetPane;
                for (Widget widget: model.getPreviewWidgetList()) {
                    widgetPane = widget.getPreviewPane();
                    widgetMenuPane.getChildren().add(widgetPane);
                    draggablePreview(widgetPane);
                }
                menuIsOpen = true;
                menuIsBusy = false;
            });
            widgetMenuPane.setVisible(true);

        } else if (menuIsOpen & !menuIsBusy) {
            menuIsBusy = true;
            Timeline closeMenuAnimation = new Timeline();
            closeMenuAnimation.getKeyFrames().addAll(
                    new KeyFrame(new Duration(200), new KeyValue(openerIcon.scaleXProperty(), 1, Interpolator.EASE_BOTH)),
                    new KeyFrame(new Duration(200), new KeyValue(widgetMenuPane.prefWidthProperty(), 0, Interpolator.EASE_BOTH))
            );
            closeMenuAnimation.play();
            closeMenuAnimation.setOnFinished(event -> {
                widgetMenuPane.setVisible(false);
                menuIsOpen = false;
                menuIsBusy = false;
            });
            model.clearPreviewWidgetList();

            //Fügt alle Widgets die im Menu sind in die model previewList ein
            for (int i = 0; i < widgetMenuPane.getChildren().size(); i++) {
                for (Widget widget: model.getAllWidgetsList()) {
                    if (widgetMenuPane.getChildren().get(i).getId().equals(widget.getName())) { //Name von Widget ist bekannt. Es wird das passende Widget zu gesucht
                        model.addToPreviewList(widget);
                    }
                }
            }
            widgetMenuPane.getChildren().clear();
        }
    }

    private void draggablePreview(StackPane draggedNode) { //TODO Man muss im Moment noch warten bis sich das Menu komplett geöffnet hat

        EventHandler<MouseEvent> onMousePressed =
                event -> {
                    sceneCursorPosX = event.getSceneX();
                    sceneCursorPosY = event.getSceneY();
                    nodeTranslatedX = draggedNode.getTranslateX();
                    nodeTranslatedY = draggedNode.getTranslateY();
                    sceneWidth = draggedNode.getScene().getWidth();
                    sceneHeight = draggedNode.getScene().getHeight()-30; //-30 weil die obere Leiste 30 Pixel groß ist

                    widgetMenuPane.getChildren().remove(draggedNode);

                    model.setDraggPreview(draggedNode.getId());
                };

        EventHandler<MouseEvent> onMouseDragged =
                event -> {
                    double offsetX = event.getSceneX() - sceneCursorPosX;
                    double offsetY = event.getSceneY() - sceneCursorPosY;
                    double newTranslateX = nodeTranslatedX + offsetX;
                    double newTranslateY = nodeTranslatedY + offsetY;

                    //Collider, der das Objekt stoppt falls es an eine Wand stößt
                    if ((draggedNode.getBoundsInParent().getMinX() > 0 || newTranslateX > draggedNode.getTranslateX()) & (draggedNode.getBoundsInParent().getMaxX() < sceneWidth || newTranslateX < draggedNode.getTranslateX())) draggedNode.setTranslateX(newTranslateX);
                    if ((draggedNode.getBoundsInParent().getMinY() > 0 || newTranslateY > draggedNode.getTranslateY()) & (draggedNode.getBoundsInParent().getMaxY() < sceneHeight || newTranslateY < draggedNode.getTranslateY())) draggedNode.setTranslateY(newTranslateY);
                    //Behebt einen Glitch, bei dem das Objekt durch schnelles bewegen durch die Wand gezogen werden kann, indem es genau so weit zurück transliert wird, wie es durch geglitcht ist.
                    if (draggedNode.getBoundsInParent().getMinX() < 0) draggedNode.setTranslateX(newTranslateX-draggedNode.getBoundsInParent().getMinX());
                    else if (draggedNode.getBoundsInParent().getMaxX() > sceneWidth) draggedNode.setTranslateX(newTranslateX-(draggedNode.getBoundsInParent().getMaxX()-sceneWidth));
                    if (draggedNode.getBoundsInParent().getMinY() < 0) draggedNode.setTranslateY(newTranslateY-draggedNode.getBoundsInParent().getMinY());
                    else if (draggedNode.getBoundsInParent().getMaxY() > sceneHeight) draggedNode.setTranslateY(newTranslateY-(draggedNode.getBoundsInParent().getMaxY()-sceneHeight));

                    if (menuIsOpen & MouseInfo.getPointerInfo().getLocation().x > 300) {
                        toggleMenu();
                    } else if (!menuIsOpen & draggedNode.getBoundsInParent().getMinX() <= 30) {
                        toggleMenu();
                    }
                };

        EventHandler<MouseEvent> onMouseReleased =
                event -> {
                    if (MouseInfo.getPointerInfo().getLocation().x > 300) {
                        model.addWidgetToWhiteboard(model.getDraggPreview());
                        model.removeDraggPreview();
                    } else if (menuIsOpen & MouseInfo.getPointerInfo().getLocation().x < 300) {
                        widgetMenuPane.getChildren().add(draggedNode);
                        model.removeDraggPreview();
                        draggedNode.setTranslateX(0);
                        draggedNode.setTranslateY(0);
                    }
                };

        draggedNode.setOnMousePressed(onMousePressed);
        draggedNode.setOnMouseDragged(onMouseDragged);
        draggedNode.setOnMouseReleased(onMouseReleased);
    }

    public void initModel(WidgetsModel model) {
        this.model = model;
    }
}
