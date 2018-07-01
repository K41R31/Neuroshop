package Neuroshop.Models.Presets;

import Neuroshop.ANN.Math.Sigmoid;
import Neuroshop.Models.ANNModel;

import java.util.Observable;

public class SigmoidObj extends Observable {

    private ANNModel annModel;
    private int numberOfHiddenLayers;

    Sigmoid hidActFnc;

    public SigmoidObj() {
        hidActFnc = new Sigmoid(1.0);
    }

    public void setNumberOfHiddenLayers(int numberOfHiddenLayers) {
        this.numberOfHiddenLayers = annModel.getNumberOfHiddenNeurons().length;
        setChanged();
        notifyObservers("setNumberOfHiddenLayers");
    }

    public int getNumberOfHiddenLayers() {
        return this.numberOfHiddenLayers;
    }

    public void initModel(ANNModel annModel) {
        this.annModel = annModel;
    }
}
