package Neuroshop.Models;

import Neuroshop.ANN.Data.DataNormalization;
import Neuroshop.ANN.Learn.LearningAlgorithm;
import Neuroshop.ANN.Math.IActivationFunction;
import Neuroshop.ANN.Math.Linear;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

public class ANNModel extends Observable {

<<<<<<< HEAD
    private File dataSetFile;

    private int[] inputColumns;
    private int[] outputColumns;

    private DataNormalization dataNormType;

    private int dataPercentage;
    private int[] numberOfHiddenNeurons;
    private int numberNeuronsHdnLayer;
    private double[][] dataSet;
=======
    private File datasetFile;
    private ArrayList<ArrayList<ArrayList<Double>>> newWeights;
    private double[][] dataset;
>>>>>>> 5c002fae2468e22fe2ed963754a87cb3c1c3e0cf

    private IActivationFunction[] actFnc;
    private IActivationFunction outputActFnc;

    private double minOverallError;
    private double learningRate;
    private double momentumRate;
    private int maxEpochs;

    private LearningAlgorithm.LearningMode lMode;


    private ArrayList<ArrayList<ArrayList<Double>>> newWeights;


    public int[] getInputColumns() {
        return this.inputColumns;
    }

    public void setInputColumns(int[] inputColumns) {
        this.inputColumns = inputColumns;
        this.setChanged();
        this.notifyObservers(inputColumns);
    }

    public int[] getOutputColums() {
        return this.outputColumns;
    }

    public void setOutputColumns(int[] outputColumns) {
        this.outputColumns = outputColumns;
        this.setChanged();
        this.notifyObservers(outputColumns);
    }

    public DataNormalization getDataNormType() {
        return this.dataNormType;
    }

    public void setDataNormType(DataNormalization dataNormType) {
        this.dataNormType = dataNormType;
        this.setChanged();
        this.notifyObservers(dataNormType);
    }

    public LearningAlgorithm.LearningMode getLearnmode() {
        return this.lMode;
    }

    public void setLearnMode(LearningAlgorithm.LearningMode lMode) {
        this.lMode = lMode;
        this.setChanged();
        this.notifyObservers(lMode);
    }

    public int getDataPercentage() {
        return this.dataPercentage;
    }

    public void setDataPercentage(int dataPercentage) {
        this.dataPercentage = dataPercentage;
        this.setChanged();
        this.notifyObservers(dataPercentage);
    }

    public int[] getNumberOfHiddenNeurons() {
        return this.numberOfHiddenNeurons;
    }

    public void setNumberOfHiddenNeurons(int[] numberOfHiddenNeurons) {
        this.numberOfHiddenNeurons = numberOfHiddenNeurons;
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
        this.notifyObservers(actFnc);
    }

    public IActivationFunction getOutputActFnc() {
        return this.outputActFnc;
    }

    public void setOutputActFnc(IActivationFunction outputActFnc) {
        this.outputActFnc = outputActFnc;
        this.setChanged();
        this.notifyObservers(outputActFnc);
    }

    public double getMinOverallError() {
        return this.minOverallError;
    }

    public void setMinOverallError(double minOverallError) {
        this.minOverallError = minOverallError;
        this.setChanged();
        this.notifyObservers(minOverallError);
    }

    public double getLearningRate() {
        return this.learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
        this.setChanged();
        this.notifyObservers(learningRate);
    }

    public double getMomentumRate() {
        return this.momentumRate;
    }

    public void setMomentumRate(double momentumRate) {
        this.momentumRate = momentumRate;
        this.setChanged();
        this.notifyObservers(momentumRate);
    }

    public int getMaxEpochs() {
        return this.maxEpochs;
    }

    public void setMaxEpochs(int iterations) {
        this.maxEpochs = iterations;
        this.setChanged();
        this.notifyObservers(iterations);
    }

    public void setNewWeights(ArrayList<ArrayList<ArrayList<Double>>> newWeights) {
        this.newWeights = newWeights;
    }

    public ArrayList<ArrayList<ArrayList<Double>>> getNewWeights() {
        return newWeights;
    }

    public void setDatasetFile(File dataSetFile) {
        this.datasetFile = dataSetFile;
        setChanged();
        notifyObservers("loadDataSet");
    }

    public File getDatasetFile() {
        return datasetFile;
    }

    public void setDataset(double[][] dataSet) {
        this.dataset = dataSet;
    }

    public double[][] getDataSet() {
        return dataset;
    }
}
