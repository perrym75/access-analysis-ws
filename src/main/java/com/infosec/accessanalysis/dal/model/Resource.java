package com.infosec.accessanalysis.dal.model;

public class Resource {
    private long id;
    private long parentId;
    private String name;
    private String systemId;
    private String typeName;
    private long agentId;

    public Resource(long id, long parentId, String name, String systemId, String typeName, long agentId) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.systemId = systemId;
        this.typeName = typeName;
        this.agentId = agentId;
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

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public long getAgentId() {
        return agentId;
    }

    public void setAgentId(long agentId) {
        this.agentId = agentId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
