package com.quickStart.quickStart.service;

import com.quickStart.quickStart.DTOs.CreateDepartment;
import com.quickStart.quickStart.abstracts.DepartmentServices;
import com.quickStart.quickStart.entities.Department;
import com.quickStart.quickStart.exceptions.CustomeExceptionHandler;
import com.quickStart.quickStart.repositories.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DepartmentServiceImpl implements DepartmentServices {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Override
    public Department createDepartment(CreateDepartment dto) {
        Department department = new Department();
        department.setName(dto.name());
        return departmentRepo.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepo.findAll();
    }

    @Override
    public Department getDepartmentById(UUID departmentId) {
        Department department = departmentRepo.findById(departmentId).orElseThrow(() -> CustomeExceptionHandler.
                ResourseNotFound("Department with id " + departmentId + " not found"));
        return department;
    }

}