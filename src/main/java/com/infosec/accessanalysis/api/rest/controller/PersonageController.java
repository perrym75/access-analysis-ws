package com.infosec.accessanalysis.api.rest.controller;

import com.infosec.accessanalysis.dao.model.Personage;
import com.infosec.accessanalysis.dao.model.Resource;
import com.infosec.accessanalysis.dao.model.Role;
import com.infosec.accessanalysis.dao.model.UserAccount;
import com.infosec.accessanalysis.dao.repository.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/1.0/personage")
public class PersonageController {

    private final PersonageRepository personageRepository = new PersonageRepository();
    private final ResourceRepository resourceRepository = new ResourceRepository();
    private final UserAccountRepository userAccountRepository = new UserAccountRepository();
    private final RoleRepository roleRepository = new RoleRepository();

    @GetMapping
    public List<Personage> getAll(@RequestParam(value="page", defaultValue = "0") long page,
                                  @RequestParam(value="count", defaultValue = "1000000000000000") long count)
            throws SQLException, IOException {
        return personageRepository.findRangeOfAll(page * count, count);
    }

    @GetMapping("/{id}")
    public Personage getOne(@PathVariable(value="id") long id) throws SQLException, IOException {
        return personageRepository.findOne(id);
    }

    @GetMapping("/{id}/resource")
    public List<Resource> getPersonageResources(@PathVariable(value="id") long id,
                                                @RequestParam(value="model_id", defaultValue = "0") long model_id)
            throws SQLException, IOException {
        return resourceRepository.findByPersonage(model_id, id);
    }

    @GetMapping("/{id}/useraccount")
    public List<UserAccount> getPersonageUserAccounts(@PathVariable(value="id") long id)
            throws SQLException, IOException {
        return userAccountRepository.findByPersonage(id);
    }

    @GetMapping("/{id}/resource/{res_id}/useraccount")
    public List<UserAccount> getPersonageUserAccounts(@PathVariable(value="id") long id,
                                                      @PathVariable(value="res_id") long res_id)
            throws SQLException, IOException {
        return userAccountRepository.findByPersonageAndResource(id, res_id);
    }

    @GetMapping("/{id}/resource/{res_id}/role")
    public List<Role> getPersonageRoles(@PathVariable(value="id") long id,
                                        @PathVariable(value="res_id") long res_id)
            throws SQLException, IOException {
        return roleRepository.findByPersonageAndResource(id, res_id);
    }
}
