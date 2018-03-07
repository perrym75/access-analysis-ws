package com.infosec.accanalysis.dbo.model;

public class Department {
    private int department_id;
    private int parent_id;
    private String name;

    public Department(int department_id, int parent_id, String name) {
        this.department_id = department_id;
        this.parent_id = parent_id;
        this.name = name;
    }

    public int getDepartmentID() {
        return department_id;
    }

    public void setDepartmentID(int department_id) {
        this.department_id = department_id;
    }

    public int getParentID() {
        return parent_id;
    }

    public void setParentID(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
