package com.studentmanagement;

import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String name;
    protected String email;
    protected String id;

    public User(String name, String email, String id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }

    
    public abstract void displayInfo();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }
}
