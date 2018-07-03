package Neuroshop;

import Neuroshop.ANN.ANNLearn;
import Neuroshop.Gui.NeuralNetSettings.NeuralNetSettingsController;
import Neuroshop.Models.ANNModel;
import Neuroshop.Gui.Border.BorderController;
import Neuroshop.Gui.Options.OptionsController;
import Neuroshop.Gui.Whiteboard.WhiteboardController;
import Neuroshop.Gui.WidgetMenu.WidgetMenuController;
import Neuroshop.Models.OptionsModel;
import Neuroshop.Models.TutorialModel;
import Neuroshop.Models.WidgetContainerModel;
import Neuroshop.Gui.Widgets.WidgetContainer;
import Neuroshop.Tutorial.TutorialController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
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

        FXMLLoader neuralNetSettingsLoader = new FXMLLoader(getClass().getResource("Gui/NeuralNetSettings/NeuralNetSettingsView.fxml"));
        VBox neuralNetSettings = neuralNetSettingsLoader.load();
        neuralNetSettings.getStylesheets().add("Neuroshop/Gui/NeuralNetSettings/NeuralNetSettingsStyle.css");
        whiteboard.getChildren().add(neuralNetSettings);

        FXMLLoader tutorialLoader = new FXMLLoader(getClass().getResource("Tutorial/TutorialView.fxml"));
        AnchorPane tutorialRoot = tutorialLoader.load();
        whiteboard.getChildren().add(tutorialRoot);

        //Init Ann------------------------------------------------------------------------------------------------------
        ANNLearn annLearn = new ANNLearn();

        //Init Tutorial-------------------------------------------------------------------------------------------------
        TutorialController tutorialController = new TutorialController();

        //Init Model----------------------------------------------------------------------------------------------------
        ANNModel annModel = new ANNModel();
        WidgetContainerModel widgetContainerModel = new WidgetContainerModel();
        OptionsModel optionsModel = new OptionsModel();

        NeuralNetSettingsController widgetSettingsController = neuralNetSettingsLoader.getController();
        WidgetMenuController widgetMenuController = widgetMenuLoader.getController();
        OptionsController optionsMenuController = optionsMenuLoader.getController();
        BorderController borderController = borderLoader.getController();
        WhiteboardController whiteboardController = whiteboardLoader.getController();

        annLearn.initModel(annModel);
        whiteboardController.initModel(widgetContainerModel);
        widgetMenuController.initModel(widgetContainerModel);
        borderController.initModel(optionsModel);
        optionsMenuController.initModel(optionsModel);
        widgetSettingsController.initModel(annModel);

        annModel.addObserver(annLearn);
        widgetContainerModel.addObserver(whiteboardController);
        widgetContainerModel.addObserver(widgetMenuController);
        widgetContainerModel.addObserver(widgetSettingsController);
        optionsModel.addObserver(optionsMenuController);

        //Init WidgetContainer------------------------------------------------------------------------------------------
        new WidgetContainer(widgetContainerModel, annModel);

        //Init Scene----------------------------------------------------------------------------------------------------
        Scene scene = new Scene(root, ScreenSize.width/1.2, (ScreenSize.height-40)/1.2);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();

        ScreenSize.toggleFullScreen(false);
        primaryStage.getIcons().add(new Image("Neuroshop/Ressources/Assets/taskbarIcon.jpg"));
        primaryStage.getIcons().size();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
