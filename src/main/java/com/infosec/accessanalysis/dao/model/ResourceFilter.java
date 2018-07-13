package com.infosec.accessanalysis.dao.model;

import java.util.Set;

public class ResourceFilter {
    private boolean fullCoverage = true;
    private boolean partialCoverage = true;
    private boolean individualAccessOnly = true;
    private boolean roleAccessOnly = true;
    private boolean noAccess = true;
    private String name = "";

    public ResourceFilter() {
    }

    public ResourceFilter(Set<ResourceStatus> statuses, String name) {
        setStatuses(statuses);
        this.name = name;
    }

    public ResourceFilter(String name) {
        this.name = name;
    }

    public void setStatuses(Set<ResourceStatus> statuses) {
        fullCoverage = statuses.contains(ResourceStatus.FULL_COVERAGE);
        partialCoverage = statuses.contains(ResourceStatus.PARTIAL_COVERAGE);
        individualAccessOnly = statuses.contains(ResourceStatus.INDIVIDUAL_ACCESS_ONLY);
        roleAccessOnly = statuses.contains(ResourceStatus.ROLE_ACCESS_ONLY);
        noAccess = statuses.contains(ResourceStatus.NO_ACCESS);
    }

    public boolean isFullCoverage() {
        return fullCoverage;
    }

    public boolean isPartialCoverage() {
        return partialCoverage;
    }

    public boolean isIndividualAccessOnly() {
        return individualAccessOnly;
    }

    public boolean isRoleAccessOnly() {
        return roleAccessOnly;
    }

    public boolean isNoAccess() {
        return noAccess;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
