package Neuroshop;

import Neuroshop.Gui.Border.BorderController;
import Neuroshop.Gui.OptionsMenu.OptionsMenuController;
import Neuroshop.Gui.Whiteboard.WhiteboardController;
import Neuroshop.Gui.WidgetMenu.WidgetMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Start extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        new ScreenSize();
        VBox root = new VBox();
        root.setStyle("-fx-background-color: #2f2f2f");
        FXMLLoader borderLoader = new FXMLLoader(getClass().getResource("Gui/Border/border.fxml"));
        root.getChildren().add(borderLoader.load());
        BorderController borderController = borderLoader.getController();

        FXMLLoader whiteboardLoader = new FXMLLoader(getClass().getResource("Gui/Whiteboard/whiteboard.fxml"));
        AnchorPane whiteboard = whiteboardLoader.load();
        whiteboard.setPrefWidth(ScreenSize.width);
        whiteboard.setPrefHeight(ScreenSize.height-70); //-70 -> 30px von Border + 40px von Taskleiste
        root.getChildren().add(whiteboard);
        WhiteboardController whiteboardController = whiteboardLoader.getController();
        FXMLLoader optionsMenuLoader = new FXMLLoader(getClass().getResource("Gui/OptionsMenu/OptionsMenu.fxml"));
        whiteboard.getChildren().add(optionsMenuLoader.load());
        OptionsMenuController optionsMenuController = optionsMenuLoader.getController();

        FXMLLoader toolMenuLoader = new FXMLLoader(getClass().getResource("Gui/WidgetMenu/WidgetMenu.fxml"));
        whiteboard.getChildren().add(toolMenuLoader.load());
        WidgetMenuController widgetListController = toolMenuLoader.getController();

        //Init Widgets
        WidgetsModel widgetsModel = new WidgetsModel();
        OptionsModel optionsModel = new OptionsModel();
        whiteboardController.initModel(widgetsModel);
        widgetListController.initModel(widgetsModel);

        borderController.initModel(optionsModel);
        optionsMenuController.initModel(optionsModel);

        widgetsModel.addObserver(whiteboardController);
        optionsModel.addObserver(optionsMenuController);

        Scene scene = new Scene(root, ScreenSize.width/1.2, (ScreenSize.height-40)/1.2);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();

        ScreenSize.toggleFullScreen(false);
        primaryStage.getIcons().add(new Image("Neuroshop/Gui/Resources/taskbarIcon.jpg"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
