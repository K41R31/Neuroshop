package Neuroshop.ANN;


import Neuroshop.ANN.Data.DataSet;
import Neuroshop.Models.ANNModel;
import Neuroshop.Models.DataModel;

import java.util.Observable;
import java.util.Observer;

public class InitANN implements Observer {

    private ANNModel annModel;
    private DataModel dataModel;
    private DataSet dataset;

    public InitANN() {

//        DataSet dataset = new DataSet("Neuroshop\\Ressources\\SavedData", "new_data.txt" ); // Spalten müssen mit "," getrennt werden
//
//        annModel.setDataset(dataset.getData());
//        ANNLearn aL = new ANNLearn();
//
//        aL.initModel(annModel);
    }

    private void loadDataSet() {

        String datasetPath = dataModel.getDatasetFile().getAbsolutePath();
        dataset = new DataSet(datasetPath); // Spalten müssen mit "," getrennt werden
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((String)arg) {
            case "loadDataSet":
                loadDataSet();
        }
    }

    public void initModel(ANNModel annModel, DataModel dataModel) {
        this.annModel = annModel;
        this.dataModel = dataModel;
    }
}
