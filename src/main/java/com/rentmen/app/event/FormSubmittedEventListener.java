package com.rentmen.app.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rentmen.app.DTO.FormSubmissionEvent;
import com.rentmen.app.DTO.InvoiceDto;
import com.rentmen.app.configurations.JwtTokenHelper;
import com.rentmen.app.entities.Invoice;
import com.rentmen.app.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class FormSubmittedEventListener {

    private final ObjectMapper objectMapper;

    @Autowired
    JobService jobService;

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    public FormSubmittedEventListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
//
//    @KafkaListener(topics = "form-events", groupId = "main-service-group")
//    public void handleFormSubmittedEvent(String eventJson, Acknowledgment acknowledgment) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.registerModule(new JavaTimeModule());
//            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//            FormSubmissionEvent event = mapper.readValue(eventJson, FormSubmissionEvent.class);
//
//            String token = event.getToken();
//            InvoiceDto formDto = event.getForm();
////
////            ObjectMapper objectMapper = new ObjectMapper();
////            objectMapper.registerModule(new JavaTimeModule());
////            InvoiceDto formObject = objectMapper.readValue(eventJson, InvoiceDto.class);
//
////            invoice.setFiles(files);
////            jobService.postReviewFormServiceProvider(token, invoice);
////            acknowledgment.acknowledge();
//        } catch (Exception e) {
//            // Handle deserialization errors
//            System.err.println("Failed to process message: " + e.getMessage());
//        }
//    }

    @KafkaListener(topics = "form-events", groupId = "main-service-group")
    public void handleFormSubmittedEvent(String eventJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            FormSubmissionEvent event = mapper.readValue(eventJson, FormSubmissionEvent.class);

            String token = event.getToken();
            InvoiceDto formDto = event.getForm();

            System.out.println("SUCCESS");
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.registerModule(new JavaTimeModule());
//            InvoiceDto formObject = objectMapper.readValue(eventJson, InvoiceDto.class);

//            invoice.setFiles(files);
            Invoice po = jobService.postReviewFormServiceProviderEvent(token, formDto);

            Long serviceProviderId = Long.parseLong(this.jwtTokenHelper.getSpIdFromToken(token));
            Long jobId = jwtTokenHelper.getJobIdFromToken(token);
            jobService.processFormFiles(jobId, serviceProviderId, po.getId());
//            acknowledgment.acknowledge();
        } catch (Exception e) {
            // Handle deserialization errors
            System.err.println("Failed to process message: " + e.getMessage());
        }
    }
}