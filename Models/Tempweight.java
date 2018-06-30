package Neuroshop.Models;
import java.util.ArrayList;
import java.util.Observable;

public class Tempweight extends Observable {

    private int epoch;
    private ANNModel annModel;
    private ArrayList<ArrayList<ArrayList<Double>>> weight;

    public int getEpoch() {
        return this.epoch;
    }

    public void setEpoch(int epoch) {
        this.epoch = epoch;
    }

    public void setWeight(ArrayList<ArrayList<ArrayList<Double>>> weight) {
        this.weight = weight;
    }

    public ArrayList<ArrayList<ArrayList<Double>>> getWeight() {
        annModel.getNewWeights();
        return this.weight;
    }

    public void initModel(ANNModel annModel) {
        this.annModel = annModel;
    }



}
