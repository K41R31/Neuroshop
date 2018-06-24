package Neuroshop.ANN.Init;

/**
 * This abstract class is responsible to generate initial weights
 * early in the training process
 * 
 * @author Alan de Souza, Fábio Soares
 * @version 0.1
 *
 */
public abstract class WeightInitialization {

	/**
	 * Abstract method to generate initial weights 
	 */
    public abstract double Generate();
    
}
