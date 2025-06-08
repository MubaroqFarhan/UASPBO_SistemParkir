package main.java.com.parkingsystem.controllers;

import main.java.com.parkingsystem.dao.TransactionDAO;

import javax.swing.table.DefaultTableModel;

public class ReportController {

    public static DefaultTableModel getTransactionReport() {
        return TransactionDAO.getAllTransactions();
    }
}
