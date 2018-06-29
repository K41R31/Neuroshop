package Neuroshop.Widgets.DataManagerWidget;

import Neuroshop.ANN.ANNModel;
import Neuroshop.Main;

import Neuroshop.Models.LastOpenedFiles;
import Neuroshop.Models.WidgetContainerModel;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class DataManagerWidgetController implements Observer {

    private ANNModel annModel;
    private LastOpenedFiles lastOpened;
    private WidgetContainerModel widgetContainerModel;

    @FXML
    private AnchorPane importPane;
    @FXML
    private StackPane button1Pane;
    @FXML
    private StackPane button2Pane;
    @FXML
    private Text button1Text;
    @FXML
    private Text button2Text;


    @FXML
    private void loadDataSet() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a dataset");

        fileChooser.setInitialDirectory(new File("C:/Users"));

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );
        File datasetFile = fileChooser.showOpenDialog(Main.primaryStage);

        if (datasetFile != null) {
            annModel.setDatasetFile(datasetFile);
            importPane.setOpacity(0);
            importPane.setDisable(true);
        }
    }
/*
    private void initializeDataQueue() {
        ArrayList<String> files = lastOpened.getOpenedFiles();
        if (files.size() == 0) {
            Text text = new Text("No recently opened files");
            text.setFont(new Font("Walkway Bold",16)); //TODO An GUI anpassen, Schriftgröße etc.

        } else {
            for (String fileString : files) {
                if (new File(fileString).exists()) {



                    Image image = new Image(new File(fileString).toURI().toString());
                    ImageView imageView = new ImageView(image);
                    imageView.setOnMousePressed(e -> {
                        quickLoadFile = new File(fileString);
                        loadImage();
                    });
                    imageView.setFitWidth(windowWidth*0.0781 * 1.5);
                    imageView.setFitHeight(windowWidth*0.0781);
                    imageView.setViewport(new Rectangle2D((image.getWidth() - image.getHeight() * 1.5) / 2, 0, image.getHeight() * 1.5, image.getHeight()));
                    imageView.setEffect(new DropShadow(windowWidth*0.0156, Color.BLACK));
                    imageView.setOnMouseEntered(e -> imageView.setEffect(new Glow()));
                    imageView.setOnMouseExited(e -> imageView.setEffect(new DropShadow(windowWidth*0.0156, Color.BLACK)));
                    imageView.getStyleClass().add("images-Queue");
                    list_queueImages.add(imageView);
                }
                else {
                    try {
                        lastOpenedFiles.deleteStringInFile(fileString);
                    } catch (IOException e) {
                        System.out.println("ERROR");
                    }
                }
            }
        }
        updateDataQueue();
    }
*/
    @Override
    public void update(Observable o, Object arg) {
        switch ((String) arg) {

        }
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

    public void initModel(ANNModel annModel, WidgetContainerModel widgetContainerModel) {
        this.annModel = annModel;
        this.widgetContainerModel = widgetContainerModel;
    }
}
