package com.quickStart.quickStart.abstracts;

import com.quickStart.quickStart.DTOs.CreateLeaveRequest;
import com.quickStart.quickStart.entities.LeaveRequest;

import java.util.List;
import java.util.UUID;


public interface LeaveRequestService {
    public LeaveRequest createLeaveRequest(CreateLeaveRequest leaveRequest, UUID employeeId);

    public List<LeaveRequest> getLeaveRequestByEmployeeId(UUID employeeId);

    public List<LeaveRequest> getAllLeaveRequests();

    public LeaveRequest updateLeaveRequest();

    public void deleteLeaveRequest();
}
