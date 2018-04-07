package com.infosec.accessanalysis.api.rest.controller;

import com.infosec.accessanalysis.dao.model.Personage;
import com.infosec.accessanalysis.dao.model.Resource;
import com.infosec.accessanalysis.dao.repository.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/1.0/resource")
public class ResourceController {

    private final ResourceRepository repository = new ResourceRepository();
    private final PersonageRepository personageRepository = new PersonageRepository();

    @GetMapping
    public List<Resource> getAll(@RequestParam(value="page", defaultValue = "0") long page,
                                 @RequestParam(value="count", defaultValue = "1000000000000000") long count)
            throws SQLException, IOException {
        return repository.findRangeOfAll(page * count, count);
    }

    @GetMapping("/{id}")
    public Resource getOne(@PathVariable(value="id") long id) throws SQLException, IOException {
        return repository.findOne(id);
    }

    @GetMapping("/children")
    public List<Resource> getRoot() throws SQLException, IOException {
        return repository.findRoot();
    }

    @GetMapping("/{id}/children")
    public List<Resource> getChildren(@PathVariable(value="id") long id) throws SQLException, IOException {
        return repository.findChildren(id);
    }

    @GetMapping("/{id}/personage")
    public List<Personage> getResourcePersonages(@PathVariable(value="id") long id) throws SQLException, IOException {
        return personageRepository.findByResource(id);
    }
}
