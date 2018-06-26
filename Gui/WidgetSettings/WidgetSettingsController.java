package Neuroshop.Gui.WidgetSettings;

import Neuroshop.Models.WidgetModels.DataManagerWidgetModel;
import Neuroshop.Models.WidgetModels.DiagramWidgetModel;
import Neuroshop.Models.WidgetModels.NeuralNetWidgetModel;

import java.util.Observable;
import java.util.Observer;

public class WidgetSettingsController implements Observer {

    private DataManagerWidgetModel dataManagerWidgetModel;
    private DiagramWidgetModel diagramWidgetModel;
    private NeuralNetWidgetModel neuralNetWidgetModel;

    @Override
    public void update(Observable o, Object arg) {
    }

    public void initModel(DataManagerWidgetModel dataManagerWidgetModel, DiagramWidgetModel diagramWidgetModel, NeuralNetWidgetModel neuralNetWidgetModel) {
        this.dataManagerWidgetModel = dataManagerWidgetModel;
        this.diagramWidgetModel = diagramWidgetModel;
        this.neuralNetWidgetModel = neuralNetWidgetModel;
    }
}
