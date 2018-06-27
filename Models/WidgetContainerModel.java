package Neuroshop.Models;

import javafx.scene.layout.StackPane;

import java.util.Observable;

public class WidgetContainerModel extends Observable {

    private int totalWidgetsCounter = 3;

    private StackPane[][] widgets = new StackPane[totalWidgetsCounter][2];
    private int[] widgetState = new int[totalWidgetsCounter]; //0 == preview, 1 == widget

    public void toggleWidgetMenu() {
        setChanged();
        notifyObservers("toggleWidgetMenu");
    }

    public void initWidgets(StackPane[][] widgets) {
        this.widgets = widgets;
    }
}
