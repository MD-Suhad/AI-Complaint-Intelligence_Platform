package org.example.complaint;

import org.example.complaint.model.Complaint;
import org.example.complaint.model.ComplaintStatus;
import org.example.complaint.repository.ComplaintRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ComplaintRepositoryTest {

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("complaintsdb")
            .withUsername("ai_user")
            .withPassword("ai_pass");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
    }

    @Autowired
    private ComplaintRepository complaintRepository;

    @Test
    void shouldPersistAndLoadComplaint() {
        Complaint complaint = new Complaint("Broken product", "The product broke after one day", "Product", ComplaintStatus.OPEN);
        Complaint saved = complaintRepository.save(complaint);

        assertTrue(saved.getId() != null);
        assertEquals("Product", complaintRepository.findById(saved.getId()).orElseThrow().getCategory());
    }
}
