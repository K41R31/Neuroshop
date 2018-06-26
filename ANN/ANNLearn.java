package Neuroshop.ANN;

import Neuroshop.ANN.Data.DataNormalization;
import Neuroshop.ANN.Data.DataSet;
import Neuroshop.ANN.Data.NeuralDataSet;
import Neuroshop.ANN.Learn.Backpropagation;
import Neuroshop.ANN.Learn.LearningAlgorithm;
import Neuroshop.ANN.Math.ArrayOperations;
import Neuroshop.ANN.Math.IActivationFunction;
import Neuroshop.ANN.Math.Linear;
import Neuroshop.ANN.Math.RandomNumberGenerator;
import Neuroshop.ANN.Neural.NeuralException;
import Neuroshop.ANN.Neural.NeuralNet;
import Neuroshop.Models.WidgetModels.NeuralNetWidgetModel;
import java.util.ArrayList;
import java.util.Arrays;

public class ANNLearn {

    NeuralNet nnWidget;

    DataSet dataSet;
    double[][] dataNormToTest;
    double[][] getDataNormToTrain;

    int numberOfInputs;
    int numberOfOutputs;
    int dataPercentage;
    int iterations;
    int[] inputColumns;
    int[] outputColumns;
    int[] numberOfHiddenNeurons;

    double minOverallError;
    double learningRate;
    double momentumRate;
    double[][] dataNormalized;

    IActivationFunction[] actFnc;
    Linear outputActFnc;
    LearningAlgorithm.LearningMode lMode;

    DataNormalization dataNormType;

    NeuralDataSet neuralDataSetToTest;
    NeuralDataSet neuralDataSetToTrain;


    public void train(DataSet dataSet, int[] numberOfHiddenNeurons, double learningRate, DataNormalization dataNormType, int iterations, double momentumRate, double minOverallError) {

        RandomNumberGenerator.setSeed(System.currentTimeMillis());

        ANNLearn annLearn = new ANNLearn();

        LearningAlgorithm.LearningMode lMode = new NeuralNetWidgetModel().getLearnmode();

        this.minOverallError = minOverallError;
        this.learningRate = learningRate;
        this.momentumRate = momentumRate;
        this.iterations = iterations;

        annLearn.createNN(dataSet, numberOfInputs, numberOfOutputs, inputColumns, outputColumns, numberOfHiddenNeurons, dataNormType, dataPercentage, actFnc, outputActFnc);

        Backpropagation backprop = new Backpropagation(nnWidget, neuralDataSetToTrain, lMode);
        backprop.setLearningRate(learningRate);
        backprop.setMaxEpochs(iterations);
        backprop.setGeneralErrorMeasurement(Backpropagation.ErrorMeasurement.SimpleError);
        backprop.setGeneralErrorMeasurement(Backpropagation.ErrorMeasurement.MSE);
        backprop.setMinOverallError(minOverallError);
        backprop.setMomentumRate(momentumRate);
        backprop.setTestingDataSet(neuralDataSetToTest);
        backprop.printTraining = true;
        backprop.showPlotError = true;

        try {
            backprop.forward();

            backprop.train();

            if (backprop.getMinOverallError() >= backprop.getOverallGeneralError()) {
                System.out.println("Training erfolgreich beendet!");
            } else {
                System.out.println("Training ist gescheitert!");
            }

            System.out.println("Overall Error:" + String.valueOf(backprop.getOverallGeneralError()));
            System.out.println("Min Overall Error:" + String.valueOf(backprop.getMinOverallError()));
            System.out.println("Epochs of training:" + String.valueOf(backprop.getEpoch()));

            backprop.showErrorEvolution();

            backprop.forward();

        } catch (NeuralException e) {
            e.printStackTrace();
        }

        try {

            backprop.test();

            ArrayList<ArrayList<Double>> listOutputValues = neuralDataSetToTest.getArrayNeuralOutputData();

            double[][] dataNormalizedOutputTest = extractMatrixByArrayList(outputColumns, dataNormToTest, listOutputValues);

            double[] dataDenormalizedOutputTest1 = dataNormType.denormalize(ArrayOperations.getColumn(dataNormalizedOutputTest, 0), outputColumns[0]);
            double[] dataDenormalizedOutputTest2 = dataNormType.denormalize(ArrayOperations.getColumn(dataNormalizedOutputTest, 1), outputColumns[1]);

            double[][] dataOutputTestAdapted = adaptData(outputColumns, dataNormToTest, dataDenormalizedOutputTest1,
                    dataDenormalizedOutputTest2);

            ArrayList<ArrayList<Double>> listTargetValues = neuralDataSetToTest.getArrayTargetOutputData();

            double[][] dataTargetOutputTest = extractMatrixByArrayList(outputColumns, dataNormToTest, listTargetValues);

            double[] dataDenormalizedTargetOutputTest1 = dataNormType.denormalize(ArrayOperations.getColumn(dataTargetOutputTest, 0), outputColumns[0]);
            double[] dataDenormalizedTargetOutputTest2 = dataNormType.denormalize(ArrayOperations.getColumn(dataTargetOutputTest, 1), outputColumns[1]);

            double[][] dataOutputTargetTestAdapted = adaptData(outputColumns, dataNormToTest,
                    dataDenormalizedTargetOutputTest1, dataDenormalizedTargetOutputTest2);

            double[][] confusionMatrix = neuralDataSetToTest.outputData.calculateConfusionMatrix(dataOutputTestAdapted, dataOutputTargetTestAdapted);

        } catch (Exception e) {
            e.printStackTrace();
        }

}

private void createNN(DataSet dataSet, int numberOfInputs, int numberOfOutputs, int[] inputColumns, int[] outputColumns, int[] numberOfHiddenNeurons, DataNormalization dataNormType, int dataPercentage, IActivationFunction[] actFnc, Linear outputActFnc) {

    this.numberOfInputs = numberOfInputs;
    this.numberOfOutputs = numberOfOutputs;
    this.inputColumns = inputColumns;
    this.outputColumns = outputColumns;

    this.dataSet = dataSet;
    double[][] dSet = dataSet.getData();

    this.dataPercentage = dataPercentage;
    this.numberOfHiddenNeurons = numberOfHiddenNeurons;
    // int numberNeuronsHdnLayer = new NeuralNetWidgetModel().getNumberNeuronsHdnLayer();
    this.actFnc = actFnc;
    this.outputActFnc = outputActFnc;
    this.dataNormType = dataNormType;

    double[][] dataNormalized = new double[dSet.length][dSet[0].length];
    dataNormalized = dataNormType.normalize(dSet);

    double[][] dataNormToTrain = Arrays.copyOfRange(dataNormalized, 0, (int) Math.ceil((dataNormalized.length * (dataPercentage / 100))));
    double[][] dataNormToTest = Arrays.copyOfRange(dataNormalized, (int) Math.ceil(dataNormalized.length * (dataPercentage / 100)) + 1, dataNormalized.length);

    NeuralDataSet neuralDataSetToTrain = new NeuralDataSet(dataNormToTrain, inputColumns, outputColumns);
    NeuralDataSet neuralDataSetToTest = new NeuralDataSet(dataNormToTest, inputColumns, outputColumns);


}



        private static double[][] extractMatrixByArrayList(int[] outputColumns, double[][] data, ArrayList<ArrayList<Double>> list) {
            double[][] matrix = new double[data.length][outputColumns.length];
            int i = 0, j = 0;
            for (ArrayList<Double> arrayList : list) {
                for (Double value : arrayList) {
                    matrix[i][j] = value;
                    j++;
                }
                i++;
                j=0;
            }
            return matrix;
        }

        private static double[][] adaptData(int[] outputColumns, double[][] data,
        double[] dataOutput1, double[] dataOutput2) {
            double[][] matrix = new double[data.length][outputColumns.length];
            for (int k = 0; k < dataOutput1.length; k++) {
                double v1 = dataOutput1[k];
                double v2 = dataOutput2[k];
                if (v1 <= 0.50) {
                    matrix[k][0] = 0.0;
                } else {
                    matrix[k][0] = 1.0;
                }
                if (v2 <= 0.50) {
                    matrix[k][1] = 0.0;
                } else {
                    matrix[k][1] = 1.0;
                }
            }
            return matrix;

        }
    }












