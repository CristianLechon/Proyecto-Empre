package ec.edu.uce.model;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ClientManager {
    private static Map<String, Client> clientes;
    private static final String clientFile = "src/main/resources/clientes.txt";

    public ClientManager() {
        clientes = new HashMap<>();
        cargarClientes();
    }

    public static boolean registrarCliente (String name, String lastName, String plate, String CI) {
        if (usuarioExiste(CI)) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(clientFile, true))) {
            writer.write(name + ":" + lastName + ":" + plate + ":" + CI);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void cargarClientes() {
        try (BufferedReader br = new BufferedReader(new FileReader(clientFile))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(":");
                if (partes.length == 4) {
                    String nombre = partes[0].trim();
                    String lastName = partes[1].trim();
                    String matricula = partes[2].trim();
                    String CI = partes[3].trim();
                    Client client = new Client(nombre,lastName, matricula, CI);
                    clientes.put(matricula, client);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean usuarioExiste(String CI) {
        try (BufferedReader reader = new BufferedReader(new FileReader(clientFile))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(":");
                if (partes.length == 4 && partes[3].equals(CI)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static Client buscarClientePorMatricula(String matricula) {
        return clientes.get(matricula);
    }
}
