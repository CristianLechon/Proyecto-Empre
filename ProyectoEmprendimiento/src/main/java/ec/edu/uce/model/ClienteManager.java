package ec.edu.uce.model;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ClienteManager {
    private static Map<String, Cliente> clientes;
    private static final String filePath = "src/main/resources/clientes.txt";

    public ClienteManager() {
        clientes = new HashMap<>();
        cargarClientes();
    }

    public static boolean registrarCliente (String nombre, String matricula, String CI) {
        if (usuarioExiste(CI)) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(nombre + ":" + matricula + ":" + CI);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void cargarClientes() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(":");
                if (partes.length == 3) {
                    String nombre = partes[0].trim();
                    String matricula = partes[1].trim();
                    String CI = partes[2].trim();
                    Cliente cliente = new Cliente(nombre, matricula, CI);
                    clientes.put(matricula, cliente);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean usuarioExiste(String CI) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(":");
                if (partes.length == 3 && partes[2].equals(CI)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static Cliente buscarClientePorMatricula(String matricula) {
        return clientes.get(matricula);
    }
}
