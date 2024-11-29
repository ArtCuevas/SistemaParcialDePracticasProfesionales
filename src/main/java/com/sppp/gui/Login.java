package com.sppp.gui;

import com.sppp.dao.UserDAO;
import com.sppp.dao.UserDAOImp;
import com.sppp.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Login extends JFrame{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    public Login(){
        setTitle("Inicio de Sesion");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);

        JButton loginButton = new JButton("Iniciar Sesion");
        loginButton.addActionListener(new LoginAction());

        panel.add(new JLabel("User:", SwingConstants.CENTER));
        panel.add(usernameField);
        panel.add(new JLabel("Password:", SwingConstants.CENTER));
        panel.add(passwordField);

        add(panel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);
        add(messageLabel, BorderLayout.NORTH);
    }


    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (authenticate(username, password)) {
                messageLabel.setText("Inicio de sesión exitoso");
                JOptionPane.showMessageDialog(Login.this, "Bienvenido " + username);
                dispose();
                new StudentManager();
                new ProjectManager();
            } else {
                messageLabel.setText("Usuario o contraseña incorrectos");
            }
        }

        private boolean authenticate(String username, String password) {
            UserDAO userDao = new UserDAOImp();
            String user;
            String pass;
            try {
                User dbUser = userDao.readUser(1);
                user = dbUser.getUsername();
                pass = dbUser.getPassword();
                return user.equals(username) && pass.equals(password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
