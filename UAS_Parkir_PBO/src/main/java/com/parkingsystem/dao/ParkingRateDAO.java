package main.java.com.parkingsystem.dao;

import main.java.com.parkingsystem.models.ParkingRate;
import java.sql.*;

public class ParkingRateDAO {

    public static ParkingRate getRateByVehicleType(String type) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM parking_rates WHERE vehicle_type = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String vehicleType = rs.getString("vehicle_type");
                int ratePerHour = rs.getInt("rate_per_hour");
                rs.close();
                stmt.close();
                conn.close();
                return new ParkingRate(vehicleType, ratePerHour);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Gagal ambil tarif: " + e.getMessage());
        }

        return new ParkingRate(type, 3000); // default fallback
    }
}
