package com.infosec.accessanalysis.dao.model;

public class RoleModel {
    private long id;
    private int active;
    private String name;

    public RoleModel(long id, int active, String name) {
        this.id = id;
        this.active = active;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
