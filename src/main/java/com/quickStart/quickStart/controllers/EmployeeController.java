package com.quickStart.quickStart.controllers;

import com.quickStart.quickStart.DTOs.CreateEmployee;
import com.quickStart.quickStart.DTOs.PaginationResponse;
import com.quickStart.quickStart.DTOs.UpdateEmployee;
import com.quickStart.quickStart.abstracts.EmployeeServices;
import com.quickStart.quickStart.entities.Employee;
import com.quickStart.quickStart.exceptions.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<GlobalResponse<PaginationResponse<Employee>>> getEmployees(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "2") int size) {
        Page<Employee> allEmployees = employeeService.getEmployees(page, size);
        String nextPageURL = allEmployees.hasNext() ? "/employees?page=" +
                                                      (page + 1) + "&size=" + size : null;
        String previosPageURL = allEmployees.hasPrevious() ? "/employees?page=" + (page - 1) + "&size=" + size : null;

        var paginationResponse = new PaginationResponse<>(allEmployees.getContent(),
                allEmployees.getNumber(),
                allEmployees.getTotalPages(),
                allEmployees.getSize(),
                allEmployees.getTotalElements(),
                allEmployees.hasNext(),
                allEmployees.hasPrevious(),
                nextPageURL,
                previosPageURL
        );

        return new ResponseEntity<>(new GlobalResponse<>(paginationResponse), HttpStatus.OK);
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
