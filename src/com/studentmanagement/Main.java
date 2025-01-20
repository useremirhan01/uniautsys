package com.studentmanagement;

public class Main {
    public static void main(String[] args) {
        // Öğrenci nesnesi oluştur
        Student student1 = new Student("Emirhan Efe", "emirhan@example.com", "2024001", "Bilgisayar Mühendisliği", 3);
        
        // Öğretim Görevlisi nesnesi oluştur
        Instructor instructor1 = new Instructor("Dr. Pelin Görgel", "pelin@example.com", "10001", "Mühendislik Fakültesi", "Doç. Dr.");

        // Nesnelerin bilgilerini ekrana yazdır
        System.out.println("Öğrenci Bilgileri:");
        student1.displayInfo();

        System.out.println("\nÖğretim Görevlisi Bilgileri:");
        instructor1.displayInfo();
    }
}
