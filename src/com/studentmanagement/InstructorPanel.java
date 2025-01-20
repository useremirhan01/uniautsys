package com.studentmanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstructorPanel extends JFrame implements ActionListener {
    private JComboBox<String> studentComboBox;
    private JTextField courseField, gradeField;
    private JButton enterGradeButton, updateGradeButton;
    private JTable gradeTable;
    private DefaultTableModel tableModel;
    private JTabbedPane tabbedPane;

    public InstructorPanel() {
        setTitle("Öğretmen Paneli");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        
        tabbedPane = new JTabbedPane();
        tabbedPane.add("Not Girişi", createGradeEntryPanel());
        tabbedPane.add("Not Listesi", createGradeListPanel());

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    
    private JPanel createGradeEntryPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        panel.add(new JLabel("Öğrenci Seç:"));
        studentComboBox = new JComboBox<>();
        loadStudentList();
        panel.add(studentComboBox);

        panel.add(new JLabel("Ders Kodu:"));
        courseField = new JTextField();
        panel.add(courseField);

        panel.add(new JLabel("Not:"));
        gradeField = new JTextField();
        panel.add(gradeField);

        enterGradeButton = new JButton("Not Gir");
        styleButton(enterGradeButton);  
        enterGradeButton.addActionListener(this);
        panel.add(enterGradeButton);

        return panel;
    }

  
    private JPanel createGradeListPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"Öğrenci Numarası", "Ad Soyad", "Ders Kodu", "Not"};
        tableModel = new DefaultTableModel(columnNames, 0);
        gradeTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(gradeTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        updateGradeButton = new JButton("Notu Güncelle");
        styleButton(updateGradeButton);  
        updateGradeButton.addActionListener(this);
        panel.add(updateGradeButton, BorderLayout.SOUTH);

        loadGradeList();
        return panel;
    }

   
    private void loadStudentList() {
        studentComboBox.removeAllItems();
        for (Student student : DataStore.students) {
            studentComboBox.addItem(student.getName() + " - " + student.getId());
        }
    }

    
    private void loadGradeList() {
        tableModel.setRowCount(0);

        for (String key : DataStore.grades.keySet()) {
            String[] parts = key.split("_");
            String studentId = parts[0];
            String courseId = parts[1];
            int grade = DataStore.grades.get(key);

            Student student = findStudentById(studentId);
            if (student != null) {
                tableModel.addRow(new Object[]{student.getId(), student.getName(), courseId, grade});
            }
        }
    }

    
    private Student findStudentById(String studentId) {
        for (Student student : DataStore.students) {
            if (student.getId().equals(studentId)) {
                return student;
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
        if (e.getSource() == enterGradeButton) {
            enterGrade();
        } else if (e.getSource() == updateGradeButton) {
            updateGrade();
        }
    }

    
    private void enterGrade() {
        String selectedStudent = (String) studentComboBox.getSelectedItem();
        String courseId = courseField.getText();
        String gradeStr = gradeField.getText();

        if (selectedStudent != null && !courseId.isEmpty() && !gradeStr.isEmpty()) {
            String studentId = selectedStudent.split(" - ")[1];
            try {
                int grade = Integer.parseInt(gradeStr);
                String key = studentId + "_" + courseId;
                DataStore.grades.put(key, grade);
                DataStore.saveData();
                loadGradeList();
                JOptionPane.showMessageDialog(this, "Not Girildi!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Geçerli bir not girin!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Tüm alanları doldurun!");
        }
    }

    
    private void updateGrade() {
        int selectedRow = gradeTable.getSelectedRow();
        if (selectedRow != -1) {
            String studentId = (String) tableModel.getValueAt(selectedRow, 0);
            String courseId = (String) tableModel.getValueAt(selectedRow, 2);
            int newGrade = Integer.parseInt(JOptionPane.showInputDialog("Yeni Notu Girin:"));

            String key = studentId + "_" + courseId;
            DataStore.grades.put(key, newGrade);
            DataStore.saveData();
            loadGradeList();
            JOptionPane.showMessageDialog(this, "Not Güncellendi!");
        } else {
            JOptionPane.showMessageDialog(this, "Güncellenecek notu seçin!");
        }
    }
}
