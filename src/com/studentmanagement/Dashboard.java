package com.studentmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame implements ActionListener {
    private JButton studentButton;
    private JButton instructorButton;
    private JButton staffButton;
    private JButton logoutButton;
    private String userType;
    private static final long serialVersionUID = 1L;


    
    public Dashboard(String userType) {
        this.userType = userType;

        setTitle("Ana Ekran - " + userType.toUpperCase());  
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        
        studentButton = new JButton("Öğrenci İşlemleri");
        instructorButton = new JButton("Öğretim Görevlisi İşlemleri");
        staffButton = new JButton("Personel İşlemleri");
        logoutButton = new JButton("Çıkış Yap");

        
        studentButton.addActionListener(this);
        instructorButton.addActionListener(this);
        staffButton.addActionListener(this);
        logoutButton.addActionListener(this);

        
        if (userType.equals("student")) {
            add(studentButton);
        } else if (userType.equals("instructor")) {
            add(instructorButton);
        } else if (userType.equals("staff")) {
            add(staffButton);
        }

        add(logoutButton);  
        setVisible(true);
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == studentButton) {
            new StudentPanel();
        } else if (e.getSource() == instructorButton) {
            new InstructorPanel();
        } else if (e.getSource() == staffButton) {
            new MemurPaneli();  
        } else if (e.getSource() == logoutButton) {
            int response = JOptionPane.showConfirmDialog(this, "Çıkış yapmak istediğinize emin misiniz?", "Çıkış", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                dispose();
                new LoginScreen();
            }
        }
    }
}
