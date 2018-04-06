package com.infosec.accessanalysis.dao.model;

public class Agent {
    private long id;
    private long parentId;
    private long platformId;
    private String displayName;
    private String description;

    public Agent(long id, long parentId, long platformId, String displayName, String description) {
        this.id = id;
        this.parentId = parentId;
        this.platformId = platformId;
        this.displayName = displayName;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(long platformId) {
        this.platformId = platformId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
