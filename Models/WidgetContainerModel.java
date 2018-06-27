package Neuroshop.Models;

import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Observable;

public class WidgetContainerModel extends Observable {

    private final int totalWidgetsCounter = 3;

    private boolean widgetMenuIsOpen = false;
    private StackPane draggPreview;
    private StackPane[][] widgets = new StackPane[totalWidgetsCounter][2]; //Für jedes Preview eine Zeile
    private int[] widgetState = new int[totalWidgetsCounter]; //0 == preview, 1 == not preview

    public void changeWidgetStateById(String id, int state) {
        for (int i = 0; i < widgets.length; i++)
            if (widgets[i][0].getId().equals(id))
                widgetState[i] = state;
    }

    public void setWidgetMenuIsOpen(boolean widgetMenuIsOpen) {
        this.widgetMenuIsOpen = widgetMenuIsOpen;
    }

    public boolean getWidgetMenuIsOpen() {
        return widgetMenuIsOpen;
    }

    public void setDraggPreview(StackPane draggPreview) {
        this.draggPreview = draggPreview;
        setChanged();
        notifyObservers("draggPreview");
    }

    public void removeDraggPreview() {
        this.draggPreview = null;
        setChanged();
        notifyObservers("removeDraggPreview");
    }

    public StackPane getDraggPreview() {
        return draggPreview;
    }

    public void toggleWidgetMenu() {
        setChanged();
        notifyObservers("toggleWidgetMenu");
    }

    public ArrayList<StackPane> getAllPreviews() {
        ArrayList<StackPane> processList = new ArrayList<>();
        for (int i = 0; i < totalWidgetsCounter; i++) {
            if (widgetState[i] == 0) processList.add(widgets[i][widgetState[0]]);
        }
        return processList;
    }

    public void initWidgets(StackPane[][] widgets) {
        this.widgets = widgets;
    }
}
