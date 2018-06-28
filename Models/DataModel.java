package Neuroshop.Models;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataModel {


    private String filename;
    private double[][] dataSet;
    private File dataSetFile;

    private ArrayList<ArrayList<String>> LastLoaded;


    public void setDataSetFile(File dataSetFile) {
        this.dataSetFile = dataSetFile;
    }

    public File getDataSetFile() {
        return dataSetFile;
    }

    public void setDataSet(double[][] dataSet) {
        this.dataSet = dataSet;
    }

    public double[][] getDataSet() {
        return dataSet;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }



    public void saveDataFile() throws IOException {
        File file = new File("Neuroshop\\Ressources\\SavedData\\Temp" + File.separator + this.filename);
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
        }
    }
}
