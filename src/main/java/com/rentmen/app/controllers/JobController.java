package com.rentmen.app.controllers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentmen.app.DTO.JobDto;
import com.rentmen.app.DTO.PotentialJobOfferDto;
import com.rentmen.app.DTO.UserDto;
import com.rentmen.app.services.JobService;

@RestController
@RequestMapping({"/api/job"})
@CrossOrigin
public class JobController {
	
	@Autowired
	private JobService jobService;
	
	@PostMapping({"/createJob"})
	public ResponseEntity<?> createJob(@RequestBody JobDto job) {
		JobDto createdJob = jobService.createJob(job);
		return new ResponseEntity<JobDto>(createdJob, HttpStatus.CREATED);
	}
	
	@PostMapping({"/updateJob"})
	public ResponseEntity<?> updateJob(@RequestBody JobDto job) {
		JobDto updatedJob = jobService.updateJob(job);
		return ResponseEntity.ok(updatedJob);
	}
	
	@GetMapping({"/getJob/{jobId}"})
	public ResponseEntity<JobDto> getJob(@PathVariable("jobId") Long jobId){
		return ResponseEntity.ok(jobService.getJobById(jobId));
	}
	
	@GetMapping({"/getAllJobs"})
	public ResponseEntity<List<JobDto>> getAllJobs(){
		return ResponseEntity.ok(jobService.getAllJobs());
	}
	
	@GetMapping({"/getAllJobsByClientId/{clientId}"})
	public ResponseEntity<List<JobDto>> getAllJobsByClientId(@PathVariable("clientId") Long clientId){
		return ResponseEntity.ok(jobService.getAllJobsByClientId(clientId));
	}
	
	@GetMapping({"/getAllJobsByModeratorId/{moderatorId}"})
	public ResponseEntity<List<JobDto>> getAllJobsByModeratorId(@PathVariable("moderatorId") Long moderatorId){
		return ResponseEntity.ok(jobService.getAllJobsByModeratorId(moderatorId));
	}
	
	@GetMapping({"/getAllJobsServiceProviderIds/{serviceProviderIds}"})
	public ResponseEntity<List<JobDto>> getAllJobsServiceProviderId(@PathVariable("serviceProviderIds") List<Long> serviceProviderIds){
		return ResponseEntity.ok(jobService.getAllJobsServiceProviderIds(serviceProviderIds));
	}
	
	@GetMapping({"/getAllJobsCreatedById/{createdById}"})
	public ResponseEntity<List<JobDto>> getAllJobsCreatedById(@PathVariable("createdById") Long createdById){
		return ResponseEntity.ok(jobService.getAllJobsCreatedById(createdById));
	}
	
	@GetMapping({"/getPotentialJobsOfferedToServiceProvider/{serviceProviderIds}"})
	public ResponseEntity<List<JobDto>> getPotentialJobsOfferedToServiceProvider(@PathVariable("serviceProviderIds") Set<Long> serviceProviderIds){
		return ResponseEntity.ok(jobService.getPotentialJobsOfferedToServiceProvider(serviceProviderIds));
	}
	
	@GetMapping({"/getPotentialJobOffersToServiceProvider/{serviceProviderId}"})
	public ResponseEntity<List<PotentialJobOfferDto>> getPotentialJobOffersToServiceProvider(@PathVariable("serviceProviderId") Long serviceProviderId){
		return ResponseEntity.ok(jobService.getPotentialJobOffersToServiceProvider(serviceProviderId));
	}
	
	@PostMapping({"/acceptJobOffers"})
	public ResponseEntity<?> acceptJobOffers(@RequestBody List<PotentialJobOfferDto> pjoDto) {
		List<Long> jobIds = pjoDto.stream().map(PotentialJobOfferDto::getJobId) // Extract job ID from each
																				// PotentialJobOfferDto
				.collect(Collectors.toList());
		jobService.acceptJobOffers(jobIds, pjoDto.get(0).getServiceProviderId());
		return ResponseEntity.ok("Updated");
	}
	
	@PostMapping({"/rejectJobOffers"})
	public ResponseEntity<?> rejectJobOffers(@RequestBody List<PotentialJobOfferDto> pjoDto) {
		List<Long> jobIds = pjoDto.stream().map(PotentialJobOfferDto::getJobId) // Extract job ID from each
																				// PotentialJobOfferDto
				.collect(Collectors.toList());
		jobService.rejectJobOffers(jobIds, pjoDto.get(0).getServiceProviderId());
		return ResponseEntity.ok("Updated");
	}
	

}
