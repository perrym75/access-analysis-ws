package com.infosec.accessanalysis.dao.model;

public class AccessRight {
    private long id;
    private String name;

    public AccessRight(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof AccessRight)) return false;

        AccessRight that = (AccessRight)obj;
        return this.id == that.id;
    }

    @Override
    public int hashCode() {
        return (int)id;
    }
}
