package Neuroshop.Gui.WidgetMenu;

import Neuroshop.Models.WidgetContainerModel;

import java.util.Observable;
import java.util.Observer;

public class WidgetMenuController implements Observer {

    private WidgetContainerModel widgetContainerModel;

    @Override
    public void update(Observable o, Object arg) {
    }

    public void initModel(WidgetContainerModel widgetContainerModel) {
        this.widgetContainerModel = widgetContainerModel;
    }
}
