package com.infosec.accanalysis.dbo.repository;

import com.infosec.accanalysis.dbo.model.Department;

import java.util.List;

public interface DepartmentRepository {

    List<Department> findAllDepartments();

    List<Department> findChildDepartments(int parentId);

    List<Department> findChildDepartments();

    Department findDepartment(int departmentId);
}
