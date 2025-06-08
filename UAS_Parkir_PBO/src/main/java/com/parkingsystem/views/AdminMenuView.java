package main.java.com.parkingsystem.views;

import main.java.com.parkingsystem.models.User;
import javax.swing.*;
import java.awt.*;

public class AdminMenuView extends JFrame {
    private Image backgroundImage;
    private Image logoImage;

    public AdminMenuView(User user) {
        setTitle("SmartPark - Admin Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        loadImages();

        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                int panelWidth = getWidth();
                int panelHeight = getHeight();

                if (backgroundImage != null) {
                    int bgWidth = backgroundImage.getWidth(this);
                    int bgHeight = backgroundImage.getHeight(this);

                    double scaleX = (double) panelWidth / bgWidth;
                    double scaleY = (double) panelHeight / bgHeight;
                    double scale = Math.max(scaleX, scaleY);

                    int scaledWidth = (int) (bgWidth * scale);
                    int scaledHeight = (int) (bgHeight * scale);

                    int bgX = (panelWidth - scaledWidth) / 2;
                    int bgY = (panelHeight - scaledHeight) / 2;

                    g2d.drawImage(backgroundImage, bgX, bgY, scaledWidth, scaledHeight, this);
                } else {
                    GradientPaint gradient = new GradientPaint(0, 0, new Color(25, 25, 112),
                            0, panelHeight, new Color(0, 0, 0));
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, panelWidth, panelHeight);
                }
            }
        };
        contentPanel.setLayout(null);

        JLabel logoLabel = new JLabel();
        if (logoImage != null) {
            ImageIcon logoIcon = new ImageIcon(logoImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            logoLabel.setIcon(logoIcon);
        } else {
            logoLabel.setText("SP");
            logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
            logoLabel.setForeground(Color.WHITE);
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            logoLabel.setVerticalAlignment(SwingConstants.CENTER);
            logoLabel.setOpaque(true);
            logoLabel.setBackground(new Color(30, 136, 229));
        }
        logoLabel.setBounds(20, 20, 100, 100);
        contentPanel.add(logoLabel);

        JLabel lblWelcome = new JLabel("Halo, " + user.getUsername() + " (Admin)");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setBounds(140, 40, 600, 30);
        contentPanel.add(lblWelcome);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        logoutBtn.setBackground(new Color(220, 53, 69));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        logoutBtn.setBounds(140, 80, 80, 30);
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginView();
        });
        logoutBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutBtn.setBackground(new Color(200, 35, 51));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutBtn.setBackground(new Color(220, 53, 69));
            }
        });
        contentPanel.add(logoutBtn);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(2, 2, 20, 20));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int buttonPanelWidth = 500;
        int buttonPanelHeight = 250;
        int buttonPanelX = (screenSize.width - buttonPanelWidth) / 2;
        int buttonPanelY = (screenSize.height - buttonPanelHeight) / 2;

        buttonPanel.setBounds(buttonPanelX, buttonPanelY, buttonPanelWidth, buttonPanelHeight);

        buttonPanel.add(createMenuButton("Entry Kendaraan", () -> new ParkingEntryView()));
        buttonPanel.add(createMenuButton("Exit Kendaraan", () -> new ParkingExitView()));
        buttonPanel.add(createMenuButton("Lihat Laporan", () -> new ReportView(true)));
        buttonPanel.add(createMenuButton("Kelola User", () -> new UserView()));

        contentPanel.add(buttonPanel);
        add(contentPanel);

        setVisible(true);
    }

    private JButton createMenuButton(String text, Runnable action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(new Color(30, 136, 229));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        btn.setPreferredSize(new Dimension(200, 100));

        btn.addActionListener(e -> {
            try {
                action.run();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(25, 118, 210));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(30, 136, 229));
            }
        });
        return btn;
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
