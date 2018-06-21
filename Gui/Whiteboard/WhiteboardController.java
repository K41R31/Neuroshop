package Neuroshop.Gui.Whiteboard;

import Neuroshop.Model.WidgetsModel;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;


public class WhiteboardController implements Observer {

    @FXML
    private AnchorPane whiteboardPane;
    private WidgetsModel model;

    @FXML
    public void initialize() {
    }

    @Override public void update(Observable o, Object arg ) {
        switch ((String)arg) {
            case "setDraggPreview": whiteboardPane.getChildren().add(model.getDraggPreview().getPreviewPane());
                break;
            case "removeDraggPreview": whiteboardPane.getChildren().remove(model.getDraggPreview().getPreviewPane());
                break;
            case "setWhiteboardWidget":
                VBox vBox = model.getWhiteboardWidgets().get(model.getWhiteboardWidgets().size()-1);
                whiteboardPane.getChildren().add(vBox);
                vBox.setLayoutX(MouseInfo.getPointerInfo().getLocation().x - 120);
                vBox.setLayoutY(MouseInfo.getPointerInfo().getLocation().y - 180);
        }
    }

    public void initModel(WidgetsModel model) {
        this.model = model;
    }
}
