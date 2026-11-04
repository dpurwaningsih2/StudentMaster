package com.studentmaster;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new StudentForm().setVisible(true);

        });
    }
}