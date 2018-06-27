package Neuroshop;

import Neuroshop.Gui.Border.BorderController;
import Neuroshop.Gui.Options.OptionsController;
import Neuroshop.Gui.Whiteboard.WhiteboardController;
import Neuroshop.Gui.WidgetMenu.WidgetMenuController;
import Neuroshop.Models.OptionsModel;
import Neuroshop.Models.WidgetContainerModel;
import Neuroshop.Models.WidgetModels.DataManagerWidgetModel;
import Neuroshop.Models.WidgetModels.DiagramWidgetModel;
import Neuroshop.Models.WidgetModels.NeuralNetWidgetModel;
import Neuroshop.Widgets.WidgetContainer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        new ScreenSize();
        VBox root = new VBox();
        root.setStyle("-fx-background-color: #2b2b2b");

        //Init Gui------------------------------------------------------------------------------------------------------
        FXMLLoader borderLoader = new FXMLLoader(getClass().getResource("Gui/Border/BorderView.fxml"));
        AnchorPane ap = borderLoader.load();
        ap.getStylesheets().add("Neuroshop/Gui/Border/borderStyle.css");
        root.getChildren().add(ap);

        FXMLLoader whiteboardLoader = new FXMLLoader(getClass().getResource("Gui/Whiteboard/WhiteboardView.fxml"));
        AnchorPane whiteboard = whiteboardLoader.load();
        whiteboard.setPrefWidth(ScreenSize.width);
        whiteboard.setPrefHeight(ScreenSize.height-70); //-70 -> 30px von Border + 40px von Taskleiste
        root.getChildren().add(whiteboard);

        FXMLLoader optionsMenuLoader = new FXMLLoader(getClass().getResource("Gui/Options/OptionsMenuView.fxml"));
        whiteboard.getChildren().add(optionsMenuLoader.load());

        FXMLLoader widgetMenuLoader = new FXMLLoader(getClass().getResource("Gui/WidgetMenu/WidgetMenuView.fxml"));
        whiteboard.getChildren().add(widgetMenuLoader.load());

        FXMLLoader widgetSettingsLoader = new FXMLLoader(getClass().getResource("Gui/WidgetSettings/WidgetSettingsView.fxml"));
        VBox widgetSettings = widgetSettingsLoader.load();
        widgetSettings.getStylesheets().add("Neuroshop/Gui/WidgetSettings/widgetSettingsStyle.css");
        whiteboard.getChildren().add(widgetSettings);

        //Init Ann------------------------------------------------------------------------------------------------------

        //Init WidgetContainer------------------------------------------------------------------------------------------
        WidgetContainer widgetContainer = new WidgetContainer();

        //Init Model----------------------------------------------------------------------------------------------------
        DataManagerWidgetModel dataManagerWidgetModel = new DataManagerWidgetModel();
        DiagramWidgetModel diagramWidgetModel = new DiagramWidgetModel();
        NeuralNetWidgetModel neuralNetWidgetModel = new NeuralNetWidgetModel();
        WidgetContainerModel widgetContainerModel = new WidgetContainerModel();
        OptionsModel optionsModel = new OptionsModel();

        WidgetMenuController widgetMenuController = widgetMenuLoader.getController();
        OptionsController optionsMenuController = optionsMenuLoader.getController();
        BorderController borderController = borderLoader.getController();
        WhiteboardController whiteboardController = whiteboardLoader.getController();

        whiteboardController.initModel(widgetContainerModel);
        widgetMenuController.initModel(widgetContainerModel);
        borderController.initModel(optionsModel);
        optionsMenuController.initModel(optionsModel);
        widgetContainer.initModel(widgetContainerModel, dataManagerWidgetModel, diagramWidgetModel, neuralNetWidgetModel);

        widgetContainerModel.addObserver(widgetMenuController); //TODO NOCH ZU OBSERVERN
        optionsModel.addObserver(optionsMenuController);

        widgetContainer.intitWidgets();

        //Init Scene----------------------------------------------------------------------------------------------------
        Scene scene = new Scene(root, ScreenSize.width/1.2, (ScreenSize.height-40)/1.2);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();

        ScreenSize.toggleFullScreen(false);
        primaryStage.getIcons().add(new Image("Neuroshop/Ressources/taskbarIcon.jpg"));
        primaryStage.getIcons().size();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
