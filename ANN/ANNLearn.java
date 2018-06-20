package Neuroshop.ANN;

import Neuroshop.ANN.Data.DataNormalization;
import Neuroshop.ANN.Data.NeuralDataSet;
import Neuroshop.ANN.Init.UniformInitialization;
import Neuroshop.ANN.Learn.Backpropagation;
import Neuroshop.ANN.Math.IActivationFunction;
import Neuroshop.ANN.Math.Linear;
import Neuroshop.ANN.Math.RandomNumberGenerator;
import Neuroshop.ANN.Neural.NeuralNet;

import java.util.Arrays;

public class ANNLearn {

    NeuralNet nn;

    private NeuralDataSet _dataToTrain;
    private NeuralDataSet _dataToTest;




    public void train (int NumberNeuronsHdnLayer, double _learningRate, DataNormalization _dataNorm, int _iterations, double _momentum, double _minOverallError) {
        RandomNumberGenerator.setSeed(System.currentTimeMillis());

        ANNLearn aNN = new ANNLearn();

        Backpropagation.backprop = new Backpropagation(nn,  )

        }

    public NeuralNet createANN() {

        int numberOfInputs = new ANNParams().getNumberOfInputs();
        int numberOfOutputs = new ANNParams().getNumberOfOutputs();
        int[] inputColumns = new ANNParams().getInputColumns();
        int[] outputColumns = new ANNParams().getOutputColums();

        //Datensatz reinladen aus Pfad
        double[][] dataSet = new DataLoader().getDataSet();

        int dataPercentage = new ANNParams().getDataPercentage();
        int[] numberOfHiddenNeurons = new ANNParams().getNumberOfHiddenNeurons();
        int numberNeuronsHdnLayer = new ANNParams().getNumberNeuronsHdnLayer();
        IActivationFunction[] actFnc = new ANNParams().getActFnc();
        Linear outputActFnc = new ANNParams().getOutputActFnc();

        double minOverallError = new ANNParams().getMinOverallError();
        double learningRate = new ANNParams().getLearningRate();
        double momentumRate = new ANNParams().getMomentumRate();
        int iterations = new ANNParams().getIterations();

        this.nn = new NeuralNet(numberOfInputs, numberOfOutputs, numberOfHiddenNeurons,actFnc, outputActFnc, new UniformInitialization(-1.0 , 1.0));

        DataNormalization dataNormType = new ANNParams().getDataNormType();

        double[][] dataNormalized = new double[dataSet.length][dataSet[0].length];
        dataNormalized = dataNormType.normalize(dataSet);

        double[][] dataNormToTrain = Arrays.copyOfRange(dataNormalized, 0, (int) Math.ceil((dataNormalized.length* (dataPercentage/100))));
        double[][] dataNormToTest = Arrays.copyOfRange(dataNormalized, (int) Math.ceil(dataNormalized.length*(dataPercentage/100))+1, dataNormalized.length);

        NeuralDataSet neuralDataSetToTrain = new NeuralDataSet(dataNormToTrain, inputColumns, outputColumns);
        NeuralDataSet neuralDataSetToTest = new NeuralDataSet(dataNormToTest, inputColumns, outputColumns);

        return this.nn;

    }
    }












