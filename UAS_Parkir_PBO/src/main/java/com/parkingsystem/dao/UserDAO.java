package main.java.com.parkingsystem.dao;

import main.java.com.parkingsystem.models.User;

import java.sql.*;

import javax.swing.table.DefaultTableModel;

public class UserDAO {

    public static User login(String username, String password) {
        User user = null;

        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.out.println("Koneksi ke database gagal.");
                return null;
            }

            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                user = new User(username, password, role);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("Login error (SQLException): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Login error (General): " + e.getMessage());
        }

        return user;
    }
    public static DefaultTableModel getAllUsers() {
        String[] columns = {"ID", "Username", "Password", "Role"};
        DefaultTableModel model = new DefaultTableModel(null, columns);
    
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM users";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
    
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                };
                model.addRow(row);
            }
    
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Load users error: " + e.getMessage());
        }
    
        return model;
    }    
    public static boolean insertUser(String username, String password, String role) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            int result = stmt.executeUpdate();
            stmt.close();
            conn.close();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Insert user error: " + e.getMessage());
            return false;
        }
    }
    
public static boolean updateUser(int id, String username, String password, String role) {
    try {
        Connection conn = DatabaseConnection.getConnection();
        String query = "UPDATE users SET username=?, password=?, role=? WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setString(3, role);
        stmt.setInt(4, id);
        int result = stmt.executeUpdate();
        stmt.close();
        conn.close();
        return result > 0;
    } catch (SQLException e) {
        System.out.println("Update user error: " + e.getMessage());
        return false;
    }
}

public static boolean deleteUserById(int id) {
    try {
        Connection conn = DatabaseConnection.getConnection();
        String query = "DELETE FROM users WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);
        int result = stmt.executeUpdate();
        stmt.close();
        conn.close();
        return result > 0;
    } catch (SQLException e) {
        System.out.println("Delete user error: " + e.getMessage());
        return false;
    }
}

    
}
