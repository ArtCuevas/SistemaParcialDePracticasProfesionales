package com.sppp.gui;

import com.sppp.model.Project;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ProjectManager extends JFrame {
    private DefaultListModel<Project> projectListModel;
    private JList<Project> projectJList;
    private JTextField searchField;
    private ArrayList<Project> projects;

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


    }
}
