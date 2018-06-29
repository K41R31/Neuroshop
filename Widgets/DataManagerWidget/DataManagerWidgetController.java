package Neuroshop.Widgets.DataManagerWidget;

import Neuroshop.Models.ANNModel;
import Neuroshop.Main;

import Neuroshop.Models.LastOpenedFiles;
import Neuroshop.Models.WidgetContainerModel;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class DataManagerWidgetController implements Observer {

    private ANNModel annModel;
    private LastOpenedFiles lastOpened;
    private WidgetContainerModel widgetContainerModel;
    private ArrayList<ChoiceBox> choosedColumns;

    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane importPane;
    @FXML
    private StackPane button1Pane;
    @FXML
    private StackPane button2Pane;
    @FXML
    private Text testdataValue;
    @FXML
    private Text learndataValue;
    @FXML
    private Text columnsText;
    @FXML
    private Text rowsText;
    @FXML
    private Text button1Text;
    @FXML
    private Text button2Text;
    @FXML
    private VBox counterPane;
    @FXML
    private HBox columnPane;
    @FXML
    private HBox chooserPane;
    @FXML
    private Slider testLearnSlider;

    //Für Mouse Events
    private double sceneCursorPosX, sceneCursorPosY;
    private double nodeTranslatedX, nodeTranslatedY;
    private double sceneWidth, sceneHeight;

    @FXML
    private void initialize() {
        makeDraggable(rootPane);
    }

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
            text.setFont(new Font("Walkway Bold",16));

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

    private void initDataManager() {
        testLearnSlider.setMin(0);
        testLearnSlider.setMax(annModel.getNumberOfRecords());
        choosedColumns = new ArrayList<>();
        columnsText.setText(columnsText.getText()+Integer.toString(annModel.getDataColumns()));
        rowsText.setText(rowsText.getText()+Integer.toString(annModel.getNumberOfRecords()));
        for(int c = 0; c < annModel.getDataColumns(); c++) {
            ChoiceBox<String> cb = new ChoiceBox<>(FXCollections.observableArrayList("Input", "Output"));
            cb.setPrefWidth(50);
            cb.setPrefHeight(30);
            StackPane stackPane = new StackPane(cb);
            choosedColumns.add(cb);
            cb.setOnAction(event -> {
                checkIfReady();
            });
            HBox.setHgrow(stackPane, Priority.ALWAYS);
            chooserPane.getChildren().add(stackPane);
            VColumn vColumn = new VColumn();
            columnPane.getChildren().add(vColumn);
            for (int r = 0; r < annModel.getNumberOfRecords(); r++) {
                double[][] dataset = annModel.getDataSet();
                vColumn.addText(Double.toString(dataset[r][c]));
                if (c == 0) {
                    Text text = new Text(Integer.toString(r));
                    text.setFill(Color.web("#777777"));
                    text.setFont(new Font("Champagne & Limousines", 18));
                    counterPane.getChildren().add(text);
                }
            }
        }
    }

    private void checkIfReady() {
        int[] finalInput;
        int[] finalOutput;
        ArrayList<Integer> input = new ArrayList<>();
        ArrayList<Integer> output = new ArrayList<>();
        for (int i = 0; i < choosedColumns.size(); i++) {
            if (choosedColumns.get(i).getValue() != null) {
                if (choosedColumns.get(i).getValue().equals("Input")) input.add(i);
                else output.add(i);
            } else return;
        }
        finalInput = new int[input.size()];
        finalOutput = new int[output.size()];
        for (int i = 0; i < input.size(); i++) {
            finalInput[i] = input.get(i);
        }
        for (int i = 0; i < output.size(); i++) {
            finalOutput[i] = output.get(i);
        }
        if (finalInput.length < 1 || finalOutput.length < 1) return;
        annModel.setInputColumns(finalInput);
        annModel.setOutputColumns(finalOutput);
    }

    private class VColumn extends VBox {
        VColumn() {
            setSpacing(5);
            setMinWidth(100);
            setPrefWidth(USE_COMPUTED_SIZE);
            setPrefHeight(USE_COMPUTED_SIZE);
            setAlignment(Pos.TOP_CENTER);
            HBox.setHgrow(this, Priority.ALWAYS);
        }

        void addText(String inputText) {
            Text text = new Text(inputText);
            text.setFill(Color.web("#888888"));
            text.setFont(new Font("Champagne & Limousines", 18));
            getChildren().add(text);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((String)arg) {
            case "initDataManager":
                initDataManager();
        }
    }

    @FXML
    private void testLearnSliderSlided() {
        int value = (int)testLearnSlider.getValue();
        learndataValue.setText(String.valueOf(value));
        testdataValue.setText(String.valueOf(testLearnSlider.getMax()-value));
        annModel.setDataPercentage((double)value/100);
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

    private void makeDraggable(Node node) {

        EventHandler<MouseEvent> onMousePressed =
                event -> {
                    sceneCursorPosX = event.getSceneX();
                    sceneCursorPosY = event.getSceneY();
                    nodeTranslatedX = node.getTranslateX();
                    nodeTranslatedY = node.getTranslateY();
                    sceneWidth = node.getScene().getWidth();
                    sceneHeight = node.getScene().getHeight()-30; //-30 weil die obere Leiste 30 Pixel groß ist
                };

        EventHandler<MouseEvent> onMouseDragged =
                event -> {
                    double offsetX = event.getSceneX() - sceneCursorPosX;
                    double offsetY = event.getSceneY() - sceneCursorPosY;
                    double newTranslateX = nodeTranslatedX + offsetX;
                    double newTranslateY = nodeTranslatedY + offsetY;

                    //Collider, der das Objekt stoppt falls es an eine Wand stößt
                    if ((node.getBoundsInParent().getMinX() > 0 || newTranslateX > node.getTranslateX()) & (node.getBoundsInParent().getMaxX() < sceneWidth || newTranslateX < node.getTranslateX())) node.setTranslateX(newTranslateX);
                    if ((node.getBoundsInParent().getMinY() > 0 || newTranslateY > node.getTranslateY()) & (node.getBoundsInParent().getMaxY() < sceneHeight || newTranslateY < node.getTranslateY())) node.setTranslateY(newTranslateY);
                    //Behebt einen Glitch, bei dem das Objekt durch schnelles bewegen durch die Wand gezogen werden kann, indem es genau so weit zurück transliert wird, wie es durch geglitcht ist.
                    if (node.getBoundsInParent().getMinX() < 0) node.setTranslateX(newTranslateX-node.getBoundsInParent().getMinX());
                    else if (node.getBoundsInParent().getMaxX() > sceneWidth) node.setTranslateX(newTranslateX-(node.getBoundsInParent().getMaxX()-sceneWidth));
                    if (node.getBoundsInParent().getMinY() < 0) node.setTranslateY(newTranslateY-node.getBoundsInParent().getMinY());
                    else if (node.getBoundsInParent().getMaxY() > sceneHeight) node.setTranslateY(newTranslateY-(node.getBoundsInParent().getMaxY()-sceneHeight));
                };

        EventHandler<MouseEvent> onMouseReleased =
                event -> {
                    if (widgetContainerModel.getWidgetMenuIsOpen() & MouseInfo.getPointerInfo().getLocation().x < 300) {
                        widgetContainerModel.changeWidgetStateById(node.getId(), 0);
                        widgetContainerModel.adddWidgetToMenu();
                        widgetContainerModel.clearBufferedWidget();
                        node.setTranslateX(0);
                        node.setTranslateY(0);
                    }
                };

        node.setOnMousePressed(onMousePressed);
        node.setOnMouseDragged(onMouseDragged);
        node.setOnMouseReleased(onMouseReleased);
    }

    public void initModel(ANNModel annModel, WidgetContainerModel widgetContainerModel) {
        this.annModel = annModel;
        this.widgetContainerModel = widgetContainerModel;
    }
}
