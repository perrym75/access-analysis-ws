package com.infosec.accessanalysis.api.rest.controller;

import com.infosec.accessanalysis.dao.model.Personage;
import com.infosec.accessanalysis.dao.model.Resource;
import com.infosec.accessanalysis.dao.model.ResourceFilter;
import com.infosec.accessanalysis.dao.model.ResourceStatus;
import com.infosec.accessanalysis.dao.repository.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/1.0/resource")
public class ResourceController {

    private final ResourceRepository repository = new ResourceRepository();
    private final PersonageRepository personageRepository = new PersonageRepository();

    @GetMapping
    public List<Resource> getAll(@RequestParam(value="page", defaultValue = "0") long page,
                                 @RequestParam(value="count", defaultValue = "1000000000000000") long count,
                                 @RequestParam(value="model_id", defaultValue = "0") long model_id)
            throws SQLException, IOException {
        return repository.findRangeOfAll(model_id, page * count, count);
    }

    @GetMapping("/{id}")
    public Resource getOne(@PathVariable(value="id") long id,
                           @RequestParam(value="model_id", defaultValue = "0") long model_id)
            throws SQLException, IOException {
        return repository.findOne(model_id, id);
    }

    @GetMapping("/children")
    public List<Resource> getRoot(@RequestParam(value="model_id", defaultValue = "0") long model_id,
                                  @RequestParam(value="filter", defaultValue = "") String filter)
            throws SQLException, IOException {
        ResourceFilter resourceFilter = new ResourceFilter();
        if (!filter.isEmpty()) {
            Set<ResourceStatus> statuses =  ResourceStatus.parse(filter);
            resourceFilter.setStatuses(statuses);
        }
        return repository.findRoot(model_id, resourceFilter);
    }

    @GetMapping("/{id}/children")
    public List<Resource> getChildren(@PathVariable(value="id") long id,
                                      @RequestParam(value="model_id", defaultValue = "0") long model_id)
            throws SQLException, IOException {
        return repository.findChildren(model_id, id);
    }

    @GetMapping("/{id}/personage")
    public List<Personage> getResourcePersonages(@PathVariable(value="id") long id) throws SQLException, IOException {
        return personageRepository.findByResource(id);
    }
}
