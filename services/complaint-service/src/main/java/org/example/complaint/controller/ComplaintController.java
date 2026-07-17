package org.example.complaint.controller;

import jakarta.validation.Valid;
import org.example.complaint.dto.ComplaintCreateRequest;
import org.example.complaint.dto.ComplaintResponse;
import org.example.complaint.dto.ComplaintStatusUpdateRequest;
import org.example.complaint.model.Complaint;
import org.example.complaint.service.ComplaintService;
import org.example.complaint.exception.ComplaintNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping
    public ResponseEntity<ComplaintResponse> createComplaint(@Valid @RequestBody ComplaintCreateRequest request) {
        Complaint complaint = complaintService.createComplaint(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ComplaintResponse.fromEntity(complaint));
    }

    @GetMapping
    public List<ComplaintResponse> getAllComplaints() {
        return complaintService.getAllComplaints().stream()
                .map(ComplaintResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ComplaintResponse getComplaintById(@PathVariable Long id) {
        return complaintService.getComplaintById(id)
                .map(ComplaintResponse::fromEntity)
                .orElseThrow(() -> new ComplaintNotFoundException(id));
    }

    @PatchMapping("/{id}/status")
    public ComplaintResponse updateStatus(@PathVariable Long id, @RequestBody ComplaintStatusUpdateRequest request) {
        Complaint complaint = complaintService.updateStatus(id, request.status());
        return ComplaintResponse.fromEntity(complaint);
    }
}
