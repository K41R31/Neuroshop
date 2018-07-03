package Neuroshop.Gui.Widgets.DiagramWidget;

import Neuroshop.ANN.Learn.Backpropagation;
import Neuroshop.Gui.Widgets.MakeDraggable;
import Neuroshop.Models.ANNModel;
import Neuroshop.Models.WidgetContainerModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.fx.interaction.ChartMouseListenerFX;
import org.jfree.chart.fx.overlay.CrosshairOverlayFX;
import org.jfree.chart.plot.Crosshair;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;
import java.awt.BasicStroke;
import java.awt.Color;
import javafx.scene.layout.StackPane;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.fx.interaction.ChartMouseEventFX;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DatasetUtils;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ui.RectangleEdge;

public class DiagramWidgetController implements Observer {

    private ANNModel annModel;
    private Backpropagation bP;
    private WidgetContainerModel widgetContainerModel;

    @FXML
    private StackPane rootPane;
    private ChartViewer chartViewer;
    private Crosshair xCrosshair;
    private Crosshair yCrosshair;

    @FXML
    private void initialize() {
    }

//    private void chart() {
//        XYDataset dataset = createDataset();
//    }
//
//    private XYDataset createDataset() {
//        double[][] ed = bP.getErrorData();
//        XYSeries series = new XYSeries("S1");
//        for (int i = 0; x < 10; x++) {
//            series.add(x, x + Math.random() * 4.0);
//        }
//        XYSeriesCollection dataset = new XYSeriesCollection(series);
//        return dataset;
//    }

    @Override
    public void update(Observable o, Object arg) {
    }


    public void initModel(ANNModel annModel, WidgetContainerModel widgetContainerModel) {
        this.annModel = annModel;
        this.bP = bP;
        this.widgetContainerModel = widgetContainerModel;
        new MakeDraggable(widgetContainerModel, rootPane);
    }


}