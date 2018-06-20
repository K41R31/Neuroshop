package Neuroshop.Model;

import Neuroshop.Gui.Widgets.Widget;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Observable;

public class WidgetsModel extends Observable {

    private ArrayList<Widget> previewWidgetsList = new ArrayList<>();
    private ArrayList<Widget> whiteboardWidgetsList = new ArrayList<>();
    private ArrayList<Widget> allWidgetsList = new ArrayList<>();

    private Widget draggPane;

    public WidgetsModel() {
        previewWidgetsList.add(new Widget("neuralNet", new Image("Neuroshop/Ressources/netThumb.png")));
        previewWidgetsList.add(new Widget("diagram", new Image("Neuroshop/Ressources/resultDiagramThumb.png")));
        allWidgetsList.addAll(previewWidgetsList);
    }

    public void setDraggPreview(String name) {
        for (Widget widget: allWidgetsList) {
            if (name.equals(widget.getName())) {
                this.draggPane = widget;
            }
        }
        setChanged();
        notifyObservers("setDraggPreview");
    }

    public Widget getDraggPreview() {
        return draggPane;
    }

    public void removeDraggPreview() {
        setChanged();
        notifyObservers("removeDraggPreview");
        this.draggPane = null;
    }

    public ArrayList<Widget> getPreviewWidgetList() {
        return previewWidgetsList;
    }

    public void addToPreviewList(Widget widget) {
        previewWidgetsList.add(widget);
    }

    public void clearPreviewWidgetList() {
        previewWidgetsList.clear();
    }

    public ArrayList<AnchorPane> getWhiteboardWidgets() {
        ArrayList<AnchorPane> returnList = new ArrayList<>();
        for (Widget procoessList: whiteboardWidgetsList) {
            returnList.add(procoessList.getWhiteboardPane());
        }
        return returnList;
    }

    public void addWidgetToWhiteboard(Widget widget) {
        whiteboardWidgetsList.add(widget);
        setChanged();
        notifyObservers("setWhiteboardWidget");
    }

    public ArrayList<Widget> getAllWidgetsList() {
        return allWidgetsList;
    }

    public void removeWidgetFromWhiteboard(int widgetIndex) {
        previewWidgetsList.add(whiteboardWidgetsList.get(widgetIndex));
        whiteboardWidgetsList.remove(widgetIndex);
    }
}
