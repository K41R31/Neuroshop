package Neuroshop.Model;

import java.awt.*;
import static Neuroshop.Start.primaryStage;

public class ScreenSize {

    public static boolean isFullscreen; //Wird von Methode die das Drag & Drop ermöglicht geändert
    public static double screenHeight;
    public static double screenWidth;
    private static double oldX, oldY, oldWidth, oldHeight;

    public ScreenSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenHeight = screenSize.height;
        screenWidth = screenSize.width;
    }

    public static void toggleFullScreen() {
        if (primaryStage.getHeight() == screenHeight - 40 & primaryStage.getWidth() == screenWidth) {
            primaryStage.setX(oldX);
            primaryStage.setY(oldY);
            primaryStage.setWidth(oldWidth);
            primaryStage.setHeight(oldHeight);
            isFullscreen = false;
        } else {
            oldX = primaryStage.getX();
            oldY = primaryStage.getY();
            oldWidth = primaryStage.getWidth();
            oldHeight = primaryStage.getHeight();

            primaryStage.setX(0);
            primaryStage.setY(0);
            primaryStage.setWidth(screenWidth);
            primaryStage.setHeight(screenHeight - 40);
            isFullscreen = true;
        }
    }
}
