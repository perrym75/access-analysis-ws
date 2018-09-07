package com.infosec.accessanalysis.dao.model;

public class UserAccount {
    private long id;
    private String displayName;
    private long unitId;
    private long agentId;
    private int system;
    private int status;

    public UserAccount(long id, String displayName, long unitId,
                       long agentId, int system, int status) {
        this.id = id;
        this.displayName = displayName;
        this.unitId = unitId;
        this.agentId = agentId;
        this.system = system;
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

    public long getAgentId() {
        return agentId;
    }

    public void setAgentId(long agentId) {
        this.agentId = agentId;
    }

    public int getSystem() {
        return system;
    }

    public void setSystem(int system) {
        this.system = system;
    }
}
