package com.infosec.accessanalysis.business;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ResourceAccess {
    private long resourceId;
    private Set<Long> accessRights = new HashSet<>();

    public ResourceAccess(long resourceId) {
        this.resourceId = resourceId;
    }

    public ResourceAccess(long resourceId, @NotNull Set<Long> accessRights) {
        this.resourceId = resourceId;
        this.accessRights.addAll(accessRights);
    }

    public void addAccessRight(Long accessRightId) {
        accessRights.add(accessRightId);
    }

    public void addAccessRights(Collection<Long> accessRights) {
        this.accessRights.addAll(accessRights);
    }

    public void addAccessRights(String accessRights) {
        String[] rights = accessRights.split(",");
        for (String right : rights) {
            this.accessRights.add(Long.parseLong(right));
        }
    }

    public long getResourceId() {
        return resourceId;
    }

    public Set<Long> getAccessRights() {
        return accessRights;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        ResourceAccess other = (ResourceAccess) obj;
        return resourceId == other.resourceId && accessRights.equals(other.accessRights);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = hash * 31 + (int)resourceId;
        hash = hash * 31 + accessRights.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("/");
        sb.append(resourceId);
        sb.append(":");
        for (Long item : accessRights) {
            sb.append(item);
            sb.append(",");
        }

        return sb.toString();
    }
}
