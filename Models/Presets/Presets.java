package Neuroshop.Models.Presets;

import Neuroshop.ANN.Data.DataNormalization;
import Neuroshop.ANN.Learn.LearningAlgorithm;
import Neuroshop.ANN.Math.IActivationFunction;
import Neuroshop.ANN.Math.Sigmoid;
import Neuroshop.Models.ANNModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

public class Presets extends Observable {

//    private ArrayList presets = new ArrayList();

    private List<Object> presets = new ArrayList<Object>();
    private ANNModel annModel;

    private File file;
    private String filename;
    private String name;

    private int[] inputColumns;
    private int[] outputColumns;
    private int[] neuronsInHiddenLayer;
    private int numberOfHiddenLayer;
    private int maxEpoch;
    private LearningAlgorithm.LearningMode learnMode;
    private double learningRate;
    private double minOverallError;
    private double momentumRate;
    private double dataPercentage;
    private DataNormalization dataNormType;
    private IActivationFunction[] actFnc;
    private List<Sigmoid> sgmList;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
        setChanged();
    }

    public void addParamsForPreset (List<Object> presets) throws IOException {
        presets.add(0, annModel.getInputColumns());
        presets.add(1, annModel.getOutputColums());
        presets.add(2, annModel.getNeuronsInHiddenLayer());
        presets.add(3, annModel.getNumberOfHiddenLayer());
        presets.add(4, annModel.getMaxEpoch());
        presets.add(5, annModel.getLearnmode());
        presets.add(6, annModel.getLearningRate());
        presets.add(7, annModel.getMinOverallError());
        presets.add(8, annModel.getMomentumRate());
        presets.add(9, annModel.getDataPercentage());
        presets.add(10, annModel.getDataNormType());
        presets.add(11, annModel.getActFnc());
        presets.add(12, annModel.getSgmList());
        this.save();
        setChanged();
        notifyObservers("addNewPresets");
     }

    public void setParamsFromPreset() { //es fehlt noch .get() um auf die Einträge zu zugreifen
          for(Object o : presets) {
            if(o instanceof int[]) {
                int[] inputColumns = (int[]) o;
            }
            if(o instanceof  int[]) {
                int[] outputColumns = (int[]) o;
                this.outputColumns = outputColumns;
            }
            if(o instanceof int[]) {
                int[] neuronsInHiddenLayer = (int[]) o;
                this.neuronsInHiddenLayer = neuronsInHiddenLayer;
            }
            if(o instanceof Number) {
                int numberOfHiddenLayer = (int) o;
                this.numberOfHiddenLayer = numberOfHiddenLayer;
            }
            if(o instanceof Number) {
                int maxEpoch = (int) o;
                this.maxEpoch = maxEpoch;
            }
            if(o instanceof LearningAlgorithm.LearningMode) {
                LearningAlgorithm.LearningMode learnMode = (LearningAlgorithm.LearningMode) o;
                this.learnMode = learnMode;
            }
            this.inputColumns = inputColumns;
            this.outputColumns = outputColumns;
            this.neuronsInHiddenLayer = neuronsInHiddenLayer;
            this.numberOfHiddenLayer = numberOfHiddenLayer;
            this.maxEpoch = maxEpoch;
        }



    }

    public void save() throws IOException {
        file = new File("Neuroshop\\Ressources\\SavedData\\Presets" + File.separator + this.filename);
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
        }
    }

    public void initModel(ANNModel annModel) {
        this.annModel = annModel;
    }
}
