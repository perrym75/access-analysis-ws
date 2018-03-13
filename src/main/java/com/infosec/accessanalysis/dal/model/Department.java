package com.infosec.accessanalysis.dal.model;

import java.util.LinkedList;
import java.util.List;

public class Department {
    private long id;
    private long parentId;
    private String name;
    private List<Personage> employees = new LinkedList<>();

    public Department(long id, long parent_id, String name) {
        this.id = id;
        this.parentId = parent_id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentID() {
        return parentId;
    }

    public void setParentID(long parent_id) {
        this.parentId = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Personage> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Personage> employees) {
        this.employees = employees;
    }
}
