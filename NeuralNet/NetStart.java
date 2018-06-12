/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Neuroshop.NeuralNet;


import Neuroshop.NeuralNet.learn.Backpropagation;
import Neuroshop.NeuralNet.learn.ELM;
import Neuroshop.NeuralNet.learn.LearningAlgorithm;
import Neuroshop.NeuralNet.learn.LevenbergMarquardt;
import Neuroshop.NeuralNet.math.IActivationFunction;
import Neuroshop.NeuralNet.math.Linear;
import Neuroshop.NeuralNet.math.RandomNumberGenerator;
import Neuroshop.NeuralNet.math.Sigmoid;
import Neuroshop.NeuralNet.utils.FileLoader;
import Neuroshop.NeuralNet.utils.NeuralDataSet;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class NetStart {
    public static void main(String[] args){
        RandomNumberGenerator.seed=0;
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(20);

             Double[][] _neuralDataSet = FileLoader.readDataFromFile("Neuroshop/NeuralNet/data/lilie.csv");

        int[] inputColumns = {0,1,2,3};
        int[] outputColumns = {4,5,6};
        
        NeuralDataSet neuralDataSet = new NeuralDataSet(_neuralDataSet,inputColumns,outputColumns);
        
        int numberOfInputs = 4;
        int numberOfOutputs = 3;
        
        int[] numberOfHiddenNeurons={6};
        
        Linear outputAcFnc = new Linear(1.0);
        Sigmoid hdAcFnc = new Sigmoid(1.0);
//        Sigmoid h10AcFnc = new Sigmoid(1.0);

        IActivationFunction[] hiddenAcFnc={hdAcFnc, /*h10AcFnc*/};
        
        NeuralNet nnlm = new NeuralNet(numberOfInputs,numberOfOutputs,numberOfHiddenNeurons,hiddenAcFnc,outputAcFnc);
        
        NeuralNet nnelm = new NeuralNet(numberOfInputs,numberOfOutputs,numberOfHiddenNeurons,hiddenAcFnc,outputAcFnc);
        
        
        LevenbergMarquardt lma = new LevenbergMarquardt(nnlm,neuralDataSet,LearningAlgorithm.LearningMode.BATCH);
        lma.setDamping(0.001);
        lma.setMaxEpochs(100);
        lma.setGeneralErrorMeasurement(Backpropagation.ErrorMeasurement.SimpleError);
        lma.setOverallErrorMeasurement(Backpropagation.ErrorMeasurement.MSE);
        lma.setMinOverallError(0.0001);
        lma.printTraining=true;
        
        ELM elm = new ELM(nnelm,neuralDataSet);
        elm.setGeneralErrorMeasurement(Backpropagation.ErrorMeasurement.SimpleError);
        elm.setOverallErrorMeasurement(Backpropagation.ErrorMeasurement.MSE);
        elm.setMinOverallError(0.0001);
        elm.printTraining=true;
        
        
        
        try{
            lma.forward();
            neuralDataSet.printNeuralOutput();

            lma.train();
            System.out.println("End of training");
            if(lma.getMinOverallError()>=lma.getOverallGeneralError()){
                System.out.println("Training successful!");
            }
            else{
                System.out.println("Training was unsuccessful");
            }
            System.out.println("Overall Error:"
                        +String.valueOf(lma.getOverallGeneralError()));
            System.out.println("Min Overall Error:"
                        +String.valueOf(lma.getMinOverallError()));
            System.out.println("Epochs of training:"
                        +String.valueOf(lma.getEpoch()));

            System.out.println("Target Outputs:");
            neuralDataSet.printTargetOutput();

            System.out.println("Neural Output after training:");
            lma.forward();
            neuralDataSet.printNeuralOutput();
            
            elm.forward();
            neuralDataSet.printNeuralOutput();

            elm.train();
            System.out.println("End of training");
            if(elm.getMinOverallError()>=elm.getOverallGeneralError()){
                System.out.println("Training successful!");
            }
            else{
                System.out.println("Training was unsuccessful");
            }
            System.out.println("Overall Error:"
                        +df.format(elm.getOverallGeneralError()));
            System.out.println("Min Overall Error:"
                        +df.format(elm.getMinOverallError()));

            System.out.println("Target Outputs:");
            neuralDataSet.printTargetOutput();

            System.out.println("Neural Output after training:");
            elm.forward();
            neuralDataSet.printNeuralOutput();
            

        }
        catch(NeuralException ne){
            
        }
        
    }
}
