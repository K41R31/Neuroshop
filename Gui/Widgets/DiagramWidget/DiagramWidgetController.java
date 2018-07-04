package Neuroshop.Gui.Widgets.DiagramWidget;

import Neuroshop.Gui.Widgets.MakeDraggable;
import Neuroshop.Models.ANNModel;
import Neuroshop.Models.TutorialModel;
import Neuroshop.Models.WidgetContainerModel;
import javafx.fxml.FXML;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.plot.Crosshair;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.layout.StackPane;
import org.jfree.data.xy.XYSeriesCollection;

public class DiagramWidgetController implements Observer {

    private ANNModel annModel;
    private WidgetContainerModel widgetContainerModel;

    private JFreeChart chart;
    private ChartViewer chartViewer;
    private XYSeriesCollection dataset;
    @FXML
    private StackPane rootPane;

    private Crosshair xCrosshair;
    private Crosshair yCrosshair;

    private TutorialModel tutorialModel;

    @FXML
    private void initialize() {
    }



    @Override
    public void update(Observable o, Object arg) {

    }


    public void initModel(ANNModel annModel, WidgetContainerModel widgetContainerModel, TutorialModel tutorialModel) {
        this.annModel = annModel;
        this.widgetContainerModel = widgetContainerModel;
        this.tutorialModel = tutorialModel;
        new MakeDraggable(widgetContainerModel, rootPane);
    }


}