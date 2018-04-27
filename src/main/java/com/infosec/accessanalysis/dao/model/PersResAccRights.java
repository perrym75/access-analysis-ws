package com.infosec.accessanalysis.dao.model;

public class PersResAccRights {
    private long persId;
    private long resId;
    private String accRights;

    public PersResAccRights(long persId, long resId, String accRights) {
        this.persId = persId;
        this.resId = resId;
        this.accRights = accRights;
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
