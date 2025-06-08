package main.java.com.parkingsystem.views;

import main.java.com.parkingsystem.dao.UserDAO;
import main.java.com.parkingsystem.utils.ExcelExporter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;

public class UserView extends JFrame {
    private JTable table;
    private JTextField tfUsername, tfPassword;
    private JComboBox<String> cbRole;

    public UserView() {
        setTitle("SmartPark - Kelola User");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel titleLabel = new JLabel("Manajemen User", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setBounds(40, 30, 400, 40);
        add(titleLabel);

        JButton exportBtn = new JButton("Export ke Excel");
        exportBtn.setBounds(900, 40, 160, 30);
        exportBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        exportBtn.setBackground(new Color(0, 123, 255));
        exportBtn.setForeground(Color.WHITE);
        exportBtn.setFocusPainted(false);
        exportBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exportBtn.addActionListener(e -> {
            try {
                File dir = new File("laporan");
                if (!dir.exists()) dir.mkdirs();
                File outFile = new File(dir, "Laporan_User_" + LocalDate.now() + ".xlsx");
                ExcelExporter.exportTableToExcel((DefaultTableModel) table.getModel(), outFile.getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Laporan berhasil diekspor ke:\n" + outFile.getAbsolutePath());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal ekspor Excel: " + ex.getMessage());
            }
        });
        add(exportBtn);

        table = new JTable();
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(50, 100, 1000, 250);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(22);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        refreshTable();
        add(scroll);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(50, 370, 100, 25);
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(lblUsername);

        tfUsername = new JTextField();
        tfUsername.setBounds(150, 370, 200, 25);
        add(tfUsername);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(50, 410, 100, 25);
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(lblPassword);

        tfPassword = new JTextField();
        tfPassword.setBounds(150, 410, 200, 25);
        add(tfPassword);

        JLabel lblRole = new JLabel("Role:");
        lblRole.setBounds(50, 450, 100, 25);
        lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(lblRole);

        cbRole = new JComboBox<>(new String[]{"admin", "operator"});
        cbRole.setBounds(150, 450, 200, 25);
        add(cbRole);

        JButton btnAdd = createButton("Tambah", 400, 370, new Color(30, 136, 229));
        btnAdd.addActionListener(e -> {
            String username = tfUsername.getText().trim();
            String password = tfPassword.getText().trim();
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username dan password tidak boleh kosong.");
                return;
            }
            if (UserDAO.insertUser(username, password, cbRole.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(this, "User berhasil ditambahkan.");
                refreshTable();
                resetForm();
            }
        });
        add(btnAdd);

        JButton btnUpdate = createButton("Update", 400, 410, new Color(30, 136, 229));
        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String username = tfUsername.getText().trim();
                String password = tfPassword.getText().trim();
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Username dan password tidak boleh kosong.");
                    return;
                }
                int id = Integer.parseInt(table.getValueAt(row, 0).toString());
                if (UserDAO.updateUser(id, username, password, cbRole.getSelectedItem().toString())) {
                    JOptionPane.showMessageDialog(this, "User berhasil diupdate.");
                    refreshTable();
                    resetForm();
                }
            }
        });
        add(btnUpdate);

        JButton btnReset = createButton("Reset", 550, 370, new Color(30, 136, 229));
        btnReset.addActionListener(e -> resetForm());
        add(btnReset);

        JButton btnDelete = createButton("Hapus", 550, 410, new Color(220, 53, 69));
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = Integer.parseInt(table.getValueAt(row, 0).toString());
                int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus user ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (UserDAO.deleteUserById(id)) {
                        JOptionPane.showMessageDialog(this, "User berhasil dihapus.");
                        refreshTable();
                        resetForm();
                    }
                }
            }
        });
        add(btnDelete);

        // Isi form ketika baris diklik
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                tfUsername.setText(table.getValueAt(row, 1).toString());
                tfPassword.setText(table.getValueAt(row, 2).toString());
                cbRole.setSelectedItem(table.getValueAt(row, 3).toString());
            }
        });

        setVisible(true);
    }

    private void refreshTable() {
        table.setModel(UserDAO.getAllUsers());
    }

    private void resetForm() {
        tfUsername.setText("");
        tfPassword.setText("");
        cbRole.setSelectedIndex(0);
    }

    private JButton createButton(String text, int x, int y, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 120, 30);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        return btn;
    }
}
