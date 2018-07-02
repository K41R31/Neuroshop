package Neuroshop.Gui.Widgets.DiagramWidget;

import Neuroshop.Gui.Widgets.MakeDraggable;
import Neuroshop.Models.ANNModel;
import Neuroshop.Models.WidgetContainerModel;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

import java.util.Observable;
import java.util.Observer;

public class DiagramWidgetController implements Observer {

    private ANNModel annModel;
    private WidgetContainerModel widgetContainerModel;
    @FXML
    private StackPane rootPane;

    @FXML
    private void initialize() {
    }

    @Override
    public void update(Observable o, Object arg) {
    }

    public void initModel(ANNModel annModel, WidgetContainerModel widgetContainerModel) {
        this.annModel = annModel;
        this.widgetContainerModel = widgetContainerModel;
        new MakeDraggable(widgetContainerModel, rootPane);
    }


}
