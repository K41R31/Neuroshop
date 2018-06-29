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
import javafx.util.Duration;
import java.util.Observable;
import java.util.Observer;

public class WidgetSettingsController implements Observer {

    private ANNModel annModel;

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

    @FXML
    private void maxEpochsSliderDragged() {
        annModel.setMaxEpochs((int)maxEpochsSlider.getValue());
    }

    @FXML
    private void minOverallErrorSliderDragged() {
        annModel.setMinOverallError(minOverallErrorSlider.getValue());
    }

    @FXML
    private void learningRateDragged() {
        annModel.setLearningRate(learningRateSlider.getValue());
    }

    @FXML
    private void momentumRateDragged() {
        annModel.setMomentumRate(momentumRateSlider.getValue());
    }

    public void initModel(ANNModel annModel) {
        this.annModel = annModel;
    }
}
