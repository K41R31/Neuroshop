package Neuroshop.Gui.Widgets.DiagramWidget;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.fx.interaction.ChartMouseEventFX;
import org.jfree.chart.fx.interaction.ChartMouseListenerFX;
import org.jfree.chart.fx.overlay.CrosshairOverlayFX;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.general.DatasetUtils;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * A demo showing crosshairs that follow the data points on an XYPlot.
 */
public class CrosshairOverlayFXDemo extends Application {

//    series = new XYSeries("S1");
//    series2 = new XYSeries("S2");
//    XYSeriesCollection datasetCollection = new XYSeriesCollection();
//        datasetCollection.addSeries(series);
//        datasetCollection.addSeries(series2);
//    JFreeChart chart = createChart(datasetCollection);
//    chartViewer = new ChartViewer(chart);
//        rootPane.getChildren().add(chartViewer);
//    CrosshairOverlayFX crosshairOverlay = new CrosshairOverlayFX();
//    xCrosshair = new Crosshair(Double.NaN, Color.GRAY,
//                new BasicStroke(0f));
//        xCrosshair.setStroke(new BasicStroke(1.5f,
//                                             BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1,
//                                                     new float[]{2.0f, 2.0f}, 0));
//        xCrosshair.setLabelVisible(true);
//    yCrosshair = new Crosshair(Double.NaN, Color.GRAY,
//                new BasicStroke(0f));
//        yCrosshair.setStroke(new BasicStroke(1.5f,
//                                             BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1,
//                                                     new float[] {2.0f, 2.0f}, 0));
//        yCrosshair.setLabelVisible(true);
//        crosshairOverlay.addDomainCrosshair(xCrosshair);
//        crosshairOverlay.addRangeCrosshair(yCrosshair);
//        Platform.runLater(() -> {
//        chartViewer.getCanvas().addOverlay(crosshairOverlay);
//    });
//    updateChart();
//}
//
//    private JFreeChart createChart(XYDataset dataset) {
//        JFreeChart chart = ChartFactory.createXYLineChart(
//                "CrosshairOverlayDemo1", "X", "Y", dataset);
//        return chart;
//    }
//
//    private void updateChart() {
//        Timeline timeline = new Timeline();
//        timeline.getKeyFrames().addAll(
//                new KeyFrame(new Duration(500), event -> {
//                    series.add(i, Math.random() * 4.0);
//                    series2.add(i, Math.random() * 4.0);
//                    i++;
//                })
//        );
//        timeline.setCycleCount(Animation.INDEFINITE);
//        timeline.play();
//    }

//    }

//    private static XYDataset createDataset() {
//        XYSeriesCollection dataset = new XYSeriesCollection();
//        XYSeries series = new XYSeries("S1");
//        for (int x = 0; x < 10; x++) {
//            for (int i = 0; i < trainingErrors.length; i++)
//                trainerr.add(i, trainingErrors[i]);
//                testerr.add(i, testingErrors[i]);
//                dataset.addSeries(trainerr);
//                dataset.addSeries(testerr);
//        }
//        XYSeriesCollection dataset = new XYSeriesCollection(series);
//        return dataset;
//    }

    private static JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                "CrosshairOverlayDemo1", "X", "Y", dataset);
        return chart;
    }

    /**
     * Adds a chart viewer to the stage and displays it.
     *
     * @param stage  the stage.
     * @throws Exception if something goes wrong.
     */
    @Override
    public void start(Stage stage) throws Exception {
//        stage.setScene(new Scene(new MyDemoPane()));
        stage.setTitle("JFreeChart: CrosshairOverlayFXDemo1.java");
        stage.setWidth(700);
        stage.setHeight(390);
        stage.show();
    }

    /**
     * Entry point.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

