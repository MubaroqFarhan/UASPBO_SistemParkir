package main.java.com.parkingsystem.controllers;

import main.java.com.parkingsystem.dao.DatabaseConnection;
import main.java.com.parkingsystem.dao.TransactionDAO;
import main.java.com.parkingsystem.utils.ParkingCalculator;
import main.java.com.parkingsystem.utils.StrukPDFGenerator;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ParkingExitController {

    public static void handleExit(String plateNumber, Component parentComponent) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT entry_time, vehicle_type FROM transactions WHERE plate_number = ? AND exit_time IS NULL";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, plateNumber);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Timestamp entryTS = rs.getTimestamp("entry_time");
                        String vehicleType = rs.getString("vehicle_type");
                        LocalDateTime entryTime = entryTS.toLocalDateTime();
                        LocalDateTime exitTime = LocalDateTime.now();

                        long duration = ChronoUnit.HOURS.between(entryTime, exitTime);
                        if (duration <= 0) duration = 1;

                        int rate = ParkingCalculator.calculateFee(vehicleType, entryTime, exitTime);
                        boolean updated = TransactionDAO.updateExit(plateNumber, exitTime, rate);

                        if (updated) {
                            String message = "Kendaraan berhasil keluar.\n" +
                                    "Durasi: " + duration + " jam\n" +
                                    "Biaya: Rp " + String.format("%,d", rate).replace(',', '.');

                            if (vehicleType.equalsIgnoreCase("Motor") && rate == 25000)
                                message += "\n*Biaya dibatasi maksimal Rp 25.000";
                            else if (vehicleType.equalsIgnoreCase("Mobil") && rate == 30000)
                                message += "\n*Biaya dibatasi maksimal Rp 30.000";

                            JOptionPane.showMessageDialog(parentComponent, message);

                            // Cetak struk
                            StrukPDFGenerator.generateStruk(plateNumber, vehicleType, entryTime, exitTime, rate);

                            // Tutup jendela jika memungkinkan
                            Window window = SwingUtilities.getWindowAncestor(parentComponent);
                            if (window != null) window.dispose();
                        } else {
                            JOptionPane.showMessageDialog(parentComponent, "Gagal memproses keluar kendaraan.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(parentComponent, "Plat tidak ditemukan atau kendaraan sudah keluar.");
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(parentComponent, "Kesalahan database:\n" + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parentComponent, "Kesalahan tak terduga:\n" + e.getMessage());
        }
    }
}
