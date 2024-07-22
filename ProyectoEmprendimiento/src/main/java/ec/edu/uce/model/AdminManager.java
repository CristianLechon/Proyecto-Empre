package ec.edu.uce.model;

import java.io.*;
import java.util.Map;

public class AdminManager {
    private Map<String, Admin> adminMap;
    private static final String ARCHIVO_USUARIOS = "src/main/resources/admin.txt";

    public AdminManager(Map<String, Admin> adminMap) {
        this.adminMap = adminMap;
        cargarAdmin();
    }

    public static boolean registrarAdmin (String username, String password) {
        if (usuarioExiste(username)) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS, true))) {
            writer.write(username + "," + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean validarUsuario(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_USUARIOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 2 && partes[0].equals(username) && partes[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void cargarAdmin() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_USUARIOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    String username = partes[0].trim();
                    String password = partes[1].trim();
                    Admin admin = new Admin(username, password);
                    adminMap.put(username, admin);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean usuarioExiste(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_USUARIOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes[0].equals(username)) {
                    return true;
                } else {
                    System.out.println("Registrese");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
