package Neuroshop.Gui.OptionsMenu;

import Neuroshop.Model.OptionsModel;
import Neuroshop.Start;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class OptionsMenuController implements Observer {

    @FXML
    private VBox optionsMenuPane;
    private boolean optionsIsOpen;
    private OptionsModel model;

    //Wird aufgerufen wenn die FXML-Datei initialisiert wurde
    @FXML
    private void initialize() {
        AnchorPane.setTopAnchor(optionsMenuPane, (double)0);
        AnchorPane.setRightAnchor(optionsMenuPane, (double)0);
        AnchorPane.setBottomAnchor(optionsMenuPane, (double)0);
    }

    private void toggleOptionsMenu() {
        if (!optionsIsOpen) {
            Timeline openOptionsAnimation = new Timeline();
            openOptionsAnimation.getKeyFrames().addAll(
                    new KeyFrame(new Duration(200), new KeyValue(optionsMenuPane.prefWidthProperty(), 300, Interpolator.EASE_BOTH))
            );
            openOptionsAnimation.play();
            openOptionsAnimation.setOnFinished(event -> {
                optionsMenuPane.getChildren().get(0).setOpacity(1);
                optionsMenuPane.getChildren().get(1).setOpacity(1);
                optionsMenuPane.getChildren().get(2).setOpacity(1);
            });
            optionsMenuPane.setVisible(true);
            optionsIsOpen = true;
        } else {
            Timeline closeMenuAnimation = new Timeline();
            closeMenuAnimation.getKeyFrames().addAll(
                    new KeyFrame(new Duration(200), new KeyValue(optionsMenuPane.prefWidthProperty(), 0, Interpolator.EASE_BOTH))
            );
            closeMenuAnimation.play();
            closeMenuAnimation.setOnFinished(event ->
                    optionsMenuPane.setVisible(false)
            );
            optionsMenuPane.getChildren().get(0).setOpacity(0);
            optionsMenuPane.getChildren().get(1).setOpacity(0);
            optionsMenuPane.getChildren().get(2).setOpacity(0);
            optionsIsOpen = false;
        }
    }

    @FXML
    private File openFile() throws Exception {

        final FileChooser fileChooser = new FileChooser();
        configuringFileChooser(fileChooser);
        //Das Fenster, das bei ownerWindow angegeben ist, wird solange der FileChooser offen ist disabled (primaryStage)
        return fileChooser.showOpenDialog(Start.primaryStage);
    }

    private void configuringFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Select a picture");

        // Set Initial Directory
        fileChooser.setInitialDirectory(new File("C:/Users"));

        // Add Extension Filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),

                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );
    }//TODO---------------------------------------------------------------

    public void initModel(OptionsModel model) {
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == "toggleOptions") toggleOptionsMenu();
    }
}