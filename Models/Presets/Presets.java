package Neuroshop.Models.Presets;

import Neuroshop.ANN.Math.Sigmoid;
import Neuroshop.Models.ANNModel;

import java.util.List;
import java.util.Observable;

public class Presets extends Observable {

    private ANNModel annModel;

    private int[] inputColumns;
    private int[] outputColumns;
    private int[] neuronsInHiddenLayer;
    private int numberOfHiddenLayer;
    private int maxEpochs;

    private double dataPercentage;
    private double minOverallError;
    private double learningRate;
    private double momentumRate;

    private List<Sigmoid> sgmList;

    private int numberOfEntrys;

    public void setInputColumnsFromANN(int[] inputColumns) {
        this.inputColumns = annModel.getInputColumns();
    }

    public int[] getInputColumnsForPreset() {
        return inputColumns;
    }

    public void setOutputColumnsFrommANN(int[] outputColumns) {
        this.outputColumns = annModel.getOutputColums();
    }

    public int[] getOutputColumnsForPreset() {
        return outputColumns;
    }

    public void setNumberOfHiddenNeuronsFromANN(int[] numberOfHiddenNeurons) {
        this.neuronsInHiddenLayer = annModel.getNeuronsInHiddenLayer();
    }

    public int[] getNumberOfHiddenNeuronsForPreset() {
        return neuronsInHiddenLayer;
    }

    public void setNumberOfHiddenLayerFromANN(int numberOfHiddenLayer) {
         this.numberOfHiddenLayer = this.neuronsInHiddenLayer.length;
    }

    public int getNumberOfHiddenLayerForPreset() {
        return numberOfHiddenLayer;
    }

    public void setMaxEpochsFromANN(int maxEpochs) {
        this.maxEpochs = annModel.getMaxEpochs();
    }

    public int getMaxEpochsForPreset(){
        return maxEpochs;
    }

    public void setDataPercentageFrommANN(double dataPercentage) {
        this.dataPercentage = annModel.getDataPercentage();
    }

    public double getDataPercentageForPreset() {
        return dataPercentage;
    }

    public void setMinOverallErrorFromANN(double minOverallError) {
        this.minOverallError = annModel.getMinOverallError();
    }

    public double getMinOverallErrorForPreset() {
        return minOverallError;
    }

    public void setLearningRateFromANN(double learningRate) {
        this.learningRate = annModel.getLearningRate();
    }

    public double getLearningRateForPreset() {
        return learningRate;
    }

    public void setMomentumRateFromANN(double momentumRate) {
        this.momentumRate = annModel.getMomentumRate();
    }

    public double getMomentumRateForPreset() {
        return momentumRate;
    }

    public void setSgmListFromANN(List<Sigmoid> sgmList) {
        this.sgmList = annModel.getSgmList();
    }

    public List<Sigmoid> getSgmListForPreset() {
        return sgmList;
    }

    public int getNumberOfEntrys() {
        this.numberOfEntrys = 10;
        return numberOfEntrys;
    }

    public void initModel(ANNModel annModel) {
        this.annModel = annModel;
    }

}
