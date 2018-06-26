package Neuroshop.Gui.Options;

import Neuroshop.Models.OptionsModel;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.Observable;
import java.util.Observer;

public class OptionsController implements Observer {

    @FXML
    private VBox optionsMenuPane;
    private boolean optionsIsOpen;
    private OptionsModel optionsModel;

    @FXML
    private void initialize() {
        optionsIsOpen = false;
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

    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals("toggleOptions")) toggleOptionsMenu();
    }

    public void initModel(OptionsModel optionsModel) {
        this.optionsModel = optionsModel;
    }
}
