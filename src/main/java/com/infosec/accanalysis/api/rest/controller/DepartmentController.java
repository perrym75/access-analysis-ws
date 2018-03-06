package com.infosec.accanalysis.api.rest.controller;

import com.infosec.accanalysis.dbo.model.Department;
import com.infosec.accanalysis.dbo.repository.DepartmentRepository;
import com.infosec.accanalysis.dbo.repository.RepositoryFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/1.0/department")
public class DepartmentController {

    private final AtomicLong counter = new AtomicLong();

    DepartmentRepository departmentRepository = RepositoryFactory.getDepartmentRepository("mssql");

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentRepository.findAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getDepartment(@PathVariable(value="id") int departmentId) {
        return departmentRepository.findDepartment(departmentId);
    }

    @GetMapping("/children")
    public List<Department> getChildDepartments() {
        return departmentRepository.findChildDepartments();
    }

    @GetMapping("/children/{id}")
    public List<Department> getChildDepartments(@PathVariable(value="id") int parentId) {
        return departmentRepository.findChildDepartments(parentId);
    }
}
