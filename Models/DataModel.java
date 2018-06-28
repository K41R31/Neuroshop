package Neuroshop.Models;

import java.io.File;
import java.util.Observable;


public class DataModel extends Observable {

    private File datasetFile;
    private double[][] dataset;

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
