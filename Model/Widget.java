package Neuroshop.Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Widget {

    private Text label;
    private ImageView thumbnail;
    private Rectangle rectangleColor, rectangleBorder;

    public Widget(String name, Image image) {
        label = new Text(name);
        thumbnail = new ImageView(image);
        rectangleColor = new Rectangle(250, 200);
        rectangleBorder = new Rectangle(250, 200);
        addStyle();
    }

    private void addStyle() {
        label.setFont(new Font("Caviar Dreams", 35));
        label.setFill(Color.WHITE);
        label.setOpacity(0);
        label.setDisable(true);
        rectangleColor.setFill(Color.web("#222222"));
        rectangleBorder.setStrokeWidth(2);
        rectangleBorder.setStroke(Color.BLACK);
        rectangleBorder.setFill(Color.TRANSPARENT);
        rectangleBorder.setOpacity(0);
        rectangleColor.setOpacity(0);
        rectangleColor.setOnMouseEntered(event -> {
            rectangleColor.setOpacity(0.7);
            rectangleBorder.setOpacity(1);
            label.setOpacity(1);
        });
        rectangleColor.setOnMouseExited(event -> {
            rectangleColor.setOpacity(0);
            rectangleBorder.setOpacity(0);
            label.setOpacity(0);
        });
    }

    public void removeWidgetFromVBox(VBox toolMenuPane_VB) {

    }

    public void trashAnimation(VBox trashBin_VB) {

    }

    public Text getLabel() {
        return label;
    }
    public ImageView getThumbnail() {
        return thumbnail;
    }
    public Rectangle getRectangleColor() {
        return rectangleColor;
    }
    public Rectangle getRectangleBorder() {
        return rectangleBorder;
    }
}
