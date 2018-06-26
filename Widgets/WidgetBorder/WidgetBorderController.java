package Neuroshop.Widgets.WidgetBorder;

import Neuroshop.Models.WidgetContainerModel;
import Neuroshop.Models.WidgetModels.DataManagerWidgetModel;
import Neuroshop.Models.WidgetModels.DiagramWidgetModel;
import Neuroshop.Models.WidgetModels.NeuralNetWidgetModel;
import Neuroshop.Widgets.WidgetContainer;

import java.util.Observable;
import java.util.Observer;

public class WidgetBorderController implements Observer {

    private WidgetContainerModel widgetContainerModel;

    @Override
    public void update(Observable o, Object arg) {
    }

    public void initModel(WidgetContainerModel widgetContainerModel) {
        this.widgetContainerModel = widgetContainerModel;
    }
}
