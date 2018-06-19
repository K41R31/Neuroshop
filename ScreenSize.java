package Neuroshop;

import java.awt.*;
import static Neuroshop.Start.primaryStage;

public class ScreenSize {

    public static boolean isFullscreen; //Wird von Methode die das Drag & Drop ermöglicht geändert
    public static double width;
    public static double height;
    private static double oldX, oldY, oldWidth, oldHeight;

    public ScreenSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.width;
        height = screenSize.height;
    }

    public static void toggleFullScreen(boolean dragged) {
        if (primaryStage.getHeight() == height - 40 & primaryStage.getWidth() == width) {
            if (!dragged) {
                primaryStage.setX(oldX);
                primaryStage.setY(oldY);
            } else {
                double mousePosX = MouseInfo.getPointerInfo().getLocation().x;
                double screenWidthThird = ScreenSize.width / 3;
                if (mousePosX <= screenWidthThird) {
                    primaryStage.setX(1);
                } else if (mousePosX > screenWidthThird & mousePosX < screenWidthThird * 2) {
                    primaryStage.setX(ScreenSize.width / 2 - oldWidth / 2);
                } else {
                    primaryStage.setX(ScreenSize.width - oldWidth);
                }
            }
            primaryStage.setWidth(oldWidth);
            primaryStage.setHeight(oldHeight);
            isFullscreen = false;
        } else {
            if (!dragged) {
                oldX = primaryStage.getX();
                oldY = primaryStage.getY();
            }
            oldWidth = primaryStage.getWidth();
            oldHeight = primaryStage.getHeight();

            primaryStage.setX(0);
            primaryStage.setY(0);
            primaryStage.setWidth(width);
            primaryStage.setHeight(height - 40);
            isFullscreen = true;
        }
    }
}
