package Neuroshop;





import Neuroshop.ANN.ANNLearn;
import Neuroshop.Models.ANNModel;
import Neuroshop.ANN.Data.DataNormalization;
import Neuroshop.ANN.Data.DataSet;
import Neuroshop.ANN.Learn.LearningAlgorithm;
import Neuroshop.ANN.Math.IActivationFunction;
import Neuroshop.ANN.Math.Linear;
import Neuroshop.ANN.Math.Sigmoid;

public class Prototyp {

    private static ANNModel annModel;

    public static void main (String[] args)  {

        annModel = new ANNModel();

        int[] numberOfHiddenNeurons = {6};

        double learningRate = (1);
        int maxEpochs = (500);
        double momentumRate = (0.7);
        double minOverallError = (0.007);
        int[] inputColumns = {0, 1, 2, 3};
        int[] outputColumns = {4};
        double dataPercentage = (0.8);

        Sigmoid h10Fnc = new Sigmoid(1.0);
//        HyperTan h20Fnc = new HyperTan(2.0);

        IActivationFunction outputActFnc = new Linear (1.0);
        IActivationFunction[] actFnc = {h10Fnc};


//
//        LearningAlgorithm.LearningMode lMode = LearningAlgorithm.LearningMode.BATCH;
//        DataNormalization dataNormType = new DataNormalization(0, 1.0);
//
//        DataSet dataSet = new DataSet("Neuroshop\\Ressources\\SavedData\\new_data.txt" ); // Spalten müssen mit "," getrennt werden
//
//        annModel.setDataset(dataSet.getData());
//        ANNLearn aL = new ANNLearn();
//        //Init Model----------------------------------------------------------------------------------------------------
//        aL.initModel(annModel);
//

        LearningAlgorithm.LearningMode lMode = LearningAlgorithm.LearningMode.BATCH;
        DataNormalization dataNormType = new DataNormalization(0, 1.0);

        DataSet dataSet = new DataSet("Neuroshop\\Ressources\\SavedData\\new_data.txt" ); // Spalten müssen mit "," getrennt werden

        annModel.setDataset(dataSet.getData());
        ANNLearn aL = new ANNLearn();
        //Init Model----------------------------------------------------------------------------------------------------
        aL.initModel(annModel);


//        aL.train(dataSet, inputColumns, outputColumns, dataPercentage, maxEpochs, numberOfHiddenNeurons, minOverallError, learningRate, momentumRate, actFnc, outputActFnc, lMode, dataNormType);


    }
}
