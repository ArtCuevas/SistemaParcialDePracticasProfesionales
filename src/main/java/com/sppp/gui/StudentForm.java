package com.sppp.gui;

import com.sppp.dao.StudentDAO;
import com.sppp.dao.StudentDAOImp;
import com.sppp.model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Crea una ventana con un formulario para registrar estudiantes
 */
public class StudentForm extends JFrame {
    private JTextField nameField;
    private JTextField lastnameField;
    private JTextField nrcField;
    private JTextField enrolmentField;
    private JButton saveButton;
    private JButton clearButton;

    private StudentDAO studentDAO;

    public StudentForm() {
        setTitle("Formulario de Estudiante");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        studentDAO = new StudentDAOImp();
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel nameLabel = new JLabel("Nombre:");
        nameField = new JTextField();

        JLabel lastnameLabel = new JLabel("Apellido:");
        lastnameField = new JTextField();

        JLabel nrcLabel = new JLabel("NRC:");
        nrcField = new JTextField();

        JLabel enrolmentLabel = new JLabel("Matrícula:");
        enrolmentField = new JTextField();

        saveButton = new JButton("Guardar");
        clearButton = new JButton("Limpiar");

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(lastnameLabel);
        panel.add(lastnameField);
        panel.add(nrcLabel);
        panel.add(nrcField);
        panel.add(enrolmentLabel);
        panel.add(enrolmentField);
        panel.add(saveButton);
        panel.add(clearButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveStudent();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        add(panel);
    }

    /**
     * Guarda en la base de datos la informacion de un estudiante llenado por el usuario,
     * valida que todos los campos esten llenos y finalmente limpia el formulario para permitir seguir registrando
     * estudiantes
     */
    private void saveStudent() {
        try {
            String name = nameField.getText().trim();
            String lastname = lastnameField.getText().trim();
            String nrc = nrcField.getText().trim();
            String enrolment = enrolmentField.getText().trim();

            if (name.isEmpty() || lastname.isEmpty() || nrc.isEmpty() || enrolment.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Student student = new Student();
            student.setName(name);
            student.setLastname(lastname);
            student.setNrc(nrc);
            student.setEnrolment(enrolment);

            studentDAO.createStudent(student);

            JOptionPane.showMessageDialog(this, "Estudiante guardado exitosamente.");
            clearForm();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar el estudiante: " +
                    ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Limpia los campos para el ingreso de datos
     */
    private void clearForm() {
        nameField.setText("");
        lastnameField.setText("");
        nrcField.setText("");
        enrolmentField.setText("");
    }
}