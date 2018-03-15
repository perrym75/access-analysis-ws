package com.infosec.accessanalysis.api.rest.controller;

import com.infosec.accessanalysis.dal.model.Agent;
import com.infosec.accessanalysis.dal.model.Resource;
import com.infosec.accessanalysis.dal.repository.AgentRepository;
import com.infosec.accessanalysis.dal.repository.RepositoryFactory;
import com.infosec.accessanalysis.dal.repository.ResourceRepository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/1.0/agent")
public class AgentController {

    private AgentRepository agentRepository = (AgentRepository) RepositoryFactory.getRepository("mssql:agent");
    private ResourceRepository resourceRepository = (ResourceRepository) RepositoryFactory.getRepository("mssql:resource");

    @GetMapping
    public List<Agent> getAll(@RequestParam(value="page", defaultValue = "0") long page,
                              @RequestParam(value="count", defaultValue = "1000000000000000") long count) throws SQLException, IOException {
        return agentRepository.findRangeOfAll(page * count, count);
    }

    @GetMapping("/{id}")
    public Agent getOne(@PathVariable(value="id") long id) throws SQLException, IOException {
        return agentRepository.findOne(id);
    }

    @GetMapping("/children")
    public List<Agent> getRoot() throws SQLException, IOException {
        return agentRepository.findRoot();
    }

    @GetMapping("/{id}/children")
    public List<Agent> getChildren(@PathVariable(value="id") long id) throws SQLException, IOException {
        return agentRepository.findChildren(id);
    }

    @GetMapping("/{id}/resource")
    public List<Resource> getAgentResources(@PathVariable(value="id") long id) throws SQLException, IOException {
        return resourceRepository.findByAgent(id);
    }
}
