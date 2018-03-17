package com.infosec.accessanalysis.api.rest.controller;

import com.infosec.accessanalysis.dal.model.Personage;
import com.infosec.accessanalysis.dal.model.Resource;
import com.infosec.accessanalysis.dal.repository.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/1.0/agent")
public class PersonageController {

    private final PersonageRepository personageRepository = new PersonageRepository();
    private final ResourceRepository resourceRepository = new ResourceRepository();

    @GetMapping
    public List<Personage> getAll(@RequestParam(value="page", defaultValue = "0") long page,
                                  @RequestParam(value="count", defaultValue = "1000000000000000") long count) throws SQLException, IOException {
        return personageRepository.findRangeOfAll(page * count, count);
    }

    @GetMapping("/{id}")
    public Personage getOne(@PathVariable(value="id") long id) throws SQLException, IOException {
        return personageRepository.findOne(id);
    }

    @GetMapping("/{id}/resource")
    public List<Resource> getAgentResources(@PathVariable(value="id") long id) throws SQLException, IOException {
        return resourceRepository.findByPersonage(id);
    }
}
