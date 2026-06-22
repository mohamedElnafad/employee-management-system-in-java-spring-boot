package com.quickStart.quickStart.service;

import com.quickStart.quickStart.DTOs.CreateEmployee;
import com.quickStart.quickStart.DTOs.UpdateEmployee;
import com.quickStart.quickStart.abstracts.EmployeeServices;
import com.quickStart.quickStart.entities.Department;
import com.quickStart.quickStart.entities.Employee;
import com.quickStart.quickStart.exceptions.CustomeExceptionHandler;
import com.quickStart.quickStart.repositories.DepartmentRepo;
import com.quickStart.quickStart.repositories.EmployeeRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeServices {
    ArrayList<Employee> employees = new ArrayList<>();
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private EmailService emailService;

    public List<Employee> getEmployees() {
        return employeeRepo.findAll();
    }


    @PreAuthorize("@authorizationSecurity.isOwner(#employeeId, authentication)")
    public Employee getEmployeeById(UUID employeeId) {
        return employeeRepo.findById(employeeId).orElseThrow(() -> CustomeExceptionHandler.ResourseNotFound("Employee with id " + employeeId + " not found"));
    }


    public Void deleteEmployee(UUID employeeId) {
        Employee employee = employeeRepo.findById(employeeId).orElse(null);
        if (employee != null) {
            employeeRepo.delete(employee);
        } else {
            throw CustomeExceptionHandler.ResourseNotFound("Employee with id " + employeeId + " not found");
        }
        return null;
    }

    @PreAuthorize("@authorizationSecurity.isOwner(#employeeId, authentication)")
    public Employee updateEmployee(UUID employeeId, UpdateEmployee updatedEmployee) {
        Employee employee = employeeRepo.findById(employeeId).orElseThrow(
                () -> CustomeExceptionHandler.ResourseNotFound("Employee with id " + employeeId + " not found")
        );

        if (employee != null) {
            employee.setFirstName(updatedEmployee.firstName());
            employee.setLastName(updatedEmployee.lastName());
            employee.setPhoneNumber(updatedEmployee.phoneNumber());
            employee.setPosition(updatedEmployee.position());

        }

        return employeeRepo.save(employee);
    }

    @Transactional
    public Employee createEmployee(CreateEmployee employee) {

        Department department = departmentRepo.findById(employee.departmentId()).orElseThrow(() -> CustomeExceptionHandler.
                ResourseNotFound("Department with id " + employee.departmentId() + " not found"));
        String token = UUID.randomUUID().toString();
        Employee newEmployee = new Employee();
        newEmployee.setEmployeeCreationToken(token);
        newEmployee.setFirstName(employee.firstName());
        newEmployee.setLastName(employee.lastName());
        newEmployee.setEmail(employee.email());
        newEmployee.setPhoneNumber(employee.phoneNumber());
        newEmployee.setHireDate(employee.hireDate());
        newEmployee.setPosition(employee.position());
        newEmployee.setDepartment(department);
        employeeRepo.save(newEmployee);

        emailService.sendEmail(newEmployee.getEmail(), token);

        return newEmployee;
//        return newEmployee;
    }
}
