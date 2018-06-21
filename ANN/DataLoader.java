package Neuroshop.ANN;

import Neuroshop.ANN.Data.DataSet;
import Neuroshop.Start;
import javafx.stage.FileChooser;

import javax.xml.crypto.Data;
import java.io.File;


public class DataLoader {

    private DataSet _inputData;

//    public File loadFile() {
//        FileChooser fileChooser = new FileChooser();
//        configFileChooser(fileChooser);
//        return fileChooser.showOpenDialog(Start.primaryStage);
//        }
//
//    public void configFileChooser(FileChooser fileChooser) {
//        fileChooser.setTitle("Name des Datensatzes");
//        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("All Files", "*.*"),
//                new FileChooser.ExtensionFilter("CSV", "*.csv"),
//                new FileChooser.ExtensionFilter("TXT", "*.txt")
//        );
//    }

        public DataSet getDataSet() {
        _inputData = new DataSet("Neuroshop/Ressources/Data", "new_data.txt");
       return this._inputData;
      }





}
