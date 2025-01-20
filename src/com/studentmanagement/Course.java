package com.studentmanagement;

import java.io.Serializable;

public class Course implements Serializable {
    private String courseId;
    private String courseName;
    private static final long serialVersionUID = 1L;



    public Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    
    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    
    public void displayInfo() {
        System.out.println("Ders Kodu: " + courseId);
        System.out.println("Ders Adı: " + courseName);
    }
}
