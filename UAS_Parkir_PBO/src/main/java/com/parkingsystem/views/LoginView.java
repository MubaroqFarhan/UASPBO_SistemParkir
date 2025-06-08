package main.java.com.parkingsystem.views;

import main.java.com.parkingsystem.controllers.LoginController;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Image backgroundImage;
    private Image logoImage;

    public LoginView() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        // Set frame
        setTitle("SmartPark - Sistem Parkir Cerdas");
        setSize(screenWidth, screenHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        setUndecorated(false);
        setLayout(new BorderLayout());

        // Load background dan logo dengan optimasi
        loadImages();

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                int panelWidth = getWidth();
                int panelHeight = getHeight();
                
                if (backgroundImage != null) {
                    // Scale background untuk mengisi layar dengan mempertahankan aspek ratio
                    int bgWidth = backgroundImage.getWidth(this);
                    int bgHeight = backgroundImage.getHeight(this);
                    
                    // Hitung scaling untuk mengisi seluruh layar
                    double scaleX = (double) panelWidth / bgWidth;
                    double scaleY = (double) panelHeight / bgHeight;
                    double scale = Math.max(scaleX, scaleY); // Gunakan scale terbesar untuk mengisi layar
                    
                    int scaledWidth = (int) (bgWidth * scale);
                    int scaledHeight = (int) (bgHeight * scale);
                    
                    // Center the background
                    int bgX = (panelWidth - scaledWidth) / 2;
                    int bgY = (panelHeight - scaledHeight) / 2;
                    
                    g2d.drawImage(backgroundImage, bgX, bgY, scaledWidth, scaledHeight, this);
                } else {
                    // Background gradient default
                    GradientPaint gradient = new GradientPaint(0, 0, new Color(25, 25, 112), 
                                                             0, panelHeight, new Color(0, 0, 0));
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, panelWidth, panelHeight);
                }

                if (logoImage != null) {
                    // Sesuaikan ukuran logo berdasarkan resolusi layar
                    int logoSize = Math.min(panelWidth, panelHeight) / 8; // Ukuran relatif terhadap layar
                    logoSize = Math.max(logoSize, 120); // Minimal 120px
                    logoSize = Math.min(logoSize, 200); // Maksimal 200px
                    
                    int logoX = (panelWidth - logoSize) / 2;
                    int logoY = panelHeight / 6; // Posisi di 1/6 dari atas layar
                    
                    g2d.drawImage(logoImage, logoX, logoY, logoSize, logoSize, this);
                } else {
                    // Text placeholder untuk logo
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(new Font("Segoe UI", Font.BOLD, 32));
                    String text = "SMARTPARK";
                    FontMetrics fm = g2d.getFontMetrics();
                    int textX = (panelWidth - fm.stringWidth(text)) / 2;
                    int textY = panelHeight / 6 + 20;
                    g2d.drawString(text, textX, textY);
                }
            }
        };

        mainPanel.setLayout(null);

        // Login box 
        int boxWidth = Math.min(450, screenWidth / 3);
        int boxHeight = 280;
        JPanel loginBox = new JPanel();
        loginBox.setLayout(null);
        
        // Background 
        loginBox.setBackground(new Color(0, 0, 0, 180));
        loginBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 50), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        loginBox.setBounds((screenWidth - boxWidth) / 2, 
                          (screenHeight - boxHeight) / 2 + 50, 
                          boxWidth, boxHeight);

        // Title
        JLabel lblTitle = new JLabel("Login ke SmartPark", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(0, 20, boxWidth, 30);
        loginBox.add(lblTitle);

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setBounds(50, 70, 80, 25);
        loginBox.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(50, 95, boxWidth - 100, 30);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        loginBox.add(usernameField);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passLabel.setBounds(50, 135, 80, 25);
        loginBox.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(50, 160, boxWidth - 100, 30);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        loginBox.add(passwordField);

        // Login button
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(50, 210, boxWidth - 100, 35);
        loginBtn.setBackground(new Color(30, 136, 229));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginBtn.setBorder(BorderFactory.createEmptyBorder());
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        loginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(new Color(25, 118, 210));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(new Color(30, 136, 229));
            }
        });
        
        loginBtn.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword());
            
            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Username dan Password tidak boleh kosong!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            LoginController.login(user, pass);
        });
        
        // Enter key untuk login
        passwordField.addActionListener(e -> loginBtn.doClick());
        
        loginBox.add(loginBtn);
        mainPanel.add(loginBox);
        add(mainPanel);

        // Focus pada username field saat startup
        SwingUtilities.invokeLater(() -> usernameField.requestFocus());
        
        setVisible(true);
    }
    
    private void loadImages() {
        try {
            // Path alternatif untuk mencari gambar
            String[] bgPaths = {
                "src/main/resources/bg.jpg",
                "src/resources/bg.jpg",
                "resources/bg.jpg",
                "bg.jpg"
            };
            
            String[] logoPaths = {
                "src/main/resources/logo.png",
                "src/resources/logo.png", 
                "resources/logo.png",
                "logo.png"
            };
            
            // Load background
            backgroundImage = loadImageFromPaths(bgPaths, "Background");
            
            // Load logo
            logoImage = loadImageFromPaths(logoPaths, "Logo");
            
        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
        }
    }
    
    private Image loadImageFromPaths(String[] paths, String imageType) {
        for (String path : paths) {
            try {
                ImageIcon icon = new ImageIcon(path);
                if (icon.getIconWidth() > 0 && icon.getIconHeight() > 0) {
                    System.out.println(imageType + " loaded successfully from: " + path);
                    System.out.println(imageType + " size: " + icon.getIconWidth() + "x" + icon.getIconHeight());
                    return icon.getImage();
                }
            } catch (Exception e) {
                // Lanjut ke path berikutnya
            }
        }
        System.out.println(imageType + " not found in any of the specified paths");
        return null;
    }
}