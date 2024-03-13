package com.rentmen.app.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.rentmen.app.entities.Job;
import com.rentmen.app.entities.PotentialJobOffer;
import com.rentmen.app.entities.ServiceProvider;

public interface JobRepo extends JpaRepository<Job, Long> {
	
	List<Job> findByClientId(Long clientId);
	
	List<Job> findByModeratorId(Long moderatorId);

//	List<Job> findByServiceProviderId(Long serviceProviderId);

	@Query(value = "SELECT j.* FROM job j WHERE j.created_by = :userId", nativeQuery = true)
	List<Job> findByCreatedBy(Long userId);
	
	List<Job> findByPotentialJobOffersIn(Set<PotentialJobOffer> potentialJobOffers);
	
	List<Job> findByServiceProvidersListIn(List<ServiceProvider> serviceProviders);
	
	@Modifying
	@Query(value = "INSERT INTO job_service_provider_ids (job_id , service_provider_id) VALUES ( :jobId, :serviceProviderId)" , nativeQuery = true)
	int addToServiceProvidersList(Long jobId,Long serviceProviderId);




}
