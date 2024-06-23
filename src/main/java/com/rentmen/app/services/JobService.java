package com.rentmen.app.services;

import java.util.List;
import java.util.Set;

import com.rentmen.app.DTO.InvoiceDto;
import com.rentmen.app.DTO.JobDto;
import com.rentmen.app.DTO.PotentialJobOfferDto;
import com.rentmen.app.DTO.ReviewFormClient;
import com.rentmen.app.entities.Job;

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
 
 public JobDto getClientReviewForm(String token) throws Exception;
 
 public void postClientReviewForm(String token, List<ReviewFormClient> review)throws Exception;
 
 public Boolean isReviewedServiceProviderReviewForm(String token, Long serviceProviderId) throws Exception;
 
 public void postReviewFormServiceProvider(String token, InvoiceDto invoiceDto) throws Exception;
 
 public String getInvoice(Long jobId) throws Exception;
}
