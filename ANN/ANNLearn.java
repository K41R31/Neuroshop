package Neuroshop.ANN;

import Neuroshop.ANN.Data.DataNormalization;
import Neuroshop.ANN.Data.DataSet;
import Neuroshop.ANN.Data.NeuralDataSet;
import Neuroshop.ANN.Init.UniformInitialization;
import Neuroshop.ANN.Learn.Backpropagation;
import Neuroshop.ANN.Learn.LearningAlgorithm;
import Neuroshop.ANN.Math.ArrayOperations;
import Neuroshop.ANN.Math.IActivationFunction;
import Neuroshop.ANN.Math.Linear;
import Neuroshop.ANN.Math.RandomNumberGenerator;
import Neuroshop.ANN.Neural.NeuralException;
import Neuroshop.ANN.Neural.NeuralNet;
import Neuroshop.Model.ANNModel;

import java.util.ArrayList;
import java.util.Arrays;

public class ANNLearn {

    public void train() {

        RandomNumberGenerator.setSeed(System.currentTimeMillis());

        NeuralNet nn;

        ANNLearn aNN = new ANNLearn();

        int numberOfInputs = new ANNModel().getNumberOfInputs();
        int numberOfOutputs = new ANNModel().getNumberOfOutputs();
        int[] inputColumns = new ANNModel().getInputColumns();
        int[] outputColumns = new ANNModel().getOutputColums();

        DataSet dataSet = new DataLoader().getDataSet();
        double[][] dSet = dataSet.getData();

        int dataPercentage = new ANNModel().getDataPercentage();
        int[] numberOfHiddenNeurons = new ANNModel().getNumberOfHiddenNeurons();
        int numberNeuronsHdnLayer = new ANNModel().getNumberNeuronsHdnLayer();
        IActivationFunction[] actFnc = new ANNModel().getActFnc();
        Linear outputActFnc = new ANNModel().getOutputActFnc();

        LearningAlgorithm.LearningMode lMode = new ANNModel().getLearnmode();

        double minOverallError = new ANNModel().getMinOverallError();
        double learningRate = new ANNModel().getLearningRate();
        double momentumRate = new ANNModel().getMomentumRate();
        int iterations = new ANNModel().getIterations();

        nn = new NeuralNet(numberOfInputs, numberOfOutputs, numberOfHiddenNeurons, actFnc, outputActFnc, new UniformInitialization(-1.0, 1.0));

        DataNormalization dataNormType = new ANNModel().getDataNormType();

        double[][] dataNormalized = new double[dSet.length][dSet[0].length];
        dataNormalized = dataNormType.normalize(dSet);

        double[][] dataNormToTrain = Arrays.copyOfRange(dataNormalized, 0, (int) Math.ceil((dataNormalized.length * (dataPercentage / 100))));
        double[][] dataNormToTest = Arrays.copyOfRange(dataNormalized, (int) Math.ceil(dataNormalized.length * (dataPercentage / 100)) + 1, dataNormalized.length);

        NeuralDataSet neuralDataSetToTrain = new NeuralDataSet(dataNormToTrain, inputColumns, outputColumns);
        NeuralDataSet neuralDataSetToTest = new NeuralDataSet(dataNormToTest, inputColumns, outputColumns);

        Backpropagation backprop = new Backpropagation(nn, neuralDataSetToTrain, lMode);
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












