package com.studentmaster;

public class Student {

    private int id;
    private String nim;
    private String nama;
    private String jenisKelamin;
    private String jurusan;
    private String email;
    private String noTelepon;
    private String foto;

    public Student(int id, String nim, String nama,
                   String jenisKelamin, String jurusan,
                   String email, String noTelepon, String foto) {

        this.id = id;
        this.nim = nim;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.jurusan = jurusan;
        this.email = email;
        this.noTelepon = noTelepon;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public String getJurusan() {
        return jurusan;
    }

    public String getEmail() {
        return email;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public String getFoto() {
        return foto;
    }
}
