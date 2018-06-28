package Neuroshop.Models;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

public class ANNModel extends Observable {

    private File dataSetFile;
    private ArrayList<ArrayList<ArrayList<Double>>> newWeights;
    private double[][] dataSet;


    public void setNewWeights(ArrayList<ArrayList<ArrayList<Double>>> newWeights) {
        this.newWeights = newWeights;
    }

    public ArrayList<ArrayList<ArrayList<Double>>> getNewWeights() {
        return newWeights;
    }

    public void setRawDataSetFile(File dataSetFile) {
        this.dataSetFile = dataSetFile;
    }

    public File getRawDataSetFile() {
        return dataSetFile;
    }

    public void setDataSet(double[][] dataSet) {
        this.dataSet = dataSet;
    }

    public double[][] getDataSet() {
        return dataSet;
    }
}
