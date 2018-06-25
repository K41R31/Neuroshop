package Neuroshop.Widgets.WidgetBorder;

import Neuroshop.Model.WidgetModels.DataManagerWidgetModel;
import Neuroshop.Model.WidgetModels.DiagramWidgetModel;
import Neuroshop.Model.WidgetModels.NeuralNetWidgetModel;

public class WidgetBorderController {

    private DataManagerWidgetModel dataManagerWidgetModel;
    private DiagramWidgetModel diagramWidgetModel;
    private NeuralNetWidgetModel neuralNetWidgetModel;

    public void initModel(DataManagerWidgetModel dataManagerWidgetModel, DiagramWidgetModel diagramWidgetModel, NeuralNetWidgetModel neuralNetWidgetModel) {
        this.dataManagerWidgetModel = dataManagerWidgetModel;
        this.diagramWidgetModel = diagramWidgetModel;
        this.neuralNetWidgetModel = neuralNetWidgetModel;
    }
}
