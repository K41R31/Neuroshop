package Neuroshop.Widgets.DataManagerWidget;

import Neuroshop.Main;
import Neuroshop.Models.ANNModel;
import Neuroshop.Models.WidgetContainerModel;
import Neuroshop.Models.WidgetModels.DataManagerWidgetModel;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class DataManagerWidgetController implements Observer {

    private DataManagerWidgetModel dataManagerWidgetModel;
    private ANNModel annModel;
    private WidgetContainerModel widgetContainerModel;

    @FXML
    private StackPane button1Pane;
    @FXML
    private StackPane button2Pane;
    @FXML
    private Text button1Text;
    @FXML
    private Text button2Text;


    @FXML
    private void loadDataSet() throws IOException {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a dataset");

        fileChooser.setInitialDirectory(new File("C:/Users"));

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );
        File dataSetFile = fileChooser.showOpenDialog(Main.primaryStage);

        if (dataSetFile != null) {
            annModel.setDatasetFile(dataSetFile);
            annModel.saveDataFile();
        }

    }

    @Override
    public void update(Observable o, Object arg) {
    }

    @FXML
    private void button1Entered() {
        button1Pane.setStyle("-fx-background-color: #4490ff");
        button1Text.setFill(Color.web("#222222"));
    }

    @FXML
    private void button1Exited() {
        button1Pane.setStyle("-fx-background-color: TRANSPARENT; -fx-border-color: #4490ff; -fx-border-width: 2");
        button1Text.setFill(Color.web("#4490ff"));
    }

    @FXML
    private void button2Entered() {
        button2Pane.setStyle("-fx-background-color: #4490ff");
        button2Text.setFill(Color.web("#222222"));
    }

    @FXML
    private void button2Exited() {
        button2Pane.setStyle("-fx-background-color: TRANSPARENT; -fx-border-color: #4490ff; -fx-border-width: 2");
        button2Text.setFill(Color.web("#4490ff"));
    }

    public void initModel(DataManagerWidgetModel dataManagerWidgetModel, ANNModel annModel, WidgetContainerModel widgetContainerModel) {
        this.dataManagerWidgetModel = dataManagerWidgetModel;
        this.annModel = annModel;
        this.widgetContainerModel = widgetContainerModel;
    }
}
