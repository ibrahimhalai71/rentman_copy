package com.rentmen.app.services;

import com.rentmen.app.DTO.*;

import java.util.List;
import java.util.Set;

public interface JobService {
 JobDto createJob(JobDto jobDto);

 JobDto updateJob(JobDto jobDto);

 JobDto getJobById(Long id);

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

 void postReviewFormServiceProvider(String token, InvoiceDto invoiceDto) throws Exception;

 GeneratedInvoiceDto getInvoice(Long jobId) throws Exception;
}
