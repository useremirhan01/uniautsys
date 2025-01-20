package com.studentmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentProfile extends JFrame implements ActionListener {
    private JTextField nameField, emailField;
    private JButton updateButton;
    private Student student;

    public StudentProfile(Student student) {
        this.student = student;
        setTitle("Profil Güncelleme");
        setSize(400, 300);
        setLayout(new GridLayout(3, 2, 10, 10));

        
        add(new JLabel("Ad:"));
        nameField = new JTextField(student.getName());
        add(nameField);

       
        add(new JLabel("E-posta:"));
        emailField = new JTextField(student.getEmail());
        add(emailField);

        
        updateButton = new JButton("Güncelle");
        updateButton.addActionListener(this);
        add(updateButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        student.setName(nameField.getText());
        student.setEmail(emailField.getText());
        DataStore.saveData();
        JOptionPane.showMessageDialog(this, "Profil güncellendi!");
        dispose();
    }
}
	