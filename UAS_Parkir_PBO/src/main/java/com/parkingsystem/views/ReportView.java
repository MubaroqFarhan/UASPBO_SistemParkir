package main.java.com.parkingsystem.views;

import main.java.com.parkingsystem.dao.TransactionDAO;
import main.java.com.parkingsystem.utils.ExcelExporter;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.LocalDate;
import java.util.Vector;
import java.util.TreeSet;

public class ReportView extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;
    private Image backgroundImage;
    private Image logoImage;
    private JLabel totalLabel;
    private JComboBox<String> dateFilter;

    public ReportView(boolean isAdmin) {
        setTitle("SmartPark - Laporan Transaksi");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        loadImages();

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                int w = getWidth();
                int h = getHeight();
                if (backgroundImage != null) {
                    int bgW = backgroundImage.getWidth(this);
                    int bgH = backgroundImage.getHeight(this);

                    double scale = Math.max((double) w / bgW, (double) h / bgH);
                    int newW = (int) (bgW * scale);
                    int newH = (int) (bgH * scale);
                    int x = (w - newW) / 2;
                    int y = (h - newH) / 2;
                    g2d.drawImage(backgroundImage, x, y, newW, newH, this);
                } else {
                    g2d.setPaint(new GradientPaint(0, 0, new Color(25, 25, 112), 0, h, Color.BLACK));
                    g2d.fillRect(0, 0, w, h);
                }
            }
        };

        mainPanel.setLayout(null);

        JLabel logoLabel = new JLabel();
        if (logoImage != null) {
            logoLabel.setIcon(new ImageIcon(logoImage.getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        }
        logoLabel.setBounds(30, 20, 80, 80);
        mainPanel.add(logoLabel);

        JLabel lblTitle = new JLabel("Laporan Transaksi Parkir");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(130, 40, 600, 40);
        mainPanel.add(lblTitle);

        JTextField searchField = new JTextField();
        searchField.setBounds(60, 120, 300, 30);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        mainPanel.add(searchField);

        dateFilter = new JComboBox<>();
        dateFilter.setBounds(380, 120, 180, 30);
        dateFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mainPanel.add(dateFilter);

        JButton exportBtn = new JButton("Export ke Excel");
        exportBtn.setBounds(580, 120, 150, 30);
        exportBtn.setBackground(new Color(0, 123, 255));
        exportBtn.setForeground(Color.WHITE);
        exportBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        exportBtn.setFocusPainted(false);
        exportBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(exportBtn);

        table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(22);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setSelectionBackground(new Color(30, 136, 229));
        table.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(60, 170, 1000, 400);
        mainPanel.add(scrollPane);

        totalLabel = new JLabel("Total Transaksi: Rp 0");
        totalLabel.setForeground(Color.WHITE);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        totalLabel.setBounds(60, 580, 400, 30);
        mainPanel.add(totalLabel);

        refreshTable();

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                applyFilters(searchField.getText().trim(), (String) dateFilter.getSelectedItem());
            }
        });

        dateFilter.addActionListener(e -> {
            applyFilters(searchField.getText().trim(), (String) dateFilter.getSelectedItem());
        });

        exportBtn.addActionListener(e -> {
            File dir = new File("laporan");
            if (!dir.exists()) dir.mkdirs();
            File outFile = new File(dir, "Laporan_Parkir_" + LocalDate.now() + ".xlsx");
            ExcelExporter.exportTableToExcel(model, outFile.getAbsolutePath());

            JOptionPane.showMessageDialog(this,
                    "Laporan berhasil diekspor ke:\n" + outFile.getAbsolutePath(),
                    "Export Berhasil", JOptionPane.INFORMATION_MESSAGE);
        });

        if (isAdmin) {
            JButton btnDelete = new JButton("Hapus");
            btnDelete.setBounds(940, 600, 120, 35);
            btnDelete.setBackground(new Color(220, 53, 69));
            btnDelete.setForeground(Color.WHITE);
            btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnDelete.setFocusPainted(false);
            btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnDelete.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    String plate = table.getValueAt(row, 1).toString();
                    int confirm = JOptionPane.showConfirmDialog(this,
                            "Yakin ingin menghapus data plat " + plate + "?",
                            "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        TransactionDAO.deleteByPlate(plate);
                        refreshTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus.");
                }
            });
            mainPanel.add(btnDelete);
        }

        add(mainPanel);
        setVisible(true);
    }

    private void refreshTable() {
        model = TransactionDAO.getAllTransactions();
        table.setModel(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        updateTotal();
        populateDateFilter();
    }

    private void applyFilters(String keyword, String date) {
        RowFilter<DefaultTableModel, Object> combined = new RowFilter<>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                boolean matchesKeyword = keyword.isEmpty();
                boolean matchesDate = (date == null || date.equals("Semua"));

                for (int i = 0; i < entry.getValueCount(); i++) {
                    if (!matchesKeyword && entry.getStringValue(i).toLowerCase().contains(keyword.toLowerCase())) {
                        matchesKeyword = true;
                    }
                    if (!matchesDate && entry.getStringValue(3).startsWith(date)) {
                        matchesDate = true;
                    }
                }
                return matchesKeyword && matchesDate;
            }
        };
        sorter.setRowFilter(combined);
        updateTotal();
    }

    private void updateTotal() {
        int total = 0;
        for (int i = 0; i < sorter.getViewRowCount(); i++) {
            int modelRow = sorter.convertRowIndexToModel(i);
            Object valueObj = model.getValueAt(modelRow, 5);
            if (valueObj != null) {
                String value = valueObj.toString().replace("Rp ", "").replace(".", "");
                try {
                    total += Integer.parseInt(value);
                } catch (NumberFormatException ignored) {
                }
            }
        }
        totalLabel.setText("Total Transaksi: Rp " + String.format("%,d", total).replace(',', '.'));
    }

    private void populateDateFilter() {
        dateFilter.removeAllItems();
        dateFilter.addItem("Semua");
        Vector<?> data = model.getDataVector();
        TreeSet<String> uniqueDates = new TreeSet<>();
        for (Object rowObj : data) {
            Vector<?> row = (Vector<?>) rowObj;
            if (row.get(3) != null) {
                String date = row.get(3).toString().split(" ")[0];
                uniqueDates.add(date);
            }
        }
        for (String d : uniqueDates) dateFilter.addItem(d);
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
