package Neuroshop.ANN;


import Neuroshop.ANN.Data.DataSet;
import Neuroshop.Models.ANNModel;
import Neuroshop.Models.DataModel;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class InitANN implements Observer{

    private ANNModel annModel;
    private DataModel dataModel;
    private File dataSetFile;

    public InitANN() {

        DataSet dataSet = new DataSet("Neuroshop\\Ressources\\SavedData", "new_data.txt" ); // Spalten müssen mit "," getrennt werden
        dataModel.setDataSet(dataSet.getData());
        ANNLearn aL = new ANNLearn();

        aL.initModel(annModel, dataModel);
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((String)arg) {
            case "rawDataSet":
                this.dataSetFile = dataModel.getDataSetFile();
        }
    }

    public void initModel(ANNModel annModel, DataModel dataModel) {
        this.annModel = annModel;
        this.dataModel = dataModel;
    }
}
