package Neuroshop.Gui.WidgetMenu;

import Neuroshop.Models.WidgetContainerModel;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.Observable;
import java.util.Observer;

public class WidgetMenuController implements Observer {

    @FXML
    private HBox widgetMenuRootPane;
    @FXML
    private VBox widgetMenuPane;
    @FXML
    private StackPane toggleButton;
    @FXML
    private ImageView openerIcon;

    private WidgetContainerModel widgetContainerModel;

    @FXML
    private void initialize() {
        AnchorPane.setTopAnchor(widgetMenuRootPane, (double)0);
        AnchorPane.setLeftAnchor(widgetMenuRootPane, (double)0);
        AnchorPane.setBottomAnchor(widgetMenuRootPane, (double)0);
    }

    @FXML
    private void toggleMenu() {
        if (!widgetContainerModel.getWidgetMenuIsOpen() & !widgetContainerModel.getMenuIsBusy()) {
            widgetContainerModel.setMenuIsBusy(true);
            Timeline openMenuAnimation = new Timeline();
            openMenuAnimation.getKeyFrames().addAll(
                    new KeyFrame(new Duration(200), new KeyValue(openerIcon.scaleXProperty(), -1, Interpolator.EASE_BOTH)),
                    new KeyFrame(new Duration(200), new KeyValue(widgetMenuPane.prefWidthProperty(), 300, Interpolator.EASE_BOTH))
            );
            openMenuAnimation.play();
            openMenuAnimation.setOnFinished(event -> {
                widgetMenuPane.getChildren().addAll(widgetContainerModel.getAllPreviews());
                widgetContainerModel.setWidgetMenuIsOpen(true);
                widgetContainerModel.setMenuIsBusy(false);
            });
            widgetMenuPane.setVisible(true);

        } else if (widgetContainerModel.getWidgetMenuIsOpen() & !widgetContainerModel.getMenuIsBusy()) {
            widgetContainerModel.setMenuIsBusy(true);
            Timeline closeMenuAnimation = new Timeline();
            closeMenuAnimation.getKeyFrames().addAll(
                    new KeyFrame(new Duration(200), new KeyValue(openerIcon.scaleXProperty(), 1, Interpolator.EASE_BOTH)),
                    new KeyFrame(new Duration(200), new KeyValue(widgetMenuPane.prefWidthProperty(), 0, Interpolator.EASE_BOTH))
            );
            closeMenuAnimation.play();
            closeMenuAnimation.setOnFinished(event -> {
                widgetMenuPane.setVisible(false);
                widgetContainerModel.setWidgetMenuIsOpen(false);
                widgetContainerModel.setMenuIsBusy(false);
            });
            widgetMenuPane.getChildren().clear();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((String)arg) {
            case "toggleWidgetMenu":
                toggleMenu();
                break;
            case "addWidgetToMenu":
                widgetMenuPane.getChildren().add(widgetContainerModel.getBufferedWidget());
                break;
            case "activateMenus":
                toggleButton.setDisable(false);
                break;
            case "menusToFront":
                widgetMenuRootPane.toFront();
        }
    }

    public void initModel(WidgetContainerModel widgetContainerModel) {
        this.widgetContainerModel = widgetContainerModel;
    }
}
