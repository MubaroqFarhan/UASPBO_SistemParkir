package main.java.com.parkingsystem.models;

public class ParkingRate {
    private String vehicleType;
    private int ratePerHour;

    public ParkingRate(String vehicleType, int ratePerHour) {
        this.vehicleType = vehicleType;
        this.ratePerHour = ratePerHour;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public int getRatePerHour() {
        return ratePerHour;
    }
}
