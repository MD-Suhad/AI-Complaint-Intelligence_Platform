package org.example.complaint.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintClassificationService {

    public String classify(String title, String description) {
        String content = (title + " " + description).toLowerCase();

        List<String> keywords = List.of(
                "billing", "charge", "refund", "payment", "invoice"
        );
        if (containsAny(content, keywords)) {
            return "Billing";
        }

        List<String> deliveryKeywords = List.of("delivery", "shipment", "late", "arrived", "missing");
        if (containsAny(content, deliveryKeywords)) {
            return "Delivery";
        }

        List<String> productKeywords = List.of("product", "quality", "defect", "broken", "damaged");
        if (containsAny(content, productKeywords)) {
            return "Product";
        }

        return "General";
    }

    private boolean containsAny(String content, List<String> keywords) {
        return keywords.stream().anyMatch(content::contains);
    }
}
