package Neuroshop;

import Neuroshop.Gui.Border.BorderController;
import Neuroshop.Gui.Options.OptionsMenuController;
import Neuroshop.Gui.Whiteboard.WhiteboardController;
import Neuroshop.Gui.WidgetContainer.WidgetContainer;
import Neuroshop.Gui.WidgetMenu.WidgetMenuController;
import Neuroshop.Model.OptionsModel;
import Neuroshop.Model.WidgetContainerModel;
import Neuroshop.Model.WidgetModels.DataManagerModel;
import Neuroshop.Model.WidgetModels.DiagramModel;
import Neuroshop.Model.WidgetModels.NeuralNetModel;
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
        root.setStyle("-fx-background-color: #2f2f2f");

        //Init Gui------------------------------------------------------------------------------------------------------
        FXMLLoader borderLoader = new FXMLLoader(getClass().getResource("Gui/Border/border.fxml"));
        AnchorPane borderPane = borderLoader.load();
        borderPane.getStylesheets().add("Neuroshop/Gui/Border/borderStyle.css");
        root.getChildren().add(borderPane);

        FXMLLoader whiteboardLoader = new FXMLLoader(getClass().getResource("Gui/Whiteboard/whiteboard.fxml"));
        AnchorPane whiteboard = whiteboardLoader.load();
        whiteboard.setPrefWidth(ScreenSize.width);
        whiteboard.setPrefHeight(ScreenSize.height-70); //-70 -> 30px von Border + 40px von Taskleiste
        root.getChildren().add(whiteboard);

        WhiteboardController whiteboardController = whiteboardLoader.getController();
        FXMLLoader optionsMenuLoader = new FXMLLoader(getClass().getResource("Gui/OptionsMenu/OptionsMenu.fxml"));
        whiteboard.getChildren().add(optionsMenuLoader.load());

        FXMLLoader toolMenuLoader = new FXMLLoader(getClass().getResource("Gui/WidgetMenu/WidgetMenu.fxml"));
        whiteboard.getChildren().add(toolMenuLoader.load());

        //Init ANN------------------------------------------------------------------------------------------------------
        //new Ann();

        //Init WidgetContainer------------------------------------------------------------------------------------------
        WidgetContainer widgetContainer = new WidgetContainer();


        //Init Model----------------------------------------------------------------------------------------------------
        BorderController borderController = borderLoader.getController();
        OptionsMenuController optionsMenuController = optionsMenuLoader.getController();
        WidgetMenuController widgetMenuController = toolMenuLoader.getController();

        DataManagerModel dataManagerModel = new DataManagerModel();
        DiagramModel diagramModel = new DiagramModel();
        NeuralNetModel neuralNetModel = new NeuralNetModel();
        OptionsModel optionsModel = new OptionsModel();
        WidgetContainerModel widgetContainerModel = new WidgetContainerModel();

        borderController.initModel(optionsModel);
        optionsMenuController.initModel(optionsModel);

        whiteboardController.initModel(widgetContainerModel);
        widgetMenuController.initModel(widgetContainerModel);


        //Init Stage----------------------------------------------------------------------------------------------------

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
