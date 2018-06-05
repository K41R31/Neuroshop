package Neuroshop;

import Neuroshop.Model.ScreenSize;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Start extends Application {

    static public Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Start.primaryStage = primaryStage;
<<<<<<< HEAD:Start.java
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Neuroshop/View/mainView.fxml"));
        primaryStage.setTitle("Neuroshop");
=======
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Neuroshop/View/mainView.fxml"));
        primaryStage.setTitle("Neuroshop");
>>>>>>> origin/_trashbin:Neuroshop/Start.java
        new ScreenSize();
        primaryStage.setScene(new Scene(root, ScreenSize.screenWidth/1.5, ScreenSize.screenHeight/1.5));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        ScreenSize.toggleFullScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
