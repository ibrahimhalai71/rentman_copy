package com.rentmen.app.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rentmen.app.DTO.FormSubmissionEvent;
import com.rentmen.app.DTO.InvoiceDto;
import com.rentmen.app.configurations.JwtTokenHelper;
import com.rentmen.app.entities.Invoice;
import com.rentmen.app.entities.PunchesDetails;
import com.rentmen.app.repositories.PunchesDetailsRepo;
import com.rentmen.app.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class PunchClockEventListener {

    private final ObjectMapper objectMapper;

    @Autowired
    JobService jobService;

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    public PunchClockEventListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "punch-details-event", groupId = "main-service-group")
    public void handlePunchDetailsEvent(String eventJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            PunchesDetails event = mapper.readValue(eventJson, PunchesDetails.class);

            ZoneId serverZone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(event.getTimestamp(), serverZone);
            Instant adjustedInstant = localDateTime.atZone(serverZone).toInstant();
            event.setTimestamp(adjustedInstant);
            System.out.println("SUCCESS");

            jobService.savePunchDetails(event);


        } catch (Exception e) {
            // Handle deserialization errors
            System.err.println("Failed to process message: " + e.getMessage());
        }
    }

}
