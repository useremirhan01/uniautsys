package com.studentmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemurPaneli extends JFrame implements ActionListener {
    private JButton addStudentButton;
    private JButton deleteStudentButton;
    private JButton defineCourseButton;
    private JTextArea studentListArea;
    private static final long serialVersionUID = 1L;

    public MemurPaneli() {
        setTitle("Memur Paneli");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        studentListArea = new JTextArea();
        studentListArea.setEditable(false);
        add(new JScrollPane(studentListArea), BorderLayout.CENTER);

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));

        addStudentButton = new JButton("Öğrenci Ekle");
        addStudentButton.addActionListener(this);
        buttonPanel.add(addStudentButton);

        deleteStudentButton = new JButton("Öğrenci Sil");
        deleteStudentButton.addActionListener(this);
        buttonPanel.add(deleteStudentButton);

        defineCourseButton = new JButton("Ders Tanımla");
        defineCourseButton.addActionListener(this);
        buttonPanel.add(defineCourseButton);

        add(buttonPanel, BorderLayout.SOUTH);
        refreshStudentList();  
        setVisible(true);
    }

    
    private void refreshStudentList() {
        studentListArea.setText("Öğrenci Listesi:\n");
        for (Student student : DataStore.students) {
            studentListArea.append(student.getId() + " - " + student.getName() + "\n");
        }
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addStudentButton) {
            addStudent();
        } else if (e.getSource() == deleteStudentButton) {
            deleteStudent();
        } else if (e.getSource() == defineCourseButton) {
            defineCourse();
        }
    }

    
    private void addStudent() {
        String studentName = JOptionPane.showInputDialog(this, "Öğrencinin adını girin:");
        if (studentName != null && !studentName.trim().isEmpty()) {
            String studentId = DataStore.generateStudentId();
            DataStore.students.add(new Student(studentName, studentName + "@example.com", studentId, "Bilinmiyor", 1));
            DataStore.saveData();
            refreshStudentList();
            JOptionPane.showMessageDialog(this, "Öğrenci Eklendi: " + studentName + " (" + studentId + ")");
        }
    }

   
    private void deleteStudent() {
        String studentId = JOptionPane.showInputDialog(this, "Silinecek öğrencinin numarasını girin:");
        if (studentId != null && !studentId.trim().isEmpty()) {
            boolean removed = DataStore.students.removeIf(student -> student.getId().equals(studentId));
            if (removed) {
                DataStore.saveData();
                refreshStudentList();
                JOptionPane.showMessageDialog(this, "Öğrenci silindi: " + studentId);
            } else {
                JOptionPane.showMessageDialog(this, "Öğrenci bulunamadı!");
            }
        }
    }

    
    private void defineCourse() {
        String courseName = JOptionPane.showInputDialog(this, "Ders Adı Girin:");
        String courseId = JOptionPane.showInputDialog(this, "Ders Kodu Girin:");

        if (courseName != null && courseId != null && !courseName.trim().isEmpty() && !courseId.trim().isEmpty()) {
            DataStore.courses.add(new Course(courseId, courseName));
            DataStore.saveData();
            JOptionPane.showMessageDialog(this, "Ders Eklendi: " + courseName + " (" + courseId + ")");
        } else {
            JOptionPane.showMessageDialog(this, "Ders eklenemedi! Eksik bilgi girdiniz.");
        }
    }
}
