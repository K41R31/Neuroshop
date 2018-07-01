package Neuroshop.Models.Presets;
import Neuroshop.ANN.Learn.Backpropagation;
import Neuroshop.ANN.Neural.NeuralNet;
import Neuroshop.Models.ANNModel;

import java.util.ArrayList;
import java.util.Observable;


public class Tempweight extends Observable {

    private int epoch;
    private ANNModel annModel;
    private Backpropagation bP;
    private ArrayList<ArrayList<ArrayList<Double>>> weight;

    public int getEpoch() {
        epoch = bP.getEpoch();
        return this.epoch;
    }

    public ArrayList<ArrayList<ArrayList<Double>>> getWeight() {
        this.weight = annModel.getNewWeights();
        return weight;
    }

    public void initModel(ANNModel annModel, Backpropagation bP) {
        this.bP = bP;
        this.annModel = annModel;
    }

}
