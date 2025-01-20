package com.studentmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame implements ActionListener {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;
    public static String loggedInUser;

    public LoginScreen() {
        setTitle("Öğrenci İşleri Otomasyon Sistemi");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        
        JPanel backgroundPanel = new JPanel() {
            Image background = new ImageIcon("src/images/school.jpg").getImage();  

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

                
                Color translucentWhite = new Color(255, 255, 255, 170);  
                g.setColor(translucentWhite);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  

        
        JLabel logoLabel = new JLabel();
        ImageIcon logo = new ImageIcon("src/images/logo.png");  
        logoLabel.setIcon(new ImageIcon(logo.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));

        gbc.gridx = 0;
        gbc.gridy = 0;
        backgroundPanel.add(logoLabel, gbc);

        
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(5, 1, 10, 10));
        loginPanel.setOpaque(false);  

        JLabel emailLabel = new JLabel("E-posta:");
        emailLabel.setForeground(Color.DARK_GRAY);  
        emailField = new JTextField();
        makeTransparent(emailField);

        JLabel passwordLabel = new JLabel("Şifre:");
        passwordLabel.setForeground(Color.DARK_GRAY);
        passwordField = new JPasswordField();
        makeTransparent(passwordField);

        loginButton = new JButton("Giriş Yap");
        styleButton(loginButton);
        loginButton.addActionListener(this);

        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setForeground(Color.RED);

        loginPanel.add(emailLabel);
        loginPanel.add(emailField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);

        gbc.gridy = 1;
        backgroundPanel.add(loginPanel, gbc);

       
        JLabel creatorsLabel = new JLabel(
                "<html><div style='text-align: center;'>Yapımcılar:<br>Emirhan Efe Demir - 1306230024<br>Samet Genç - 1306230064</div></html>",
                JLabel.CENTER);
        creatorsLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        creatorsLabel.setForeground(Color.DARK_GRAY);

        gbc.gridy = 2;
        backgroundPanel.add(creatorsLabel, gbc);

        add(backgroundPanel);
        setVisible(true);
    }

    
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
    }

    private void makeTransparent(JComponent component) {
        component.setOpaque(false);
        component.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
        component.setForeground(Color.DARK_GRAY);
        component.setFont(new Font("Arial", Font.PLAIN, 16));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (DataStore.users.containsKey(email) && DataStore.users.get(email).equals(password)) {
            String role = DataStore.userRoles.get(email);
            statusLabel.setText("Giriş Başarılı!");
            loggedInUser = email;

            dispose();
            switch (role) {
                case "admin":
                    new AdminPanel();
                    break;
                case "instructor":
                    new InstructorPanel();
                    break;
                case "student":
                    new StudentPanel();
                    break;
            }
        } else {
            statusLabel.setText("Hatalı E-posta veya Şifre!");
        }
    }

    public static void main(String[] args) {
        new LoginScreen();
    }
}
