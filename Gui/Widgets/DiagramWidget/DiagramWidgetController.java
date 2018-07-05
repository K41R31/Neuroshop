package Neuroshop.Gui.Widgets.DiagramWidget;

import Neuroshop.Gui.Widgets.MakeDraggable;
import Neuroshop.Models.ANNModel;
import Neuroshop.Models.TutorialModel;
import Neuroshop.Models.WidgetContainerModel;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.Observable;
import java.util.Observer;
import javafx.scene.layout.StackPane;

public class DiagramWidgetController implements Observer {

    private ANNModel annModel;
    private WidgetContainerModel widgetContainerModel;
    private TutorialModel tutorialModel;

    @FXML
    private StackPane rootPane;
    private XYChart.Series overallErrorSeries;
    private XYChart.Series trainErrorSeries;
    private Timeline updateErrors;
    private LineChart<Number,Number> lineChart;
    private Thread updateThread;


    @FXML
    private void initialize() {
        final NumberAxis xAxis = new NumberAxis();
        xAxis.setTickUnit(100);
        xAxis.setTickLabelGap(0);
        xAxis.setTickLabelFill(Color.web("#888888"));
        xAxis.setTickLabelFont(new Font(10));
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setUpperBound(0.5);
        yAxis.setTickUnit(0.1);
        yAxis.setTickLabelFill(Color.web("#888888"));
        yAxis.setAutoRanging(false);
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setCreateSymbols(false);

        overallErrorSeries = new XYChart.Series();
        overallErrorSeries.setName("test error");
        trainErrorSeries = new XYChart.Series();
        trainErrorSeries.setName("train error");

        lineChart.getData().addAll(overallErrorSeries, trainErrorSeries);
        lineChart.setPrefWidth(Region.USE_COMPUTED_SIZE);
        lineChart.setPrefHeight(Region.USE_COMPUTED_SIZE);

        rootPane.getChildren().add(lineChart);
    }

    private void updateErrorsInSeries() {
        updateErrors = new Timeline();
        updateErrors.getKeyFrames().addAll(
                new KeyFrame(new Duration(50), event -> {
                    overallErrorSeries.getData().add(new XYChart.Data(annModel.getActualEpoch(), annModel.getActualOverallError()));
                    trainErrorSeries.getData().add(new XYChart.Data(annModel.getActualEpoch(), annModel.getActualTestError()));
                })
        );
        updateErrors.setCycleCount(Animation.INDEFINITE);
        updateErrors.play();
    }


    @Override
    public void update(Observable o, Object arg) {
        switch ((String)arg) {
            case "train":
                if (overallErrorSeries.getData().size() > 0 & trainErrorSeries.getData().size() > 0) {
                    overallErrorSeries.getData().clear();
                    trainErrorSeries.getData().clear();
                }
                updateThread = new Thread(this::updateErrorsInSeries);
                updateThread.start();
                break;
            case "stop":
                updateErrors.stop();
                updateThread.stop();
        }
    }


    public void initModel(ANNModel annModel, WidgetContainerModel widgetContainerModel, TutorialModel tutorialModel) {
        this.annModel = annModel;
        this.widgetContainerModel = widgetContainerModel;
        this.tutorialModel = tutorialModel;
        new MakeDraggable(widgetContainerModel, rootPane);
    }
}
