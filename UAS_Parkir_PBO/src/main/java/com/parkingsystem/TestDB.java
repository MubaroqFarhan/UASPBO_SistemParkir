package main.java.com.parkingsystem;

import java.sql.Connection;
import java.sql.SQLException;

import main.java.com.parkingsystem.dao.DatabaseConnection;

public class TestDB {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                System.out.println("Koneksi ke database berhasil!");
            } else {
                System.out.println("Koneksi gagal.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error umum: " + e.getMessage());
        }
    }
}
