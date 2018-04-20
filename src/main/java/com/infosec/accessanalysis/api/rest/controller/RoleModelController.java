package com.infosec.accessanalysis.api.rest.controller;

import com.infosec.accessanalysis.dao.model.RoleModel;
import com.infosec.accessanalysis.dao.repository.RoleModelRepository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/1.0/rolemodel")
public class RoleModelController {

    private final RoleModelRepository roleModelRepository = new RoleModelRepository();

    @GetMapping
    public List<RoleModel> getAll(@RequestParam(value="page", defaultValue = "0") long page,
                                  @RequestParam(value="count", defaultValue = "1000000000000000") long count)
            throws SQLException, IOException {
        return roleModelRepository.findRangeOfAll(page * count, count);
    }

    @GetMapping("/{id}")
    public RoleModel getOne(@PathVariable(value="id") long id) throws SQLException, IOException {
        return roleModelRepository.findOne(id);
    }
}
