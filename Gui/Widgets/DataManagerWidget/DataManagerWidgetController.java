package Neuroshop.Gui.Widgets.DataManagerWidget;

import Neuroshop.Gui.Widgets.MakeDraggable;
import Neuroshop.Models.ANNModel;
import Neuroshop.Main;

import Neuroshop.Models.Presets.LastOpenedFiles;
import Neuroshop.Models.TutorialModel;
import Neuroshop.Models.WidgetContainerModel;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class DataManagerWidgetController implements Observer {

    private ANNModel annModel;
    private LastOpenedFiles lastOpenedFiles;
    private WidgetContainerModel widgetContainerModel;
    private ArrayList<ChoiceBox> choosedColumns;
    private TutorialModel tutorialModel;

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


    @FXML
    private void initialize() {
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

    private double roundDouble(double start) {
        double processing;
        String toString = String.valueOf(start);
        processing = Double.valueOf(toString.substring(0, toString.indexOf(".")+2));
        return processing;
    }

    @FXML
    private void testLearnSliderSlided() {
        int value = (int)testLearnSlider.getValue();
        trainValue.setText(String.valueOf(value));
        testValue.setText(String.valueOf((int)(testLearnSlider.getMax() - value)));
        annModel.setDataPercentage((roundDouble(value)) / testLearnSlider.getMax()); //TODO Vielleicht noch etwas viele Nachkommastellen
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
        annModel.setOutputColumns(outputList.stream().mapToInt(i -> i).toArray());
        widgetContainerModel.activateMenus();

        widgetContainerModel.changeWidgetStateById(DataManager.getId(), 0);
        widgetContainerModel.removeWidgetFromWhiteboard(DataManager.getId());
        DataManager.setTranslateX(0);
        DataManager.setTranslateY(0);
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
        tutorialModel.showLastOpened();
        lastOpenedPane.setOpacity(1);
        lastOpenedPane.setDisable(false);
        for (int i = 0; i < lastOpenedFiles.getLastOpened().size(); i++) {
            File actualFile = lastOpenedFiles.getLastOpened().get(i);
            filesPane.getChildren().add(new lastOpenedItem(actualFile.getName(), actualFile));
            deleterPane.getChildren().add(new lastOpenedItemDeleter(actualFile));
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((String)arg) {
            case "initDataManager":
                initDataManager();
        }
    }

    public void initModel(ANNModel annModel, WidgetContainerModel widgetContainerModel, LastOpenedFiles lastOpenedFiles, TutorialModel tutorialModel) {
        this.annModel = annModel;
        this.widgetContainerModel = widgetContainerModel;
        this.lastOpenedFiles = lastOpenedFiles;
        this.tutorialModel = tutorialModel;
        new MakeDraggable(widgetContainerModel, DataManager);
    }
}
