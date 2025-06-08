package main.java.com.parkingsystem.models;

public class Vehicle {
    private String plateNumber;
    private String vehicleType;

    public Vehicle(String plateNumber, String vehicleType) {
        this.plateNumber = plateNumber;
        this.vehicleType = vehicleType;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }
}
