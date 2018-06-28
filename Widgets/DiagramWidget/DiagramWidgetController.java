package Neuroshop.Widgets.DiagramWidget;

import Neuroshop.ANN.ANNModel;
import Neuroshop.Models.WidgetContainerModel;

import java.util.Observable;
import java.util.Observer;

public class DiagramWidgetController implements Observer {

    private ANNModel annModel;
    private WidgetContainerModel widgetContainerModel;

    @Override
    public void update(Observable o, Object arg) {
    }

    public void initModel(ANNModel annModel, WidgetContainerModel widgetContainerModel) {
        this.annModel = annModel;
        this.widgetContainerModel = widgetContainerModel;
    }


}
