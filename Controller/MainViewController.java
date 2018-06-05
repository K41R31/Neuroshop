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
import javafx.scene.shape.SVGPath;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.util.ArrayList;

import static Neuroshop.Start.primaryStage;

public class MainViewController {

    @FXML
    private VBox trashBin_VB;
    @FXML
    private SVGPath trashBinDeckel_SVG;
    @FXML
    private SVGPath trashBin_SVG;
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

        trashBinDeckel_SVG.setContent("M91.754,48.489h-6.361v-3.686c0-2.906-2.065-5.262-4.615-5.262H69.15c-2.548,0-4.615,2.355-4.615,5.262v3.686h-6.29c-3.965,0-8.246,2.784-8.246,7.282v3.436h3.429H75h21.571H100v-3.436C100,51.273,95.72,48.489,91.754,48.489zM67.964,44.804c0-1.045,0.626-1.826,1.186-1.826h11.627c0.561,0,1.187,0.781,1.187,1.826v3.686H75h-7.036V44.804z M75,55.771H53.429c0-2.364,2.633-3.847,4.817-3.847H75h16.754c2.185,0,4.817,1.482,4.817,3.847H75z");
        trashBin_SVG.setContent("M76.548,96.272c0,0.877-0.693,1.589-1.548,1.589l0,0c-0.855,0-1.548-0.712-1.548-1.589V76.928c0-0.877,0.693-1.588,1.548-1.588l0,0c0.855,0,1.548,0.711,1.548,1.588V96.272z"+
                                "M84.167,96.272c0,0.877-0.692,1.589-1.548,1.589l0,0c-0.854,0-1.548-0.712-1.548-1.589V76.928c0-0.877,0.693-1.588,1.548-1.588l0,0c0.855,0,1.548,0.711,1.548,1.588V96.272z"+
                                "M68.978,96.272c0,0.877-0.694,1.589-1.549,1.589l0,0c-0.855,0-1.547-0.712-1.547-1.589V76.928c0-0.877,0.692-1.588,1.547-1.588l0,0c0.854,0,1.549,0.711,1.549,1.588V96.272z"+
                                "M75,62.861H53.429l3.762,43.691c0,0.002,0.001,0.003,0.001,0.004c0.331,3.82,2.79,3.901,4.147,3.901H75h13.66c1.357,0,3.817-0.081,4.147-3.901c0-0.001,0.001-0.002,0.001-0.004l3.763-43.691H75z M89.399,107.255H60.601L57,66.225h36L89.399,107.255z"
                                );
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
    @FXML
    private void scaleTrashBin() {
        trashBin_VB.setScaleX(2);
        trashBin_VB.setScaleY(2);
        trashBin_VB.setTranslateX(30-trashBin_VB.getBoundsInParent().getMinX());
        trashBin_VB.setTranslateY((whiteboardPane_AP.getHeight()-30-trashBin_VB.getBoundsInParent().getMaxY()));
        }
}
