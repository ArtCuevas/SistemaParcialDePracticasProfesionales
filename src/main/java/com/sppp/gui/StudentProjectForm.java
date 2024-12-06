package com.sppp.gui;

import com.sppp.dao.ProjectDAO;
import com.sppp.dao.ProjectDAOImp;
import com.sppp.dao.StudentDAO;
import com.sppp.dao.StudentDAOImp;
import com.sppp.model.Project;
import com.sppp.model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentProjectForm extends JFrame {
    private JComboBox<String> studentComboBox;
    private JComboBox<String> projectComboBox;
    private JButton assignButton;

    private StudentDAO studentDAO;
    private ProjectDAO projectDAO;
    private Map<String, Integer> studentMap = new HashMap<>();
    private Map<String, Integer> projectMap = new HashMap<>();

    public StudentProjectForm() {
        studentDAO = new StudentDAOImp();
        projectDAO = new ProjectDAOImp();

        setTitle("Asignar Estudiante a Proyecto");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        loadComboBoxData();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel studentLabel = new JLabel("Selecciona un estudiante:");
        studentComboBox = new JComboBox<>();

        JLabel projectLabel = new JLabel("Selecciona un proyecto:");
        projectComboBox = new JComboBox<>();

        assignButton = new JButton("Asignar");

        panel.add(studentLabel);
        panel.add(studentComboBox);
        panel.add(projectLabel);
        panel.add(projectComboBox);
        panel.add(new JLabel());
        panel.add(assignButton);

        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assignStudentToProject();
            }
        });

        add(panel);
    }

    private void loadComboBoxData() {
        try {
            List<Student> students = studentDAO.getAllStudents();
            for (Student student : students) {
                String displayName = student.getName() + " " + student.getLastname();
                studentComboBox.addItem(displayName);
                studentMap.put(student.getName(), student.getIdstudent());
            }

            List<Project> projects = projectDAO.getAllProjects();
            for (Project project : projects) {
                projectComboBox.addItem(project.getNameprj());
                projectMap.put(project.getNameprj(), project.getIdproject());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " +
                    e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void assignStudentToProject() {
        try {
            String selectedStudent = (String) studentComboBox.getSelectedItem();
            String selectedProject = (String) projectComboBox.getSelectedItem();

            if (selectedStudent == null || selectedProject == null) {
                JOptionPane.showMessageDialog(
                        this, "Por favor, selecciona un estudiante y un proyecto.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int studentId = studentMap.get(selectedStudent.split(" ")[0]); //0
            int projectId = projectMap.get(selectedProject); //10
            System.out.println(studentId);
            System.out.println(selectedStudent.split(" ")[0]);
            System.out.println(projectId);
            Student student = studentDAO.readStudent(studentId);
            Project project = projectDAO.readProject(projectId);

            studentDAO.assignStudentToProject(student,project);

            JOptionPane.showMessageDialog(this, "Estudiante asignado al proyecto exitosamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al asignar estudiante al proyecto: " +
                    e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}