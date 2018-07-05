package Neuroshop.ANN;

import Neuroshop.ANN.Data.DataNormalization;
import Neuroshop.ANN.Data.DataSet;
import Neuroshop.ANN.Data.NeuralDataSet;
import Neuroshop.ANN.Init.UniformInitialization;
import Neuroshop.ANN.Init.WeightInitialization;
import Neuroshop.ANN.Learn.Backpropagation;
import Neuroshop.ANN.Math.*;
import Neuroshop.ANN.Neural.NeuralException;
import Neuroshop.ANN.Neural.NeuralNet;
import Neuroshop.Models.ANNModel;

import java.io.IOException;
import java.util.*;

public class ANNLearn implements Observer {

    private ANNModel annModel;
    private double dataPercentage;
    private int[] inputColumns;
    private int[] outputColumns;
    private double[] nOutTrain;
    private int maxEpochs;
    private double minOverallError;
    private double learningRate;
    private double momentumRate;
//  private IActivationFunction outputActFnc; TODO: Festgelegt auf LINEAR(1.0)
    private IActivationFunction[] actFnc;
    private DataNormalization dataNormType;
    private Thread trainThread;

    public void train() {
        RandomNumberGenerator.setSeed(System.currentTimeMillis());
        annModel.addActFnc();
        this.actFnc = annModel.getActFnc();

        dataNormType = new DataNormalization(DataNormalization.NormalizationTypes.MIN_MAX);
        IActivationFunction outputActFnc = new Linear(1.0);
        NeuralNet nnWidget = new NeuralNet(inputColumns.length, outputColumns.length, annModel.getNeuronsInHiddenLayer(), actFnc, outputActFnc, new UniformInitialization(-1.0, 1.0));
        nnWidget.print();

        double[][] dSet = annModel.getDataSet();
        double[][] dataNormalized = new double[dSet.length][dSet[0].length];
        dataNormalized = dataNormType.normalize(dSet);
        double[][] dataNormToTrain = Arrays.copyOfRange(dataNormalized, 0, (int) Math.ceil(dataNormalized.length * (dataPercentage)));
        double[][] dataNormToTest = Arrays.copyOfRange(dataNormalized, (int) Math.ceil(dataNormalized.length * (dataPercentage)) + 1, dataNormalized.length);

        System.out.println("Gesamter Datensatz: " +Arrays.deepToString(dataNormalized));
        System.out.println("Datensatz zum Trainieren: " + Arrays.deepToString(dataNormToTrain));
        System.out.println("Datensatz zum Testen: " + Arrays.deepToString(dataNormToTest));

        NeuralDataSet neuralDataSetToTrain = new NeuralDataSet(dataNormToTrain, inputColumns, outputColumns);
        NeuralDataSet neuralDataSetToTest = new NeuralDataSet(dataNormToTest, inputColumns, outputColumns);
        Backpropagation backprop = new Backpropagation(nnWidget, neuralDataSetToTrain, annModel.getLearnmode());
        System.out.println(maxEpochs);
        backprop.initModel(annModel);
        backprop.setLearningRate(learningRate);
        backprop.setMaxEpochs(maxEpochs);
        backprop.setGeneralErrorMeasurement(Backpropagation.ErrorMeasurement.SimpleError);
        backprop.setOverallErrorMeasurement(Backpropagation.ErrorMeasurement.MSE);
        backprop.setMomentumRate(momentumRate);
        backprop.setMinOverallError(minOverallError);
        backprop.setTestingDataSet(neuralDataSetToTest);
//        backprop.printTraining = true;
//        backprop.showPlotError = true;


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

            System.out.println("Gesamter Fehler:" + String.valueOf(backprop.getOverallGeneralError()));
            System.out.println("Minimaler Fehler:" + String.valueOf(backprop.getMinOverallError()));
            System.out.println("Epochen:" + String.valueOf(backprop.getEpoch()));

//            backprop.showErrorEvolution();
            neuralDataSetToTrain.printTargetOutput();
            neuralDataSetToTest.printTargetOutput();

            backprop.forward();
//            backprop.print();

            annModel.save();

            neuralDataSetToTrain.printNeuralOutput();


        } catch (NeuralException ne) {
            ne.printStackTrace();
        }
    }


    public void validate() {

        this.actFnc = annModel.getActFnc();
        dataNormType = new DataNormalization(DataNormalization.NormalizationTypes.MIN_MAX);
        IActivationFunction outputActFnc = new Linear(1.0);
        NeuralNet nnValid = new NeuralNet(inputColumns.length, outputColumns.length, annModel.getNeuronsInHiddenLayer(), actFnc, outputActFnc);



    }
    private void loadDataSet() {
        String datasetPath = annModel.getDatasetFile().getAbsolutePath();
        DataSet dataset = new DataSet(datasetPath); // Spalten müssen mit "," getrennt werden
        annModel.setDataColumns(dataset.numberOfColumns);
        annModel.setNumberOfRecords(dataset.numberOfRecords);
        annModel.setDataset(dataset.getData());
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((String)arg) {
            case "setDatasetFile":
                loadDataSet();
                break;
            case "setInputColumns":
                this.inputColumns = annModel.getInputColumns();
                break;
            case "setOutputColumns":
                this.outputColumns = annModel.getOutputColums();
                break;
            case "setDataPercentage":
                this.dataPercentage = annModel.getDataPercentage();
                break;
            case "setMomentumRate":
                this.momentumRate = annModel.getMomentumRate();
                break;
            case "setMaxEpoch":
                this.maxEpochs = annModel.getMaxEpoch();
                break;
            case "setMinOverallError":
                this.minOverallError = annModel.getMinOverallError();
                break;
            case "setLearningRate":
                this.learningRate = annModel.getLearningRate();
                break;
            case "train":
                trainThread = new Thread((this::train));
                trainThread.start();
                break;
            case "stop":
                trainThread.stop();
        }
    }

    public void initModel(ANNModel annModel) {
        this.annModel = annModel;
    }

}
