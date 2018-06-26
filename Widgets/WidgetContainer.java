package Neuroshop.Widgets;

import Neuroshop.Models.WidgetContainerModel;
import Neuroshop.Models.WidgetModels.DataManagerWidgetModel;
import Neuroshop.Models.WidgetModels.DiagramWidgetModel;
import Neuroshop.Models.WidgetModels.NeuralNetWidgetModel;
import Neuroshop.Widgets.DataManagerWidget.DataManagerWidgetController;
import Neuroshop.Widgets.DiagramWidget.DiagramWidgetController;
import Neuroshop.Widgets.NeuralNetWidget.NeuralNetController;
import Neuroshop.Widgets.WidgetBorder.WidgetBorderController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class WidgetContainer {

    private FXMLLoader widgetBorderLoader;
    private FXMLLoader dataManagerLoader;
    private FXMLLoader diagramWidgetLoader;
    private FXMLLoader neuralNetWidgetLoader;

    private AnchorPane widgetBorderRoot;
    private AnchorPane dataManagerWidgetRoot;
    private AnchorPane diagramWidgetRoot;
    private AnchorPane neuralNetWidgetRoot;

    public WidgetContainer() throws IOException {
        widgetBorderLoader = new FXMLLoader(getClass().getResource("WidgetBorder/WidgetBorderView.fxml"));
        widgetBorderRoot = widgetBorderLoader.load();

        dataManagerLoader = new FXMLLoader(getClass().getResource("DataManagerWidget/DataManagerWidgetView.fxml"));
        dataManagerWidgetRoot = dataManagerLoader.load();

        diagramWidgetLoader = new FXMLLoader(getClass().getResource("DiagramWidget/DiagramWidgetView.fxml"));
        dataManagerWidgetRoot = diagramWidgetLoader.load();

        neuralNetWidgetLoader = new FXMLLoader(getClass().getResource("NeuralNetWidget/NeuralNetView.fxml"));
        dataManagerWidgetRoot = neuralNetWidgetLoader.load();
    }

        public void initModel(WidgetContainerModel widgetContainerModel, DataManagerWidgetModel dataManagerWidgetModel, DiagramWidgetModel diagramWidgetModel, NeuralNetWidgetModel neuralNetWidgetModel) {

        //Init Model----------------------------------------------------------------------------------------------------
        WidgetBorderController widgetBorderController = widgetBorderLoader.getController();
        DataManagerWidgetController dataManagerWidgetController = dataManagerLoader.getController();
        DiagramWidgetController diagramWidgetController = diagramWidgetLoader.getController();
        NeuralNetController neuralNetController = neuralNetWidgetLoader.getController();

        widgetBorderController.initModel(widgetContainerModel);
        dataManagerWidgetController.initModel(dataManagerWidgetModel, widgetContainerModel);
        diagramWidgetController.initModel(diagramWidgetModel, widgetContainerModel);
        neuralNetController.initModel(neuralNetWidgetModel, widgetContainerModel);
    }
}
