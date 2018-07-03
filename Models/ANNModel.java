package Neuroshop.Models;

import Neuroshop.ANN.Data.DataNormalization;
import Neuroshop.ANN.Learn.LearningAlgorithm;
import Neuroshop.ANN.Math.IActivationFunction;
import Neuroshop.ANN.Math.Sigmoid;
import Neuroshop.Models.TempWeights.Tempweight;
import Neuroshop.Models.TempWeights.TempweightList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ANNModel extends Observable {

    private String filename;
    private File file;
    private File dataSetFile;
    private double[][] dataSet;

    private int numberOfRecords;
    private int dataColumns;
    private int[] inputColumns;
    private int[] outputColumns;
    private DataNormalization dataNormType;
    private double dataPercentage;
    private ArrayList<Integer> neuronsInHiddenLayer;
    private int numberOfHiddenLayer;
    private ArrayList<ArrayList<ArrayList<Double>>> newWeights;
    private IActivationFunction[] actFnc;
    private IActivationFunction outputActFnc;
    private double minOverallError;
    private double learningRate;
    private double momentumRate;
    private int maxEpoch;

    private LearningAlgorithm.LearningMode learnMode;
    private List<Sigmoid> sgmList;
    private List<TempweightList> tempList;

    public void setSigmList(List<Sigmoid> sgmList) {
        SigmoidList sigList = new SigmoidList();
        SigmoidObj sgO = new SigmoidObj();
        for (int i = 0; i < neuronsInHiddenLayer.size(); i++) {
            sigList.addSigmoids(sgO);
            this.sgmList = sgmList;
            setChanged();
            notifyObservers("setSigmList");
        }
    }

    public List<Sigmoid> getSgmList() {
        return this.sgmList;
    }

    public IActivationFunction[] getActFnc() {
        return this.actFnc;
    }

    public void setActFnc(IActivationFunction[] actFnc) {
        this.actFnc = new IActivationFunction[]{};
        setChanged();
        this.notifyObservers("setActFnc");
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
        this.setChanged();
        this.notifyObservers("setInputColumns");
    }

    public int[] getOutputColums() {
        return this.outputColumns;
    }

    public void setOutputColumns(int[] outputColumns) {
        this.outputColumns = outputColumns;
        this.setChanged();
        this.notifyObservers("setOutputColumns");
    }

    public DataNormalization getDataNormType() {
        return this.dataNormType;
    }

    public void setDataNormType(DataNormalization dataNormType) { //TODO: Vorerst auf .MIN_MAX(-1.0, 1.0) festgelegt
        this.dataNormType = dataNormType;
        this.setChanged();
        this.notifyObservers("setDataNormType");
    }

    public LearningAlgorithm.LearningMode getLearnmode() {
        return learnMode;
    }

    public void setLearnMode(LearningAlgorithm.LearningMode learnMode) {
        this.learnMode = LearningAlgorithm.LearningMode.BATCH;
        this.setChanged();
        this.notifyObservers("setLearnMode");
    }

    public double getDataPercentage() {
        return this.dataPercentage;
    }

    public void setDataPercentage(double dataPercentage) {
        this.dataPercentage = dataPercentage;
        this.setChanged();
        this.notifyObservers("setDataPercentage");
        System.out.println("Console from Model: " + dataPercentage);
    }

    public int[] getNeuronsInHiddenLayer() {
        return neuronsInHiddenLayer.stream().mapToInt(i -> i).toArray();
    }

    public void setNeuronsInHiddenLayer(ArrayList<Integer> neuronsInHiddenLayer) {
        this.neuronsInHiddenLayer = neuronsInHiddenLayer;
    }

    public int getNumberOfHiddenLayer() {
        return this.numberOfHiddenLayer;
    } //TODO: NeuronsNumberOfNeurons auslesen!!!!!!!

    public void setNumberOfHiddenLayer(int numberOfHiddenLayer) {
        this.numberOfHiddenLayer = neuronsInHiddenLayer.size();
        this.setChanged();
        this.notifyObservers(numberOfHiddenLayer);
    }

    public IActivationFunction getOutputActFnc() {
        return this.outputActFnc;
    }

    public void setOutputActFnc(IActivationFunction outputActFnc) { //TODO: Vorerst auf LINEAR(1.0) festgelegt
        this.outputActFnc = outputActFnc;
        this.setChanged();
        this.notifyObservers("setOutputActFnc");
    }

    public double getMinOverallError() {
        return this.minOverallError;
    }

    public void setMinOverallError(double minOverallError) {
        this.minOverallError = minOverallError;
        this.setChanged();
        this.notifyObservers("setMinOverallError");
    }

    public double getLearningRate() {
        return this.learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
        this.setChanged();
        this.notifyObservers("setLearningRate");
    }

    public double getMomentumRate() {
        return this.momentumRate;
    }

    public void setMomentumRate(double momentumRate) {
        this.momentumRate = momentumRate;
        this.setChanged();
        this.notifyObservers("setMomentumRate");
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
        this.setChanged();
        this.notifyObservers("setMaxEpoch");
    }

    public void setNewWeights(ArrayList<ArrayList<ArrayList<Double>>> newWeights) {
        this.newWeights = newWeights;
        this.setChanged();
        this.notifyObservers("setNewWeights");
    }

    public ArrayList<ArrayList<ArrayList<Double>>> getNewWeights() {
        return newWeights;
    }



    public void train() {
        setChanged();
        notifyObservers("train");
    }
}


