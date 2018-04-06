package com.infosec.accessanalysis.api.rest.controller;

import com.infosec.accessanalysis.dao.model.Department;
import com.infosec.accessanalysis.dao.repository.DepartmentRepository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/1.0/department")
public class DepartmentController {

    private final DepartmentRepository repository = new DepartmentRepository();

    @GetMapping
    public List<Department> getAll(@RequestParam(value="page", defaultValue = "0") long page,
                                              @RequestParam(value="count", defaultValue = "1000000000000000") long count) throws SQLException, IOException {
        return repository.findRangeOfAll(page * count, count);
    }

    @GetMapping("/{id}")
    public Department getOne(@PathVariable(value="id") long id) throws SQLException, IOException {
        return repository.findOne(id);
    }

    @GetMapping("/children")
    public List<Department> getRoot() throws SQLException, IOException {
        return repository.findRoot();
    }

    @GetMapping("/{id}/children")
    public List<Department> getChildren(@PathVariable(value="id") long id) throws SQLException, IOException {
        return repository.findChildren(id);
    }
}
