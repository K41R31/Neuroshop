package Neuroshop.Gui.WidgetSettings;

import Neuroshop.Models.WidgetModels.DataManagerWidgetModel;
import Neuroshop.Models.WidgetModels.DiagramWidgetModel;
import Neuroshop.Models.WidgetModels.NeuralNetWidgetModel;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.util.Observable;
import java.util.Observer;

public class WidgetSettingsController implements Observer {

    @FXML
    private HBox contentPane;
    @FXML
    private AnchorPane rootPane;
    private DataManagerWidgetModel dataManagerWidgetModel;
    private DiagramWidgetModel diagramWidgetModel;
    private NeuralNetWidgetModel neuralNetWidgetModel;
    private boolean menuIsOpen;

    @FXML
    private void initialize() {
        menuIsOpen = false;
        AnchorPane.setLeftAnchor(rootPane, (double)0);
        AnchorPane.setBottomAnchor(rootPane, (double)0);
        AnchorPane.setRightAnchor(rootPane, (double)0);
    }

    private void toggleOptionsMenu() { //TODO Ka wann/ wie man das Menu öffen können soll
        if (!menuIsOpen) {
            Timeline openSettingsAnimation = new Timeline();
            openSettingsAnimation.getKeyFrames().addAll(
                    new KeyFrame(new Duration(200), new KeyValue(rootPane.prefWidthProperty(), 350, Interpolator.EASE_BOTH)),
                    new KeyFrame(new Duration(200), new KeyValue(contentPane.opacityProperty(), 1, Interpolator.EASE_BOTH)) //TODO Soll nach dem KeyFrame davor starten
            );
            openSettingsAnimation.play();
            openSettingsAnimation.setOnFinished(event -> contentPane.setDisable(false));
            rootPane.setVisible(true);
            menuIsOpen = true;
        } else {
            Timeline closeSettingsAnimation = new Timeline();
            closeSettingsAnimation.getKeyFrames().addAll(
                    new KeyFrame(new Duration(200), new KeyValue(rootPane.prefWidthProperty(), 0, Interpolator.EASE_BOTH)),
                    new KeyFrame(new Duration(200), new KeyValue(contentPane.opacityProperty(), 0, Interpolator.EASE_BOTH)) //TODO Soll nach dem KeyFrame davor starten
            );
            closeSettingsAnimation.play();
            closeSettingsAnimation.setOnFinished(event -> rootPane.setVisible(false));
            contentPane.setDisable(true);
            menuIsOpen = false;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
    }

    public void initModel(DataManagerWidgetModel dataManagerWidgetModel, DiagramWidgetModel diagramWidgetModel, NeuralNetWidgetModel neuralNetWidgetModel) {
        this.dataManagerWidgetModel = dataManagerWidgetModel;
        this.diagramWidgetModel = diagramWidgetModel;
        this.neuralNetWidgetModel = neuralNetWidgetModel;
    }
}
