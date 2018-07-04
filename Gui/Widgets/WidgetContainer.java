package Neuroshop.Gui.Widgets;

import Neuroshop.Models.ANNModel;
import Neuroshop.Models.Presets.LastOpenedFiles;
import Neuroshop.Models.TutorialModel;
import Neuroshop.Models.WidgetContainerModel;
import Neuroshop.Gui.Widgets.DataManagerWidget.DataManagerWidgetController;
import Neuroshop.Gui.Widgets.DiagramWidget.DiagramWidgetController;
import Neuroshop.Gui.Widgets.NeuralNetWidget.NeuralNetController;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class WidgetContainer implements Observer {

    private WidgetContainerModel widgetContainerModel;

    private FXMLLoader dataManagerLoader, diagramWidgetLoader, neuralNetWidgetLoader;
    private StackPane dataManagerWidgetRoot, diagramWidgetRoot, neuralNetWidgetRoot;
    private StackPane dataManagerPrevRoot, diagramPrevRoot, neuralNetPrevRoot;

    public WidgetContainer(WidgetContainerModel widgetContainerModel, ANNModel annModel, TutorialModel tutorialModel) throws IOException {
        this.widgetContainerModel = widgetContainerModel;

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

        dataManagerPrevRoot = new PreviewWidget("Data Manager", new Image("Neuroshop/Ressources/Assets/dataManagerThumb.png"), this.widgetContainerModel);
        diagramPrevRoot = new PreviewWidget("Diagram", new Image("Neuroshop/Ressources/Assets/resultDiagramThumb.png"), this.widgetContainerModel);
        neuralNetPrevRoot = new PreviewWidget("Neural Net", new Image("Neuroshop/Ressources/Assets/netThumb.png"), this.widgetContainerModel);

        StackPane[][] widgets = new StackPane[][] {
                { dataManagerPrevRoot, dataManagerWidgetRoot },
                { diagramPrevRoot, diagramWidgetRoot },
                { neuralNetPrevRoot, neuralNetWidgetRoot }
        };

        this.widgetContainerModel.initWidgets(widgets);

        //Init Model----------------------------------------------------------------------------------------------------
        LastOpenedFiles lastOpenedFiles = new LastOpenedFiles();

        DataManagerWidgetController dataManagerWidgetController = dataManagerLoader.getController();
        DiagramWidgetController diagramWidgetController = diagramWidgetLoader.getController();
        NeuralNetController neuralNetController = neuralNetWidgetLoader.getController();

        dataManagerWidgetController.initModel(annModel, this.widgetContainerModel, lastOpenedFiles, tutorialModel);
        diagramWidgetController.initModel(annModel, this.widgetContainerModel, tutorialModel);
        neuralNetController.initModel(annModel, this.widgetContainerModel, tutorialModel);

        this.widgetContainerModel.addObserver(dataManagerWidgetController);
        this.widgetContainerModel.addObserver(diagramWidgetController);
        this.widgetContainerModel.addObserver(neuralNetController);

        tutorialModel.addObserver(dataManagerWidgetController);
        tutorialModel.addObserver(diagramWidgetController);
        tutorialModel.addObserver(neuralNetController);

        annModel.addObserver(dataManagerWidgetController);
        annModel.addObserver(diagramWidgetController);
        annModel.addObserver(neuralNetController);

        //Show DataManager----------------------------------------------------------------------------------------------
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((String)arg) {
            case "initDataManager":
                widgetContainerModel.addWidgetToWhiteboard("Data Manager", true);
                widgetContainerModel.clearBufferedWidget();
                widgetContainerModel.changeWidgetStateById("Data Manager", 1);
        }
    }
}
