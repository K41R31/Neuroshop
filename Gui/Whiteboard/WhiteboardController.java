package Neuroshop.Gui.Whiteboard;

import Neuroshop.Models.WidgetContainerModel;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class WhiteboardController implements Observer {

    @FXML
    private AnchorPane whiteboardPane;
    @FXML
    private AnchorPane draggPane;
    private WidgetContainerModel widgetContainerModel;

    private void addWidgetToWhiteboard(StackPane widget) {
        whiteboardPane.getChildren().add(widget);
        widget.setLayoutX(MouseInfo.getPointerInfo().getLocation().x - 150); //TODO irgendwie Breite der Pane bekommen und Hälfte abzeiehen
        widget.setLayoutY(MouseInfo.getPointerInfo().getLocation().y - 150);
        widget.toBack();
    }

    private void setDraggPane(StackPane draggPreview) {
        draggPane.getChildren().add(draggPreview);
    }

    private void removeDraggPane() {
        draggPane.getChildren().clear();
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((String)arg) {
            case "draggPreview":
                setDraggPane(widgetContainerModel.getBufferedWidget());
                draggPane.toFront();
                break;
            case "clearBufferedWidget":
                removeDraggPane();
                draggPane.toBack();
                break;
            case "addWidgetToWhiteboard":
                removeDraggPane();
                draggPane.toBack();
                addWidgetToWhiteboard(widgetContainerModel.getBufferedWidget());
        }
    }

    public void initModel(WidgetContainerModel widgetContainerModel) {
        this.widgetContainerModel = widgetContainerModel;
    }
}
