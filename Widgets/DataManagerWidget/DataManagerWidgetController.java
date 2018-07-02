package Neuroshop.Widgets.DataManagerWidget;

import Neuroshop.Models.ANNModel;
import Neuroshop.Main;

import Neuroshop.Models.Presets.LastOpenedFiles;
import Neuroshop.Models.WidgetContainerModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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
    private LastOpenedFiles lastOpenedFiles;
    private WidgetContainerModel widgetContainerModel;
    private ArrayList<ChoiceBox> choosedColumns;

    @FXML
    private Slider testLearnSlider;
    @FXML
    private StackPane DataManager;
    @FXML
    private AnchorPane importPane;
    @FXML
    private StackPane applyButtonPane;
    @FXML
    private StackPane button1Pane;
    @FXML
    private StackPane button2Pane;
    @FXML
    private Text trainValue;
    @FXML
    private Text testValue;
    @FXML
    private Text applyButtonText;
    @FXML
    private Text button1Text;
    @FXML
    private Text button2Text;
    @FXML
    private Text errorInfo;
    @FXML
    private VBox filesPane;
    @FXML
    private VBox deleterPane;
    @FXML
    private HBox chooserPane;
    @FXML
    private HBox seperatorPane;
    @FXML
    private HBox columnPane;
    @FXML
    private AnchorPane lastOpenedPane;

    //Für Mouse Events
    private double sceneCursorPosX, sceneCursorPosY;
    private double nodeTranslatedX, nodeTranslatedY;
    private double sceneWidth, sceneHeight;

    @FXML
    private void initialize() {
        makeDraggable(DataManager);
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
            lastOpenedFiles.setLastOpened(datasetFile);
            importPane.setOpacity(0);
            importPane.setDisable(true);
        }
    }

    private class lastOpenedItem extends Text {
        lastOpenedItem(String fileName, File file) {
            setText(fileName);
            getStyleClass().add("lastOpenedText");
            setWrappingWidth(500);
            setOnMouseClicked(event -> {
                annModel.setDatasetFile(file);
                importPane.setOpacity(0);
                importPane.setDisable(true);
            });
        }
    }

    private class lastOpenedItemDeleter extends ImageView {
         lastOpenedItemDeleter(File file) {
             getStyleClass().add("lastOpenedDeleter");
             setFitWidth(48);
             setFitHeight(30);
             setPickOnBounds(true);
             setOnMouseClicked(event -> {
                 lastOpenedFiles.deleteLastOpened(file);
                 filesPane.getChildren().remove(deleterPane.getChildren().indexOf(this)+1);
                 deleterPane.getChildren().remove(this);
                 lastOpenedFiles.updateLastFiles();
             });
         }
    }

    private void initDataManager() {
        testLearnSlider.setMin(0);
        testLearnSlider.setMax(annModel.getNumberOfRecords());
        testLearnSlider.setValue(testLearnSlider.getMax() / 2);
        trainValue.setText(String.valueOf((int)testLearnSlider.getValue()));
        testValue.setText(String.valueOf((int)(testLearnSlider.getMax() - testLearnSlider.getValue() + 0.5)));
        annModel.setDataPercentage(testLearnSlider.getValue() / testLearnSlider.getMax());

        choosedColumns = new ArrayList<>();
        for(int c = 0; c < annModel.getDataColumns(); c++) {
            chooserPane.getChildren().add(new IOChooser(c));
            seperatorPane.getChildren().add(new Separator());
            VColumn vColumn = new VColumn();
            columnPane.getChildren().add(vColumn);
            for (int r = 0; r < annModel.getNumberOfRecords(); r++) {
                double[][] dataset = annModel.getDataSet();
                vColumn.addText(Double.toString(dataset[r][c]));
            }
        }
    }

    private class IOChooser extends StackPane {
        IOChooser(int index) {
            HBox.setHgrow(this, Priority.ALWAYS);
            ImageView imageView = new ImageView();
            imageView.setPickOnBounds(true);
            imageView.setFitWidth(45);
            imageView.setFitHeight(35);
            imageView.getStyleClass().add("ioChooserNothing");
            imageView.setId(index+"nothing");
            imageView.setOnMouseClicked(event -> {
                if (imageView.getId().contains("nothing") | imageView.getId().contains("output")) {
                    imageView.getStyleClass().clear();
                    imageView.getStyleClass().add("ioChooserInput");
                    imageView.setId(index+"input");
                } else if (imageView.getId().contains("input")) {
                    imageView.getStyleClass().clear();
                    imageView.getStyleClass().add("ioChooserOutput");
                    imageView.setId(index+"output");
                }
            });
            getChildren().add(imageView);
        }
    }

    private class Separator extends StackPane {
        Separator() {
            HBox.setHgrow(this, Priority.ALWAYS);
            this.setAlignment(Pos.CENTER_RIGHT);
            Line line = new Line();
            line.setStartX(0);
            line.setEndX(0);
            line.setStartY(0);
            line.setEndY(200);
            line.setStroke(Color.web("#656565"));
            if (seperatorPane.getChildren().size() < annModel.getDataColumns() - 1) getChildren().add(line);
        }
    }

    private class VColumn extends VBox {
        VColumn() {
            HBox.setHgrow(this, Priority.ALWAYS);
            setAlignment(Pos.TOP_CENTER);
            setSpacing(3);
            setMinWidth(100);
            setPrefWidth(USE_COMPUTED_SIZE);
            setPrefHeight(USE_COMPUTED_SIZE);
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
        trainValue.setText(String.valueOf(value));
        testValue.setText(String.valueOf((int)(testLearnSlider.getMax() - value)));
        annModel.setDataPercentage((double)value / testLearnSlider.getMax()); //TODO Vielleicht noch etwas viele Nachkommastellen
    }

    @FXML
    private void applyButtonEntered() {
        applyButtonPane.setStyle("-fx-background-color: #4490ff");
        applyButtonText.setFill(Color.web("#222222"));
    }

    @FXML
    private void applyButtonExited() {
        applyButtonPane.setStyle("-fx-background-color: TRANSPARENT; -fx-border-color: #4490ff; -fx-border-width: 2");
        applyButtonText.setFill(Color.web("#4490ff"));
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

    @FXML
    private void apply() {
        ArrayList<Integer> inputList = new ArrayList<>();
        ArrayList<Integer> outputList = new ArrayList<>();
        for (int i = 0; i < chooserPane.getChildren().size(); i++) {
            if (((StackPane)chooserPane.getChildren().get(i)).getChildren().get(0).getId().contains("nothing")) {
                errorInfo.setText("Set each column to input or output");
                errorInfo.setVisible(true);
                return;
            } else {
                if (((StackPane)chooserPane.getChildren().get(i)).getChildren().get(0).getId().contains("input")) {
                    inputList.add(i);
                } else {
                    outputList.add(i);
                }
            }
        }
        if (inputList.size() == 0) {
            errorInfo.setText("You have to set at least one column as output");
            errorInfo.setVisible(true);
            return;
        } else if (outputList.size() == 0) {
            errorInfo.setText("You have to set at least one column as input");
            errorInfo.setVisible(true);
            return;
        }
        errorInfo.setVisible(false);
        annModel.setInputColumns(inputList.stream().mapToInt(i -> i).toArray());
        annModel.setInputColumns(outputList.stream().mapToInt(i -> i).toArray());
    }

    @FXML
    private void back() {
        lastOpenedPane.setOpacity(0);
        lastOpenedPane.setDisable(true);
        filesPane.getChildren().remove(1, filesPane.getChildren().size());
        deleterPane.getChildren().clear();
    }

    @FXML
    private void showLastOpened() {
        lastOpenedPane.setOpacity(1);
        lastOpenedPane.setDisable(false);
        for (int i = 0; i < lastOpenedFiles.getLastOpened().size(); i++) {
            File actualFile = lastOpenedFiles.getLastOpened().get(i);
            filesPane.getChildren().add(new lastOpenedItem(actualFile.getName(), actualFile));
            deleterPane.getChildren().add(new lastOpenedItemDeleter(actualFile));
        }
    }

    private void makeDraggable(StackPane node) {

        EventHandler<MouseEvent> onMousePressed =
                event -> {
                    sceneCursorPosX = event.getSceneX();
                    sceneCursorPosY = event.getSceneY();
                    nodeTranslatedX = node.getTranslateX();
                    nodeTranslatedY = node.getTranslateY();
                    sceneWidth = node.getScene().getWidth();
                    sceneHeight = node.getScene().getHeight()-30; //-30 weil die obere Leiste 30 Pixel groß ist

                    node.setOpacity(0.5);
                    node.setScaleX(0.5);
                    node.setScaleY(0.5);
                    nodeTranslatedX = nodeTranslatedX + (event.getSceneX() - node.getLayoutX() - (node.getWidth() / 2));
                    nodeTranslatedY = nodeTranslatedY + (event.getSceneY()-30 - node.getLayoutY() - (node.getHeight() / 2));

                    node.setTranslateX(nodeTranslatedX);
                    node.setTranslateY(nodeTranslatedY);

                    widgetContainerModel.removeWidgetFromWhiteboard(node);
                    widgetContainerModel.setBufferedWidget(node);
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

                    if (!widgetContainerModel.getWidgetMenuIsOpen() & node.getBoundsInParent().getMinX() < 20) {
                        widgetContainerModel.toggleWidgetMenu();
                    }
                };

        EventHandler<MouseEvent> onMouseReleased =
                event -> {
                    if (widgetContainerModel.getWidgetMenuIsOpen() & node.getBoundsInParent().getMinX() < 300) {
                        widgetContainerModel.toggleWidgetMenu();
                        widgetContainerModel.changeWidgetStateById(node.getId(), 0);
                        node.setTranslateX(0);
                        node.setTranslateY(0);
                    } else {
                        node.setOpacity(1);
                        node.setScaleX(1);
                        node.setScaleY(1);
                        widgetContainerModel.addWidgetToWhiteboard(node.getId(), false);
                        widgetContainerModel.clearBufferedWidget();
                    }
                };

        node.setOnMousePressed(onMousePressed);
        node.setOnMouseDragged(onMouseDragged);
        node.setOnMouseReleased(onMouseReleased);
    }

    public void initModel(ANNModel annModel, WidgetContainerModel widgetContainerModel, LastOpenedFiles lastOpenedFiles) {
        this.annModel = annModel;
        this.widgetContainerModel = widgetContainerModel;
        this.lastOpenedFiles = lastOpenedFiles;
    }
}
