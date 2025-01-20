package com.studentmanagement;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DataStore implements Serializable {
    private static final long serialVersionUID = 1L;

    public static HashMap<String, String> users = new HashMap<>();        
    public static HashMap<String, String> userRoles = new HashMap<>();    
    public static ArrayList<Student> students = new ArrayList<>();        
    public static ArrayList<Instructor> instructors = new ArrayList<>();  
    public static ArrayList<Course> courses = new ArrayList<>();          
    public static HashMap<String, Integer> grades = new HashMap<>();      
    private static final String FILE_NAME = "datastore.ser";              
    private static int studentCounter = 2024001;                          

    
    static {
        loadData();  
        if (users.isEmpty()) {
            
            users.put("emirhan@example.com", "12345");  
            userRoles.put("emirhan@example.com", "student");

            users.put("pelin@example.com", "54321");    
            userRoles.put("pelin@example.com", "instructor");

            users.put("admin@example.com", "admin123"); 
            userRoles.put("admin@example.com", "admin");

            
            students.add(new Student("Emirhan Efe", "emirhan@example.com", generateStudentId(), "Bilgisayar Mühendisliği", 3));
            students.add(new Student("Ayşe Yılmaz", "ayse@example.com", generateStudentId(), "Elektrik Mühendisliği", 2));
            students.add(new Student("Ahmet Can", "ahmet@example.com", generateStudentId(), "Makine Mühendisliği", 1));

            
            instructors.add(new Instructor("Pelin Görgel", "pelin@example.com", "Bilgisayar Mühendisliği"));
            instructors.add(new Instructor("Murat Demir", "murat@example.com", "Elektrik Mühendisliği"));

            
            courses.add(new Course("CS101", "Programlama Temelleri"));
            courses.add(new Course("MATH102", "Lineer Cebir"));

            saveData();  
        }
    }

    
    public static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
            oos.writeObject(userRoles);
            oos.writeObject(students);
            oos.writeObject(instructors);  
            oos.writeObject(courses);
            oos.writeObject(grades);
            System.out.println("Veriler dosyaya kaydedildi.");
        } catch (IOException e) {
            System.out.println("Veri kaydetme hatası: " + e.getMessage());
        }
    }

    
    public static void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            users = (HashMap<String, String>) ois.readObject();
            userRoles = (HashMap<String, String>) ois.readObject();
            students = (ArrayList<Student>) ois.readObject();
            instructors = (ArrayList<Instructor>) ois.readObject();  
            courses = (ArrayList<Course>) ois.readObject();
            grades = (HashMap<String, Integer>) ois.readObject();
            System.out.println("Veriler yüklendi.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Veri yükleme hatası: " + e.getMessage());
        }
    }

    
    public static String generateStudentId() {
        return String.valueOf(studentCounter++); //StudenId +1
    }

    
    public static void addUser(String name, String email, String password, String role, String major, int year) {
        users.put(email, password);
        userRoles.put(email, role);

        if (role.equals("student")) {
            String studentId = generateStudentId();  
            students.add(new Student(name, email, studentId, major, year));
            System.out.println("Öğrenci Eklendi -> Adı: " + name + ", ID: " + studentId);
        } else if (role.equals("instructor")) {
            instructors.add(new Instructor(name, email, major));  
            System.out.println("Öğretmen Eklendi -> Adı: " + name + ", Bölüm: " + major);
        } else {
            System.out.println("Kullanıcı Eklendi -> Adı: " + name + ", Rol: " + role);
        }
        saveData();
    }

    
    public static void removeUser(String email) {
        users.remove(email);
        userRoles.remove(email);

        students.removeIf(student -> student.getEmail().equals(email));
        instructors.removeIf(instructor -> instructor.getEmail().equals(email));  
        saveData();
    }
}
