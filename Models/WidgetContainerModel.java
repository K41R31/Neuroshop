package Neuroshop.Models;

import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Observable;

public class WidgetContainerModel extends Observable {

    private final int totalWidgetsCounter = 3;

    private boolean menuIsBusy = false;
    private boolean widgetMenuIsOpen = false;
    private StackPane bufferedWidget;
    private StackPane[][] widgets = new StackPane[totalWidgetsCounter][2]; //Für jedes Preview eine Zeile
    private int[] widgetState = new int[totalWidgetsCounter]; //0 == preview, 1 == not preview

    public void addWidgetToWhiteboard(String id) {
        for (int i = 0; i < totalWidgetsCounter; i++) {
            if (widgets[i][0].getId().equals(id)) {
                bufferedWidget = widgets[i][1];
            }
        }
        setChanged();
        notifyObservers("addWidgetToWhiteboard");
        initWidgetOnCreated(id); //TODO Später vielleicht anders
    }

    private void initWidgetOnCreated(String id) {
        setChanged();
        notifyObservers("init"+id.replace(" ", ""));
    }

    public boolean getMenuIsBusy() {
        return menuIsBusy;
    }

    public void setMenuIsBusy(boolean menuIsBusy) {
        this.menuIsBusy = menuIsBusy;
    }

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

    public void adddWidgetToMenu() {
        setChanged();
        notifyObservers("adddWidgetToMenu");
    }

    public void setBufferedWidget(StackPane bufferedWidget) {
        this.bufferedWidget = bufferedWidget;
        setChanged();
        notifyObservers("draggPreview");
    }

    public void clearBufferedWidget() {
        this.bufferedWidget = null;
        setChanged();
        notifyObservers("clearBufferedWidget");
    }

    public StackPane getBufferedWidget() {
        return bufferedWidget;
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
