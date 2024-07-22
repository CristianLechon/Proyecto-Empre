package ec.edu.uce;

import javax.swing.*;

import ec.edu.uce.view.SplashScreen;
import ec.edu.uce.view.AdminMetod;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SplashScreen::new);
    }
}