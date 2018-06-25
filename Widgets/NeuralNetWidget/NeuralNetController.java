package Neuroshop.Widgets.NeuralNetWidget;

import Neuroshop.Model.WidgetContainerModel;
import Neuroshop.Model.WidgetModels.NeuralNetWidgetModel;

public class NeuralNetController {

    private NeuralNetWidgetModel neuralNetWidgetModel;
    private WidgetContainerModel widgetContainerModel;

    public void initModel(NeuralNetWidgetModel neuralNetWidgetModel,WidgetContainerModel widgetContainerModel) {
        this.neuralNetWidgetModel = neuralNetWidgetModel;
        this.widgetContainerModel = widgetContainerModel;
    }
}
