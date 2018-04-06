package com.infosec.accessanalysis.dao.model;

public class Platform {
    private long id;
    private long parentId;
    private String name;
    private String displayName;
    private String description;

    public Platform(long id, long parentId, String name, String displayName, String description) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
