package com.infosec.accessanalysis.dao.repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface HierarchicalRepository<Entity> extends Repository<Entity> {
    List<Entity> findRoot() throws SQLException, IOException;
    List<Entity> findChildren(long id) throws SQLException, IOException;
}
