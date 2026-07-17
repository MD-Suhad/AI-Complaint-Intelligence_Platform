package org.example.complaint.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ComplaintCreateRequest(
        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Description is required")
        @Size(min = 10, message = "Description must be at least 10 characters")
        String description
) {
}
