package Neuroshop.Gui.Widgets;

import Neuroshop.Models.ANNModel;
import Neuroshop.Models.Presets.LastOpenedFiles;
import Neuroshop.Models.WidgetContainerModel;
import Neuroshop.Gui.Widgets.DataManagerWidget.DataManagerWidgetController;
import Neuroshop.Gui.Widgets.DiagramWidget.DiagramWidgetController;
import Neuroshop.Gui.Widgets.NeuralNetWidget.NeuralNetController;
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
        dataManagerWidgetRoot.getStylesheets().add("Neuroshop/Gui/Widgets/DataManagerWidget/DataManagerWidgetStyle.css");
        dataManagerWidgetRoot.setId("Data Manager");

        diagramWidgetLoader = new FXMLLoader(getClass().getResource("DiagramWidget/DiagramWidgetView.fxml"));
        diagramWidgetRoot = diagramWidgetLoader.load();
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
        LastOpenedFiles lastOpenedFiles = new LastOpenedFiles();

        DataManagerWidgetController dataManagerWidgetController = dataManagerLoader.getController();
        DiagramWidgetController diagramWidgetController = diagramWidgetLoader.getController();
        NeuralNetController neuralNetController = neuralNetWidgetLoader.getController();

        dataManagerWidgetController.initModel(annModel, widgetContainerModel, lastOpenedFiles);
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
