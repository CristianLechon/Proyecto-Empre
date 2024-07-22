package ec.edu.uce.model;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DispatcherManager {
    private static Map<String, Dispatcher> dispatchers = new HashMap<>();
    private static final String FILE_PATH = "src/main/resources/dispatcher.txt"; // Ruta al archivo de texto

    static {
        cargarDespachadores();
    }

    private static void cargarDespachadores() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    String lastName = parts[2].trim();
                    dispatchers.put(username, new Dispatcher(username, password, lastName));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean registrarDispatcher(String username, String lastName, String password) {
        if (dispatchers.containsKey(username)) {
            return false;
        }
        dispatchers.put(username, new Dispatcher(username, password, lastName));
        guardarDespachador(username, password, lastName);
        return true;
    }

    public static boolean validarUsuarioDispatcher(String username, String password) {
        Dispatcher dispatcher = dispatchers.get(username);
        return dispatcher != null && dispatcher.getPassword().equals(password);
    }

    public static Dispatcher obtenerDispatcherPorUsername(String username) {
        return dispatchers.get(username);
    }

    private static void guardarDespachador(String username, String password, String lastName) {
        try (FileWriter fw = new FileWriter(FILE_PATH, true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println(username + "," + password + "," + lastName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}