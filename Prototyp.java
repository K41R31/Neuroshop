package Neuroshop;

import Neuroshop.ANN.ANNLearn;
import Neuroshop.ANN.Data.DataNormalization;
import Neuroshop.ANN.Data.DataSet;
import Neuroshop.ANN.Learn.LearningAlgorithm;
import Neuroshop.ANN.Math.HyperTan;
import Neuroshop.ANN.Math.IActivationFunction;
import Neuroshop.ANN.Math.Linear;
import Neuroshop.ANN.Math.Sigmoid;

public class Prototyp {

    public static void main (String[] args)  {

        int[] numberOfHiddenNeurons = {3,2};
        double learningRate = (0.01);
        int maxEpochs = (15000);
        double momentumRate = (0.7);
        double minOverallError = (0.01);
        int[] inputColumns = {0, 1, 2, 3};
        int[] outputColumns = {4};
        double dataPercentage = (0.8);

        Sigmoid h10Fnc = new Sigmoid(1.0);
        HyperTan h20Fnc = new HyperTan();

        IActivationFunction outputActFnc = new Linear (1.0);
        IActivationFunction[] actFnc = {h10Fnc ,h20Fnc};

        LearningAlgorithm.LearningMode lMode = LearningAlgorithm.LearningMode.BATCH;
        DataNormalization dataNormType = new DataNormalization(-1.0, 1.0);

        DataSet dataSet = new DataSet("Neuroshop/Ressources/Data", "new_data.csv");
        ANNLearn aL = new ANNLearn();
        aL.train(dataSet, inputColumns, outputColumns, dataPercentage, maxEpochs, numberOfHiddenNeurons, minOverallError, learningRate, momentumRate, actFnc, outputActFnc, lMode, dataNormType);

    }


}




