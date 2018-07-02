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

public class Presets extends Observable {

    private ArrayList presets = new ArrayList();

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

    public ArrayList getParamsForPreset() {
        presets.add(annModel.getInputColumns());
        presets.add(annModel.getOutputColums());
        presets.add(annModel.getNeuronsInHiddenLayer());
        presets.add(annModel.getNumberOfHiddenLayer());
        presets.add(annModel.getMaxEpoch());
        presets.add(annModel.getLearnmode());
        presets.add(annModel.getLearningRate());
        presets.add(annModel.getMinOverallError());
        presets.add(annModel.getMomentumRate());
        presets.add(annModel.getDataPercentage());
        presets.add(annModel.getDataNormType());
        presets.add(annModel.getActFnc());
        presets.add(annModel.getSgmList());

        return this.presets;
    }

    public void setParamsFromPreset(ArrayList presets) { //es fehlt noch .get() um auf die Einträge zu zugreifen
        this.inputColumns = inputColumns;
        this.outputColumns = outputColumns;
        this.neuronsInHiddenLayer = neuronsInHiddenLayer;
        this.numberOfHiddenLayer = numberOfHiddenLayer;
        this.maxEpoch = maxEpoch;


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
