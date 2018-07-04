package Neuroshop.Tutorial;

import Neuroshop.Models.TutorialModel;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.Observable;
import java.util.Observer;

public class TutorialController implements Observer {

    @FXML
    private AnchorPane tutorialStart, dataPrep, importData, dataSettings, dragNeural, neuralSettings, dropDiagram, changeValues, startNetwork;
    @FXML
    private StackPane importPane, tutorialRoot;

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
        dataPrep.setVisible(true);
        dataPrep.setDisable(false);
        tutorialStart.setVisible(false);
        tutorialStart.setDisable(true);
    }

    @FXML
    private void step1() {
        dataPrep.setOpacity(0);
        dataPrep.setVisible(false);
        importData.setOpacity(1);
        importData.setVisible(true);
        tutorialModel.initDataManager();
    }

    @FXML
    private void step2() {
        importData.setOpacity(0);
        importData.setVisible(false);
        dataSettings.setOpacity(1);
        dataSettings.setVisible(true);
    }

    @FXML
    private void step3() {
        dataSettings.setOpacity(0);
        dataSettings.setVisible(false);
        dragNeural.setOpacity(1);
        dragNeural.setVisible(true);
    }

    @FXML
    private void step4() {
        dragNeural.setOpacity(0);
        dragNeural.setVisible(false);
        neuralSettings.setOpacity(1);
        neuralSettings.setVisible(true);
    }

    @FXML
    private void step5(){
        neuralSettings.setOpacity(0);
        neuralSettings.setVisible(false);
        dropDiagram.setOpacity(1);
        dropDiagram.setVisible(true);
    }

    @FXML
    private void step6() {
        dropDiagram.setOpacity(0);
        dropDiagram.setVisible(false);
        changeValues.setOpacity(1);
        changeValues.setVisible(true);
    }

    @FXML
    private void step7() {
        changeValues.setOpacity(0);
        changeValues.setVisible(false);
        startNetwork.setOpacity(1);
        startNetwork.setVisible(true);
    }

    @FXML
    private void stepFinal() {
        startNetwork.setOpacity(0);
        startNetwork.setVisible(false);
    }

    public void initModel(TutorialModel tutorialModel) {
        this.tutorialModel = tutorialModel;
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((String)arg) {
        }
    }
}
