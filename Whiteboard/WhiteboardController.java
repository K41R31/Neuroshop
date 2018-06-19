package Neuroshop.Whiteboard;

import Neuroshop.WidgetsModel;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

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
            case "removeDraggPreview": whiteboardPane.getChildren().remove(model.getDraggPreview());
                break;
            case "setDraggPreview": whiteboardPane.getChildren().add(model.getDraggPreview());
                break;
            case "setWhiteboardWidget":
                AnchorPane ap = model.getWhiteboardWidgets().get(model.getWhiteboardWidgets().size()-1);
                whiteboardPane.getChildren().add(ap);
                ap.setLayoutX(MouseInfo.getPointerInfo().getLocation().x - 120);
                ap.setLayoutY(MouseInfo.getPointerInfo().getLocation().y - 180);
        }
    }

    public void initModel(WidgetsModel model) {
        this.model = model;
    }
}
