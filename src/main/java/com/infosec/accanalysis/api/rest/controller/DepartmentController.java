package com.infosec.accanalysis.api.rest.controller;

import com.infosec.accanalysis.api.rest.model.Department;
import com.infosec.accanalysis.api.rest.model.DepartmentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/1.0/department")
public class DepartmentController {

    private final AtomicLong counter = new AtomicLong();

    @GetMapping
    public List<Department> getAllDepartments() {
        return DepartmentRepository.findAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getDepartment(@PathVariable(value="id") int departmentId) {
        return DepartmentRepository.findDepartment(departmentId);
    }

    @GetMapping("/children")
    public List<Department> getChildDepartments() {
        return DepartmentRepository.findChildDepartments();
    }

    @GetMapping("/children/{id}")
    public List<Department> getChildDepartments(@PathVariable(value="id") int parentId) {
        return DepartmentRepository.findChildDepartments(parentId);
    }
}
