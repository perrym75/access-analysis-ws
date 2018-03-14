package com.infosec.accessanalysis.dal.repository;

import com.infosec.accessanalysis.dal.model.Resource;

import java.sql.SQLException;
import java.util.List;

public interface ResourceRepository extends HierarchicalRepository<Resource> {
    List<Resource> findByAgent(long id) throws SQLException;
}
