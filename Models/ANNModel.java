package Neuroshop.Models;

import java.util.ArrayList;
import java.util.Observable;

public class ANNModel extends Observable {

    private ArrayList<ArrayList<ArrayList<Double>>> newWeights;
    private double[][] dataSet;

    public ArrayList<ArrayList<ArrayList<Double>>> getNewWeights() {
        return newWeights;
    }

    public void setNewWeights(ArrayList<ArrayList<ArrayList<Double>>> newWeights) {
        this.newWeights = newWeights;
    }

    public double[][] getDataSet() {
        return dataSet;
    }

    public void setDataSet(double[][] dataSet) {
        this.dataSet = dataSet;
    }
}
