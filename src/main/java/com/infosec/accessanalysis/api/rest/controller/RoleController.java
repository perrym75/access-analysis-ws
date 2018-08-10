package com.infosec.accessanalysis.api.rest.controller;

import com.infosec.accessanalysis.dao.model.Personage;
import com.infosec.accessanalysis.dao.model.Resource;
import com.infosec.accessanalysis.dao.model.Role;
import com.infosec.accessanalysis.dao.repository.PersonageRepository;
import com.infosec.accessanalysis.dao.repository.ResourceRepository;
import com.infosec.accessanalysis.dao.repository.RoleRepository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/1.0/role")
public class RoleController {

    private final RoleRepository repository = new RoleRepository();
    private final PersonageRepository personageRepository = new PersonageRepository();
    private final ResourceRepository resourceRepository = new ResourceRepository();

    @GetMapping
    public List<Role> getAll(@RequestParam(value="page", defaultValue = "0") long page,
                             @RequestParam(value="count", defaultValue = "1000000000000000") long count)
            throws SQLException, IOException {
        return repository.findAll();//repository.findRangeOfAll(page * count, count);
    }

    @GetMapping("/{id}")
    public Role getOne(@PathVariable(value="id") long id)
            throws SQLException, IOException {
        return repository.findOne(id);
    }

    @GetMapping("/{id}/personage")
    public List<Personage> getRolePersonages(@PathVariable(value="id") long id) throws SQLException, IOException {
        return personageRepository.findByRole(id);
    }

    @GetMapping("/{id}/resource")
    public List<Resource> getRoleResources(@PathVariable(value="id") long id) throws SQLException, IOException {
        return resourceRepository.findByRole(0, id);
    }
}
