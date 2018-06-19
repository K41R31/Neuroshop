package Neuroshop;

import Neuroshop.Gui.Widgets.Widget;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Observable;

public class WidgetsModel extends Observable { //Kein "echtes" Model

    private ArrayList<Widget> previewWidgetsList = new ArrayList<>();
    private ArrayList<Widget> whiteboardWidgetsList = new ArrayList<>();

    private StackPane draggPane;

    public WidgetsModel() {
        previewWidgetsList.add(new Widget("neuralNet", new Image("Neuroshop/Gui/Resources/netThumb.png")));
        previewWidgetsList.add(new Widget("diagram", new Image("Neuroshop/Gui/Resources/resultDiagramThumb.png")));
    }

    public void setDraggPreview(StackPane draggPane) {
        this.draggPane = draggPane;
        setChanged();
        notifyObservers("setDraggPreview");
    }
    public StackPane getDraggPreview() {
        return draggPane;
    }
    public void removeDraggPreview() {
        setChanged();
        notifyObservers("removeDraggPreview");
    }
    public ArrayList<StackPane> getPreviewWidgets() {
        ArrayList<StackPane> returnList = new ArrayList<>();
        for (Widget procoessList: previewWidgetsList) {
            returnList.add(procoessList.getPreviewPane());
        }
        return returnList;
    }
    public ArrayList<AnchorPane> getWhiteboardWidgets() {
        ArrayList<AnchorPane> returnList = new ArrayList<>();
        for (Widget procoessList: whiteboardWidgetsList) {
            returnList.add(procoessList.getWhiteboardPane());
        }
        return returnList;
    }

    public void addWidgetToWhiteboard(int widgetIndex) {
        whiteboardWidgetsList.add(previewWidgetsList.get(widgetIndex));
        previewWidgetsList.remove(widgetIndex);
        setChanged();
        notifyObservers("setWhiteboardWidget");
    }

    public void removeWidgetFromWhiteboard(int widgetIndex) {
        previewWidgetsList.add(whiteboardWidgetsList.get(widgetIndex));
        whiteboardWidgetsList.remove(widgetIndex);
    }
}
