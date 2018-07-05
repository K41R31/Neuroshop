package Neuroshop.Models;

import Neuroshop.ANN.Data.DataNormalization;
import Neuroshop.ANN.Learn.LearningAlgorithm;
import Neuroshop.ANN.Math.IActivationFunction;
import Neuroshop.ANN.Math.Sigmoid;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ANNModel extends Observable {

    private File dataSetFile;
    private double[][] dataSet;
    private double[] output;
    private int numberOfRecords;
    private int dataColumns;
    private int[] inputColumns;
    private int[] outputColumns;
    private DataNormalization dataNormType;
    private double dataPercentage;
    private ArrayList<Integer> neuronsInHiddenLayer;
    private ArrayList<ArrayList<ArrayList<Double>>> newWeights;
    private ArrayList<IActivationFunction> actFnc;
    private IActivationFunction outputActFnc;
    private LearningAlgorithm.LearningMode learnMode;
    private double minOverallError;
    private double learningRate;
    private double momentumRate;
    private double overallError;
    private double testError;
    private double seed;
    private int maxEpoch;
    private int actualEpoch;
    private boolean isTraining = false;
    private String filename;

    public void addActFnc() {
        actFnc = new ArrayList<>();
        for (int i = 0; i < neuronsInHiddenLayer.size(); i++) {
            actFnc.add(new Sigmoid(1.0));
        }
    }

    public IActivationFunction[] getActFnc() {
        IActivationFunction[] ia = new IActivationFunction[actFnc.size()];
        for (int i = 0; i < actFnc.size(); i++) {
            ia[i] = actFnc.get(i);
        }
        return ia;
    }

    public int getDataColumns() {
        return dataColumns;
    }

    public void setDataColumns(int dataColumns) {
        this.dataColumns = dataColumns;
    }

    public int[] getInputColumns() {
        return this.inputColumns;
    }

    public void setInputColumns(int[] inputColumns) {
        this.inputColumns = inputColumns;
        setChanged();
        notifyObservers("setInputColumns");
    }

    public int[] getOutputColums() {
        return this.outputColumns;
    }

    public void setOutputColumns(int[] outputColumns) {
        this.outputColumns = outputColumns;
        setChanged();
        notifyObservers("setOutputColumns");
    }

    public DataNormalization getDataNormType() {
        return this.dataNormType;
    }

    public void setDataNormType(String normtype) {
        double _min = -1.0;
        double _max = 1.0;
        double _score = 1.0;
        switch (normtype) {
            case "minMax":
                this.dataNormType = new DataNormalization(_min, _max);
                break;
            case "zScore":
                this.dataNormType = new DataNormalization(_score);
        }
        setChanged();
        notifyObservers("setDataNormType");
    }

    public LearningAlgorithm.LearningMode getLearnmode() {
        return this.learnMode;
    }

    public void setLearnmode(String mode) {
        switch (mode) {
            case "batchMode":
                this.learnMode = LearningAlgorithm.LearningMode.BATCH;
                break;
            case "onlineMode":
                this.learnMode = LearningAlgorithm.LearningMode.ONLINE;
        }
    }

    public double getDataPercentage() {
        return this.dataPercentage;
    }

    public void setDataPercentage(double dataPercentage) {
        this.dataPercentage = dataPercentage;
        setChanged();
        notifyObservers("setDataPercentage");
    }

    public int[] getNeuronsInHiddenLayer() {
        System.out.println(neuronsInHiddenLayer.get(0));
        return neuronsInHiddenLayer.stream().mapToInt(i -> i).toArray();
    }

    public void setNeuronsInHiddenLayer(ArrayList<Integer> neuronsInHiddenLayer) {
        this.neuronsInHiddenLayer = neuronsInHiddenLayer;
    }

    public IActivationFunction getOutputActFnc() {
        return this.outputActFnc;
    }

    public void setOutputActFnc(IActivationFunction outputActFnc) { //TODO: Vorerst auf LINEAR(1.0) festgelegt
        this.outputActFnc = outputActFnc;
        setChanged();
        notifyObservers("setOutputActFnc");
    }

    public double getMinOverallError() {
        return this.minOverallError;
    }

    public void setMinOverallError(double minOverallError) {
        this.minOverallError = minOverallError;
        setChanged();
        notifyObservers("setMinOverallError");
    }

    public double getLearningRate() {
        return this.learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
        setChanged();
        notifyObservers("setLearningRate");
    }

    public double getMomentumRate() {
        return this.momentumRate;
    }

    public void setMomentumRate(double momentumRate) {
        this.momentumRate = momentumRate;
        setChanged();
        notifyObservers("setMomentumRate");
    }

    public void setNumberOfRecords(int numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }

    public int getNumberOfRecords() {
        return numberOfRecords;
    }

    public void setDatasetFile(File dataSetFile) {
        this.dataSetFile = dataSetFile;
        setChanged();
        notifyObservers("setDatasetFile");
    }

    public File getDatasetFile() {
        return dataSetFile;
    }

    public void setDataset(double[][] dataSet) {
        this.dataSet = dataSet;
        setChanged();
        notifyObservers("initDataManager");
    }

    public double[][] getDataSet() {
        return dataSet;
    }

    public int getMaxEpoch() {
        return this.maxEpoch;
    }

    public void setMaxEpoch(int maxEpoch) {
        this.maxEpoch = maxEpoch;
        setChanged();
        notifyObservers("setMaxEpoch");
    }

    public void setNewWeights(ArrayList<ArrayList<ArrayList<Double>>> newWeights) {
        this.newWeights = newWeights;
    }

    public void addNewWeights(ArrayList<ArrayList<ArrayList<Double>>> newWeights) {
//        FileWriter file = new File("Neuroshop\\Ressources\\SavedData\\Tempweights");
    }

    public ArrayList<ArrayList<ArrayList<Double>>> getNewWeights() {
        return newWeights;
    }

    public void setActualEpoch(int actualEpoch) {
        this.actualEpoch = actualEpoch;
    }

    public int getActualEpoch() {
        return actualEpoch;
    }

    public void setActualOverallError(double overallError)  {
        this.overallError = overallError;
    }

    public double getOverallError() {
        return overallError;
    }

    public void setActualTestError(double testError) {
        this.testError = testError;
    }

    public double getActualTestError() {
        return testError;
    }
    public void setOutputFromTrain(double[] output) {
        this.output = output;
    }

    public double[] getOutputForResults() {
        return output;
    }

    public void setSeed(double seed) {
        this.seed = seed;
    }

    public double getSeed() {
        return seed;
    }

    public void train() {
        setChanged();
        notifyObservers("train");
        isTraining = true;
    }

    public void stop() {
        setChanged();
        notifyObservers("stop");
        isTraining = false;
    }

    public void setFilename(String filename) {
    this.filename = filename;
    }

    public String getFilename() {
    return filename;
    }


    public void save() throws IOException {
        File file = new File(new File("tempWeights.txt"));
        try{
            while(isTraining == true) {

            }
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
        }
    }

    public boolean getIsTraining() {
        return isTraining;
    }
}