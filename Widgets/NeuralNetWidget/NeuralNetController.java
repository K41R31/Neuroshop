package Neuroshop.Widgets.NeuralNetWidget;

import Neuroshop.Models.WidgetContainerModel;
import Neuroshop.Models.WidgetModels.NeuralNetWidgetModel;

import java.util.Observable;
import java.util.Observer;

public class NeuralNetController implements Observer {

    private NeuralNetWidgetModel neuralNetWidgetModel;
    private WidgetContainerModel widgetContainerModel;

    @Override
    public void update(Observable o, Object arg) {
    }

    public void initModel(NeuralNetWidgetModel neuralNetWidgetModel,WidgetContainerModel widgetContainerModel) {
        this.neuralNetWidgetModel = neuralNetWidgetModel;
        this.widgetContainerModel = widgetContainerModel;
    }
}
