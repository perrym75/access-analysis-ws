package com.infosec.accessanalysis.business;

public class PersDep {
    private long persId;
    private long depId;

    PersDep(long persId, long depId) {
        this.persId = persId;
        this.depId = depId;
    }

    public long getPersId() {
        return persId;
    }

    public void setPersId(long persId) {
        this.persId = persId;
    }

    public long getDepId() {
        return depId;
    }

    public void setDepId(long depId) {
        this.depId = depId;
    }
}
