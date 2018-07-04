package Neuroshop.Models;

import java.util.Observable;

public class TutorialModel extends Observable {

    private boolean neuralDrag, diagramDrag, tutorialIsRunning;
    private Object neuralNet, diagram;
    private boolean customLayers, start;
    private double valueOne, valueTwo, valueThree, valueFour;

    public TutorialModel() {
        tutorialIsRunning = false;
    }

    public boolean getNeuralDrag() {return neuralDrag;}

    public void setNeuralDrag(Object neuralNet) {
        this.neuralNet = neuralNet;
        this.setChanged();
        this.notifyObservers("setNeuralDrag");
//        TutorialController.step4();
    }

    public boolean getCustomLayers() {return customLayers;}

    public void setCustomLayers(boolean customLayers) {
        this.customLayers = customLayers;
        this.setChanged();
        this.notifyObservers("setCustomLayers");
//        TutorialController.step5();
    }

    public boolean getDiagramDrag() {return diagramDrag;}

    public void setDiagramDrag(Object diagram) {
        this.diagram = diagram;
        this.setChanged();
        this.notifyObservers("setDiagramDrag");
//        TutorialController.step6();
    }

    public double getValueOne() {return valueOne;}
    public double getValueTwo() {return valueTwo;}
    public double getValueThree() {return valueThree;}
    public double getValueFour() {return valueFour;}

    public void setValuesChanged(double valueOne,double valueTwo,double valueThree,double valueFour) {
        this.valueOne = valueOne;
        this.setChanged();
        this.valueTwo = valueTwo;
        this.setChanged();
        this.valueThree = valueThree;
        this.setChanged();
        this.valueFour = valueFour;
        this.setChanged();
        this.notifyObservers("setDiagramDrag");
//        TutorialController.step7();
    }

    public boolean getStart() {return start;}

    public void setStart(boolean Start) {
        this.start = start;
        this.setChanged();
        this.notifyObservers("setDiagramDrag");
//        TutorialController.stepFinal();
    }

    public void initDataManager() {
        setChanged();
        notifyObservers("initDataManager");
    }

    public void setTutorialIsRunning(boolean tutorialIsRunning) {
        this.tutorialIsRunning = tutorialIsRunning;
    }

    public boolean getTutorialIsRunning() {
        return tutorialIsRunning;
    }

    public void showLastOpened() {
        setChanged();
        notifyObservers("showLastOpened");
    }

    public void step2() {
        setChanged();
        notifyObservers("step2");
    }

    public void step3() {
        setChanged();
        notifyObservers("step3");
    }

    public void step4() {
        setChanged();
        notifyObservers("step4");
    }

    public void step5() {
        setChanged();
        notifyObservers("step5");
    }
}
