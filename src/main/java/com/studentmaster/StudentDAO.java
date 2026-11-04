package com.studentmaster;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // 1. Menampilkan semua data mahasiswa
    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();

        String sql = "SELECT * FROM students";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("nim"),
                        rs.getString("nama"),
                        rs.getString("jenis_kelamin"),
                        rs.getString("jurusan"),
                        rs.getString("email"),
                        rs.getString("no_telepon"),
                        rs.getString("foto")
                );

                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }


    // 2. Menambahkan mahasiswa
    public boolean addStudent(Student student) {

        String sql = "INSERT INTO students " +
                "(nim, nama, jenis_kelamin, jurusan, email, no_telepon, foto) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, student.getNim());
            ps.setString(2, student.getNama());
            ps.setString(3, student.getJenisKelamin());
            ps.setString(4, student.getJurusan());
            ps.setString(5, student.getEmail());
            ps.setString(6, student.getNoTelepon());
            ps.setString(7, student.getFoto());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // 3. Mengubah data mahasiswa
    public boolean updateStudent(Student student) {

        String sql = "UPDATE students SET nim=?, nama=?, " +
                "jenis_kelamin=?, jurusan=?, email=?, " +
                "no_telepon=?, foto=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, student.getNim());
            ps.setString(2, student.getNama());
            ps.setString(3, student.getJenisKelamin());
            ps.setString(4, student.getJurusan());
            ps.setString(5, student.getEmail());
            ps.setString(6, student.getNoTelepon());
            ps.setString(7, student.getFoto());
            ps.setInt(8, student.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // 4. Menghapus data mahasiswa
    public boolean deleteStudent(int id) {

        String sql = "DELETE FROM students WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}