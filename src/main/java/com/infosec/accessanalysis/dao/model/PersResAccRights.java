package com.infosec.accessanalysis.dao.model;

public class PersResAccRights {
    private long personageId;
    private long resourceId;
    private long departmentId;
    private String accessRights;

    public PersResAccRights(long personageId, long resourceId, long departmentId, String accessRights) {
        this.personageId = personageId;
        this.resourceId = resourceId;
        this.departmentId = departmentId;
        this.accessRights = accessRights;
    }

    public long getPersonageId() {
        return personageId;
    }

    public void setPersonageId(long personageId) {
        this.personageId = personageId;
    }

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public String getAccessRights() {
        return accessRights;
    }

    public void setAccessRights(String accessRights) {
        this.accessRights = accessRights;
    }

    /*@Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof PersResAccRights)) return false;

        PersResAccRights that = (PersResAccRights)obj;
        return this.id == that.id;
    }

    @Override
    public int hashCode() {
        return (int)id;
    }*/
}
