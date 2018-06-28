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

public class InitANN implements Observer{

    private ANNModel annModel;
    private File datasetFile;

    public InitANN() {

        DataSet dataSet = new DataSet("Neuroshop\\Ressources\\Data", "new_data.txt" ); // Spalten müssen mit "," getrennt werden

        annModel.setDataset(dataSet.getData());
        ANNLearn aL = new ANNLearn();

        aL.initModel(annModel);
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((String)arg) {
            case "rawDataSet":
                this.datasetFile = annModel.getDatasetFile();
        }
    }

    public void initModel(ANNModel annModel) {
        this.annModel = annModel;
    }
}
