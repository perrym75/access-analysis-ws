package com.infosec.accanalysis.dbo.repository;

import java.util.Map;
import java.util.HashMap;

public class RepositoryFactory {
    private static Map<String, Class<? extends DepartmentRepository>> departmentRepositories = new HashMap<>();

    public static void registerDepartmentRepository(String repositoryName, Class<? extends DepartmentRepository> repository) {
        departmentRepositories.put(repositoryName, repository);
    }

    public static DepartmentRepository getDepartmentRepository(String repositoryName) {
        try {
            Class<? extends DepartmentRepository> drClass = departmentRepositories.get(repositoryName);
            return drClass.getConstructor().newInstance();
        } catch(Exception ignored) {
        }
        return null;
    }
}
