package org.example.complaint.service;

import org.example.complaint.dto.ComplaintCreateRequest;
import org.example.complaint.model.Complaint;
import org.example.complaint.model.ComplaintStatus;
import org.example.complaint.repository.ComplaintRepository;
import org.example.complaint.exception.ComplaintNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final ComplaintClassificationService classificationService;

    public ComplaintService(ComplaintRepository complaintRepository,
                           ComplaintClassificationService classificationService) {
        this.complaintRepository = complaintRepository;
        this.classificationService = classificationService;
    }

    @Transactional
    public Complaint createComplaint(ComplaintCreateRequest request) {
        String category = classificationService.classify(request.title(), request.description());
        Complaint complaint = new Complaint(
                request.title(),
                request.description(),
                category,
                ComplaintStatus.OPEN
        );
        return complaintRepository.save(complaint);
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Optional<Complaint> getComplaintById(Long id) {
        return complaintRepository.findById(id);
    }

    @Transactional
    public Complaint updateStatus(Long id, ComplaintStatus status) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ComplaintNotFoundException(id));
        complaint.setStatus(status);
        return complaintRepository.save(complaint);
    }
}
