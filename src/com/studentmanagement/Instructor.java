package com.studentmanagement;


public class Instructor extends User {
    private String department;  
    private String title;       
    private static final long serialVersionUID = 1L;

    
    public Instructor(String name, String email, String department) {
        super(name, email, generateInstructorId());
        this.department = department;
        this.title = "Öğretim Görevlisi";  
    }

    
    public Instructor(String name, String email, String id, String department, String title) {
        super(name, email, id);
        this.department = department;
        this.title = title;
    }

   
    @Override
    public void displayInfo() {
        System.out.println("Adı: " + name);
        System.out.println("E-posta: " + email);
        System.out.println("ID: " + id);
        System.out.println("Bölüm: " + department);
        System.out.println("Unvan: " + title);
    }

    
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    
    private static String generateInstructorId() {
        return "INST" + (int) (Math.random() * 10000);
    }
}
