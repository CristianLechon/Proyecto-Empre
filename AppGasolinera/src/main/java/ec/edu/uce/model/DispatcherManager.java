package ec.edu.uce.model;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DispatcherManager {
    private static Map<String, Dispatcher> dispatchers = new HashMap<>();
    private static final String dispatcherFile = "src/main/resources/dispatcher.txt"; // Ruta al archivo de texto

    public DispatcherManager() {
        cargarDespachadores();
    }

    public static boolean registrarDispatcher(String userName, String lastName, String password) {
        if (dispacherExiste(userName, password)) {
            return false;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dispatcherFile, true))) {
            writer.write(userName + ":" + lastName + ":" + password);
            writer.newLine();
            dispatchers.put(userName, new Dispatcher(userName, lastName, password));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static void cargarDespachadores() {
        try (BufferedReader br = new BufferedReader(new FileReader(dispatcherFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    String userName = parts[0].trim();
                    String lastName = parts[1].trim();
                    String password = parts[2].trim();
                    Dispatcher dispatcher = new Dispatcher(userName, lastName, password);
                    dispatchers.put(userName, dispatcher);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean dispacherExiste(String userName, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(dispatcherFile))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(":");
                if (partes.length == 3 && partes[0].equals(userName) && partes[2].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Dispatcher obtenerDispatcherPorUsername(String username) {
        return dispatchers.get(username);
    }
}