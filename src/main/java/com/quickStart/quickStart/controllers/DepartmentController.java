package com.quickStart.quickStart.controllers;

import com.quickStart.quickStart.DTOs.CreateDepartment;
import com.quickStart.quickStart.abstracts.DepartmentServices;
import com.quickStart.quickStart.entities.Department;
import com.quickStart.quickStart.exceptions.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentServices departmentService;

    @PostMapping
    public ResponseEntity<GlobalResponse<Department>> createDepartment(@Valid @RequestBody CreateDepartment dto) {
        Department department = new Department();
        department.setName(dto.name());
        return new ResponseEntity<>(new GlobalResponse<>(departmentService.createDepartment(dto)), HttpStatus.CREATED);
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<GlobalResponse<Department>> getDepartment(@PathVariable UUID departmentId) {
        Department department = departmentService.getDepartmentById(departmentId);
        return new ResponseEntity<>(new GlobalResponse<>(department), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<Department>>> getAllDepartments() {
        return new ResponseEntity<>(new GlobalResponse<>(departmentService.getAllDepartments()), HttpStatus.OK);
    }
}