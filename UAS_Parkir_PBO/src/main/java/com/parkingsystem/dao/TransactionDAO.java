package main.java.com.parkingsystem.dao;

import main.java.com.parkingsystem.models.ParkingTransaction;

import java.sql.*;
import java.time.LocalDateTime;

import javax.swing.table.DefaultTableModel;

public class TransactionDAO {

    public static boolean insertEntry(ParkingTransaction trx) {
        boolean success = false;

        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) return false;

            String query = "INSERT INTO transactions (plate_number, vehicle_type, entry_time) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, trx.getPlateNumber());
            stmt.setString(2, trx.getVehicleType());
            stmt.setTimestamp(3, Timestamp.valueOf(trx.getEntryTime()));

            int rows = stmt.executeUpdate();
            success = (rows > 0);

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Insert error: " + e.getMessage());
        }

        return success;
    }
    public static boolean updateExit(String plateNumber, LocalDateTime exitTime, int fee) {
        boolean success = false;
    
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) return false;
    
            String query = "UPDATE transactions SET exit_time = ?, total_fee = ? WHERE plate_number = ? AND exit_time IS NULL";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setTimestamp(1, Timestamp.valueOf(exitTime));
            stmt.setInt(2, fee);
            stmt.setString(3, plateNumber);
    
            int rows = stmt.executeUpdate();
            success = (rows > 0);
    
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Update error: " + e.getMessage());
        }
    
        return success;
    }
    public static DefaultTableModel getAllTransactions() {
    String[] columns = {"No", "Plat Nomor", "Jenis", "Masuk", "Keluar", "Biaya"};
    DefaultTableModel model = new DefaultTableModel(null, columns);

    try {
        Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT * FROM transactions ORDER BY id DESC";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        int no = 1;
        while (rs.next()) {
            String plate = rs.getString("plate_number");
            String type = rs.getString("vehicle_type");
            String masuk = rs.getString("entry_time");
            String keluar = rs.getString("exit_time");
            String fee = rs.getString("total_fee");

            Object[] row = {no++, plate, type, masuk, keluar, fee};
            model.addRow(row);
        }

        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        System.out.println("Load transaksi error: " + e.getMessage());
    }
    return model;
}
    public static boolean deleteByPlate(String plate) {
    try {
        Connection conn = DatabaseConnection.getConnection();
        String query = "DELETE FROM transactions WHERE plate_number = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, plate);
        int result = stmt.executeUpdate();
        stmt.close();
        conn.close();
        return result > 0;
    } catch (SQLException e) {
        System.out.println("Hapus gagal: " + e.getMessage());
        return false;
    }
}

    
}
