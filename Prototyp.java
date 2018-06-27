package Neuroshop;

import Neuroshop.ANN.ANNLearn;
import Neuroshop.ANN.Data.DataNormalization;
import Neuroshop.ANN.Data.DataSet;
import Neuroshop.ANN.Learn.LearningAlgorithm;
import Neuroshop.ANN.Math.IActivationFunction;
import Neuroshop.ANN.Math.Linear;
import Neuroshop.ANN.Math.Sigmoid;



public class Prototyp {

    public static void main (String[] args)  {

        int x = 5;
        int[] numberOfHiddenNeurons = {x};
        int numberNeuronsHdnlayer = 1;
        double learningRate = 0.1;
        int iterations = 500;
        double momentumRate = 0.7;
        double minOverallError = 0.001;
        int[] inputColumns = {0, 1, 2, 3};
        int[] outputColumns = {1};
        double dataPercentage = 0.8;
        Sigmoid h10Fnc = new Sigmoid(1.0);
        LearningAlgorithm.LearningMode lMode = LearningAlgorithm.LearningMode.ONLINE;
        IActivationFunction[] actFnc = {h10Fnc};
        Linear outputActFnc = new Linear (1.0);
        DataNormalization dataNormType = new DataNormalization(-1.0, 1.0);

        DataSet dataSet = new DataSet("Neuroshop/Ressources/Data", "new_data.txt");
        ANNLearn aL = new ANNLearn();
        aL.train(dataSet, numberNeuronsHdnlayer, inputColumns, outputColumns, dataPercentage, iterations, numberOfHiddenNeurons, minOverallError, learningRate, momentumRate, actFnc, outputActFnc, lMode, dataNormType);


    }


}




