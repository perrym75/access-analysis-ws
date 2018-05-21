package com.infosec.accessanalysis.dao.model;

public class Personage {
    private long id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String email;
    private String position;
    private long departmentId;
    private int status;

    public Personage(long id, String firstName, String secondName, String lastName, String email, String position,
                     long departmentId, int status) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.email = email;
        this.position = position;
        this.departmentId = departmentId;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
