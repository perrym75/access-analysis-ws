package com.infosec.accessanalysis.api.rest.controller;

import com.infosec.accessanalysis.dal.model.Agent;
import com.infosec.accessanalysis.dal.model.Platform;
import com.infosec.accessanalysis.dal.repository.AgentRepository;
import com.infosec.accessanalysis.dal.repository.PlatformRepository;
import com.infosec.accessanalysis.dal.repository.RepositoryFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/1.0/platform")
public class PlatformController {

    private PlatformRepository platformRepository = (PlatformRepository) RepositoryFactory.getRepository("mssql:platform");
    private AgentRepository agentRepository = (AgentRepository) RepositoryFactory.getRepository("mssql:agent");

    @GetMapping
    public List<Platform> getAll(@RequestParam(value="page", defaultValue = "0") long page,
                                          @RequestParam(value="count", defaultValue = "1000000000000000") long count) throws SQLException, IOException {
        return platformRepository.findRangeOfAll(page * count, count);
    }

    @GetMapping("/{id}")
    public Platform getOne(@PathVariable(value="id") long id) throws SQLException, IOException {
        return platformRepository.findOne(id);
    }

    @GetMapping("/children")
    public List<Platform> getRoot() throws SQLException, IOException {
        return platformRepository.findRoot();
    }

    @GetMapping("/{id}/children")
    public List<Platform> getChildren(@PathVariable(value="id") long id) throws SQLException, IOException {
        return platformRepository.findChildren(id);
    }

    @GetMapping("/{id}/agent")
    public List<Agent> getPlatformAgents(@PathVariable(value="id") long id) throws SQLException, IOException {
        return agentRepository.findByPlatform(id);
    }
}
