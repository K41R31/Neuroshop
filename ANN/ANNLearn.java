package Neuroshop.ANN;

import Neuroshop.ANN.Data.DataNormalization;
import Neuroshop.ANN.Data.DataSet;
import Neuroshop.ANN.Data.NeuralDataSet;
import Neuroshop.ANN.Init.UniformInitialization;
import Neuroshop.ANN.Learn.Backpropagation;
import Neuroshop.ANN.Learn.LearningAlgorithm;
import Neuroshop.ANN.Math.IActivationFunction;
import Neuroshop.ANN.Math.Linear;
import Neuroshop.ANN.Math.RandomNumberGenerator;
import Neuroshop.ANN.Neural.NeuralException;
import Neuroshop.ANN.Neural.NeuralNet;
import java.util.Arrays;

public class ANNLearn {

    double dataPercentage;
    int[] inputColumns;
    int[] outputColumns;
    int[] numberOfHiddenNeurons;
    int maxEpochs;

    double minOverallError;
    double learningRate;
    double momentumRate;

    IActivationFunction[] actFnc;
    Linear outputActFnc;
    LearningAlgorithm.LearningMode lMode;
    DataNormalization dataNormType;

    public void train(DataSet dataSet, int numberNeuronsHdnLayer, int[] inputColumns, int[] outputColumns, double dataPercentage, int maxEpochs, int[] numberOfHiddenNeurons,
                      double minOverallError, double learningRate, double momentumRate, IActivationFunction[] actFnc, Linear outputActFnc, LearningAlgorithm.LearningMode lMode, DataNormalization dataNormType) {

        RandomNumberGenerator.setSeed(System.currentTimeMillis());

        this.inputColumns = inputColumns;
        this.outputColumns = outputColumns;
        this.numberOfHiddenNeurons = numberOfHiddenNeurons;
        this.actFnc = actFnc;

        this.outputActFnc = outputActFnc;

        NeuralNet nnWidget = new NeuralNet(inputColumns.length, outputColumns.length, numberOfHiddenNeurons, actFnc, outputActFnc, new UniformInitialization(-1.0, 1.0));
        nnWidget.print();

        double[][] dSet = dataSet.getData();
        this.dataNormType = dataNormType;
        this.dataPercentage = dataPercentage;

        double[][] dataNormalized = new double[dSet.length][dSet[0].length];
        dataNormalized = dataNormType.normalize(dSet);

        double[][] dataNormToTrain = Arrays.copyOfRange(dataNormalized, 0, (int) Math.ceil(dataNormalized.length * (dataPercentage)));
        double[][] dataNormToTest = Arrays.copyOfRange(dataNormalized, (int) Math.ceil(dataNormalized.length * (dataPercentage)) + 1, dataNormalized.length);

        NeuralDataSet neuralDataSetToTrain = new NeuralDataSet(dataNormToTrain, inputColumns, outputColumns);

        System.out.println(Arrays.toString(inputColumns));
        System.out.println(Arrays.toString(outputColumns));

        NeuralDataSet neuralDataSetToTest = new NeuralDataSet(dataNormToTest, inputColumns, outputColumns);

        this.maxEpochs = maxEpochs;
        System.out.println(maxEpochs);
        this.momentumRate = momentumRate;
        this.learningRate = learningRate;
        this.minOverallError = minOverallError;
        this.lMode = lMode;

        Backpropagation backprop = new Backpropagation(nnWidget, neuralDataSetToTrain, lMode);
        backprop.setLearningRate(learningRate);
        backprop.setMaxEpochs(maxEpochs);
        backprop.setGeneralErrorMeasurement(Backpropagation.ErrorMeasurement.SimpleError);
        backprop.setGeneralErrorMeasurement(Backpropagation.ErrorMeasurement.MSE);
        backprop.setMinOverallError(minOverallError);
        backprop.setMomentumRate(momentumRate);
        backprop.setTestingDataSet(neuralDataSetToTest);
        backprop.printTraining = true;
        backprop.showPlotError = true;
        System.out.println(dataSet.numberOfRecords);

        try {
            backprop.forward();

            backprop.train();
            neuralDataSetToTest.printInput();
            neuralDataSetToTrain.printInput();

            if (backprop.getMinOverallError() >= backprop.getOverallGeneralError()) {
                System.out.println("Training erfolgreich beendet!");
            } else {
                System.out.println("Training ist gescheitert!");
            }

            System.out.println("Overall Error:" + String.valueOf(backprop.getOverallGeneralError()));
            System.out.println("Min Overall Error:" + String.valueOf(backprop.getMinOverallError()));
            System.out.println("Epochen:" + String.valueOf(backprop.getEpoch()));

            backprop.showErrorEvolution();

            neuralDataSetToTrain.printTargetOutput();

            backprop.forward();

            neuralDataSetToTrain.printNeuralOutput();


        } catch (NeuralException e) {
            e.printStackTrace();
        }

        try {

            backprop.test(); // forward();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
