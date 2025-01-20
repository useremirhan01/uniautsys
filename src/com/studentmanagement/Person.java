package com.studentmanagement;

import java.io.Serializable;

public abstract class Person implements Serializable {
    protected String name;
    protected String email;
    private static final long serialVersionUID = 1L;

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public abstract void displayInfo();  
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
