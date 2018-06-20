package Neuroshop.Model;

import Neuroshop.ANN.Data.DataNormalization;
import Neuroshop.ANN.Math.IActivationFunction;
import Neuroshop.ANN.Math.Linear;

import java.util.Observable;

public class ANNModel extends Observable {

    private int numberOfInputs;
    private int numberOfOutputs;
    private int[] inputColumns;
    private int[] outputColums;

    private DataNormalization dataNormType;

    private int dataPercentage;
    private int[] numberOfHiddenNeurons;
    private int numberNeuronsHdnLayer;

    private IActivationFunction[] actFnc;
    private Linear outputActFnc;

    private double minOverallError;
    private double learningRate;
    private double momentumRate;
    private int iterations;

    public ANNModel() {
    }

    public int getNumberOfInputs() {
        return this.numberOfInputs;
    }

    public void setNumberOfInputs(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
    }

    public int getNumberOfOutputs() {
        return this.numberOfOutputs;
    }

    public void setNumberOfOutputs() {
        this.numberOfOutputs = numberOfOutputs;
    }

    public int[] getInputColumns() {
        return this.inputColumns;
    }

    public void setInputColumns(int[] inputColumns) {
        this.inputColumns = inputColumns;
    }

    public int[] getOutputColums() {
        return this.outputColums;
    }

    public void setOutputColums(int[] outputColums) {
        this.outputColums = outputColums;
    }

    public DataNormalization getDataNormType() {
        return this.dataNormType;
    }

    public void setDataNormType(DataNormalization dataNormType) {
        this.dataNormType = dataNormType;
    }

    public int getDataPercentage() {
        return this.dataPercentage;
    }

    public void setDataPercentage(int dataPercentage) {
        this.dataPercentage = dataPercentage;
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
    }

    public IActivationFunction[] getActFnc() {
        return this.actFnc;
    }

    public void setActFnc(IActivationFunction[] actFnc) {
        this.actFnc = actFnc;
    }

    public Linear getOutputActFnc() {
        return this.outputActFnc;
    }

    public void setOutputActFnc(Linear outputActFnc) {
        this.outputActFnc = outputActFnc;
    }

    public double getMinOverallError() {
        return this.minOverallError;
    }

    public void setMinOverallError(double minOverallError) {
        this.minOverallError = minOverallError;
    }

    public double getLearningRate() {
        return this.learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public double getMomentumRate() {
        return this.momentumRate;
    }

    public void setMomentumRate(double momentumRate) {
        this.momentumRate = momentumRate;
    }

    public int getIterations() {
        return this.iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
}
