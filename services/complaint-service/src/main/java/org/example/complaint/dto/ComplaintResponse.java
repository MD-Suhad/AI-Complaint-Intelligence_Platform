package org.example.complaint.dto;

import org.example.complaint.model.Complaint;
import org.example.complaint.model.ComplaintStatus;

import java.time.Instant;

public record ComplaintResponse(
        Long id,
        String title,
        String description,
        String category,
        ComplaintStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public static ComplaintResponse fromEntity(Complaint complaint) {
        return new ComplaintResponse(
                complaint.getId(),
                complaint.getTitle(),
                complaint.getDescription(),
                complaint.getCategory(),
                complaint.getStatus(),
                complaint.getCreatedAt(),
                complaint.getUpdatedAt()
        );
    }
}
