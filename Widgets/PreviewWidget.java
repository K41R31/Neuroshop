package Neuroshop.Widgets;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;

public class PreviewWidget extends StackPane {

    private Text label;
    private ImageView thumbnail;
    private Rectangle rectangleBorder;
    private StackPane previewPane;

    public PreviewWidget(String name, Image thumbnail) throws IOException {
        this.previewPane = new StackPane();
        this.thumbnail = new ImageView(thumbnail);
        this.label = new Text(name);
        this.rectangleBorder = new Rectangle(250, 200);
        initPreview();

        previewPane.setId(name);
        widgetPane.setId(name);
    }
}
