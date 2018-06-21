package Neuroshop.Gui.Widgets;

import Neuroshop.Gui.Widgets.NeuralNet.NeuralNetWidgetController;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

public class Widget {

    private Text label;
    private ImageView thumbnail;
    private Rectangle rectangleBorder;
    private StackPane previewPane;
    private VBox widgetPane;

    public Widget(String name, Image thumbnail) throws IOException {
        this.previewPane = new StackPane();
        this.thumbnail = new ImageView(thumbnail);
        this.label = new Text(name);
        this.rectangleBorder = new Rectangle(250, 200);
        initPreview();

        switch (name) {
            case "neuralNet": {
                FXMLLoader widgetViewLoader = new FXMLLoader(getClass().getResource("NeuralNet/NeuralNetWidgetView.fxml"));
                widgetPane = widgetViewLoader.load();
                NeuralNetWidgetController widgetController = widgetViewLoader.getController();
            }
            break;
            case "diagram": {
                //widgetPane = new DiagramWidget();
                widgetPane = new VBox();
                widgetPane.getChildren().add(new ImageView(new Image("Neuroshop/Ressources/resultDiagramFull.png")));
            }
        }
        previewPane.setId(name);
        widgetPane.setId(name);
    }

    private void initPreview() {
        label.setFont(new Font("Caviar Dreams", 35));
        label.setFill(Color.WHITE);
        label.setOpacity(0);
        label.setDisable(true);
        rectangleBorder.setStrokeWidth(2);
        rectangleBorder.setStroke(Color.BLACK);
        rectangleBorder.setFill(Color.TRANSPARENT);
        rectangleBorder.setFill(Color.web("#222222"));
        rectangleBorder.setOpacity(0);
        rectangleBorder.setOnMouseEntered(event -> {
            rectangleBorder.setOpacity(0.7);
            label.setOpacity(1);
        });
        rectangleBorder.setOnMouseExited(event -> {
            rectangleBorder.setOpacity(0);
            label.setOpacity(0);
        });

        previewPane.getChildren().addAll(thumbnail, rectangleBorder, label);
    }

    public String getName() {
        return label.getText();
    }
    public StackPane getPreviewPane() {
        return previewPane;
    }
    public VBox getWidgetPane() {
        return widgetPane;
    }
}
