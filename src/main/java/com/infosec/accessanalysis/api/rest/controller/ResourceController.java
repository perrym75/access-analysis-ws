package com.infosec.accessanalysis.api.rest.controller;

import com.infosec.accessanalysis.dal.model.Resource;
import com.infosec.accessanalysis.dal.repository.RepositoryFactory;
import com.infosec.accessanalysis.dal.repository.ResourceRepository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/1.0/resource")
public class ResourceController {

    private ResourceRepository repository = (ResourceRepository) RepositoryFactory.getRepository("mssql:resource");

    @GetMapping
    public List<Resource> getAll(@RequestParam(value="page", defaultValue = "0") long page,
                                              @RequestParam(value="count", defaultValue = "1000000000000000") long count) throws SQLException, IOException {
        return repository.findRangeOfAll(page * count, count);
    }

    @GetMapping("/{id}")
    public Resource getOne(@PathVariable(value="id") long departmentId) throws SQLException, IOException {
        return repository.findOne(departmentId);
    }

    @GetMapping("/children")
    public List<Resource> getRoot() throws SQLException, IOException {
        return repository.findRoot();
    }

    @GetMapping("/{id}/children")
    public List<Resource> getChildren(@PathVariable(value="id") long parentId) throws SQLException, IOException {
        return repository.findChildren(parentId);
    }
}
