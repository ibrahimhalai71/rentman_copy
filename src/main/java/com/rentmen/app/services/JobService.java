package com.rentmen.app.services;

import com.rentmen.app.DTO.*;
import com.rentmen.app.entities.Invoice;
import com.rentmen.app.entities.PunchesDetails;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface JobService {
 JobDto createJob(JobDto jobDto);

 JobDto updateJob(JobDto jobDto);

 JobDto getJobById(Long id);

 List<PunchesDetails> getPunchedDetailsByJobIds(List<Long> jobIds);

 List<JobDto> getAllJobs();

 Boolean deleteJob(Long id);

 List<JobDto> getAllJobsByClientId(Long id);

 List<JobDto> getAllJobsByModeratorId(Long id);

 List<JobDto> getAllJobsServiceProviderIds(List<Long> serviceProviderIds);

 List<JobDto> getAllJobsCreatedById(Long id);

 List<JobDto> getPotentialJobsOfferedToServiceProvider(Set<Long> serviceProviderIds);

 List<PotentialJobOfferDto> getPotentialJobOffersToServiceProvider(Long serviceProviderIds);

 int acceptJobOffers(List<Long> jobIds, Long serviceProviderId);

 int rejectJobOffers(List<Long> jobIds, Long serviceProviderId);

 JobDto getClientReviewForm(String token) throws Exception;

 void postClientReviewForm(String token, List<ReviewFormClient> review) throws Exception;

 Boolean isReviewedServiceProviderReviewForm(String token, Long serviceProviderId) throws Exception;

 Boolean isOverriddenByClientBeforeServiceProviderReviewForm(String token, Long serviceProviderId) throws Exception;

 Invoice createInvoice(Long jobId) throws Exception;

 Invoice mergeInvoices(Long invoiceNumber, List<Long> jobIds);

 Invoice updateInvoice(Long invoiceId,Invoice invoice);

 InvoiceDto updateInvoiceTimesheet(InvoiceDto invoice);

 Boolean deleteInvoice(Long invoiceId);

 List<Invoice> getAllInvoices();

 List<Resource> getFilesForInvoice(Long invoiceId);

 byte[] generatePdf(Long invoiceId) throws Exception;
 List<Invoice> createAllPurchaseOrders(Long jobId) throws Exception;
 void postReviewFormServiceProvider(String token, InvoiceDto invoiceDto) throws Exception;

 Invoice postReviewFormServiceProviderEvent(String token, InvoiceDto invoiceDto) throws Exception;
 GeneratedInvoiceDto getInvoice(Long jobId) throws Exception;

 Boolean sendManualMessageToSP(Long jobId);

 void processFormFiles(Long jobId, Long serviceProviderId, Long poId);

 void savePunchDetails(PunchesDetails punchesDetails);
}
