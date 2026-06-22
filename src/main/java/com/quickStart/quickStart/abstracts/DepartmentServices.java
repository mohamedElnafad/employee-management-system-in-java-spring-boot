package com.quickStart.quickStart.abstracts;

import com.quickStart.quickStart.DTOs.CreateDepartment;
import com.quickStart.quickStart.entities.Department;

import java.util.List;
import java.util.UUID;

public interface DepartmentServices {
    public Department createDepartment(CreateDepartment dto);

    public Department getDepartmentById(UUID departmentId);

    public List<Department> getAllDepartments();
}
