package com.sppp.gui;

import com.sppp.dao.StudentDAO;
import com.sppp.dao.StudentDAOImp;
import com.sppp.model.Student;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class StudentManager extends JFrame {
    private DefaultListModel<String> studentListModel;
    private JList<String> studentJList;
    private JTextField searchField;
    private ArrayList<String> students;

    public StudentManager() {
        setTitle("Lista de Estudiantes");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        students = new ArrayList<>();
        studentListModel = new DefaultListModel<>();
        studentJList = new JList<>(studentListModel);
        searchField = new JTextField();

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Buscar estudiante");
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
        add(new JScrollPane(studentJList), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        searchField.addActionListener(e -> filterStudentList());

        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudentList());
        deleteButton.addActionListener(e -> deleteStudent());

        loadInitialStudents();
    }

    private void loadInitialStudents() {
        StudentDAO studentDAO = new StudentDAOImp();
        try {
            ArrayList<Student> studentData = (ArrayList<Student>) studentDAO.getAllStudents();
            students = (ArrayList<String>) studentData.stream()
                    .map(Student::getName)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        updateStudentListDisplay();
    }

    private void updateStudentListDisplay() {
        studentListModel.clear();
        for (String student : students) {
            studentListModel.addElement(student);
        }
    }

    private void filterStudentList() {
        String searchText = searchField.getText().toLowerCase();
        studentListModel.clear();
        for (String student : students) {
            if (student.toLowerCase().contains(searchText)) {
                studentListModel.addElement(student);
            }
        }
    }

    private void addStudent() {
        SwingUtilities.invokeLater(() -> {
            StudentForm studentForm = new StudentForm();
            studentForm.setVisible(true);
        });
        updateStudentListDisplay();
    }

    private void updateStudentList() {
        String selectedStudent = studentJList.getSelectedValue();
        if (selectedStudent != null) {
            StudentDAO studentDAO = new StudentDAOImp();
            try {
                Student student = studentDAO.getStudentByName(selectedStudent);
                if (student != null) {
                    SwingUtilities.invokeLater(() -> {
                        new UpdateStudentForm(student);
                    });
                    updateStudentListDisplay();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al obtener los datos del estudiante.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un estudiante para actualizar.");
        }
    }


    private void deleteStudent() {
        String selectedStudent = studentJList.getSelectedValue();
        if (selectedStudent != null) {
            students.remove(selectedStudent);
            StudentDAO studentDAO = new StudentDAOImp();
            Student student = new Student();
            student.setName(selectedStudent);
            try {
                studentDAO.deleteStudent(student);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            updateStudentListDisplay();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un estudiante para eliminar.");
        }
    }
}
