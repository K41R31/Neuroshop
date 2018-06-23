package Neuroshop;

import java.awt.*;

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
        if (Start.primaryStage.getHeight() == height - 40 & Start.primaryStage.getWidth() == width) {
            if (!dragged) {
                Start.primaryStage.setX(oldX);
                Start.primaryStage.setY(oldY);
            } else {
                double mousePosX = MouseInfo.getPointerInfo().getLocation().x;
                double screenWidthThird = ScreenSize.width / 3;
                if (mousePosX <= screenWidthThird) {
                    Start.primaryStage.setX(1);
                } else if (mousePosX > screenWidthThird & mousePosX < screenWidthThird * 2) {
                    Start.primaryStage.setX(ScreenSize.width / 2 - oldWidth / 2);
                } else {
                    Start.primaryStage.setX(ScreenSize.width - oldWidth);
                }
            }
            Start.primaryStage.setWidth(oldWidth);
            Start.primaryStage.setHeight(oldHeight);
            isFullscreen = false;
        } else {
            if (!dragged) {
                oldX = Start.primaryStage.getX();
                oldY = Start.primaryStage.getY();
            }
            oldWidth = Start.primaryStage.getWidth();
            oldHeight = Start.primaryStage.getHeight();

            Start.primaryStage.setX(0);
            Start.primaryStage.setY(0);
            Start.primaryStage.setWidth(width);
            Start.primaryStage.setHeight(height - 40);
            isFullscreen = true;
        }
    }
}
