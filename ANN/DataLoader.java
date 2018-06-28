package Neuroshop.ANN;

import Neuroshop.ANN.Data.DataSet;


public class DataLoader {

    private DataSet _inputData;

        public DataSet getDataSet() {
        _inputData = new DataSet("Neuroshop/Ressources/SavedData", "new_data.txt");
       return this._inputData;
      }





}
