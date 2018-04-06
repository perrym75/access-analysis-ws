package com.infosec.accessanalysis.dao.repository;

import java.util.Map;
import java.util.HashMap;

public class RepositoryFactory {
    private final static Map<String, Class<? extends Repository>> repositories = new HashMap<>();

    public static void registerRepository(String repositoryName, Class<? extends Repository> repository) {
        repositories.put(repositoryName, repository);
    }

    public static Repository getRepository(String repositoryName) {
        try {
            Class<? extends Repository> drClass = repositories.get(repositoryName);
            return drClass.getConstructor().newInstance();
        } catch(Exception ignored) {
        }
        return null;
    }
}
