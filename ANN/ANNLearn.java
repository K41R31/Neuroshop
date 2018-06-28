package Neuroshop.ANN;

import Neuroshop.ANN.Data.DataNormalization;
import Neuroshop.ANN.Data.DataSet;
import Neuroshop.ANN.Data.NeuralDataSet;
import Neuroshop.ANN.Init.UniformInitialization;
import Neuroshop.ANN.Learn.Backpropagation;
import Neuroshop.ANN.Learn.LearningAlgorithm;
import Neuroshop.ANN.Math.*;
import Neuroshop.ANN.Neural.NeuralException;
import Neuroshop.ANN.Neural.NeuralNet;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

public class ANNLearn implements Observer {

    private ANNModel annModel;

    double dataPercentage;
    int[] inputColumns;
    int[] outputColumns;
    int[] numberOfHiddenNeurons;
    int maxEpochs;

    double minOverallError;
    double learningRate;
    double momentumRate;

    IActivationFunction[] actFnc;
    IActivationFunction outputActFnc;
    LearningAlgorithm.LearningMode lMode;
    DataNormalization dataNormType;


    public void train(DataSet dataSet, int[] inputColumns, int[] outputColumns, double dataPercentage, int maxEpochs, int[] numberOfHiddenNeurons,
                      double minOverallError, double learningRate, double momentumRate, IActivationFunction[] actFnc, IActivationFunction outputActFnc, LearningAlgorithm.LearningMode lMode, DataNormalization dataNormType) {

        RandomNumberGenerator.setSeed(5);

        this.inputColumns = inputColumns;
        this.outputColumns = outputColumns;

        this.numberOfHiddenNeurons = (numberOfHiddenNeurons);

        this.actFnc = actFnc;
        this.outputActFnc = outputActFnc;

        NeuralNet nnWidget = new NeuralNet(inputColumns.length, outputColumns.length, numberOfHiddenNeurons, actFnc, outputActFnc, new UniformInitialization(0, 1.0));
        nnWidget.print();
        System.out.println(nnWidget.isBiasActive());

        this.dataPercentage = dataPercentage;

        double[][] dSet = dataSet.getData();
        this.dataNormType = dataNormType;
        System.out.print("Geladener Datensatz, unverändert: " + Arrays.deepToString(dSet));

        double[][] dataNormalized = new double[dSet.length][dSet[0].length];
        dataNormalized = dataNormType.normalize(dSet);

        System.out.println("Datensatz normalisiert: " + Arrays.deepToString(dataNormalized));
        System.out.println("Anzahl der Einträge im Datensatz: " + dataNormalized.length);

        double[][] dataNormToTrain = Arrays.copyOfRange(dataNormalized, 0, (int) Math.ceil(dataNormalized.length * (dataPercentage)));
        double[][] dataNormToTest = Arrays.copyOfRange(dataNormalized, (int) Math.ceil(dataNormalized.length * (dataPercentage)) + 1, dataNormalized.length);
        System.out.println("Datensatz zum Trainieren: " + Arrays.deepToString(dataNormToTrain));
        System.out.println("Datensatz zum Testen: " + Arrays.deepToString(dataNormToTest));

        NeuralDataSet neuralDataSetToTrain = new NeuralDataSet(dataNormToTrain, inputColumns, outputColumns);
        NeuralDataSet neuralDataSetToTest = new NeuralDataSet(dataNormToTest, inputColumns, outputColumns);

        this.maxEpochs = maxEpochs;
        this.momentumRate = momentumRate;
        this.learningRate = learningRate;
        this.minOverallError = minOverallError;
        this.lMode = lMode;

        Backpropagation backprop = new Backpropagation(nnWidget, neuralDataSetToTrain, lMode);
        backprop.initModel(annModel);
        backprop.setLearningRate(learningRate);
        backprop.setMaxEpochs(maxEpochs);
        backprop.setGeneralErrorMeasurement(Backpropagation.ErrorMeasurement.SimpleError);
        backprop.setOverallErrorMeasurement(Backpropagation.ErrorMeasurement.MSE);
        backprop.setMomentumRate(momentumRate);
        backprop.setMinOverallError(minOverallError);
        backprop.setTestingDataSet(neuralDataSetToTest);
        backprop.printTraining = true;
        backprop.showPlotError = true;



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
            neuralDataSetToTest.printTargetOutput();
            backprop.forward();



//            backprop.backward();
            neuralDataSetToTrain.printNeuralOutput();


        } catch (NeuralException ne) {
            ne.printStackTrace();
        }
    }

    private void loadDataSet() {
        String datasetPath = annModel.getDatasetFile().getAbsolutePath();
        DataSet dataset = new DataSet(datasetPath); // Spalten müssen mit "," getrennt werden
        annModel.setDataset(dataset.getData());
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((String)arg) {
            case "loadDataSet":
                loadDataSet();
        }
    }

    public void initModel(ANNModel annModel) {
        this.annModel = annModel;
    }
}
