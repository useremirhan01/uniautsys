package com.studentmanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPanel extends JFrame implements ActionListener {
    private JButton addStudentButton, addInstructorButton, addCourseButton, deleteUserButton, logoutButton;
    private JTable userTable, courseTable;
    private DefaultTableModel userTableModel, courseTableModel;

    public AdminPanel() {
        setTitle("Admin Paneli");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        addStudentButton = new JButton("Öğrenci Ekle");
        addInstructorButton = new JButton("Öğretmen Ekle");
        addCourseButton = new JButton("Ders Ekle");
        deleteUserButton = new JButton("Kullanıcı Sil");
        logoutButton = new JButton("Çıkış Yap");

        
        styleButton(addStudentButton);
        styleButton(addInstructorButton);
        styleButton(addCourseButton);
        styleButton(deleteUserButton);
        styleButton(logoutButton);


        addStudentButton.addActionListener(this);
        addInstructorButton.addActionListener(this);
        addCourseButton.addActionListener(this);
        deleteUserButton.addActionListener(this);
        logoutButton.addActionListener(this);


        topPanel.add(addStudentButton);
        topPanel.add(addInstructorButton);
        topPanel.add(addCourseButton);
        topPanel.add(deleteUserButton);
        topPanel.add(logoutButton);

        add(topPanel, BorderLayout.NORTH);


        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        String[] userColumnNames = {"Ad", "E-posta", "Rol"};
        userTableModel = new DefaultTableModel(userColumnNames, 0);
        userTable = new JTable(userTableModel);

        String[] courseColumnNames = {"Ders Kodu", "Ders Adı"};
        courseTableModel = new DefaultTableModel(courseColumnNames, 0);
        courseTable = new JTable(courseTableModel);

        centerPanel.add(new JScrollPane(userTable));
        centerPanel.add(new JScrollPane(courseTable));
        add(centerPanel, BorderLayout.CENTER);


        loadUserData();
        loadCourseData();

        setVisible(true);
    }


    private void loadUserData() {
        userTableModel.setRowCount(0);
        for (Student student : DataStore.students) {
            userTableModel.addRow(new Object[]{student.getName(), student.getEmail(), "Öğrenci"});
        }
        for (Instructor instructor : DataStore.instructors) {
            userTableModel.addRow(new Object[]{instructor.getName(), instructor.getEmail(), "Öğretmen"});
        }
    }

    private void loadCourseData() {
        courseTableModel.setRowCount(0);
        for (Course course : DataStore.courses) {
            courseTableModel.addRow(new Object[]{course.getCourseId(), course.getCourseName()});
        }
    }


    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addStudentButton) {
            addUser("student");
        } else if (e.getSource() == addInstructorButton) {
            addUser("instructor");
        } else if (e.getSource() == addCourseButton) {
            addCourse();
        } else if (e.getSource() == deleteUserButton) {
            deleteUser();
        } else if (e.getSource() == logoutButton) {
            dispose();
            new LoginScreen();
        }
    }


    private void addUser(String role) {
        String name = JOptionPane.showInputDialog("Ad Soyad:");
        String email = JOptionPane.showInputDialog("E-posta:");
        String password = JOptionPane.showInputDialog("Şifre:");
        String department = JOptionPane.showInputDialog("Bölüm:");

        if (role.equals("student")) {
            int year = Integer.parseInt(JOptionPane.showInputDialog("Sınıf (1-4):"));
            DataStore.addUser(name, email, password, "student", department, year);
        } else if (role.equals("instructor")) {
            DataStore.addUser(name, email, password, "instructor", department, 0);
        }
        loadUserData();
        JOptionPane.showMessageDialog(this, "Kullanıcı Eklendi!");
    }

    private void addCourse() {
        String courseName = JOptionPane.showInputDialog("Ders Adı:");
        String courseId = JOptionPane.showInputDialog("Ders Kodu:");
        DataStore.courses.add(new Course(courseId, courseName));
        DataStore.saveData();
        loadCourseData();
        JOptionPane.showMessageDialog(this, "Ders Eklendi!");
    }


    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            String email = (String) userTableModel.getValueAt(selectedRow, 1);
            DataStore.removeUser(email);
            loadUserData();
            JOptionPane.showMessageDialog(this, "Kullanıcı Silindi!");
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen bir kullanıcı seçin.");
        }
    }
}
