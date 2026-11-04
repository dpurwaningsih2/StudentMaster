package com.studentmaster;

import net.sf.jasperreports.engine.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.awt.Desktop;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.List;

public class StudentForm extends JFrame {

    private JTextField txtNim, txtNama, txtEmail, txtTelepon;
    private JComboBox<String> cmbGender, cmbJurusan;
    private JTable table;
    private DefaultTableModel model;

    private final StudentDAO dao = new StudentDAO();

    public StudentForm() {

        setTitle("Student Master Management System");
        setSize(1150, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // =====================================
        // HEADER
        // =====================================

        JPanel header = new JPanel(new BorderLayout(15, 0));
        header.setBackground(new Color(30, 58, 95));
        header.setPreferredSize(new Dimension(1150, 100));
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // LOGO UNIVERSITAS BUDI LUHUR
        JLabel logoLabel = new JLabel();

        URL logoURL = getClass().getResource("/images/logo_ubl.png");

        if (logoURL != null) {

            ImageIcon logoIcon = new ImageIcon(logoURL);

            Image logoImage = logoIcon.getImage().getScaledInstance(
                    75, 75, Image.SCALE_SMOOTH
            );

            logoLabel.setIcon(new ImageIcon(logoImage));

        } else {

            logoLabel.setText("LOGO");
            logoLabel.setForeground(Color.WHITE);

            System.out.println("Logo tidak ditemukan!");
        }

        header.add(logoLabel, BorderLayout.WEST);

        JLabel title = new JLabel("STUDENT MASTER");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));

        JLabel subtitle = new JLabel(
                "Student Data Management System"
        );

        subtitle.setForeground(new Color(210, 225, 245));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel headerText = new JPanel(new GridLayout(2, 1));
        headerText.setOpaque(false);
        headerText.add(title);
        headerText.add(subtitle);

        header.add(headerText, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);


        // =====================================
        // MAIN PANEL
        // =====================================

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(238, 243, 250));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );


        // =====================================
        // FORM PANEL
        // =====================================

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);

        formPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 225, 235)
                        ),
                        BorderFactory.createEmptyBorder(
                                15, 15, 15, 15
                        )
                )
        );

        formPanel.setPreferredSize(new Dimension(340, 500));

        JLabel formTitle = new JLabel("Student Information");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 19));
        formTitle.setForeground(new Color(30, 58, 95));

        formPanel.add(formTitle);
        formPanel.add(Box.createVerticalStrut(20));


        // INPUT FIELD
        txtNim = new JTextField();
        txtNama = new JTextField();
        txtEmail = new JTextField();
        txtTelepon = new JTextField();

        cmbGender = new JComboBox<>(
                new String[]{"Male", "Female"}
        );

        cmbJurusan = new JComboBox<>(
                new String[]{
                        "Teknik Informatika",
                        "Sistem Informasi",
                        "Manajemen",
                        "Akuntansi",
                        "Ilmu Komunikasi"
                }
        );


        addField(formPanel, "Student ID / NIM", txtNim);
        addField(formPanel, "Full Name", txtNama);
        addField(formPanel, "Gender", cmbGender);
        addField(formPanel, "Major / Jurusan", cmbJurusan);
        addField(formPanel, "Email Address", txtEmail);
        addField(formPanel, "Phone Number", txtTelepon);


        formPanel.add(Box.createVerticalStrut(15));


        // =====================================
        // BUTTONS
        // =====================================

        JButton btnAdd = createButton(
                "ADD",
                new Color(34, 139, 87)
        );

        JButton btnUpdate = createButton(
                "UPDATE",
                new Color(42, 111, 180)
        );

        JButton btnDelete = createButton(
                "DELETE",
                new Color(190, 55, 55)
        );

        JButton btnClear = createButton(
                "CLEAR",
                new Color(100, 110, 125)
        );

        JButton btnReport = createButton(
                "REPORT",
                new Color(120, 85, 170)
        );


        JPanel buttonPanel = new JPanel(
                new GridLayout(3, 2, 8, 8)
        );

        buttonPanel.setOpaque(false);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnReport);

        formPanel.add(buttonPanel);


        // =====================================
        // TABLE PANEL
        // =====================================

        JPanel tablePanel = new JPanel(
                new BorderLayout(10, 10)
        );

        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        JLabel tableTitle = new JLabel("Student Records");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 19));
        tableTitle.setForeground(new Color(30, 58, 95));

        tablePanel.add(tableTitle, BorderLayout.NORTH);


        // KOLOM TABEL
        String[] columns = {
                "ID",
                "NIM",
                "Name",
                "Gender",
                "Major / Jurusan",
                "Email",
                "Phone"
        };

        model = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);

        table.setRowHeight(32);
        // Mengatur lebar kolom tabel
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(0).setMaxWidth(50);

        table.getColumnModel().getColumn(1).setPreferredWidth(110);
        table.getColumnModel().getColumn(2).setPreferredWidth(160);
        table.getColumnModel().getColumn(3).setPreferredWidth(85);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(180);
        table.getColumnModel().getColumn(6).setPreferredWidth(120);


        // HEADER TABEL
        table.getTableHeader().setBackground(
                new Color(30, 58, 95)
        );

        table.getTableHeader().setForeground(Color.WHITE);

        table.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 12)
        );


        // =====================================
        // WARNA BERBEDA SETIAP MAHASISWA
        // =====================================

        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean selected,
                            boolean focus,
                            int row,
                            int column
                    ) {

                        Component c = super.getTableCellRendererComponent(
                                table,
                                value,
                                selected,
                                focus,
                                row,
                                column
                        );

                        if (selected) {

                            c.setBackground(new Color(180, 210, 245));
                            c.setForeground(Color.BLACK);

                        } else {

                            int studentId = (int) table.getValueAt(row, 0);

                            Color[] colors = {
                                    new Color(235, 245, 255),
                                    new Color(240, 250, 240),
                                    new Color(255, 245, 235),
                                    new Color(250, 240, 255),
                                    new Color(255, 250, 225)
                            };

                            c.setBackground(
                                    colors[Math.floorMod(studentId, colors.length)]
                            );

                            c.setForeground(new Color(40, 50, 65));
                        }

                        return c;
                    }
                };


        for (int i = 0; i < table.getColumnCount(); i++) {

            table.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(renderer);
        }


        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);


        // =====================================
        // STATUS FOOTER
        // =====================================

        JLabel footer = new JLabel(
                "Student Master System | Ready"
        );

        footer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footer.setForeground(Color.GRAY);

        tablePanel.add(footer, BorderLayout.SOUTH);


        // =====================================
        // MENGGABUNGKAN PANEL
        // =====================================

        mainPanel.add(formPanel, BorderLayout.WEST);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);


        // =====================================
        // EVENT BUTTON
        // =====================================

        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearForm());

        btnReport.addActionListener(e -> generateReport());

        // =====================================
        // TABLE SELECTION
        // =====================================

        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                selectStudent();
            }
        });


        loadStudents();
    }


    // =====================================
    // ADD FIELD
    // =====================================

    private void addField(
            JPanel panel,
            String label,
            JComponent field
    ) {

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        field.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 30)
        );

        panel.add(lbl);
        panel.add(Box.createVerticalStrut(4));
        panel.add(field);
        panel.add(Box.createVerticalStrut(8));
    }


    // =====================================
    // CREATE BUTTON
    // =====================================

    private JButton createButton(String text, Color color) {

        JButton button = new JButton(text);

        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 11));
        button.setFocusPainted(false);

        return button;
    }


    // =====================================
    // MENAMPILKAN DATA
    // =====================================

    private void loadStudents() {

        model.setRowCount(0);

        List<Student> students = dao.getAllStudents();

        for (Student s : students) {

            model.addRow(new Object[]{
                    s.getId(),
                    s.getNim(),
                    s.getNama(),
                    s.getJenisKelamin(),
                    s.getJurusan(),
                    s.getEmail(),
                    s.getNoTelepon()
            });
        }
    }


    // =====================================
    // MENAMBAHKAN DATA
    // =====================================

    private void addStudent() {

        if (!validateForm()) return;

        Student student = new Student(
                0,
                txtNim.getText().trim(),
                txtNama.getText().trim(),
                cmbGender.getSelectedItem().toString(),
                cmbJurusan.getSelectedItem().toString(),
                txtEmail.getText().trim(),
                txtTelepon.getText().trim(),
                ""
        );

        if (dao.addStudent(student)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student added successfully!"
            );

            loadStudents();
            clearForm();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add student. Check NIM and database."
            );
        }
    }


    // =====================================
    // MEMILIH DATA DARI TABEL
    // =====================================

    private void selectStudent() {

        int row = table.getSelectedRow();

        if (row == -1) return;

        txtNim.setText(model.getValueAt(row, 1).toString());
        txtNama.setText(model.getValueAt(row, 2).toString());

        cmbGender.setSelectedItem(model.getValueAt(row, 3));
        cmbJurusan.setSelectedItem(model.getValueAt(row, 4));

        txtEmail.setText(model.getValueAt(row, 5).toString());
        txtTelepon.setText(model.getValueAt(row, 6).toString());
    }


    // =====================================
    // UPDATE DATA
    // =====================================

    private void updateStudent() {

        int row = table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a student first!"
            );

            return;
        }

        if (!validateForm()) return;

        int id = (int) model.getValueAt(row, 0);

        Student student = new Student(
                id,
                txtNim.getText().trim(),
                txtNama.getText().trim(),
                cmbGender.getSelectedItem().toString(),
                cmbJurusan.getSelectedItem().toString(),
                txtEmail.getText().trim(),
                txtTelepon.getText().trim(),
                ""
        );

        if (dao.updateStudent(student)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student updated successfully!"
            );

            loadStudents();
            clearForm();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update student."
            );
        }
    }


    // =====================================
    // DELETE DATA
    // =====================================

    private void deleteStudent() {

        int row = table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a student first!"
            );

            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this student?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {

            int id = (int) model.getValueAt(row, 0);

            if (dao.deleteStudent(id)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Student deleted successfully!"
                );

                loadStudents();
                clearForm();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to delete student."
                );
            }
        }
    }


    // =====================================
    // MEMBERSIHKAN FORM
    // =====================================

    private void clearForm() {

        txtNim.setText("");
        txtNama.setText("");
        txtEmail.setText("");
        txtTelepon.setText("");

        cmbGender.setSelectedIndex(0);
        cmbJurusan.setSelectedIndex(0);

        table.clearSelection();
    }


    // =====================================
    // VALIDASI FORM
    // =====================================

    private boolean validateForm() {

        if (txtNim.getText().trim().isEmpty()
                || txtNama.getText().trim().isEmpty()
                || txtEmail.getText().trim().isEmpty()
                || txtTelepon.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please complete all student information!"
            );

            return false;
        }

        return true;
    }
// =====================================
// GENERATE JASPER REPORT
// =====================================

    private void generateReport() {
        JTextField txtIdAwal = new JTextField();
        JTextField txtIdAkhir = new JTextField();

        Object[] input = {
                "ID Awal:", txtIdAwal,
                "ID Akhir:", txtIdAkhir
        };

        int result = JOptionPane.showConfirmDialog(
                this,
                input,
                "Cetak Laporan Mahasiswa",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {

            int idAwal = Integer.parseInt(txtIdAwal.getText().trim());
            int idAkhir = Integer.parseInt(txtIdAkhir.getText().trim());

            if (idAwal <= 0 || idAkhir < idAwal) {
                JOptionPane.showMessageDialog(
                        this,
                        "Rentang ID tidak valid!"
                );
                return;
            }

            String fileJRXML =
                    "src/main/reports/laporan_mahasiswa_baru.jrxml";

            String filePDF = "Laporan_Mahasiswa.pdf";

            JasperReport jasperReport =
                    JasperCompileManager.compileReport(fileJRXML);

            try (Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/students",
                    "root",
                    ""
            )) {

                Map<String, Object> parameters = new HashMap<>();

                parameters.put("ID_AWAL", idAwal);
                parameters.put("ID_AKHIR", idAkhir);

                parameters.put(
                        "LOGO_PATH",
                        "C:/Users/dwipu/IdeaProjects/StudentMaster/src/main/resources/images/logo_ubl.png"
                );

                JasperPrint jasperPrint =
                        JasperFillManager.fillReport(
                                jasperReport,
                                parameters,
                                connection
                        );

                JasperExportManager.exportReportToPdfFile(
                        jasperPrint,
                        filePDF
                );
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Laporan berhasil dibuat!\nFile: " + filePDF
            );

            Desktop.getDesktop().open(new File(filePDF));

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "ID Awal dan ID Akhir harus berupa angka!"
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Gagal membuat laporan: " + e.getMessage(),
                    "Report Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

}