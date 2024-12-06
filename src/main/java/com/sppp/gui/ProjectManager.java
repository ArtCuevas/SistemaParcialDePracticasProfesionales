package com.sppp.gui;

import com.sppp.dao.ProjectDAO;
import com.sppp.dao.ProjectDAOImp;
import com.sppp.model.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectManager extends JFrame {
    private DefaultListModel<String> projectListModel;
    private JList<String> projectJList;
    private JTextField searchField;
    private ArrayList<String> projects;

    public ProjectManager() throws HeadlessException {
        setTitle("Lista de proyectos");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        projects = new ArrayList<>();
        projectListModel = new DefaultListModel<>();
        projectJList = new JList<>(projectListModel);
        searchField = new JTextField();

        initComponents();
        setVisible(true);
    }

    private void initComponents(){
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Buscar projecto");
        topPanel.add(searchLabel, BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);

        JButton addButton = new JButton("Agregar");
        JButton updateButton = new JButton("Actualizar");
        JButton deleteButton = new JButton("Eliminar");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(projectJList), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        searchField.addActionListener(e -> filterProjectList());

        addButton.addActionListener(e -> addProject());
        updateButton.addActionListener(e -> updateProjectList());
        deleteButton.addActionListener(e -> deleteProject());

        loadInitialProjects();
    }

    private void loadInitialProjects(){
        ProjectDAO projectDAO = new ProjectDAOImp();
        try{
            ArrayList<Project> p = (ArrayList<Project>) projectDAO.getAllProjects();
            projects.clear();
            for(Project project : p){
                projects.add(project.getNameprj());
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        updateProjectListDisplay();
    }

    private void updateProjectListDisplay() {
        projectListModel.clear();
        for (String project : projects) {
            projectListModel.addElement(project);
        }
    }

    private void updateProjectList() {
        String selectedProject = projectJList.getSelectedValue();
        if (selectedProject != null) {
            ProjectDAO projectDAO = new ProjectDAOImp();
            try {
                Project project = projectDAO.getProjectByName(selectedProject);
                if (project != null) {
                    SwingUtilities.invokeLater(() -> {
                        new UpdateProjectForm(project);
                    });
                    updateProjectListDisplay();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al obtener los datos del proyecto.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proyecto para actualizar.");
        }
    }

    private void filterProjectList(){
        String searchText = searchField.getText().toLowerCase();
        projectListModel.clear();
        for (String project : projects) {
            if (project.toLowerCase().contains(searchText)) {
                   projectListModel.addElement(project);
            }
        }
    }

    private void addProject() {
        SwingUtilities.invokeLater(() -> {
            ProjectForm projectForm = new ProjectForm();
            projectForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadInitialProjects();
                }
            });
            projectForm.setVisible(true);
        });
    }

    private void deleteProject(){
        String selectedProject = projectJList.getSelectedValue();
        if (selectedProject != null) {
            projects.remove(selectedProject);
            ProjectDAO pjD = new ProjectDAOImp();
            Project p = new Project();
            p.setNameprj(selectedProject);
            try {
                pjD.deleteProject(p);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            updateProjectListDisplay();
        } else {
            JOptionPane
                    .showMessageDialog(this,
                            "Seleccione un projecto para eliminar.");
        }
    }
}
