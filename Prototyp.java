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

        int[] numberOfHiddenNeurons = {9}; //JA

        double learningRate = (1);
        int maxEpochs = (5000);
        double momentumRate = (0.7);
        double minOverallError = (0.0001);
        int[] inputColumns = {0, 1, 2, 3, 4, 5, 6, 7};
        int[] outputColumns = {8, 9};
        double dataPercentage = (0.8);

        Sigmoid h10Fnc = new Sigmoid(1.0);
//        HyperTan h20Fnc = new HyperTan(2.0);

        IActivationFunction outputActFnc = new Linear (1.0);
        IActivationFunction[] actFnc = {h10Fnc};


        LearningAlgorithm.LearningMode lMode = LearningAlgorithm.LearningMode.BATCH;
        DataNormalization dataNormType = new DataNormalization(0, 1.0);

        DataSet dataSet = new DataSet("Neuroshop\\Ressources\\Data", "diabetes_fulldata.txt" ); // Spalten müssen mit "," getrennt werden

        double[][] dSet = dataSet.getData();
        System.out.println(dataSet.numberOfColumns);



//        for (int r = 0; r < dataSet.numberOfRecords; r++) {
//            for (int c = 0; c < dataSet.numberOfColumns; c++) {
//                System.out.println(dSet[r][c]);
//            }
//        }

        ANNLearn aL = new ANNLearn();
        aL.train(dataSet, inputColumns, outputColumns, dataPercentage, maxEpochs, numberOfHiddenNeurons, minOverallError, learningRate, momentumRate, actFnc, outputActFnc, lMode, dataNormType);

    }
}
