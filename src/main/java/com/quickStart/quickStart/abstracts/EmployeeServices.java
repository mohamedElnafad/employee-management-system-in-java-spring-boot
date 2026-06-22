package com.quickStart.quickStart.abstracts;


import com.quickStart.quickStart.DTOs.CreateEmployee;
import com.quickStart.quickStart.DTOs.UpdateEmployee;
import com.quickStart.quickStart.entities.Employee;

import java.util.List;
import java.util.UUID;


public interface EmployeeServices {
    public List<Employee> getEmployees();

    public Employee getEmployeeById(UUID employeeId);

    public Void deleteEmployee(UUID employeeId);

    public Employee updateEmployee(UUID employeeId, UpdateEmployee updatedEmployee);

    public Employee createEmployee(CreateEmployee employee);
}
