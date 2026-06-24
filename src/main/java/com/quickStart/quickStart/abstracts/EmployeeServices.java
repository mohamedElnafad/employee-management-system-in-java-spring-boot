package com.quickStart.quickStart.abstracts;


import com.quickStart.quickStart.DTOs.CreateEmployee;
import com.quickStart.quickStart.DTOs.UpdateEmployee;
import com.quickStart.quickStart.entities.Employee;
import org.springframework.data.domain.Page;

import java.util.UUID;


public interface EmployeeServices {
    public Page<Employee> getEmployees(int page, int size);

    public Employee getEmployeeById(UUID employeeId);

    public Void deleteEmployee(UUID employeeId);

    public Employee updateEmployee(UUID employeeId, UpdateEmployee updatedEmployee);

    public Employee createEmployee(CreateEmployee employee);
}
