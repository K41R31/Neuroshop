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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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


    @FXML
    private void initialize() {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<Number,Number> lineChart =
                new LineChart<>(xAxis, yAxis);

        xAxis.setLabel("test");

        XYChart.Series series = new XYChart.Series();
        series.setName("train error");
        lineChart.setCreateSymbols(false);

        series.getData().add(new XYChart.Data(1, 23));

        rootPane.getChildren().add(lineChart);
        lineChart.getData().add(series);
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
