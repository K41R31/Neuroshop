package Neuroshop.Models;

import java.util.ArrayList;
import java.util.Observable;

public class ANNModel extends Observable {

    int counter = 0;

    public void setWeights(ArrayList weights) {
        System.out.println(counter);
        counter++;
    }
}
