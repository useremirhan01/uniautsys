package com.studentmanagement;

import java.io.Serializable;

public class Student extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String major;
    private int year;

    public Student(String name, String email, String id, String major, int year) {
        super(name, email, id);
        this.major = major;
        this.year = year;
    }

    
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public void displayInfo() {
        System.out.println("Öğrenci Adı: " + getName());
        System.out.println("E-posta: " + getEmail());
        System.out.println("Öğrenci No: " + getId());
        System.out.println("Bölüm: " + major);
        System.out.println("Sınıf: " + year);
    }


}
