package com.sppp.gui;

import com.sppp.dao.StudentDAO;
import com.sppp.dao.StudentDAOImp;
import com.sppp.model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class UpdateStudentForm extends JFrame {
    private JTextField nameField;
    private JTextField lastnameField;
    private JTextField nrcField;
    private JTextField enrolmentField;
    private JButton saveButton;
    private JButton cancelButton;
    private Student student;

    public UpdateStudentForm(Student student) {
        this.student = student;

        setTitle("Actualizar Estudiante");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        mainPanel.add(new JLabel("Nombre:"));
        nameField = new JTextField(student.getName()); // Pre-rellenado
        mainPanel.add(nameField);

        mainPanel.add(new JLabel("Apellido:"));
        lastnameField = new JTextField(student.getLastname()); // Pre-rellenado
        mainPanel.add(lastnameField);

        mainPanel.add(new JLabel("NRC:"));
        nrcField = new JTextField(student.getNrc()); // Pre-rellenado
        mainPanel.add(nrcField);

        mainPanel.add(new JLabel("Matrícula:"));
        enrolmentField = new JTextField(student.getEnrolment()); // Pre-rellenado
        mainPanel.add(enrolmentField);

        saveButton = new JButton("Guardar");
        cancelButton = new JButton("Cancelar");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(this::saveChanges);
        cancelButton.addActionListener(e -> dispose());
    }

    private void saveChanges(ActionEvent e) {
        // Actualizar solo los campos modificados
        student.setName(nameField.getText());
        student.setLastname(lastnameField.getText());
        student.setNrc(nrcField.getText());
        student.setEnrolment(enrolmentField.getText());

        // Guardar en la base de datos
        StudentDAO studentDAO = new StudentDAOImp();
        try {
            studentDAO.updateStudent(student);
            JOptionPane.showMessageDialog(this, "Estudiante actualizado exitosamente.");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el estudiante: "
                    + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
