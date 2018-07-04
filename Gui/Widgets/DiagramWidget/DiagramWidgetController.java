package Neuroshop.Gui.Widgets.DiagramWidget;

import Neuroshop.Gui.Widgets.MakeDraggable;
import Neuroshop.Models.ANNModel;
import Neuroshop.Models.TutorialModel;
import Neuroshop.Models.WidgetContainerModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.fx.interaction.ChartMouseListenerFX;
import org.jfree.chart.fx.overlay.CrosshairOverlayFX;
import org.jfree.chart.plot.Crosshair;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.layout.StackPane;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
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

    private void chart() {

//        XYDataset dataset = this.dataset;
//        JFreeChart chart = this.chart;
//        this.chartViewer = new ChartViewer(chart);
//        this.chartViewer.addChartMouseListener();
//
//        CrosshairOverlayFX crosshairOverlay = new CrosshairOverlayFX();
//        this.xCrosshair = new Crosshair(Double.NaN, Color.GRAY,
//                new BasicStroke(0f));
//        this.xCrosshair.setStroke(new BasicStroke(1.5f,
//                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1,
//                new float[]{2.0f, 2.0f}, 0));
//        this.xCrosshair.setLabelVisible(true);
//        this.yCrosshair = new Crosshair(Double.NaN, Color.GRAY,
//                new BasicStroke(0f));
//        this.yCrosshair.setStroke(new BasicStroke(1.5f,
//                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1,
//                new float[] {2.0f, 2.0f}, 0));
//        this.yCrosshair.setLabelVisible(true);
//        crosshairOverlay.addDomainCrosshair(xCrosshair);
//        crosshairOverlay.addRangeCrosshair(yCrosshair);
//
//        Platform.runLater(() -> {
//            this.chartViewer.getCanvas().addOverlay(crosshairOverlay);
//        });
//
//    }
//
//    private void updateErrorData() {
//        double[][] ed = annModel.getErrorData();
//        XYSeries trainingErrors = new XYSeries("Training Error");
//        XYSeries testingErrors = new XYSeries("Testing Error");
//        for (int i = 0;i < ed.length; i++) {
//            trainingErrors.add(i, ed[i][1]);
//            testingErrors.add(i, ed[i][2]);
//        }
//        dataset.addSeries(trainingErrors);
//        dataset.addSeries(testingErrors);
//    }
//
//    private JFreeChart createXYLineChart (XYDataset dataset) {
//        JFreeChart chart = ChartFactory.createXYLineChart("DerNameKommtNoch", "X", "Y", dataset, PlotOrientation.HORIZONTAL, true, true, false);
//        return chart;
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