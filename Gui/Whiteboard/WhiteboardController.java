package Neuroshop.Gui.Whiteboard;

import Neuroshop.Models.WidgetContainerModel;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.Observable;
import java.util.Observer;

public class WhiteboardController implements Observer {

    @FXML
    private AnchorPane draggPane;
    private WidgetContainerModel widgetContainerModel;

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
                setDraggPane(widgetContainerModel.getDraggPreview());
                break;
            case "removeDraggPreview":
                removeDraggPane();

        }
    }

    public void initModel(WidgetContainerModel widgetContainerModel) {
        this.widgetContainerModel = widgetContainerModel;
    }
}
