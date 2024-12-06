package com.sppp.gui;

import com.sppp.dao.ProjectDAO;
import com.sppp.dao.ProjectDAOImp;
import com.sppp.model.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Crea una ventana donde se solicitan los datos del proyecto
 */
public class ProjectForm extends JFrame {
    private JTextField projectNameField;
    private JTextField orgField;
    private JTextField quotaField;
    private ProjectDAO projectDAO;

    public ProjectForm() {
        setTitle("Formulario de Proyecto");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        JLabel nameLabel = new JLabel("Nombre del Proyecto:");
        projectNameField = new JTextField();

        JLabel orglabel = new JLabel("Organización vinculada:");
        orgField = new JTextField();

        JLabel quotaLabel = new JLabel("Cuota (Número de cupos):");
        quotaField = new JTextField();

        JButton saveButton = new JButton("Guardar");
        JButton cleanButton = new JButton("Limpiar");

        add(nameLabel);
        add(projectNameField);
        add(orglabel);
        add(orgField);
        add(quotaLabel);
        add(quotaField);
        add(saveButton);
        add(cleanButton);

        projectDAO = new ProjectDAOImp();


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProject();
            }
        });

        cleanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cleanForm();
            }
        });
    }

    /**
     * Guarda el registro del proyecto en la base de datos, dado el Objeto Project creado y llenado por el usuario
     * valida que los campos no esten vacios, pues todos son obligatorios, finalmente se limpia el formulario
     * para que pueda seguir agregando proyectos
     */
    private void saveProject() {
        try {
            String name = projectNameField.getText().trim();
            String org = orgField.getText().trim();
            int quota = Integer.parseInt(quotaField.getText().trim());

            if (name.isEmpty() || org.isEmpty() || quotaField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Project project = new Project();
            project.setNameprj(name);
            project.setRelatedorg(org);
            project.setQuota(quota);

            projectDAO.createProject(project);

            JOptionPane.showMessageDialog(this, "Proyecto guardado exitosamente.");
            cleanForm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cuota debe ser un número válido.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el proyecto: " +
                    e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Limpia los campos para el ingreso de datos
     */
    private void cleanForm() {
        projectNameField.setText("");
        orgField.setText("");
        quotaField.setText("");
    }
}