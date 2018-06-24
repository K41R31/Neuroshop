package Neuroshop.ANN.Neural;

import Neuroshop.ANN.Init.UniformInitialization;
import Neuroshop.ANN.Init.WeightInitialization;
import Neuroshop.ANN.Math.IActivationFunction;

import java.util.ArrayList;


/**
 *
 * Neuron
 * This class represents the artificial Neuron, the most basic unit in a neural 
 * network. It encapsulates all attributes and properties related to a neuron, 
 * including weights, inputs, output, activation function, calculation. 
 * One important notation about bias: The weights are numbered from 0 to the 
 * total number of inputs, because the last weight will be included for 
 * multiplying the bias.
 * 
 * @author Alan de Souza, Fábio Soares
 * @version 0.1
 */
public class Neuron {
    
    /**
     * Weights associated with this Neuron
     */
    protected ArrayList<Double> weight;
    /**
     * Inputs of this neuron
     */
    private ArrayList<Double> input;
    /**
     * Output of this neuron, generated by the activation function.
     */
    private Double output;
    /**
     * Value that is passed to the activation function.
     */
    private Double outputBeforeActivation;
    
    /**
     * Number of Inputs. If is 0, it means the neuron wasn't initialized yet.
     */
    private int numberOfInputs = 0;
    
    /**
     * Bias of the neuron. It should be always 1.0, except for the first layer.
     */
    protected Double bias = 1.0;
    /**
     * Activation function of this neuron
     */
    private IActivationFunction activationFunction;
    
    
    private NeuralLayer neuralLayer;
    
    
    private Double firstDerivative;
    /**
     * Neuron dummy constructor
     */
    public Neuron(){
        
    }
    /**
     * Neuron constructor
     * @param numberofinputs Number of Inputs 
     */
    public Neuron(int numberofinputs){
        numberOfInputs=numberofinputs;
        weight=new ArrayList<>(numberofinputs+1);
        input=new ArrayList<>(numberofinputs);
    }
    /**
     * Neuron constructor
     * @param numberofinputs Number of inputs
     * @param iaf Activation function
     */
    public Neuron(int numberofinputs,IActivationFunction iaf){
        numberOfInputs=numberofinputs;
        weight=new ArrayList<>(numberofinputs+1);
        input=new ArrayList<>(numberofinputs);
        activationFunction=iaf;
    }
    
    public void setNeuralLayer(NeuralLayer _neuralLayer){
        if(this.neuralLayer==null){
            this.neuralLayer=_neuralLayer;
        }
    }
    
    
    /**
     * Init
     * This method initializes the neuron by setting randomly its weights
     */
    public void init(){
        init(new UniformInitialization(0.0,1.0));
    }
    
    public void init(WeightInitialization weightInit){
        if(numberOfInputs>0){
            for(int i=0;i<=numberOfInputs;i++){
                double newWeight = weightInit.Generate();
                try{
                    this.weight.set(i, newWeight);
                }
                catch(IndexOutOfBoundsException iobe){
                    this.weight.add(newWeight);
                }
            }
        }
    }
    
    /**
     * setInputs
     * Sets a vector of double-precision values to the neuron input
     * @param values vector of values applied at the neuron input
     */
    public void setInputs(double [] values){
        if(values.length==numberOfInputs){
            for(int i=0;i<numberOfInputs;i++){
                try{
                    input.set(i, values[i]);
                }
                catch(IndexOutOfBoundsException iobe){
                    input.add(values[i]);
                }
            }
        }
    }
    
    /**
     * setInputs
     * Sets an array of values to the neuron's input
     * @param values 
     */
    public void setInputs(ArrayList<Double> values){
        if(values.size()==numberOfInputs){
            input=values;
        }
    }
    
    /**
     * getArrayInputs
     * @return Returns the neuron's inputs in an ArrayList
     */
    public ArrayList<Double> getArrayInputs(){
        return input;
    }
    
    /** 
     * getInputs
     * @return Return the neuron's inputs in a vector
     */
    public double[] getInputs(){
        double[] inputs = new double[numberOfInputs];
        for (int i=0;i<numberOfInputs;i++){
            inputs[i]=this.input.get(i);
        }
        return inputs;
    }
    
    /**
     * setInput
     * Sets a real value at the ith java position of the neuron's inputs
     * @param i neuron input java index 
     * @param value value to be set in the input
     */
    public void setInput(int i,double value){
        if(i>=0 && i<numberOfInputs){
            try{
                input.set(i, value);
            }
            catch(IndexOutOfBoundsException iobe){
                input.add(value);
            }
        }
    }
    
    /**
     * getInput
     * @param i ith java position at the input
     * @return Returns the ith java input
     */
    public double getInput(int i){
        return input.get(i);
    }
    
    /**
     * getWeights
     * @return Returns the neuron's weights in the form of vector
     */
    public double[] getWeights(){
        double[] weights = new double[numberOfInputs+1];
        for(int i=0;i<=numberOfInputs;i++){
            weights[i]=weight.get(i);
        }
        return weights;
    }
    
    public Double getWeight(int i){
        return weight.get(i);
    }
    
    public Double getBias(){
        return weight.get(numberOfInputs);
    }
    
    /**
     * getArrayWeights
     * @return Returns the neuron's weights in the form of Arraylist
     */
    public ArrayList<Double> getArrayWeights(){
        return weight;
    }
    
    /**
     * updateWeight
     * Method used for updating the weight during learning
     * @param i ith java position of the weight
     * @param value value to be updated on the weight
     */
    public void updateWeight(int i, double value){
        if(i>=0 && i<=numberOfInputs){
            weight.set(i, value);
        }
    }
    
    /**
     * getNumberOfInputs
     * @return Returns the number of inputs
     */
    public int getNumberOfInputs(){
        return this.numberOfInputs;
    }
    
    /**
     * setWeight
     * sets the weight at the ith java position
     * @param i ith java position
     * @param value value to be set on the weight
     * @throws NeuralException 
     */
    public void setWeight(int i,double value) throws NeuralException{
        if(i>=0 && i<numberOfInputs){
            this.weight.set(i, value);
        }
        else{
            throw new NeuralException("Invalid weight index");
        }
    }
    
    /**
     * getOutput
     * @return Returns the neuron's output
     */
    public double getOutput(){
        return output;
    }
    
    /**
     * calc
     * Calculates the neuron's output
     */
    public void calc(){
        outputBeforeActivation=0.0;
        if(numberOfInputs>0){
            if(input!=null && weight!=null){
                for(int i=0;i<=numberOfInputs;i++){
                    outputBeforeActivation+=(i==numberOfInputs?bias:input.get(i))*weight.get(i);
                }
            }
        }
        output=activationFunction.calc(outputBeforeActivation);
        if(neuralLayer.getNeuralMode()==NeuralNet.NeuralNetMode.TRAINING){
            firstDerivative=activationFunction.derivative(outputBeforeActivation);
        }
    }
    
    public Double calc(ArrayList<Double> _input){
        Double _outputBeforeActivation=0.0;
        if(numberOfInputs>0){
            if(weight!=null){
                for(int i=0;i<=numberOfInputs;i++){
                    _outputBeforeActivation+=(i==numberOfInputs?bias:_input.get(i))*weight.get(i);
                }
            }
        }
        return activationFunction.calc(_outputBeforeActivation);
    }
    
    public Double calc(Double[] _input){
        Double _outputBeforeActivation=0.0;
        if(numberOfInputs>0){
            if(weight!=null){
                for(int i=0;i<=numberOfInputs;i++){
                    _outputBeforeActivation+=(i==numberOfInputs?bias:_input[i])*weight.get(i);
                }
            }
        }
        return activationFunction.calc(_outputBeforeActivation);
    }
    
    public Double derivative(double[] _input){
        Double _outputBeforeActivation=0.0;
        if(numberOfInputs>0){
            if(weight!=null){
                for(int i=0;i<=numberOfInputs;i++){
                    _outputBeforeActivation+=(i==numberOfInputs?bias:_input[i])*weight.get(i);
                }
            }
        }
        return activationFunction.derivative(_outputBeforeActivation);
    }
    
    public ArrayList<Double> calcBatch(ArrayList<ArrayList<Double>> _input){
        ArrayList<Double> result = new ArrayList<>();
        for(int i=0;i<_input.size();i++){
            result.add(0.0);
            Double _outputBeforeActivation=0.0;
            for(int j=0;j<numberOfInputs;j++){
                _outputBeforeActivation+=(j==numberOfInputs?bias:_input.get(i).get(j))*weight.get(j);
            }
            result.set(i,activationFunction.calc(_outputBeforeActivation));
        }
        return result;
    }
    
    public ArrayList<Double> derivativeBatch(ArrayList<ArrayList<Double>> _input){
        ArrayList<Double> result = new ArrayList<>();
        for(int i=0;i<_input.size();i++){
            result.add(0.0);
            Double _outputBeforeActivation=0.0;
            for(int j=0;j<numberOfInputs;j++){
                _outputBeforeActivation+=(j==numberOfInputs?bias:_input.get(i).get(j))*weight.get(j);
            }
            result.set(i,activationFunction.derivative(_outputBeforeActivation));
        }
        return result;
    }
    
    /**
     * setActivationFunction
     * Sets the activation function of this neuron
     * @param iaf Activation function
     */
    public void setActivationFunction(IActivationFunction iaf){
        this.activationFunction=iaf;
    }
    
    /**
     * getOutputBeforeActivation
     * @return Returns the weighted sum of the inputs multiplied by weights
     */
    public double getOutputBeforeActivation(){
        return outputBeforeActivation;
    }

    public void deactivateBias(){
        this.bias=0.0;
    }
    
    public void activateBias(){
        this.bias=1.0;
    }
    
    public double getFirstDerivative(){
        return firstDerivative;
    }
    
    public double getBiasSource(){
        return this.bias;
    }
    
}
