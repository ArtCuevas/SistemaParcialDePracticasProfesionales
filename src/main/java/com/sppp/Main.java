package com.sppp;

import com.sppp.gui.Login;

import javax.swing.*;

/**
 * Se inicia el sistema desde el Login, mismo que dispara el acceso a las demas partes del sistema
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login loginForm = new Login();
            loginForm.setVisible(true);
        });
    }

}