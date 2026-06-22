package com.quickStart.quickStart.service;

import com.quickStart.quickStart.DTOs.CreateLeaveRequest;
import com.quickStart.quickStart.abstracts.LeaveRequestService;
import com.quickStart.quickStart.entities.Employee;
import com.quickStart.quickStart.entities.LeaveRequest;
import com.quickStart.quickStart.exceptions.CustomeExceptionHandler;
import com.quickStart.quickStart.repositories.EmployeeRepo;
import com.quickStart.quickStart.repositories.LeaveRequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {
    @Autowired
    private LeaveRequestRepo leaveRequestReop;
    @Autowired
    private EmployeeRepo employeeRepo;

    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestReop.findAll();
    }

    @Override
    public LeaveRequest updateLeaveRequest() {
        return null;
    }

    @Override
    public void deleteLeaveRequest() {

    }


    public LeaveRequest getLeaveRequestById(UUID leaveRequestId) {
        return leaveRequestReop.findById(leaveRequestId).orElseThrow(() -> CustomeExceptionHandler.
                ResourseNotFound("Leave request with id " + leaveRequestId + " not found"));
    }


    public LeaveRequest createLeaveRequest(CreateLeaveRequest leaveRequest, UUID employeeId) {
        Employee currentEmplyee = employeeRepo.findById(employeeId).orElseThrow(() -> CustomeExceptionHandler.
                ResourseNotFound("Employee with id " + employeeId + " not found"));

        LeaveRequest newLeaveRequest = new LeaveRequest();
        newLeaveRequest.setStartDate(leaveRequest.startDate());
        newLeaveRequest.setEndDate(leaveRequest.endDate());
        newLeaveRequest.setReason(leaveRequest.reason());
        newLeaveRequest.setStatus("PENDING");
        newLeaveRequest.setEmployee(currentEmplyee);
        return leaveRequestReop.save(newLeaveRequest);

    }


    public List<LeaveRequest> getLeaveRequestByEmployeeId(UUID employeeId) {

        List<LeaveRequest> leaveRequests = leaveRequestReop.findByEmployeeId(employeeId);
        if (leaveRequests.isEmpty()) {
            throw CustomeExceptionHandler.ResourseNotFound("No leave requests found for employee with");
        }
        return leaveRequests;
    }
}
