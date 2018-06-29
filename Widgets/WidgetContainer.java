package Neuroshop.Widgets;

import Neuroshop.Models.ANNModel;
import Neuroshop.Models.WidgetContainerModel;
import Neuroshop.Widgets.DataManagerWidget.DataManagerWidgetController;
import Neuroshop.Widgets.DiagramWidget.DiagramWidgetController;
import Neuroshop.Widgets.NeuralNetWidget.NeuralNetController;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class WidgetContainer {

    private FXMLLoader dataManagerLoader, diagramWidgetLoader, neuralNetWidgetLoader;
    private StackPane dataManagerWidgetRoot, diagramWidgetRoot, neuralNetWidgetRoot;
    private StackPane dataManagerPrevRoot, diagramPrevRoot, neuralNetPrevRoot;

    public WidgetContainer(WidgetContainerModel widgetContainerModel, ANNModel annModel) throws IOException {

        dataManagerLoader = new FXMLLoader(getClass().getResource("DataManagerWidget/DataManagerWidgetView.fxml"));
        dataManagerWidgetRoot = dataManagerLoader.load();
        dataManagerWidgetRoot.setId("Data Manager");

        diagramWidgetLoader = new FXMLLoader(getClass().getResource("DiagramWidget/DiagramWidgetView.fxml"));
        diagramWidgetRoot = diagramWidgetLoader.load();
        diagramWidgetRoot.getStylesheets().add("Neuroshop/Widgets/DataManagerWidget/DataManagerWidgetStyle.css");
        diagramWidgetRoot.setId("Diagram");

        neuralNetWidgetLoader = new FXMLLoader(getClass().getResource("NeuralNetWidget/NeuralNetView.fxml"));
        neuralNetWidgetRoot = neuralNetWidgetLoader.load();
        neuralNetWidgetRoot.setId("Neural Net");

        dataManagerPrevRoot = new PreviewWidget("Data Manager", new Image("Neuroshop/Ressources/Assets/thumbKommtNoch.png"), widgetContainerModel);
        diagramPrevRoot = new PreviewWidget("Diagram", new Image("Neuroshop/Ressources/Assets/resultDiagramThumb.png"), widgetContainerModel);
        neuralNetPrevRoot = new PreviewWidget("Neural Net", new Image("Neuroshop/Ressources/Assets/netThumb.png"), widgetContainerModel);

        StackPane[][] widgets = new StackPane[][] {
                { dataManagerPrevRoot, dataManagerWidgetRoot },
                { diagramPrevRoot, diagramWidgetRoot },
                { neuralNetPrevRoot, neuralNetWidgetRoot }
        };

        widgetContainerModel.initWidgets(widgets);

        //Init Model----------------------------------------------------------------------------------------------------
        DataManagerWidgetController dataManagerWidgetController = dataManagerLoader.getController();
        DiagramWidgetController diagramWidgetController = diagramWidgetLoader.getController();
        NeuralNetController neuralNetController = neuralNetWidgetLoader.getController();

        dataManagerWidgetController.initModel(annModel, widgetContainerModel);
        diagramWidgetController.initModel(annModel, widgetContainerModel);
        neuralNetController.initModel(annModel, widgetContainerModel);

        widgetContainerModel.addObserver(dataManagerWidgetController);
        widgetContainerModel.addObserver(diagramWidgetController);
        widgetContainerModel.addObserver(neuralNetController);

        annModel.addObserver(dataManagerWidgetController);
        annModel.addObserver(diagramWidgetController);
        annModel.addObserver(neuralNetController);

        //Show DataManager----------------------------------------------------------------------------------------------
        widgetContainerModel.addWidgetToWhiteboard("Data Manager", true);
        widgetContainerModel.clearBufferedWidget();
        widgetContainerModel.changeWidgetStateById("Data Manager", 1);
    }
}
