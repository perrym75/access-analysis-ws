package com.infosec.accessanalysis.dal.repository;

import com.infosec.accessanalysis.dal.model.Agent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface AgentRepository extends HierarchicalRepository<Agent> {
    List<Agent> findByPlatform(long id) throws SQLException, IOException;
}
