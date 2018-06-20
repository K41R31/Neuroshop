package Neuroshop.ANN;

import Neuroshop.ANN.Data.DataSet;
import Neuroshop.Start;
import javafx.stage.FileChooser;

import javax.xml.crypto.Data;
import java.io.File;


public class DataLoader {

    private DataSet _inputData;
    private double[][] _dataSet;



    public File loadFile() {
        FileChooser fileChooser = new FileChooser();
        configFileChooser(fileChooser);
        return fileChooser.showOpenDialog(Start.primaryStage);
        }

    public void configFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Name des Datensatzes");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );
    }

    public DataSet loadInputData() {
        _inputData = new DataSet(loadFile().getPath());
        return this._inputData;
    }

      public double[][] getDataSet() {
        double[][] _dataSet = _inputData.getData();
        return  this._dataSet;
      }





}
