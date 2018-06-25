package Neuroshop.Widgets.DiagramWidget;

import Neuroshop.Model.WidgetContainerModel;
import Neuroshop.Model.WidgetModels.DiagramWidgetModel;

public class DiagramWidgetController {

    private DiagramWidgetModel diagramWidgetModel;
    private WidgetContainerModel widgetContainerModel;

    public void initModel(DiagramWidgetModel diagramWidgetModel, WidgetContainerModel widgetContainerModel) {
        this.diagramWidgetModel = diagramWidgetModel;
        this.widgetContainerModel = widgetContainerModel;
    }
}
