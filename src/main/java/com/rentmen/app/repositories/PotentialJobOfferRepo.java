package com.rentmen.app.repositories;

import com.rentmen.app.entities.PotentialJobOffer;
import com.rentmen.app.entities.PotentialJobOfferId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

public interface PotentialJobOfferRepo extends JpaRepository<PotentialJobOffer, PotentialJobOfferId> {
	
	@Query(value = "SELECT pjo.* FROM potential_job_offers pjo WHERE pjo.service_provider_id = :serviceProviderId", nativeQuery = true)
	List<PotentialJobOffer> findbyServiceProviderId(Long serviceProviderId);
	
	@Query(value = "SELECT pjo.* FROM potential_job_offers pjo WHERE pjo.job_id = :jobId", nativeQuery = true)
	List<PotentialJobOffer> findbyJobId(Long jobId);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE PotentialJobOffer pjo set pjo.accepted = 1 WHERE pjo.job.id IN :jobIds and pjo.serviceProvider.id = :serviceProviderId")
	int acceptJobOffers(@Param("jobIds") Collection<Long> jobIds,@Param("serviceProviderId") Long serviceProviderId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE PotentialJobOffer pjo set pjo.accepted = 1 WHERE pjo.job.id IN :jobIds and pjo.serviceProvider.id IN :serviceProviderIds")
	int acceptNewJobOffers(@Param("jobIds") Collection<Long> jobIds,@Param("serviceProviderIds") Collection<Long> serviceProviderIds);

	@Transactional
	@Modifying
	@Query(value = "UPDATE PotentialJobOffer pjo set pjo.accepted = -1 WHERE pjo.job.id IN :jobIds and pjo.serviceProvider.id = :serviceProviderId")
	int rejectJobOffers(@Param("jobIds") Collection<Long> jobIds,@Param("serviceProviderId") Long serviceProviderId);
	
    @Modifying
    @Query("UPDATE PotentialJobOffer pjo SET pjo.job.id = :archiveJobId WHERE pjo.job.id = :jobId")
    void updateJobId(@Param("jobId") Long jobId, @Param("archiveJobId") Long archiveJobId);

}
