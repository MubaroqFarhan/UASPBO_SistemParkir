package main.java.com.parkingsystem.models;

import java.time.LocalDateTime;

public class ParkingTransaction {
    private String plateNumber;
    private String vehicleType;
    private LocalDateTime entryTime;

    public ParkingTransaction(String plateNumber, String vehicleType, LocalDateTime entryTime) {
        this.plateNumber = plateNumber;
        this.vehicleType = vehicleType;
        this.entryTime = entryTime;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }
}
