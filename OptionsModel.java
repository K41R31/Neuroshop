package Neuroshop;

import java.util.Observable;

public class OptionsModel extends Observable {


    public OptionsModel() {
    }

    public void toggleOptions () {
        setChanged();
        notifyObservers("toggleOptions");
    }
}
