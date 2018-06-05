package Neuroshop.Controller;

import Neuroshop.Model.ScreenSize;
import Neuroshop.Model.AddMouseEvents;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.WindowEvent;

import static Neuroshop.Start.primaryStage;

public class MainViewController {

    @FXML
    private Region frameBorder_RE;
    @FXML
    private Rectangle testmoveObject;
    @FXML
    private StackPane testmoveObject2;
    @FXML
    private StackPane whiteboardPane_SP;

    public MainViewController() {
        primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWN, window -> initialize());
    }

    private void initialize() {
        AddMouseEvents addMouseEvents = new AddMouseEvents();
        addMouseEvents.draggablePrimaryStage(frameBorder_RE);
        addMouseEvents.fullscreenOnDoubleclick(frameBorder_RE);
        addMouseEvents.draggableWhiteboardOject(testmoveObject);
        addMouseEvents.draggableWhiteboardOject(testmoveObject2);

        whiteboardPane_SP.setPrefHeight(ScreenSize.screenHeight-30);
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
}
