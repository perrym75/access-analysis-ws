package com.infosec.accessanalysis.dal.repository;

import java.sql.SQLException;
import java.util.List;

public interface HierarchicalRepository<Entity> extends Repository<Entity> {
    List<Entity> findRoot() throws SQLException;
    List<Entity> findChildren(long id) throws SQLException;
    Entity findParent(long id) throws SQLException;
}
