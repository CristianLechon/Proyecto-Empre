package ec.edu.uce;

import ec.edu.uce.model.Aditivo;
import ec.edu.uce.model.AditivoManager;
import ec.edu.uce.model.Gasolina;
import ec.edu.uce.model.GasolineraManager;
import ec.edu.uce.view.ClientLoginFrame;
import ec.edu.uce.view.GasolinaFrame;
import ec.edu.uce.view.MainUI;
import ec.edu.uce.view.SplashScreen;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        //new ClientLoginFrame();
        SwingUtilities.invokeLater(SplashScreen::new);
        //new MainUI().display();
        /*AditivoManager manager = new AditivoManager();
        manager.cargarDatosDesdeArchivo();

        // Verificar que se hayan cargado correctamente
        List<Aditivo> gasolinas = manager.getListaAditivos();
        for (Aditivo gasolina : gasolinas) {
            System.out.println(gasolina.getTipoAditivo() + " - Precio: $" + gasolina.getPrecio() + " - Galones: " + gasolina.getUnidades());
        }*/
    }
}