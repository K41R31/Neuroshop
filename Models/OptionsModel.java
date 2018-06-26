package Neuroshop.Models;

import java.util.Observable;

public class OptionsModel extends Observable {

    public OptionsModel() {
    }

    public void toggleOptions () {
        setChanged();
        notifyObservers("toggleOptions");
    }
}
