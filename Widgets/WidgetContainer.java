package Neuroshop.Widgets;

import Neuroshop.Models.WidgetContainerModel;
import Neuroshop.Models.WidgetModels.DataManagerWidgetModel;
import Neuroshop.Models.WidgetModels.DiagramWidgetModel;
import Neuroshop.Models.WidgetModels.NeuralNetWidgetModel;
import Neuroshop.Widgets.DataManagerWidget.DataManagerWidgetController;
import Neuroshop.Widgets.DiagramWidget.DiagramWidgetController;
import Neuroshop.Widgets.NeuralNetWidget.NeuralNetController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.awt.*;
import java.io.IOException;
import java.util.Stack;

public class WidgetContainer {

    private FXMLLoader widgetBorderLoader, dataManagerLoader, diagramWidgetLoader, neuralNetWidgetLoader;
    private StackPane widgetBorderRoot, dataManagerWidgetRoot, diagramWidgetRoot, neuralNetWidgetRoot;
    private StackPane dataManagerPrevRoot, diagramPrevRoot, neuralNetPrevRoot;

    public WidgetContainer() {
    }

    public void intitWidgets() throws IOException {
        dataManagerLoader = new FXMLLoader(getClass().getResource("DataManagerWidget/DataManagerWidgetView.fxml"));
        dataManagerWidgetRoot = dataManagerLoader.load();

        diagramWidgetLoader = new FXMLLoader(getClass().getResource("DiagramWidget/DiagramWidgetView.fxml"));
        diagramWidgetRoot = diagramWidgetLoader.load();

        neuralNetWidgetLoader = new FXMLLoader(getClass().getResource("NeuralNetWidget/NeuralNetView.fxml"));
        neuralNetWidgetRoot = neuralNetWidgetLoader.load();

        StackPane[][] widgets = new StackPane[][]{
                {

                }
        };
        dataManagerPrevRoot = new PreviewWidget("Data Manager", new Image("Neuroshop/Ressources/thumbKommtNoch.png"));
        diagramPrevRoot = new PreviewWidget("Diagram", new Image("Neuroshop/Ressources/resultDiagramThumb.png"));
        neuralNetPrevRoot = new PreviewWidget("Neural Net", new Image("Neuroshop/Ressources/netThumb.png"));
    }

        public void initModel(WidgetContainerModel widgetContainerModel, DataManagerWidgetModel dataManagerWidgetModel, DiagramWidgetModel diagramWidgetModel, NeuralNetWidgetModel neuralNetWidgetModel) {

//      widgetContainerModel.initWidgets();
        //Init Model----------------------------------------------------------------------------------------------------
        DataManagerWidgetController dataManagerWidgetController = dataManagerLoader.getController();
        DiagramWidgetController diagramWidgetController = diagramWidgetLoader.getController();
        NeuralNetController neuralNetController = neuralNetWidgetLoader.getController();

        dataManagerWidgetController.initModel(dataManagerWidgetModel, widgetContainerModel);
        diagramWidgetController.initModel(diagramWidgetModel, widgetContainerModel);
        neuralNetController.initModel(neuralNetWidgetModel, widgetContainerModel);
    }
}
