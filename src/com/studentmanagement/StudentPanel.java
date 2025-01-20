package com.studentmanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentPanel extends JFrame implements ActionListener {
    private JTable gradeTable;
    private JButton selectCourseButton, refreshButton;
    private JLabel welcomeLabel;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private JTabbedPane tabbedPane;

    public StudentPanel() {
        setTitle("Öğrenci Paneli");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        
        welcomeLabel = new JLabel("Hoş Geldiniz, " + getLoggedInStudent().getName());
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(welcomeLabel, BorderLayout.NORTH);

       
        tabbedPane = new JTabbedPane();

        tabbedPane.add("Notlarım", createGradePanel());

       
        tabbedPane.add("Profilim", createProfilePanel());

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    
    private JPanel createGradePanel() {
        JPanel panel = new JPanel(new BorderLayout());

      
        String[] columnNames = {"Ders Kodu", "Ders Adı", "Not"};
        tableModel = new DefaultTableModel(columnNames, 0);
        gradeTable = new JTable(tableModel);
        scrollPane = new JScrollPane(gradeTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        
        JPanel buttonPanel = new JPanel();
        selectCourseButton = new JButton("Ders Seç");
        refreshButton = new JButton("Tabloyu Yenile");

        styleButton(selectCourseButton);
        styleButton(refreshButton);

        selectCourseButton.addActionListener(this);
        refreshButton.addActionListener(this);

        buttonPanel.add(selectCourseButton);
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        loadStudentGrades();  
        return panel;
    }

   
    private JPanel createProfilePanel() {
        Student student = getLoggedInStudent();

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        panel.add(new JLabel("Ad Soyad:"));
        panel.add(new JLabel(student.getName()));

        panel.add(new JLabel("E-posta:"));
        panel.add(new JLabel(student.getEmail()));

        panel.add(new JLabel("Öğrenci Numarası:"));
        panel.add(new JLabel(student.getId()));

        panel.add(new JLabel("Bölüm:"));
        panel.add(new JLabel(student.getMajor()));

        panel.add(new JLabel("Sınıf:"));
        panel.add(new JLabel(String.valueOf(student.getYear())));

        return panel;
    }

    
    private Student getLoggedInStudent() {
        String email = LoginScreen.loggedInUser;
        for (Student student : DataStore.students) {
            if (student.getEmail().equals(email)) {
                return student;
            }
        }
        return null;
    }

   
    private void loadStudentGrades() {
        Student student = getLoggedInStudent();
        if (student == null) return;

        tableModel.setRowCount(0);  

        for (String key : DataStore.grades.keySet()) {
            if (key.startsWith(student.getId())) {
                int grade = DataStore.grades.get(key);
                String courseId = key.split("_")[1];

                Course course = findCourseById(courseId);
                tableModel.addRow(new Object[]{course.getCourseId(), course.getCourseName(), grade});
            }
        }
    }

    
    private void selectCourse() {
        ArrayList<String> courseList = new ArrayList<>();
        for (Course course : DataStore.courses) {
            courseList.add(course.getCourseName() + " - " + course.getCourseId());
        }

        String selectedCourse = (String) JOptionPane.showInputDialog(
                this,
                "Ders Seçin",
                "Ders Kaydı",
                JOptionPane.QUESTION_MESSAGE,
                null,
                courseList.toArray(),
                courseList.get(0)
        );

        if (selectedCourse != null) {
            String courseId = selectedCourse.split(" - ")[1];
            Student student = getLoggedInStudent();
            String key = student.getId() + "_" + courseId;

            if (!DataStore.grades.containsKey(key)) {
                DataStore.grades.put(key, 0);  // 
                DataStore.saveData();
                JOptionPane.showMessageDialog(this, "Ders Kaydedildi!");
            } else {
                JOptionPane.showMessageDialog(this, "Bu dersi zaten seçtiniz!");
            }
        }
    }

    
    private Course findCourseById(String courseId) {
        for (Course course : DataStore.courses) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }

    
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(41, 128, 185));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectCourseButton) {
            selectCourse();
            loadStudentGrades();  
        } else if (e.getSource() == refreshButton) {
            loadStudentGrades();  
        }
    }
}
