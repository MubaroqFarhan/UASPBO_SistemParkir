package main.java.com.parkingsystem.views;

import main.java.com.parkingsystem.controllers.ParkingEntryController;

import javax.swing.*;
import java.awt.*;

public class ParkingEntryView extends JFrame {
    private Image backgroundImage;

    public ParkingEntryView() {
        setTitle("SmartPark - Entry Kendaraan");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        loadBackground();

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                int panelWidth = getWidth();
                int panelHeight = getHeight();

                if (backgroundImage != null) {
                    int imgWidth = backgroundImage.getWidth(this);
                    int imgHeight = backgroundImage.getHeight(this);

                    double scaleX = (double) panelWidth / imgWidth;
                    double scaleY = (double) panelHeight / imgHeight;
                    double scale = Math.max(scaleX, scaleY);

                    int scaledWidth = (int) (imgWidth * scale);
                    int scaledHeight = (int) (imgHeight * scale);

                    int x = (panelWidth - scaledWidth) / 2;
                    int y = (panelHeight - scaledHeight) / 2;

                    g2d.drawImage(backgroundImage, x, y, scaledWidth, scaledHeight, this);
                } else {
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.fillRect(0, 0, panelWidth, panelHeight);
                }
            }
        };
        mainPanel.setLayout(null);

        int boxWidth = 400;
        int boxHeight = 220;
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - boxWidth) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - boxHeight) / 2;

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(x, y, boxWidth, boxHeight);
        formPanel.setBackground(new Color(0, 0, 0, 180));

        JLabel title = new JLabel("Entry Kendaraan", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 10, boxWidth, 30);
        formPanel.add(title);

        JLabel lblPlate = new JLabel("Plat Nomor:");
        lblPlate.setBounds(50, 60, 100, 25);
        lblPlate.setForeground(Color.WHITE);
        formPanel.add(lblPlate);

        JTextField plateField = new JTextField();
        plateField.setBounds(150, 60, 180, 25);
        formPanel.add(plateField);

        JLabel lblType = new JLabel("Jenis Kendaraan:");
        lblType.setBounds(30, 100, 120, 25);
        lblType.setForeground(Color.WHITE);
        formPanel.add(lblType);

        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Motor", "Mobil"});
        typeBox.setBounds(150, 100, 180, 25);
        formPanel.add(typeBox);

        JButton btnSubmit = new JButton("Simpan");
        btnSubmit.setBounds((boxWidth - 100) / 2, 150, 100, 30);
        btnSubmit.setBackground(new Color(30, 136, 229));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFocusPainted(false);
        btnSubmit.addActionListener(e -> {
            String plate = plateField.getText().trim();
            String type = (String) typeBox.getSelectedItem();
            if (!plate.isEmpty()) {
                ParkingEntryController.handleEntry(plate, type);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Plat nomor tidak boleh kosong!");
            }
        });
        formPanel.add(btnSubmit);

        mainPanel.add(formPanel);
        setContentPane(mainPanel);
        setVisible(true);
        plateField.addActionListener(e -> btnSubmit.doClick());
    }

    private void loadBackground() {
        String[] paths = {
                "src/main/resources/bg.jpg", "resources/bg.jpg", "bg.jpg"
        };
        for (String path : paths) {
            ImageIcon icon = new ImageIcon(path);
            if (icon.getIconWidth() > 0 && icon.getIconHeight() > 0) {
                backgroundImage = icon.getImage();
                break;
            }
        }
    }
}
