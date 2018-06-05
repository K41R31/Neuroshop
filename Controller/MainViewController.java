package Neuroshop.Controller;

import Neuroshop.Model.AddMouseEvents;
import Neuroshop.Model.ScreenSize;
import Neuroshop.Model.Widget;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

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
    private AddMouseEvents addMouseEvents;

    public MainViewController() {
        primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWN, window -> initialize());
    }

    private void initialize() {

        addMouseEvents = new AddMouseEvents();
        addMouseEvents.draggablePrimaryStage(frameBorder_RE);
        addMouseEvents.fullscreenOnDoubleclick(frameBorder_RE);
        addMouseEvents.draggableWhiteboardOject(TESTOBJECT);

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
                    addMouseEvents.draggableToolMenuItem(sp);
                    sp.getChildren().add(aWidgetList.getThumbnail());
                    sp.getChildren().add(aWidgetList.getRectangleBorder());
                    sp.getChildren().add(aWidgetList.getRectangleColor());
                    sp.getChildren().add(aWidgetList.getLabel());
                    toolMenuPane_VB.getChildren().add(sp);
                    sp.getChildren().remove(2, 3);
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
}
