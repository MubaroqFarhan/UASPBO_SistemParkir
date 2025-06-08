package main.java.com.parkingsystem.controllers;
import main.java.com.parkingsystem.dao.TransactionDAO;
import main.java.com.parkingsystem.models.ParkingTransaction;
import javax.swing.*;
import java.time.LocalDateTime;

public class ParkingEntryController {
    public static void handleEntry(String plate, String type) {
        LocalDateTime now = LocalDateTime.now();
        ParkingTransaction trx = new ParkingTransaction(plate, type, now);
        boolean result = TransactionDAO.insertEntry(trx);
        if (result) {
            JOptionPane.showMessageDialog(null, "Data parkir berhasil disimpan!");
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data parkir.");
        }
    }
}