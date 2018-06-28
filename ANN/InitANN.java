package Neuroshop.ANN;

import Neuroshop.ANN.Data.DataNormalization;
import Neuroshop.ANN.Data.DataSet;
import Neuroshop.ANN.Learn.LearningAlgorithm;
import Neuroshop.ANN.Math.IActivationFunction;
import Neuroshop.ANN.Math.Linear;
import Neuroshop.ANN.Math.Sigmoid;
import Neuroshop.Models.ANNModel;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class InitANN implements Observer {

    private ANNModel annModel;

    public InitANN() {

//        DataSet dataset = new DataSet("Neuroshop\\Ressources\\SavedData", "new_data.txt" ); // Spalten müssen mit "," getrennt werden
//
//        annModel.setDataset(dataset.getData());
//        ANNLearn aL = new ANNLearn();
//
//        aL.initModel(annModel);
    }

    private void loadDataSet() {
        String datasetPath = annModel.getDatasetFile().getAbsolutePath();
        datasetPath = datasetPath.substring(0, datasetPath.lastIndexOf("\\")-1);
        DataSet dataSet = new DataSet("Neuroshop\\Ressources\\Data", "new_data.txt" ); // Spalten müssen mit "," getrennt werden
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((String)arg) {
            case "loadDataSet":
                loadDataSet();
        }
    }

    public void initModel(ANNModel annModel) {
        this.annModel = annModel;
    }
}
