package org.example.complaint.dto;

import jakarta.validation.constraints.NotNull;
import org.example.complaint.model.ComplaintStatus;

public record ComplaintStatusUpdateRequest(
        @NotNull(message = "Status is required")
        ComplaintStatus status
) {
}
