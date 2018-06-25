package Neuroshop.Widgets.DataManagerWidget;

import Neuroshop.Model.WidgetContainerModel;
import Neuroshop.Model.WidgetModels.DataManagerWidgetModel;

public class DataManagerWidgetController {

    private DataManagerWidgetModel dataManagerWidgetModel;
    private WidgetContainerModel widgetContainerModel;

    public void initModel(DataManagerWidgetModel dataManagerWidgetModel, WidgetContainerModel widgetContainerModel) {
        this.dataManagerWidgetModel = dataManagerWidgetModel;
        this.widgetContainerModel = widgetContainerModel;
    }
}
