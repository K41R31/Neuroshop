package Neuroshop.Gui.WidgetSettings;

import Neuroshop.Models.ANNModel;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class WidgetSettingsController implements Observer {

    private ANNModel annModel;

    @FXML
    private Text maxEpochsValue;
    @FXML
    private Text minOverallErrorValue;
    @FXML
    private Text learningRateValue;
    @FXML
    private Text momentumRateValue;
    @FXML
    private Slider maxEpochsSlider;
    @FXML
    private Slider minOverallErrorSlider;
    @FXML
    private Slider learningRateSlider;
    @FXML
    private Slider momentumRateSlider;
    @FXML
    private ImageView openerIcon;
    @FXML
    private AnchorPane menuPane;
    @FXML
    private HBox contentPane;
    @FXML
    private VBox rootPane;
    private boolean menuIsOpen;

    @FXML
    private void initialize() {
        menuIsOpen = false;
        AnchorPane.setLeftAnchor(rootPane, (double)0);
        AnchorPane.setBottomAnchor(rootPane, (double)0);
        AnchorPane.setRightAnchor(rootPane, (double)0);
        maxEpochsValue.setText(String.valueOf(maxEpochsSlider.getValue()));
        minOverallErrorValue.setText(String.valueOf(minOverallErrorSlider.getValue()));
        learningRateValue.setText(String.valueOf(learningRateSlider.getValue()));
        momentumRateValue.setText(String.valueOf(momentumRateSlider.getValue()));
    }

    @FXML
    private void toggleMenu() {
        if (!menuIsOpen) {
            Timeline openSettingsAnimation = new Timeline();
            openSettingsAnimation.getKeyFrames().addAll(
                    new KeyFrame(new Duration(200), new KeyValue(menuPane.prefHeightProperty(), 350, Interpolator.EASE_BOTH)),
                    new KeyFrame(new Duration(200), new KeyValue(openerIcon.scaleXProperty(), -1, Interpolator.EASE_BOTH)),
                    new KeyFrame(new Duration(200), new KeyValue(contentPane.opacityProperty(), 1, Interpolator.EASE_BOTH))
            );
            openSettingsAnimation.play();
            openSettingsAnimation.setOnFinished(event -> menuPane.setDisable(false));
            contentPane.setVisible(true);
            menuIsOpen = true;
        } else {
            Timeline closeSettingsAnimation = new Timeline();
            closeSettingsAnimation.getKeyFrames().addAll(
                    new KeyFrame(new Duration(200), new KeyValue(menuPane.prefHeightProperty(), 0, Interpolator.EASE_BOTH)),
                    new KeyFrame(new Duration(200), new KeyValue(openerIcon.scaleXProperty(), 1, Interpolator.EASE_BOTH)),
                    new KeyFrame(new Duration(200), new KeyValue(contentPane.opacityProperty(), 0, Interpolator.EASE_BOTH))
            );
            closeSettingsAnimation.play();
            closeSettingsAnimation.setOnFinished(event -> contentPane.setVisible(false));
            menuPane.setDisable(true);
            menuIsOpen = false;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
    }

    private double roundDouble(double start) {
        double processing;
        String toString = String.valueOf(start);
        processing = Double.valueOf(toString.substring(0, toString.indexOf(".")+2));
        return processing;
    }

    @FXML
    private void maxEpochsSliderDragged() {
        System.out.println(roundDouble(156.15462));
//        annModel.setMaxEpochs(value);
//        maxEpochsValue.setText(String.valueOf(value));
    }

    @FXML
    private void minOverallErrorSliderDragged() {
        int value = (int)minOverallErrorSlider.getValue();
        annModel.setMinOverallError(value);
        minOverallErrorValue.setText(String.valueOf(value));
    }

    @FXML
    private void learningRateDragged() {
        int value = (int)learningRateSlider.getValue();
        annModel.setLearningRate(value);
        learningRateValue.setText(String.valueOf(value));
    }

    @FXML
    private void momentumRateDragged() {
        int value = (int)momentumRateSlider.getValue();
        annModel.setMomentumRate(value);
        momentumRateValue.setText(String.valueOf(value));
    }

    public void initModel(ANNModel annModel) {
        this.annModel = annModel;
    }
}
