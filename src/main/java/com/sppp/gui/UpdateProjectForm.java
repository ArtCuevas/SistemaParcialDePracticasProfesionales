package com.sppp.gui;

import com.sppp.dao.ProjectDAO;
import com.sppp.dao.ProjectDAOImp;
import com.sppp.model.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Crea una ventana para poder actualizar los datos de algun proyecto previamente registrado
 * el formulario que muestra esta prellenado con los datos de dicho proyecto y el usuario puede editar los que desee
 */
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
            nameField = new JTextField(project.getNameprj());
            mainPanel.add(nameField);

            mainPanel.add(new JLabel("Organización Relacionada:"));
            orgField = new JTextField(project.getRelatedorg());
            mainPanel.add(orgField);

            mainPanel.add(new JLabel("Cupo:"));
            quotaField = new JTextField(String.valueOf(project.getQuota()));
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

    /**
     * Actualiza los cambios hechos por el usuario a la informacion del proyecto y lo guarda en la base de datos
     * @param e Recibe el evento del boton "Guardar"
     */
        private void saveChanges(ActionEvent e) {
            project.setNameprj(nameField.getText());
            project.setRelatedorg(orgField.getText());
            try {
                project.setQuota(Integer.parseInt(quotaField.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El cupo debe ser un número válido.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ProjectDAO projectDAO = new ProjectDAOImp();
            try {
                projectDAO.updateProject(project);
                JOptionPane.showMessageDialog(this, "Proyecto actualizado exitosamente.");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar el proyecto: " +
                        ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
}