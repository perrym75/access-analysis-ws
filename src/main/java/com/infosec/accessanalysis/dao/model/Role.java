package com.infosec.accessanalysis.dao.model;

public class Role {
    private long id;
    private long modelId;
    private String name;

    public Role(long id, long modelId, String name) {
        this.id = id;
        this.modelId = modelId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
