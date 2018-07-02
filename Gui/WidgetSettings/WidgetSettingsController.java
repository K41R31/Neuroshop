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
    private VBox widgetSettings;
    private boolean menuIsOpen;

    @FXML
    private void initialize() {
        menuIsOpen = false;
        AnchorPane.setLeftAnchor(widgetSettings, (double)0);
        AnchorPane.setBottomAnchor(widgetSettings, (double)0);
        AnchorPane.setRightAnchor(widgetSettings, (double)0);
        maxEpochsValue.setText(String.valueOf(maxEpochsSlider.getValue()));
        minOverallErrorValue.setText(String.valueOf(minOverallErrorSlider.getValue()));
        learningRateValue.setText(String.valueOf(learningRateSlider.getValue()));
        momentumRateValue.setText(String.valueOf(momentumRateSlider.getValue()));
        System.out.println("Lernrate: " + learningRateSlider.getValue() + "Momenturm Rate: " + (momentumRateSlider.getValue()) +"Max Epoch: " + (maxEpochsSlider.getValue()));
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
        processing = Double.valueOf(toString.substring(0, toString.indexOf(".")+4));
        return processing;
    }

    @FXML
    private void maxEpochsSliderDragged() {
        int value = (int)maxEpochsSlider.getValue();
        maxEpochsSlider.setMax(50000);
        annModel.setMaxEpoch(value);
        maxEpochsValue.setText(String.valueOf(value));
    }

    @FXML
    private void minOverallErrorSliderDragged() {
        double value = minOverallErrorSlider.getValue();
        minOverallErrorSlider.setMin(0.001);
        minOverallErrorSlider.setMax(0.999);
        annModel.setMinOverallError(roundDouble(value));
        minOverallErrorValue.setText(String.valueOf(roundDouble(value)));
    }

    @FXML
    private void learningRateDragged() {
        double value = learningRateSlider.getValue();
        learningRateSlider.setMin(0.001);
        learningRateSlider.setMax(1.0);
        annModel.setLearningRate(roundDouble(value));
        learningRateValue.setText(String.valueOf(roundDouble(value)));

    }

    @FXML
    private void momentumRateDragged() {
        double value = momentumRateSlider.getValue();
        momentumRateSlider.setMin(0.001);
        momentumRateSlider.setMax(1.0);
        annModel.setMomentumRate(roundDouble(value));
        momentumRateValue.setText(String.valueOf(roundDouble(value)));
    }

    @FXML
    private void train() {
        annModel.train();
    }

    public void initModel(ANNModel annModel) {
        this.annModel = annModel;
    }
}
