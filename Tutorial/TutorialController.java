package Neuroshop.Tutorial;

import Neuroshop.Models.TutorialModel;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.Observable;
import java.util.Observer;

public class TutorialController implements Observer {

    @FXML
    private AnchorPane tutorialStart, dataPrep, importData, dataSettings, dragNeural, neuralSettings;
    @FXML
    private StackPane tutorialRoot;

    private TutorialModel tutorialModel;

    @FXML
    private void skipTutorial() {
        tutorialRoot.getChildren().clear();
        tutorialRoot.setDisable(true);
        tutorialRoot.setVisible(false);
        tutorialModel.initDataManager();
    }

    @FXML
    private void startTutorial() {
        tutorialModel.setTutorialIsRunning(true);
        dataPrep.setVisible(true);
        tutorialStart.setVisible(false);
        tutorialStart.setDisable(true);
    }

    @FXML
    private void step1() {
        tutorialRoot.setDisable(true);
        dataPrep.setVisible(false);
        importData.setVisible(true);
        tutorialModel.initDataManager();
    }

    @FXML
    private void step2() {
        importData.setDisable(true);
        importData.setVisible(false);
        dataSettings.setDisable(false);
        dataSettings.setVisible(true);
    }

    @FXML
    private void step3() {
        dataSettings.setDisable(true);
        dataSettings.setVisible(false);
        dragNeural.setDisable(false);
        dragNeural.setVisible(true);
    }

    @FXML
    private void step4() {
        dragNeural.setDisable(true);
        dragNeural.setVisible(false);
        neuralSettings.setDisable(false);
        neuralSettings.setVisible(true);
    }

    @FXML
    private void step5() {
        neuralSettings.setDisable(true);
        neuralSettings.setVisible(false);
    }

    public void initModel(TutorialModel tutorialModel) {
        this.tutorialModel = tutorialModel;
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((String)arg) {
            case "showDataSettings":
                step1();
                break;
            case "step2":
                step2();
                break;
            case "step3":
                step3();
                break;
            case "step4":
                step4();
                break;
            case "step5":
                step5();
        }
    }
}
