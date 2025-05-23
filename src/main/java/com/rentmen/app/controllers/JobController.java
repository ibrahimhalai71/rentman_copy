
package com.rentmen.app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentmen.app.DTO.*;
import com.rentmen.app.configurations.JwtTokenHelper;
import com.rentmen.app.entities.Invoice;
import com.rentmen.app.services.JobService;
import com.rentmen.app.services.UserService;
import com.rentmen.app.services.imp.EmailSendingService;
import com.rentmen.app.services.imp.TwilioMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/api/job"})
@CrossOrigin
public class JobController {
    @Autowired
    private JobService jobService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    TwilioMessageService twilioMessageService;
    @Autowired
    EmailSendingService emailSendingService;
    @Autowired
    private UserService userService;

    public JobController() {
    }

    @PostMapping({"/createJob"})
    public ResponseEntity<?> createJob(@RequestBody JobDto job) {
        JobDto createdJob = this.jobService.createJob(job);
        return new ResponseEntity(createdJob, HttpStatus.CREATED);
    }

    @GetMapping({"/delete-job/{id}"})
    public Map<String, Boolean> deleteJob(@PathVariable Long id) {
        Map<String, Boolean> response = new HashMap();
        Boolean isDeleted = this.jobService.deleteJob(id);
        if (isDeleted) {
            response.put("success", true);
            return response;
        } else {
            response.put("success", false);
            return response;
        }
    }

    @PostMapping({"/updateJob"})
    public ResponseEntity<?> updateJob(@RequestBody JobDto job) {
        JobDto updatedJob = this.jobService.updateJob(job);
        updatedJob.getPotentialJobOffers().forEach((sp) -> {
            UserDto spData = this.userService.getServiceProvider(sp.getServiceProviderId());

            Exception e;
            try {
                this.twilioMessageService.sendSMS(spData.getContact(), "Beste gebruiker, \nU heeft een werkverzoek ontvangen. \nTitel: " + updatedJob.getTitle() + " \nDatum/Tijd: " + updatedJob.getStartDate() + " " + updatedJob.getStartTime() + ", \nLog alstublieft in om te accepteren.");
            } catch (Exception var6) {
                e = var6;
                e.printStackTrace();
            }

            try {
                this.emailSendingService.sendEmail("info@eventico.nl", "Job Request" + updatedJob.getTitle(), spData.getEmail(), updatedJob.getId(), sp.getServiceProviderId(), updatedJob);
            } catch (Exception var5) {
                e = var5;
                e.printStackTrace();
            }

        });
        return ResponseEntity.ok(updatedJob);
    }

    @GetMapping({"/getJob/{jobId}"})
    public ResponseEntity<JobDto> getJob(@PathVariable("jobId") Long jobId) {
        return ResponseEntity.ok(this.jobService.getJobById(jobId));
    }

    @GetMapping({"/getAllJobs"})
    public ResponseEntity<List<JobDto>> getAllJobs() {
        List<JobDto> dto = this.jobService.getAllJobs();
        System.out.println("something");
        return ResponseEntity.ok(dto);
    }

    @GetMapping({"/getAllJobsByClientId/{clientId}"})
    public ResponseEntity<List<JobDto>> getAllJobsByClientId(@PathVariable("clientId") Long clientId) {
        List<JobDto> dto = this.jobService.getAllJobsByClientId(clientId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping({"/getAllJobsByModeratorId/{moderatorId}"})
    public ResponseEntity<List<JobDto>> getAllJobsByModeratorId(@PathVariable("moderatorId") Long moderatorId) {
        return ResponseEntity.ok(this.jobService.getAllJobsByModeratorId(moderatorId));
    }

    @GetMapping({"/getAllJobsServiceProviderIds/{serviceProviderIds}"})
    public ResponseEntity<List<JobDto>> getAllJobsServiceProviderId(@PathVariable("serviceProviderIds") List<Long> serviceProviderIds) {
        return ResponseEntity.ok(this.jobService.getAllJobsServiceProviderIds(serviceProviderIds));
    }

    @GetMapping({"/getAllJobsCreatedById/{createdById}"})
    public ResponseEntity<List<JobDto>> getAllJobsCreatedById(@PathVariable("createdById") Long createdById) {
        return ResponseEntity.ok(this.jobService.getAllJobsCreatedById(createdById));
    }

    @GetMapping({"/getPotentialJobsOfferedToServiceProvider/{serviceProviderIds}"})
    public ResponseEntity<List<JobDto>> getPotentialJobsOfferedToServiceProvider(@PathVariable("serviceProviderIds") Set<Long> serviceProviderIds) {
        return ResponseEntity.ok(this.jobService.getPotentialJobsOfferedToServiceProvider(serviceProviderIds));
    }

    @GetMapping({"/getPotentialJobOffersToServiceProvider/{serviceProviderId}"})
    public ResponseEntity<List<PotentialJobOfferDto>> getPotentialJobOffersToServiceProvider(@PathVariable("serviceProviderId") Long serviceProviderId) {
        return ResponseEntity.ok(this.jobService.getPotentialJobOffersToServiceProvider(serviceProviderId));
    }

    @PostMapping({"/acceptJobOffers"})
    public ResponseEntity<?> acceptJobOffers(@RequestBody List<PotentialJobOfferDto> pjoDto) {
        List<Long> jobIds = (List) pjoDto.stream().map(PotentialJobOfferDto::getJobId).collect(Collectors.toList());
        this.jobService.acceptJobOffers(jobIds, ((PotentialJobOfferDto) pjoDto.get(0)).getServiceProviderId());
        return ResponseEntity.ok("Updated");
    }

    @GetMapping({"/acceptJobOfferFromEmail"})
    public ResponseEntity<?> acceptJobOffersromEmail(@RequestParam Long jobId, @RequestParam Long userId) {
        if (jobId != null && userId != null) {
            List<Long> ids = new ArrayList();
            ids.add(jobId);
            this.jobService.acceptJobOffers(ids, userId);
            return ResponseEntity.ok("Updated");
        } else {
            return ResponseEntity.badRequest().body("Job ID and User ID cannot be null");
        }
    }

    @PostMapping({"/rejectJobOffers"})
    public ResponseEntity<?> rejectJobOffers(@RequestBody List<PotentialJobOfferDto> pjoDto) {
        List<Long> jobIds = (List) pjoDto.stream().map(PotentialJobOfferDto::getJobId).collect(Collectors.toList());
        this.jobService.rejectJobOffers(jobIds, ((PotentialJobOfferDto) pjoDto.get(0)).getServiceProviderId());
        return ResponseEntity.ok("Updated");
    }

    @GetMapping({"/review-form/client/{token}"})
    public ResponseEntity<?> getClientReviewForm(@PathVariable("token") String token) {
        try {
            return ResponseEntity.ok(this.jobService.getClientReviewForm(token));
        } catch (Exception var3) {
            Exception e = var3;
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping({"/review-form/client"})
    public ResponseEntity<?> postClientReviewForm(@RequestParam("token") String token, @RequestBody List<ReviewFormClient> reviews) throws Exception {
        this.jobService.postClientReviewForm(token, reviews);
        return ResponseEntity.ok("review posted successfully");
    }

    @GetMapping({"/review-form/getToken/{jobId}"})
    public ResponseEntity<?> getReviewToken(@PathVariable("jobId") String jobId) {
        return ResponseEntity.ok(this.jwtTokenHelper.generateTokenForReviewForm(jobId));
    }

    @GetMapping({"/review-form/service-provider-is-filled/{spId}"})
    public ResponseEntity<?> isReviewedServiceProviderReviewForm(@RequestParam("token") String token, @PathVariable("spId") Long spId) throws Exception {
        return ResponseEntity.ok(this.jobService.isReviewedServiceProviderReviewForm(token, spId));
    }

    @GetMapping({"/review-form/service-provider-is-overridden/{spId}"})
    public ResponseEntity<?> isOverriddenByClientServiceProviderReviewForm(@RequestParam("token") String token, @PathVariable("spId") Long spId) throws Exception {
        return ResponseEntity.ok(this.jobService.isOverriddenByClientBeforeServiceProviderReviewForm(token, spId));
    }

//    @PostMapping({"/review-form/service-provider"})
//    public ResponseEntity<?> postReviewFormServiceProvider(@RequestParam("token") String token, @RequestBody InvoiceDto invoice) throws Exception {
//        this.jobService.postReviewFormServiceProvider(token, invoice);
//        return ResponseEntity.ok("Sucsess: review form posted successfully");
//    }


    @PostMapping(value = "/review-form/service-provider", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> postReviewFormServiceProvider(
            @RequestParam("token") String token,
            @RequestPart("invoice") String invoiceJson,
            @RequestPart("files") List<MultipartFile> files) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        InvoiceDto invoice = objectMapper.readValue(invoiceJson, InvoiceDto.class);

        invoice.setFiles(files);
        jobService.postReviewFormServiceProvider(token, invoice);
        return ResponseEntity.ok("Success: review form posted successfully");
    }

//    @PostMapping(value = "/review-form/service-provider/{token}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> postReviewFormServiceProvider(
//            @RequestParam("token") String token,
//            @RequestPart("invoice") String invoiceJson,
//            @RequestPart("files") List<MultipartFile> files) throws Exception {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        InvoiceDto invoice = objectMapper.readValue(invoiceJson, InvoiceDto.class);
//
//        invoice.setFiles(files);
//        jobService.postReviewFormServiceProvider(token, invoice);
//        return ResponseEntity.ok("Success: review form posted successfully");
//    }

    @GetMapping({"/generateInvoice/{jobId}"})
    public ResponseEntity<?> generateInvoice(@PathVariable("jobId") Long jobId) throws Exception {
        return ResponseEntity.ok(this.jobService.getInvoice(jobId));
    }

    @PostMapping({"/create-invoices-po/{jobId}"})
    public ResponseEntity<?> createInvoice(@PathVariable("jobId") Long jobId) throws Exception {
        List<Invoice> invoices = new ArrayList<>();
        Invoice clientInvoice = this.jobService.createInvoice(jobId);
        List<Invoice> serviceProvidersInvoices = this.jobService.createAllPurchaseOrders(jobId);
        invoices.add(clientInvoice);
        invoices.addAll(serviceProvidersInvoices);
        return ResponseEntity.ok(invoices);
    }

    @PostMapping({"/merge-invoices/{invoiceNumber}"})
    public ResponseEntity<?> mergeInvoice(@PathVariable("invoiceNumber") Long invoiceNumber, @RequestBody List<Long> jobIds) throws Exception {
        Invoice mergedInvoice = this.jobService.mergeInvoices(invoiceNumber, jobIds);
        return ResponseEntity.ok(mergedInvoice);
    }

    @PutMapping({"/update-invoice/{invoiceId}"})
    public ResponseEntity<?> updateInvoice(@PathVariable("invoiceId") Long invoiceId, @RequestBody Invoice invoice) throws Exception {
        Invoice updatedInvoice = this.jobService.updateInvoice(invoiceId, invoice);
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping({"/delete-invoice/{invoiceId}"})
    public ResponseEntity<?> updatePurchaseOrder(@PathVariable("invoiceId") Long invoiceId) throws Exception {
        Boolean deleted = this.jobService.deleteInvoice(invoiceId);
        return ResponseEntity.ok(deleted);
    }

    @GetMapping({"/list"})
    public ResponseEntity<?> getAllInvoices() throws Exception {
        return ResponseEntity.ok(this.jobService.getAllInvoices());
    }

    @GetMapping("/files/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws IOException {
        Path path = Paths.get("src/main/resources/uploads/").resolve(filename);
        Resource resource = new UrlResource(path.toUri());

        if (resource.exists()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/files/invoice/{invoiceId}")
    public ResponseEntity<List<Resource>> getFilesByInvoiceId(@PathVariable Long invoiceId) throws IOException {

        List<Resource> files = jobService.getFilesForInvoice(invoiceId);
        return ResponseEntity.ok(files);
    }

    @GetMapping("/invoice/print-pdf/{invoiceId}")
    public ResponseEntity<byte[]> generateInvoicePdf(@PathVariable("invoiceId") Long invoiceId) {
        try {
            byte[] pdfBytes = jobService.generatePdf(invoiceId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename("invoice-" + invoiceId + ".pdf")
                    .build());
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }


    @GetMapping("/file/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        // Load the image from the classpath resources
        Resource resource = new ClassPathResource("uploads/" + imageName);

        // Check if the resource exists
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        // Return the image as a response
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // You can dynamically set the correct content type
                .body(resource);
    }
}
