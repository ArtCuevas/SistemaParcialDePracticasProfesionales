package com.sppp.gui;

import com.sppp.dao.UserDAO;
import com.sppp.dao.UserDAOImp;
import com.sppp.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Proporciona un GUI para un Login, permite denegar acceso a usuarios no autorizados
 * Pide un nombre de usuario y una contrasena que se lee desde  (userNameField),(passwordField) respectivamente
 */
public class Login extends JFrame{
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    public Login(){
        setTitle("Inicio de Sesion");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 15, 15));

        userNameField = new JTextField();
        passwordField = new JPasswordField();
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);

        JButton loginButton = new JButton("Iniciar Sesion");
        loginButton.addActionListener(new LoginAction());

        panel.add(new JLabel("User:", SwingConstants.CENTER));
        panel.add(userNameField);
        panel.add(new JLabel("Password:", SwingConstants.CENTER));
        panel.add(passwordField);

        add(panel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);
        add(messageLabel, BorderLayout.NORTH);
    }

    /**
     * Lleva a cabo la accion de autenticar al usuario que ingrese al sistema
     */
    private class LoginAction implements ActionListener {
        /**
         * Si la autenticacion es exitosa le da acceso a un StudentManager y un ProjectManager
         * lo que le permite realizar operaciones CRUD sobre dichos Objetos (Student y Project)
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = userNameField.getText();
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

        /**
         * Valida el usuario y contrasena comparandolo con lo registrado en la base de datos
         * @param username String a comparar con el campo user en la base de datos
         * @param password String a comparar con el campo password en la base de datos
         * @return retorna verdadero en caso de exito y falso si las credenciales son incorrectas
         */
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