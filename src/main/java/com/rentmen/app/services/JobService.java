package com.rentmen.app.services;

import java.util.List;
import java.util.Set;

import com.rentmen.app.DTO.JobDto;

public interface JobService {
 JobDto createJob(JobDto jobDto);
 
 JobDto updateJob(JobDto jobDto);
 
 JobDto getJobById(Long id);
 
 List<JobDto> getAllJobs();
 
 Boolean deleteJob(Long id);
 
 List<JobDto> getAllJobsByClientId(Long id);
 
 List<JobDto> getAllJobsByModeratorId(Long id);
 
 List<JobDto> getAllJobsServiceProviderId(Long id);
 
 List<JobDto> getAllJobsCreatedById(Long id);
 
 List<JobDto> getPotentialJobsOfferedToServiceProvider(Set<Long> serviceProviderIds);
}
