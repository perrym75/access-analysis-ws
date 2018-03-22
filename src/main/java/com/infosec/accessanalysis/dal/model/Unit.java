package com.infosec.accessanalysis.dal.model;

import java.util.LinkedList;
import java.util.List;

public class Unit {
    private long id;
    private long parentId;
    private String displayName;
    private List<UserAccount> userAccounts = new LinkedList<>();

    public Unit(long id, long parent_id, String displayName) {
        this.id = id;
        this.parentId = parent_id;
        this.displayName = displayName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentID() {
        return parentId;
    }

    public void setParentID(long parent_id) {
        this.parentId = parent_id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<UserAccount> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(List<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }
}
