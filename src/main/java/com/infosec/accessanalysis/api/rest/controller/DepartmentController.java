package com.infosec.accessanalysis.api.rest.controller;

import com.infosec.accessanalysis.dal.model.Department;
import com.infosec.accessanalysis.dal.repository.DepartmentRepository;
import com.infosec.accessanalysis.dal.repository.RepositoryFactory;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/1.0/department")
public class DepartmentController {

    private final AtomicLong counter = new AtomicLong();

    DepartmentRepository departmentRepository = RepositoryFactory.getDepartmentRepository("mssql");

    @GetMapping
    public List<Department> getAllDepartments(@RequestParam(value="page", defaultValue = "0") long page,
                                              @RequestParam(value="count", defaultValue = "1000000000000000") long count) throws SQLException {
        List<Department> deps = departmentRepository.findRangeOfAll(page * count, count);
        return deps;
    }

    @GetMapping("/{id}")
    public Department getDepartment(@PathVariable(value="id") long departmentId) throws SQLException {
        return departmentRepository.findOne(departmentId);
    }

    @GetMapping("/children")
    public List<Department> getChildDepartments() throws SQLException {
        return departmentRepository.findRoot();
    }

    @GetMapping("/{id}/children")
    public List<Department> getChildDepartments(@PathVariable(value="id") long parentId) throws SQLException {
        return departmentRepository.findChildren(parentId);
    }
}
