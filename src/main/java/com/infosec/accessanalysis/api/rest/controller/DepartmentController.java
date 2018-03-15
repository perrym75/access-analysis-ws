package com.infosec.accessanalysis.api.rest.controller;

import com.infosec.accessanalysis.dal.model.Department;
import com.infosec.accessanalysis.dal.repository.DepartmentRepository;
import com.infosec.accessanalysis.dal.repository.RepositoryFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/1.0/department")
public class DepartmentController {

    private final AtomicLong counter = new AtomicLong();

    private DepartmentRepository repository = (DepartmentRepository) RepositoryFactory.getRepository("mssql:department");

    @GetMapping
    public List<Department> getAll(@RequestParam(value="page", defaultValue = "0") long page,
                                              @RequestParam(value="count", defaultValue = "1000000000000000") long count) throws SQLException, IOException {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Department getOne(@PathVariable(value="id") long departmentId) throws SQLException, IOException {
        return repository.findOne(departmentId);
    }

    @GetMapping("/children")
    public List<Department> getRoot() throws SQLException, IOException {
        return repository.findRoot();
    }

    @GetMapping("/{id}/children")
    public List<Department> getChildren(@PathVariable(value="id") long parentId) throws SQLException, IOException {
        return repository.findChildren(parentId);
    }
}
