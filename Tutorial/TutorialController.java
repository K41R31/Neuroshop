package Neuroshop.Tutorial;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class TutorialController {

    @FXML
    private AnchorPane tutorialStart,dataPrep,importData,dataSettings,dragNeural,neuralSettings,dropDiagram,changeValues,startNetwork;

    @FXML
    private void skipTutorial() {
        int i;
        for (i = 0; i < 8;i++){
            startNetwork.getChildren().get(i).setOpacity(0);
        }
        startNetwork.setVisible(false);
    }

    @FXML
    private void startTutorial(){
        dataPrep.setOpacity(1);
        dataPrep.setVisible(true);
    }

    @FXML
    private void step1(){
        dataPrep.setOpacity(0);
        dataPrep.setVisible(false);
        importData.setOpacity(1);
        importData.setVisible(true);
    }

    @FXML
    private void step2(){
        importData.setOpacity(0);
        importData.setVisible(false);
        dataSettings.setOpacity(1);
        dataSettings.setVisible(true);
    }

    @FXML
    private void step3(){
        dataSettings.setOpacity(0);
        dataSettings.setVisible(false);
        dragNeural.setOpacity(1);
        dragNeural.setVisible(true);
    }

    @FXML
    private void step4(){
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
    private void step6(){
        dropDiagram.setOpacity(0);
        dropDiagram.setVisible(false);
        changeValues.setOpacity(1);
        changeValues.setVisible(true);
    }

    @FXML
    private void step7(){
        changeValues.setOpacity(0);
        changeValues.setVisible(false);
        startNetwork.setOpacity(1);
        startNetwork.setVisible(true);
    }

    @FXML
    private void stepFinal(){
        startNetwork.setOpacity(0);
        startNetwork.setVisible(false);
    }
}
