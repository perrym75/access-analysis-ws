package com.infosec.accessanalysis.dal.model;

public class UserAccount {
    private long id;
    private String displayName;
    private long unitId;
    private int status;

    public UserAccount(long id, String displayName, long unitId,
                       int status) {
        this.id = id;
        this.displayName = displayName;
        this.unitId = unitId;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
