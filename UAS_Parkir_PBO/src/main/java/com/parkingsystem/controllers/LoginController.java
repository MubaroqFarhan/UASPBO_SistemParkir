package main.java.com.parkingsystem.controllers;
import main.java.com.parkingsystem.dao.UserDAO;
import main.java.com.parkingsystem.models.User;
import main.java.com.parkingsystem.views.AdminMenuView;
import main.java.com.parkingsystem.views.OperatorMenuView;
import javax.swing.*;

public class LoginController {
    public static boolean login(String username, String password) {
        User user = UserDAO.login(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(null, "Login berhasil sebagai " + user.getRole());
            if (user.getRole().equalsIgnoreCase("admin")) {
                new AdminMenuView(user);
            } else {
                new OperatorMenuView(user);
            }
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Username atau password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}