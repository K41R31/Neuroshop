package Neuroshop.Models.WidgetModels;

import java.util.Observable;

public class DiagramWidgetModel extends Observable {

    double[][] errors;

    public double[][] getErrorEvolution() {
        return this.errors;
    }

    public void setErrorEvolution(double[][] errors) {
        this.errors = errors;
        this.setChanged();
        this.notifyObservers(errors);
    }

    public void saveErrors() {
        System.out.print(getErrorEvolution().toString());

    }
}
