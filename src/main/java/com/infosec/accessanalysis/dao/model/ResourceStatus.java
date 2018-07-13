package com.infosec.accessanalysis.dao.model;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Stream;

public enum ResourceStatus {
    FULL_COVERAGE(1),
    PARTIAL_COVERAGE(2),
    INDIVIDUAL_ACCESS_ONLY(3),
    NO_ACCESS(4),
    ROLE_ACCESS_ONLY(5);

    private final int status;
    public final static String STATUS_DELIMITER = ",";

    ResourceStatus(int status) {
        this.status = status;
    }

    public static ResourceStatus from(int status) {
        for (ResourceStatus value : values()) {
            if (value.status == status) {
                return value;
            }
        }

        throw new InvalidParameterException("Invalid value=" + status +
                " of resource status. Can only have following values: " +
                Stream.of(values()).map(x -> x.status).toString());
    }

    public static ResourceStatus from(String status) {
        try {
            return from(Integer.parseInt(status));
        }
        catch(Exception ignored) {
            throw new InvalidParameterException("Invalid value=" + status +
                    " of resource status. Can only have following values: " +
                    Stream.of(values()).map(x -> x.status).toString());
        }
    }

    public static Set<ResourceStatus> parse(String statuses) {
        String[] parsed = statuses.split(STATUS_DELIMITER);
        Set<ResourceStatus> result = new HashSet<>();

        for (String item : parsed) {
            String trimmedItem = item.trim();
            if (!trimmedItem.isEmpty()) {
                result.add(from(trimmedItem));
            }
        }

        return result;
    }
}
