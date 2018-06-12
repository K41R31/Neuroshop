/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Neuroshop.NeuralNet.test;

import Neuroshop.NeuralNet.NeuralException;
import Neuroshop.NeuralNet.NeuralNet;
import Neuroshop.NeuralNet.init.UniformInitialization;
import Neuroshop.NeuralNet.learn.Backpropagation;
import Neuroshop.NeuralNet.learn.LearningAlgorithm;
import Neuroshop.NeuralNet.math.IActivationFunction;
import Neuroshop.NeuralNet.math.Linear;
import Neuroshop.NeuralNet.math.RandomNumberGenerator;
import Neuroshop.NeuralNet.math.Sigmoid;
import Neuroshop.NeuralNet.utils.FileLoader;
import Neuroshop.NeuralNet.utils.NeuralDataSet;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author fab
 */
public class BackpropagationTest {
    public static void main(String[] args){
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(20);

//        NeuralNet nn = new NeuralNet;
        RandomNumberGenerator.seed=850;
        

        
        
        Linear outputAcFnc = new Linear(1.0);
        Sigmoid hl0Fnc = new Sigmoid(1.0);
        Sigmoid hl1Fnc = new Sigmoid(1.0);
        Sigmoid hl2Fnc = new Sigmoid(1.0);
        IActivationFunction[] hiddenAcFnc={hl0Fnc,hl1Fnc,hl2Fnc};
        System.out.println("Creating Neural Network...");

        int numberOfInputs=4;
        int numberOfOutputs=3;
        int[] numberOfHiddenNeurons={6};
        NeuralNet nn = new NeuralNet(numberOfInputs, numberOfOutputs, numberOfHiddenNeurons, hiddenAcFnc, outputAcFnc,new UniformInitialization(-1,1.0));

        System.out.println("Neural Network created!");
        nn.print();
        
        Double[][] _neuralDataSet = FileLoader.readDataFromFile("neuralnet/data/lilie.csv");

        int[] inputColumns = {0,1,2,3};
        int[] outputColumns = {3,4,5};

        NeuralDataSet neuralDataSet = new NeuralDataSet(_neuralDataSet,inputColumns,outputColumns);

        System.out.println("Dataset created");
        neuralDataSet.printInput();
        neuralDataSet.printTargetOutput();
        
        System.out.println("Getting the first output of the neural network");
        
        Backpropagation backprop = new Backpropagation(nn,neuralDataSet,LearningAlgorithm.LearningMode.ONLINE);
        backprop.setLearningRate(0.3);
//        backprop.setMaxEpochs(5000);
        backprop.setGeneralErrorMeasurement(Backpropagation.ErrorMeasurement.SimpleError);
        backprop.setOverallErrorMeasurement(Backpropagation.ErrorMeasurement.MSE);
        backprop.setMinOverallError(0.1);
        backprop.printTraining=true;
        backprop.setMomentumRate(0.7);
        
        try{
            backprop.forward();
            neuralDataSet.printNeuralOutput();
            
            backprop.train();
            System.out.println("End of training");
            if(backprop.getMinOverallError()>=backprop.getOverallGeneralError()){
                System.out.println("Training successful!");
            }
            else{
                System.out.println("Training was unsuccessful");
            }
            System.out.println("Overall Error:"
                        +df.format(backprop.getOverallGeneralError()));
            System.out.println("Min Overall Error:"
                        +df.format(backprop.getMinOverallError()));
            System.out.println("Epochs of training:"
                        +String.valueOf(backprop.getEpoch()));
            
            System.out.println("Target Outputs:");
            neuralDataSet.printTargetOutput();
            
            System.out.println("Neural Output after training:");
            backprop.forward();
            neuralDataSet.printNeuralOutput();
        }
        catch(NeuralException ne){
            
        }

    }
}
