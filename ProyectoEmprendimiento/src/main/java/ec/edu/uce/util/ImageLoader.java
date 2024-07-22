package ec.edu.uce.util;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ImageLoader {

    public static ImageIcon loadImage(String path) {
        File imageFile = new File(path);
        if (imageFile.exists()) {
            return new ImageIcon(path);
        } else {
            System.err.println("Error: La imagen " + path + " no se encontró.");
            return null;
        }
    }

    public static ImageIcon loadImage(String path, int width, int height) {
        ImageIcon icon = loadImage(path);
        if (icon != null) {
            Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(image);
        }
        return null;
    }
}

