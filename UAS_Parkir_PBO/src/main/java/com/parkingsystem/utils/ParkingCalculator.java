package main.java.com.parkingsystem.utils;

import main.java.com.parkingsystem.dao.ParkingRateDAO;
import main.java.com.parkingsystem.models.ParkingRate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ParkingCalculator {
    public static int calculateFee(String vehicleType, LocalDateTime entry, LocalDateTime exit) {
        long duration = ChronoUnit.HOURS.between(entry, exit);
        if (duration <= 0) duration = 1;

        int total;

        if (vehicleType.equalsIgnoreCase("Motor")) {
            total = 2000 + (int) ((duration - 1) * 2000);
            if (total > 25000) total = 25000;
        } else if (vehicleType.equalsIgnoreCase("Mobil")) {
            total = 3000 + (int) ((duration - 1) * 3000);
            if (total > 30000) total = 30000;
        } else {
            // fallback untuk jenis kendaraan lain, pakai tarif per jam dari DB
            ParkingRate rate = ParkingRateDAO.getRateByVehicleType(vehicleType);
            total = (int) duration * rate.getRatePerHour();
        }

        return total;
    }
}
