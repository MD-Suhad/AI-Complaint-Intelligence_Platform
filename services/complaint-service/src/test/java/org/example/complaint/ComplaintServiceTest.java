package org.example.complaint;

import org.example.complaint.dto.ComplaintCreateRequest;
import org.example.complaint.model.Complaint;
import org.example.complaint.model.ComplaintStatus;
import org.example.complaint.repository.ComplaintRepository;
import org.example.complaint.service.ComplaintClassificationService;
import org.example.complaint.service.ComplaintService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ComplaintServiceTest {

    @Mock
    private ComplaintRepository complaintRepository;

    @Mock
    private ComplaintClassificationService classificationService;

    @InjectMocks
    private ComplaintService complaintService;

    @Test
    void shouldCreateComplaintWithClassifiedCategory() {
        ComplaintCreateRequest request = new ComplaintCreateRequest("Late delivery", "My delivery arrived very late");
        when(classificationService.classify("Late delivery", "My delivery arrived very late")).thenReturn("Delivery");

        Complaint complaint = new Complaint("Late delivery", "My delivery arrived very late", "Delivery", ComplaintStatus.OPEN);
        when(complaintRepository.save(org.mockito.ArgumentMatchers.any(Complaint.class))).thenReturn(complaint);

        Complaint result = complaintService.createComplaint(request);

        assertEquals("Delivery", result.getCategory());
        verify(complaintRepository).save(org.mockito.ArgumentMatchers.any(Complaint.class));
    }

    @Test
    void shouldThrowWhenComplaintNotFoundOnStatusUpdate() {
        when(complaintRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> complaintService.updateStatus(99L, ComplaintStatus.RESOLVED));
    }
}
