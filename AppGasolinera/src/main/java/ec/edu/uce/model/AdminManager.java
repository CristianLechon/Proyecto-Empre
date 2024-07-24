package ec.edu.uce.model;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AdminManager {
    private static Map<String, Admin> adminMap;
    private static final String adminFile = "src/main/resources/admin.txt";

    public AdminManager() {
        adminMap = new HashMap<>();
        cargarAdmin();
    }

    public static boolean registrarAdmin(String userName, String lastName, String password) {
        if (adminExiste(userName, password)) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(adminFile, true))) {
            writer.write(userName + "," + lastName + "," + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static void cargarAdmin() {
        try (BufferedReader br = new BufferedReader(new FileReader(adminFile))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    String userName = partes[0].trim();
                    String lastName = partes[1].trim();
                    String password = partes[2].trim();
                    Admin admin = new Admin(userName,lastName, password);
                    adminMap.put(userName, admin);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean adminExiste(String userName, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(adminFile))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3 && partes[0].equals(userName) && partes[2].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Admin buscarAdmin(String matricula) {
        return adminMap.get(matricula);
    }

}
