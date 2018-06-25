package Neuroshop.Widgets;

import Neuroshop.Model.WidgetContainerModel;
import Neuroshop.Model.WidgetModels.DataManagerWidgetModel;
import Neuroshop.Model.WidgetModels.DiagramWidgetModel;
import Neuroshop.Model.WidgetModels.NeuralNetWidgetModel;
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
        widgetBorderLoader = new FXMLLoader(getClass().getResource("Widgets/WidgetBorder/WidgetBorderView.fxml"));
        widgetBorderRoot.getChildren().add(widgetBorderLoader.load());

        dataManagerLoader = new FXMLLoader(getClass().getResource("Widgets/DataManagerWidget/DataManagerWidgetView.fxml"));
        dataManagerWidgetRoot.getChildren().add(dataManagerLoader.load());

        diagramWidgetLoader = new FXMLLoader(getClass().getResource("Widgets/DiagramWidget/DiagramWidgetView.fxml"));
        dataManagerWidgetRoot.getChildren().add(diagramWidgetLoader.load());

        neuralNetWidgetLoader = new FXMLLoader(getClass().getResource("Widgets/NeuralNetWidget/NeuralNetWidgetView.fxml"));
        dataManagerWidgetRoot.getChildren().add(neuralNetWidgetLoader.load());
    }

        public void initModel(WidgetContainerModel widgetContainerModel, DataManagerWidgetModel dataManagerWidgetModel, DiagramWidgetModel diagramWidgetModel, NeuralNetWidgetModel neuralNetWidgetModel) {

        //Init Model----------------------------------------------------------------------------------------------------
        WidgetBorderController widgetBorderController = widgetBorderLoader.getController();
        DataManagerWidgetController dataManagerWidgetController = dataManagerLoader.getController();
        DiagramWidgetController diagramWidgetController = diagramWidgetLoader.getController();
        NeuralNetController neuralNetController = neuralNetWidgetLoader.getController();

        widgetBorderController.initModel(dataManagerWidgetModel, diagramWidgetModel, neuralNetWidgetModel);
        dataManagerWidgetController.initModel(dataManagerWidgetModel, widgetContainerModel);
        diagramWidgetController.initModel(diagramWidgetModel, widgetContainerModel);
        neuralNetController.initModel(neuralNetWidgetModel, widgetContainerModel);
    }
}
