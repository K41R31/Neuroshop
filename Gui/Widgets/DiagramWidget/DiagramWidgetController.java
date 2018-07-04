package Neuroshop.Gui.Widgets.DiagramWidget;

import Neuroshop.Gui.Widgets.MakeDraggable;
import Neuroshop.Models.ANNModel;
import Neuroshop.Models.TutorialModel;
import Neuroshop.Models.WidgetContainerModel;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.util.Duration;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.fx.overlay.CrosshairOverlayFX;
import org.jfree.chart.plot.Crosshair;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.layout.StackPane;

import java.awt.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class DiagramWidgetController implements Observer {

    private ANNModel annModel;
    private WidgetContainerModel widgetContainerModel;
    private TutorialModel tutorialModel;

    @FXML
    private StackPane rootPane;
    private ChartViewer chartViewer;
    private Crosshair xCrosshair;
    private Crosshair yCrosshair;
    private XYSeries series;
    private XYSeries series2;
    private int i = 0;


    @FXML
    private void initialize() {
        series = new XYSeries("S1");
        series2 = new XYSeries("S2");
        XYSeriesCollection datasetCollection = new XYSeriesCollection();
        datasetCollection.addSeries(series);
        datasetCollection.addSeries(series2);
        JFreeChart chart = createChart(datasetCollection);
        chartViewer = new ChartViewer(chart);
        rootPane.getChildren().add(chartViewer);
        CrosshairOverlayFX crosshairOverlay = new CrosshairOverlayFX();
        xCrosshair = new Crosshair(Double.NaN, Color.GRAY,
                new BasicStroke(0f));
        xCrosshair.setStroke(new BasicStroke(1.5f,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1,
                new float[]{2.0f, 2.0f}, 0));
        xCrosshair.setLabelVisible(true);
        yCrosshair = new Crosshair(Double.NaN, Color.GRAY,
                new BasicStroke(0f));
        yCrosshair.setStroke(new BasicStroke(1.5f,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1,
                new float[] {2.0f, 2.0f}, 0));
        yCrosshair.setLabelVisible(true);
        crosshairOverlay.addDomainCrosshair(xCrosshair);
        crosshairOverlay.addRangeCrosshair(yCrosshair);
        Platform.runLater(() -> {
            chartViewer.getCanvas().addOverlay(crosshairOverlay);
        });
        updateChart();
    }

    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                "CrosshairOverlayDemo1", "X", "Y", dataset);
        return chart;
    }

    private void updateChart() {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(new Duration(500), event -> {
                    series.add(i, Math.random() * 4.0);
                    series2.add(i, Math.random() * 4.0);
                    i++;
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((String)arg) {
            case "initDiagram":

        }
    }


    public void initModel(ANNModel annModel, WidgetContainerModel widgetContainerModel, TutorialModel tutorialModel) {
        this.annModel = annModel;
        this.widgetContainerModel = widgetContainerModel;
        this.tutorialModel = tutorialModel;
        new MakeDraggable(widgetContainerModel, rootPane);
    }
}
