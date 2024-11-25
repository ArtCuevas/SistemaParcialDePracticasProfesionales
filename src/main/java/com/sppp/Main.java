package com.sppp;

import com.sppp.gui.Login;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //pruebz comentsrio
        SwingUtilities.invokeLater(() -> {
            Login loginForm = new Login();
            loginForm.setVisible(true);
        });
    }

}