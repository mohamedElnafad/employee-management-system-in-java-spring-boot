package com.quickStart.quickStart.controllers;

import com.quickStart.quickStart.DTOs.CreateLeaveRequest;
import com.quickStart.quickStart.abstracts.LeaveRequestService;
import com.quickStart.quickStart.entities.LeaveRequest;
import com.quickStart.quickStart.exceptions.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/leave-requests")
public class LeaveRequestController {
    @Autowired
    private LeaveRequestService leaveRequestService;

    @GetMapping()
    public ResponseEntity<GlobalResponse<List<LeaveRequest>>> getAllLeaveRequests() {
        List<LeaveRequest> allLeaveRequests = leaveRequestService.getAllLeaveRequests();
        return new ResponseEntity<>(new GlobalResponse<>(allLeaveRequests), HttpStatus.OK);
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<GlobalResponse<List<LeaveRequest>>> getLeaveRequestByEmployeeId(@PathVariable UUID employeeId) {
        List<LeaveRequest> leaveRequests = leaveRequestService.getLeaveRequestByEmployeeId(employeeId);
        return new ResponseEntity<>(new GlobalResponse<>(leaveRequests), HttpStatus.OK);
    }

    @PostMapping("/employees/{employeeId}")
    public ResponseEntity<GlobalResponse<LeaveRequest>> createLeaveRequest(@Valid @RequestBody CreateLeaveRequest leaveRequest, @PathVariable UUID employeeId) {
        LeaveRequest createdLeaveRequest = leaveRequestService.createLeaveRequest(leaveRequest, employeeId);
        return new ResponseEntity<>(new GlobalResponse<>(createdLeaveRequest), HttpStatus.CREATED);
    }
    
}
