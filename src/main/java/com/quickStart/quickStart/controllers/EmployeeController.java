package com.quickStart.quickStart.controllers;

import com.quickStart.quickStart.DTOs.CreateEmployee;
import com.quickStart.quickStart.DTOs.UpdateEmployee;
import com.quickStart.quickStart.abstracts.EmployeeServices;
import com.quickStart.quickStart.entities.Employee;
import com.quickStart.quickStart.exceptions.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    @Autowired
    private final EmployeeServices employeeService;

    public EmployeeController(EmployeeServices employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public ResponseEntity<GlobalResponse<List<Employee>>> getEmployees() {
        List<Employee> allEmployees = employeeService.getEmployees();

        return new ResponseEntity<>(new GlobalResponse<>(allEmployees), HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<GlobalResponse<Employee>> getEmployeeById(@PathVariable UUID employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);

        return new ResponseEntity<>(new GlobalResponse<>(employee), HttpStatus.OK);

    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID employeeId) {
        employeeService.deleteEmployee(employeeId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<GlobalResponse<Employee>> updateEmployee(@PathVariable UUID employeeId, @Valid @RequestBody UpdateEmployee updatedEmployee) {
        Employee updatedEmp = employeeService.updateEmployee(employeeId, updatedEmployee);

        return new ResponseEntity<>(new GlobalResponse<>(updatedEmp), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<GlobalResponse<Employee>> createEmployee(@Valid @RequestBody CreateEmployee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);

        return new ResponseEntity<GlobalResponse<Employee>>(new GlobalResponse(createdEmployee), HttpStatus.CREATED);
    }
}
