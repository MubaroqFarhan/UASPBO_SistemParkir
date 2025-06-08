package main.java.com.parkingsystem.views;

import main.java.com.parkingsystem.controllers.ParkingExitController;

import javax.swing.*;
import java.awt.*;

public class ParkingExitView extends JFrame {
    private Image backgroundImage;
    private Image logoImage;

    public ParkingExitView() {
        setTitle("Kendaraan Keluar - SmartPark");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        loadImages();

        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                int w = getWidth();
                int h = getHeight();

                if (backgroundImage != null) {
                    int imgW = backgroundImage.getWidth(this);
                    int imgH = backgroundImage.getHeight(this);
                    double scale = Math.max((double) w / imgW, (double) h / imgH);
                    int newW = (int) (imgW * scale);
                    int newH = (int) (imgH * scale);
                    int x = (w - newW) / 2;
                    int y = (h - newH) / 2;
                    g2d.drawImage(backgroundImage, x, y, newW, newH, this);
                } else {
                    g2d.setPaint(new GradientPaint(0, 0, new Color(25, 25, 112), 0, h, Color.BLACK));
                    g2d.fillRect(0, 0, w, h);
                }
            }
        };
        contentPanel.setLayout(null);

        JLabel logoLabel = new JLabel();
        if (logoImage != null) {
            logoLabel.setIcon(new ImageIcon(logoImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        } else {
            logoLabel.setText("SP");
            logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
            logoLabel.setOpaque(true);
            logoLabel.setForeground(Color.WHITE);
            logoLabel.setBackground(new Color(30, 136, 229));
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        logoLabel.setBounds(20, 20, 100, 100);
        contentPanel.add(logoLabel);

        JLabel title = new JLabel("Proses Kendaraan Keluar", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBounds(140, 40, 600, 40);
        contentPanel.add(title);

        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(null);
        int formWidth = 400;
        int formHeight = 160;
        formPanel.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - formWidth) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - formHeight) / 2,
                formWidth, formHeight);

        JLabel lblPlate = new JLabel("Plat Nomor:");
        lblPlate.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblPlate.setForeground(Color.WHITE);
        lblPlate.setBounds(20, 10, 120, 30);
        formPanel.add(lblPlate);

        JTextField tfPlate = new JTextField();
        tfPlate.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tfPlate.setBounds(150, 10, 200, 30);
        formPanel.add(tfPlate);

        JButton btnProses = new JButton("Proses Keluar");
        btnProses.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnProses.setBackground(new Color(30, 136, 229));
        btnProses.setForeground(Color.WHITE);
        btnProses.setFocusPainted(false);
        btnProses.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnProses.setBounds(150, 60, 200, 40);

        btnProses.addActionListener(e -> {
            String plate = tfPlate.getText().trim();
            if (!plate.isEmpty()) {
                ParkingExitController.handleExit(plate, btnProses);
            } else {
                JOptionPane.showMessageDialog(this, "Plat nomor tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        tfPlate.addActionListener(e -> btnProses.doClick());

        formPanel.add(btnProses);
        contentPanel.add(formPanel);
        add(contentPanel);
        setVisible(true);
    }

    private void loadImages() {
        backgroundImage = loadImageFromPaths(new String[]{
                "src/main/resources/bg.jpg", "resources/bg.jpg", "bg.jpg"
        });
        logoImage = loadImageFromPaths(new String[]{
                "src/main/resources/logo.png", "resources/logo.png", "logo.png"
        });
    }

    private Image loadImageFromPaths(String[] paths) {
        for (String path : paths) {
            ImageIcon icon = new ImageIcon(path);
            if (icon.getIconWidth() > 0 && icon.getIconHeight() > 0) {
                return icon.getImage();
            }
        }
        return null;
    }
}
