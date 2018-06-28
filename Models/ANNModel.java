package Neuroshop.Models;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

public class ANNModel extends Observable {

    private File datasetFile;
    private ArrayList<ArrayList<ArrayList<Double>>> newWeights;
    private double[][] dataset;


    public void setNewWeights(ArrayList<ArrayList<ArrayList<Double>>> newWeights) {
        this.newWeights = newWeights;
    }

    public ArrayList<ArrayList<ArrayList<Double>>> getNewWeights() {
        return newWeights;
    }

    public void setDatasetFile(File dataSetFile) {
        this.datasetFile = dataSetFile;
        setChanged();
        notifyObservers("loadDataSet");
    }

    public File getDatasetFile() {
        return datasetFile;
    }

    public void setDataset(double[][] dataSet) {
        this.dataset = dataSet;
    }

    public double[][] getDataSet() {
        return dataset;
    }
}
