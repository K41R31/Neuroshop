package Neuroshop.Models;

import java.util.Observable;

public class WidgetContainerModel extends Observable {

    public void toggleWidgetMenu() {
        setChanged();
        notifyObservers("toggleWidgetMenu");
    }
}
