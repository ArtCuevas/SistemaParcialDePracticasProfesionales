package com.sppp.gui;

import com.sppp.dao.ProjectDAO;
import com.sppp.dao.ProjectDAOImp;
import com.sppp.model.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class UpdateProjectForm extends JFrame {

        private JTextField nameField;
        private JTextField orgField;
        private JTextField quotaField;
        private JButton saveButton;
        private JButton cancelButton;
        private Project project;

        public UpdateProjectForm(Project project) {
            this.project = project;

            setTitle("Actualizar Proyecto");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            initComponents();
            setVisible(true);
        }

        private void initComponents() {
            JPanel mainPanel = new JPanel(new GridLayout(4, 2, 10, 10));

            mainPanel.add(new JLabel("Nombre del Proyecto:"));
            nameField = new JTextField(project.getNameprj()); // Pre-rellenado
            mainPanel.add(nameField);

            mainPanel.add(new JLabel("Organización Relacionada:"));
            orgField = new JTextField(project.getRelatedorg()); // Pre-rellenado
            mainPanel.add(orgField);

            mainPanel.add(new JLabel("Cupo:"));
            quotaField = new JTextField(String.valueOf(project.getQuota())); // Pre-rellenado
            mainPanel.add(quotaField);

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
            project.setNameprj(nameField.getText());
            project.setRelatedorg(orgField.getText());
            try {
                project.setQuota(Integer.parseInt(quotaField.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El cupo debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Guardar en la base de datos
            ProjectDAO projectDAO = new ProjectDAOImp();
            try {
                projectDAO.updateProject(project);
                JOptionPane.showMessageDialog(this, "Proyecto actualizado exitosamente.");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar el proyecto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }


}
