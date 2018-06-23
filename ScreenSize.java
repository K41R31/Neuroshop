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
        if (Main.primaryStage.getHeight() == height - 40 & Main.primaryStage.getWidth() == width) {
            if (!dragged) {
                Main.primaryStage.setX(oldX);
                Main.primaryStage.setY(oldY);
            } else {
                double mousePosX = MouseInfo.getPointerInfo().getLocation().x;
                double screenWidthThird = ScreenSize.width / 3;
                if (mousePosX <= screenWidthThird) {
                    Main.primaryStage.setX(1);
                } else if (mousePosX > screenWidthThird & mousePosX < screenWidthThird * 2) {
                    Main.primaryStage.setX(ScreenSize.width / 2 - oldWidth / 2);
                } else {
                    Main.primaryStage.setX(ScreenSize.width - oldWidth);
                }
            }
            Main.primaryStage.setWidth(oldWidth);
            Main.primaryStage.setHeight(oldHeight);
            isFullscreen = false;
        } else {
            if (!dragged) {
                oldX = Main.primaryStage.getX();
                oldY = Main.primaryStage.getY();
            }
            oldWidth = Main.primaryStage.getWidth();
            oldHeight = Main.primaryStage.getHeight();

            Main.primaryStage.setX(0);
            Main.primaryStage.setY(0);
            Main.primaryStage.setWidth(width);
            Main.primaryStage.setHeight(height - 40);
            isFullscreen = true;
        }
    }
}
