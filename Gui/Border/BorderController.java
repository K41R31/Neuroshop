package Neuroshop.Gui.Border;

import Neuroshop.Models.OptionsModel;
import Neuroshop.ScreenSize;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import static Neuroshop.Main.primaryStage;

public class BorderController implements Observer {

    @FXML
    private AnchorPane frameBorderRootPane;
    private double windowCursorPosX, windowCursorPosY;
    private double sceneOnWindowPosX, sceneOnWindowPosY;
    private boolean mousePressedInBorder = false;

    private OptionsModel optionsModel;

    @FXML
    private void initialize() {
        draggablePrimaryStage();
    }

    @Override
    public void update(Observable o, Object arg) {
    }

    private void draggablePrimaryStage() { //TODO resize Git https://github.com/goxr3plus/FX-BorderlessScene/blob/master/src/main/java/com/goxr3plus/fxborderlessscene/borderless/BorderlessController.java

        EventHandler<MouseEvent> onMousePressed =
                event -> {
                    if (MouseInfo.getPointerInfo().getLocation().x < ScreenSize.width - 180) {
                        mousePressedInBorder = true;
                        windowCursorPosX = MouseInfo.getPointerInfo().getLocation().x;
                        windowCursorPosY = MouseInfo.getPointerInfo().getLocation().y;
                        sceneOnWindowPosX = primaryStage.getX();
                        sceneOnWindowPosY = primaryStage.getY();
                    }
                };

        EventHandler<MouseEvent> onMouseDragged = //TODO old screensize beim pressen und nicht beim letzten dragg
                event -> {
                    if (mousePressedInBorder) {
                        double offsetX = MouseInfo.getPointerInfo().getLocation().x - windowCursorPosX;
                        double offsetY = MouseInfo.getPointerInfo().getLocation().y - windowCursorPosY;
                        double newPosX = sceneOnWindowPosX + offsetX;
                        double newPosY = sceneOnWindowPosY + offsetY;
                        if (ScreenSize.isFullscreen) {
                            ScreenSize.toggleFullScreen(true); //Wenn das Fenster im Vollbildmodus gedraggt wird, wird es verkleinert
                            sceneOnWindowPosX = primaryStage.getX();
                        } else {
                            primaryStage.setX(newPosX);
                            primaryStage.setY(newPosY);
                        }
                    }
                };

        EventHandler<MouseEvent> onMouseReleased =
                event -> {
                    if (mousePressedInBorder) {
                        mousePressedInBorder = false;
                        if (MouseInfo.getPointerInfo().getLocation().y == 0) ScreenSize.toggleFullScreen(false); //Wenn das Fenster oben losgelassen wird, wird es in den Vollbildmodus gesetzt
                        else if (primaryStage.getY() < 0) primaryStage.setY(0); //Wenn das Fenster höher als 0 losgelassen wird, wird die Höhe auf 0 gesetzt
                        else if (primaryStage.getY() + 30 > ScreenSize.height - 40) primaryStage.setY(ScreenSize.height - 70); //Wenn das Fenster in der Taskbar losgelassen wird, wird es drüber gesetzt
                    }
                };

        EventHandler<MouseEvent> onMouseDoubleClicked =
                event -> {

                    if(event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY){
                        ScreenSize.toggleFullScreen(false);
                    }

                };

        frameBorderRootPane.setOnMousePressed(onMousePressed);
        frameBorderRootPane.setOnMouseDragged(onMouseDragged);
        frameBorderRootPane.setOnMouseReleased(onMouseReleased);
        frameBorderRootPane.setOnMouseClicked(onMouseDoubleClicked);
    }

    @FXML
    private void toggleOptions() {
        optionsModel.toggleOptions();
    }
    @FXML
    private void minimize() {
        primaryStage.setIconified(true); //setIconfield(false)
    }
    @FXML
    private void minMax() {
        ScreenSize.toggleFullScreen(false);
    }
    @FXML
    private void exit() {
        System.exit(0);
    }

    public void initModel(OptionsModel optionsModel) {
        this.optionsModel = optionsModel;
    }
}
