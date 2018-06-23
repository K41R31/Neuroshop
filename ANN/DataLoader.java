package Neuroshop.ANN;

import Neuroshop.ANN.Data.DataSet;
import Neuroshop.Start;
import javafx.stage.FileChooser;

import javax.xml.crypto.Data;
import java.io.File;


public class DataLoader {

    private DataSet _inputData;

        public DataSet getDataSet() {
        _inputData = new DataSet("Neuroshop/Ressources/Data", "new_data.txt");
       return this._inputData;
      }





}
