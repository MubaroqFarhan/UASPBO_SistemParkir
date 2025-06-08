package main.java.com.parkingsystem;

import main.java.com.parkingsystem.views.LoginView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set Look and Feel 
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("Gagal atur tema: " + e.getMessage());
        }
        // Jalankan tampilan Login
        SwingUtilities.invokeLater(() -> new LoginView());
    }
}
