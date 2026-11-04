package com.studentmaster;

import net.sf.jasperreports.engine.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class ReportTest {

    public static void main(String[] args) {

        String fileJRXML = "src/main/reports/laporan_mahasiswa_baru.jrxml";
        String filePDF = "Laporan_Mahasiswa.pdf";

        try {
            System.out.println("Mengompilasi laporan...");

            JasperReport jasperReport =
                    JasperCompileManager.compileReport(fileJRXML);

            System.out.println("Menghubungkan database...");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/students",
                    "root",
                    ""
            );

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("ID_AWAL", 1);
            parameters.put("ID_AKHIR", 100);

            System.out.println("Mengambil data mahasiswa...");

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                            jasperReport,
                            parameters,
                            connection
                    );

            System.out.println("Membuat file PDF...");

            JasperExportManager.exportReportToPdfFile(
                    jasperPrint,
                    filePDF
            );

            connection.close();

            System.out.println("BERHASIL!");
            System.out.println("PDF tersimpan: " + filePDF);

        } catch (Exception e) {
            System.out.println("GAGAL MEMBUAT PDF!");
            e.printStackTrace();
        }
    }
}