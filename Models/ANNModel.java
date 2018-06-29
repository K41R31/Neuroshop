package Neuroshop.Models;

import Neuroshop.ANN.Data.DataNormalization;
import Neuroshop.ANN.Learn.LearningAlgorithm;
import Neuroshop.ANN.Math.IActivationFunction;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

public class ANNModel extends Observable {

    private File dataSetFile;
    private double[][] dataSet;

    private  int numberOfRecords;
    private int dataColumns;
    private int[] inputColumns;
    private int[] outputColumns;

    private DataNormalization dataNormType;

    private int dataPercentage;
    private int[] numberOfHiddenNeurons;
    private int numberNeuronsHdnLayer;
    

    private ArrayList<ArrayList<ArrayList<Double>>> newWeights;


    private IActivationFunction[] actFnc;
    private IActivationFunction outputActFnc;

    private double lastWeight;
    private double minOverallError;
    private double learningRate;
    private double momentumRate;
    private int maxEpochs;

    private LearningAlgorithm.LearningMode lMode;

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

    public void setDataNormType(DataNormalization dataNormType) {
        this.dataNormType = dataNormType;
        this.setChanged();
        this.notifyObservers("setDataNormType");
    }

    public LearningAlgorithm.LearningMode getLearnmode() {
        return this.lMode;
    }

    public void setLearnMode(LearningAlgorithm.LearningMode lMode) {
        this.lMode = lMode;
        this.setChanged();
        this.notifyObservers("setLearnMode");
    }

    public int getDataPercentage() {
        return this.dataPercentage;
    }

    public void setDataPercentage(int dataPercentage) {
        this.dataPercentage = dataPercentage;
        this.setChanged();
        this.notifyObservers("setDataPercentage");
    }

    public int[] getNumberOfHiddenNeurons() {
        return this.numberOfHiddenNeurons;
    }

    public void setNumberOfHiddenNeurons(int[] numberOfHiddenNeurons) {
        this.numberOfHiddenNeurons = numberOfHiddenNeurons;
        this.setChanged();
        this.notifyObservers("setNumberOfHiddenNeurons");
    }

    public int getNumberNeuronsHdnLayer() {
        return this.numberNeuronsHdnLayer;
    }

    public void setNumberNeuronsHdnLayer(int numberNeuronsHdnLayer) {
        this.numberNeuronsHdnLayer = numberNeuronsHdnLayer;
        this.setChanged();
        this.notifyObservers(numberNeuronsHdnLayer);
    }

    public IActivationFunction[] getActFnc() {
        return this.actFnc;
    }

    public void setActFnc(IActivationFunction[] actFnc) {
        this.actFnc = actFnc;
        this.setChanged();
        this.notifyObservers("setActFnc");
    }

    public IActivationFunction getOutputActFnc() {
        return this.outputActFnc;
    }

    public void setOutputActFnc(IActivationFunction outputActFnc) {
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
        this.notifyObservers(momentumRate);
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

    public int getMaxEpochs() {
        return this.maxEpochs;
    }

    public void setMaxEpochs(int maxEpochs) {
        this.maxEpochs = maxEpochs;
        this.setChanged();
        this.notifyObservers("setMaxEpochs");
    }

    public void setNewWeights(ArrayList<ArrayList<ArrayList<Double>>> newWeights) {
        this.newWeights = newWeights;
        this.setChanged();
        this.notifyObservers("setNewWeights");
    }

    public void saveTempWeights(epochs)
    public ArrayList<ArrayList<ArrayList<Double>>> getNewWeights() {
        return newWeights;
    }
}
