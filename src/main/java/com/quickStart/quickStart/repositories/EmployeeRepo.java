package com.quickStart.quickStart.repositories;


import com.quickStart.quickStart.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, UUID> {
    public Employee findByemployeeCreationToken(String token);
}
