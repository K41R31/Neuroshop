package Neuroshop.Gui.NeuralNetSettings;

import Neuroshop.Models.ANNModel;
import Neuroshop.Models.TutorialModel;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.util.Observable;
import java.util.Observer;

public class NeuralNetSettingsController implements Observer {

    private TutorialModel tutorialModel;
    private ANNModel annModel;
    @FXML
    private Text batchMode;
    @FXML
    private Text onlineMode;
    @FXML
    private Text trainMode;
    @FXML
    private Text validateMode;
    @FXML
    private Text actualEpoch;
    @FXML
    private Text actualOverallError;
    @FXML
    private Text actualTestError;
    @FXML
    private TextField maxEpochsValue;
    @FXML
    private TextField minOverallErrorValue;
    @FXML
    private TextField learningRateValue;
    @FXML
    private TextField momentumRateValue;
    @FXML
    private TextField seedTextField;
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
    private ImageView startButton;
    @FXML
    private AnchorPane menuPane;
    @FXML
    private AnchorPane epochPane;
    @FXML
    private HBox contentPane;
    @FXML
    private HBox epochInnerPane;
    @FXML
    private HBox minOverallErrorPane;
    @FXML
    private VBox widgetSettings;
    @FXML
    private boolean menuIsOpen;
    private boolean epochPaneIsOpen;
    private Glow glow;

    @FXML
    private void initialize() {
        epochPaneIsOpen = false;
        menuIsOpen = false;
        glow = new Glow(1);
        AnchorPane.setLeftAnchor(widgetSettings, (double)0);
        AnchorPane.setBottomAnchor(widgetSettings, (double)0);
        AnchorPane.setRightAnchor(widgetSettings, (double)0);
        maxEpochsValue.setText(String.valueOf(maxEpochsSlider.getValue()));
        minOverallErrorValue.setText(String.valueOf(minOverallErrorSlider.getValue()));
        momentumRateValue.setText(String.valueOf(momentumRateSlider.getValue()));
        learningRateValue.setText(String.valueOf(learningRateSlider.getValue()));
        glowEffect(batchMode);
        glowEffect(onlineMode);
        startButton.setImage(new Image("Neuroshop/Ressources/Assets/playButton.png"));
        startButton.setOnMouseEntered(event -> new Image("Neuroshop/Ressources/Assets/playButtonHover.png"));
        startButton.setOnMouseExited(event -> new Image("Neuroshop/Ressources/Assets/playButton.png"));
    }

    @FXML
    private void toggleMenu() {
        if (tutorialModel.getTutorialIsRunning()) {
            tutorialModel.step5();
            tutorialModel.setTutorialIsRunning(false);
        }
        if (!menuIsOpen) {
            Timeline openSettingsAnimation = new Timeline();
            openSettingsAnimation.getKeyFrames().addAll(
                    new KeyFrame(new Duration(200), new KeyValue(menuPane.prefHeightProperty(), 244, Interpolator.EASE_BOTH),
                            new KeyValue(openerIcon.scaleXProperty(), -1, Interpolator.EASE_BOTH)),
                    new KeyFrame(new Duration(400), new KeyValue(contentPane.opacityProperty(), 1, Interpolator.EASE_BOTH))
            );
            openSettingsAnimation.play();
            openSettingsAnimation.setOnFinished(event -> menuPane.setDisable(false));
            menuIsOpen = true;
        } else {
            Timeline closeSettingsAnimation = new Timeline();
            closeSettingsAnimation.getKeyFrames().addAll(
                    new KeyFrame(new Duration(200), new KeyValue(menuPane.prefHeightProperty(), 0, Interpolator.EASE_BOTH),
                            new KeyValue(openerIcon.scaleXProperty(), 1, Interpolator.EASE_BOTH),
                            new KeyValue(contentPane.opacityProperty(), 0, Interpolator.EASE_BOTH))
            );
            closeSettingsAnimation.play();
            menuPane.setDisable(true);
            menuIsOpen = false;
        }
    }

    /**
     * Rundet einen Double auf >decimal< Nachkommastellen
     * Falls der Double weniger als >decimal< Nachkommastellen hat,
     * wird er unverändert returnt
     */

    private double roundDouble(double start, int decimal) {
        double processing;
        String toString = String.valueOf(start);
        if (toString.length() < 5) return start;
        processing = Double.valueOf(toString.substring(0, toString.indexOf(".")+1+decimal));
        return processing;
    }

    private void toggleEpochPane() {
        if (!epochPaneIsOpen) {
            Timeline openEpochPaneAnimation = new Timeline();
            openEpochPaneAnimation.getKeyFrames().addAll(
                    new KeyFrame(new Duration(200), new KeyValue(epochPane.layoutYProperty(), 30, Interpolator.EASE_BOTH)),
                    new KeyFrame(new Duration(400), new KeyValue(epochInnerPane.opacityProperty(), 1, Interpolator.EASE_BOTH)),
                    new KeyFrame(new Duration(400), new KeyValue(minOverallErrorPane.opacityProperty(), 1, Interpolator.EASE_BOTH))

            );
            openEpochPaneAnimation.play();
            epochPaneIsOpen = true;
        } else {
            Timeline closeEpochPaneAnimation = new Timeline();
            closeEpochPaneAnimation.getKeyFrames().addAll(
                    new KeyFrame(new Duration(200), new KeyValue(epochPane.layoutYProperty(), 120, Interpolator.EASE_BOTH)),
                    new KeyFrame(new Duration(400), new KeyValue(epochInnerPane.opacityProperty(), 0, Interpolator.EASE_BOTH)),
                    new KeyFrame(new Duration(400), new KeyValue(minOverallErrorPane.opacityProperty(), 0, Interpolator.EASE_BOTH))
            );
            closeEpochPaneAnimation.play();
            epochPaneIsOpen = false;
        }
    }

    private void glowEffect(Text node) {

        EventHandler<MouseEvent> onMouseEntered =
                event ->
                        node.setEffect(glow);

        EventHandler<MouseEvent> onMouseExited =
                event ->
                        node.setEffect(null);

    node.setOnMouseEntered(onMouseEntered);
    node.setOnMouseExited(onMouseExited);
    }

    private void updateEpochPane() {
        Timeline updateEpochPane = new Timeline();
        updateEpochPane.getKeyFrames().addAll(
                new KeyFrame(new Duration(100), event -> actualEpoch.setText(String.valueOf(annModel.getActualEpoch()))),
                new KeyFrame(new Duration(100), event -> actualOverallError.setText(String.valueOf(roundDouble(annModel.getOverallError(), 5))))
        );
        updateEpochPane.setCycleCount(Animation.INDEFINITE);
        updateEpochPane.play();
    }

    @FXML
    private void batchModeClicked() {
        switchLearnmethod("batchMode");
    }

    @FXML
    private void onlineModeClicked() {
        switchLearnmethod("onlineMode");
    }

    private void switchLearnmethod(String mode) {
        switch (mode) {
            case "batchMode":
                if (onlineMode.getFill().toString().contains("35baff")) {
                    batchMode.setFill(Color.web("#35baff"));
                    onlineMode.setFill(Color.web("#c6c6c6"));
                    annModel.setLearnmode("batchMode");
                }
                break;
            case "onlineMode":
                if (batchMode.getFill().toString().contains("35baff")) {
                    onlineMode.setFill(Color.web("#35baff"));
                    batchMode.setFill(Color.web("#c6c6c6"));
                    annModel.setLearnmode("onlineMode");
                }
        }
    }

    @FXML
    private void openerPaneEntered() {
        openerIcon.setImage(new Image("Neuroshop/Ressources/Assets/nnOpenerIconHover.png"));

    }
    @FXML
    private void openerPaneExited() {
        openerIcon.setImage(new Image("Neuroshop/Ressources/Assets/nnOpenerIcon.png"));
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
        minOverallErrorSlider.setMax(1.0);
        annModel.setMinOverallError(roundDouble(value, 3));
        minOverallErrorValue.setText(String.valueOf(roundDouble(value, 3)));
    }

    @FXML
    private void learningRateDragged() {
        double value = learningRateSlider.getValue();
        learningRateSlider.setMin(0.001);
        learningRateSlider.setMax(1.0);
        annModel.setLearningRate(roundDouble(value, 3));
        learningRateValue.setText(String.valueOf(roundDouble(value, 3)));

    }

    @FXML
    private void momentumRateDragged() {
        double value = momentumRateSlider.getValue();
        momentumRateSlider.setMin(0.001);
        momentumRateSlider.setMax(1.0);
        annModel.setMomentumRate(roundDouble(value, 1));
        momentumRateValue.setText(String.valueOf(roundDouble(value, 1)));
    }

    @FXML
    private void seedChanged() { //TODO
        String seedText = seedTextField.getText();
        try {
            annModel.setSeed(Double.parseDouble(seedText));
        } catch(NumberFormatException e) {
            seedTextField.setText("Nur Ziffern");
        }
    }

    @FXML
    private void train() {
        if (!annModel.getIsTraining()) {
            annModel.train();
            toggleEpochPane();
            updateEpochPane();
            startButton.setImage(new Image("Neuroshop/Ressources/Assets/stopButton.png"));
            startButton.setOnMouseEntered(event -> new Image("Neuroshop/Ressources/Assets/stopButtonHover.png"));
            startButton.setOnMouseExited(event -> new Image("Neuroshop/Ressources/Assets/stopButton.png"));
        } else {
            annModel.stop();
            toggleEpochPane();
            updateEpochPane();
            startButton.setImage(new Image("Neuroshop/Ressources/Assets/playButton.png"));
            startButton.setOnMouseEntered(event -> new Image("Neuroshop/Ressources/Assets/playButtonHover.png"));
            startButton.setOnMouseExited(event -> new Image("Neuroshop/Ressources/Assets/playButton.png"));
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((String)arg) {
            case "activateMenus":
                openerIcon.setDisable(false);
                break;
            case "menusToFront":
                widgetSettings.toFront();
        }
    }

    public void initModel(ANNModel annModel, TutorialModel tutorialModel) {
        this.annModel = annModel;
        this.tutorialModel = tutorialModel;
        annModel.setLearnmode("batchMode");
        maxEpochsSliderDragged();
        minOverallErrorSliderDragged();
        momentumRateDragged();
        learningRateDragged();
        annModel.setLearnmode("batchMode");
    }
}
