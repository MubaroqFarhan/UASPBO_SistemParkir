package main.java.com.parkingsystem.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StrukPDFGenerator {

    public static void generateStruk(String plate, String type, LocalDateTime entry, LocalDateTime exit, int fee) {
        try {
            // Format waktu
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            // Menyimpan di Folder struk
            File folder = new File("struk");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Nama file dan path
            String fileName = "Struk_" + plate + "_" + System.currentTimeMillis() + ".pdf";
            File outputFile = new File(folder, fileName);

            // Menyiapkan PDF Document
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputFile));
            document.open();

            // Logo di atas 
            String logoPath = "src/main/resources/logo.png"; 
            File logoFile = new File(logoPath);
            if (logoFile.exists()) {
                Image logo = Image.getInstance(logoPath);
                logo.scaleAbsolute(80, 80); 
                logo.setAlignment(Element.ALIGN_CENTER);
                document.add(logo);
            }

            // Judul
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("SMARTPARK - STRUK PARKIR", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Isi data
            document.add(new Paragraph("Plat Nomor     : " + plate));
            document.add(new Paragraph("Jenis Kendaraan: " + type));
            document.add(new Paragraph("Waktu Masuk    : " + entry.format(formatter)));
            document.add(new Paragraph("Waktu Keluar   : " + exit.format(formatter)));
            document.add(new Paragraph("Durasi Parkir  : " + getDuration(entry, exit) + " jam"));
            document.add(new Paragraph("Biaya Parkir   : Rp " + fee));
            document.add(new Paragraph(""));

            // Info tarif
            if (type.equalsIgnoreCase("Motor")) {
                document.add(new Paragraph("* Tarif: Rp 2.000 per jam"));
                document.add(new Paragraph("* Maksimal bayar: Rp 25.000"));
            } else if (type.equalsIgnoreCase("Mobil")) {
                document.add(new Paragraph("* Tarif: Rp 3.000 per jam"));
                document.add(new Paragraph("* Maksimal bayar: Rp 30.000"));
            } else {
                document.add(new Paragraph("* Tarif mengikuti sistem database"));
            }

            // Footer
            Paragraph footer = new Paragraph("\nTerima kasih telah menggunakan SmartPark!", new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC));
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(20);
            document.add(footer);

            document.close();

            // Buka file otomatis setelah disimpan
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(outputFile);
            }

            System.out.println("Struk berhasil disimpan di: " + outputFile.getAbsolutePath());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal mencetak struk: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static long getDuration(LocalDateTime entry, LocalDateTime exit) {
        long durasi = java.time.temporal.ChronoUnit.HOURS.between(entry, exit);
        return durasi <= 0 ? 1 : durasi;
    }
}
