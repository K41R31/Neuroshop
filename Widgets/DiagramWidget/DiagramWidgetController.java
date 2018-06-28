package Neuroshop.Widgets.DiagramWidget;

import Neuroshop.ANN.ANNLearn;
import Neuroshop.ANN.Learn.Backpropagation;
import Neuroshop.Models.ANNModel;
import Neuroshop.Models.WidgetContainerModel;
import Neuroshop.Models.WidgetModels.DiagramWidgetModel;

import java.util.Observable;
import java.util.Observer;

public class DiagramWidgetController implements Observer {

    private DiagramWidgetModel diagramWidgetModel;
    private ANNModel annModel;
    private WidgetContainerModel widgetContainerModel;

    @Override
    public void update(Observable o, Object arg) {
    }

    public void initModel(DiagramWidgetModel diagramWidgetModel, ANNModel annModel, WidgetContainerModel widgetContainerModel) {
        this.diagramWidgetModel = diagramWidgetModel;
        this.annModel = annModel;
        this.widgetContainerModel = widgetContainerModel;
    }


}
