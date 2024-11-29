package com.sppp.gui;

import com.sppp.dao.ProjectDAO;
import com.sppp.dao.ProjectDAOImp;
import com.sppp.model.Project;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProjectManager extends JFrame {
    private DefaultListModel<String> projectListModel;
    private JList<String> projectJList;
    private JTextField searchField;
    private ArrayList<String> projects;

    public ProjectManager() throws HeadlessException {
        setTitle("Lista de proyectos");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    }

    private void loadInitialProjects(){
        ProjectDAO projectDAO = new ProjectDAOImp();
        try{
            ArrayList<Project> p = (ArrayList<Project>) projectDAO.getAllProjects();
            projects = (ArrayList<String>) p.stream().map(Project::getNameprj).collect(Collectors.toList());
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        updateProjectList();
    }

    private void updateProjectList(){
        projectListModel.clear();
        for(String project : projects){
            projectListModel.addElement(project);
        }
    }

    private void filterProjectList(){
        String searchText = searchField.getText().toLowerCase();
        projectListModel.clear();
        for (String project: projects){
            if(project.toLowerCase().contains(searchText)){
                projectListModel.addElement(project);
            }
        }
    }

    private void addProject() {
        String newProjectName = JOptionPane
                .showInputDialog(this, "Ingrese el nombre del nuevo Projecto:");
        String newProjectOrg = JOptionPane
                .showInputDialog(this, "Ingrese el nombre del nuevo Projecto:");
        String newProjectCuota = JOptionPane
                .showInputDialog(this, "Ingrese el nombre del nuevo Projecto:");
        if (newProjectName != null && !newProjectName.trim().isEmpty()) {
            projects.add(newProjectName.trim());
            ProjectDAO projectDAO = new ProjectDAOImp();
            Project p = new Project();
            p.setNameprj(newProjectName);
            try {
                projectDAO.createProject(p);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            updateProjectList();
        }
    }


}
