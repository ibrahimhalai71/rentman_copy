package com.rentmen.app.repositories;

import com.rentmen.app.entities.Job;
import com.rentmen.app.entities.PotentialJobOffer;
import com.rentmen.app.entities.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public interface JobRepo extends JpaRepository<Job, Long> {
	
	List<Job> findByClientId(Long clientId);
	
	List<Job> findByModeratorId(Long moderatorId);

//	List<Job> findByServiceProviderId(Long serviceProviderId);

	@Query(value = "SELECT j.* FROM job j WHERE j.created_by = :userId", nativeQuery = true)
	List<Job> findByCreatedBy(Long userId);
	
	List<Job> findByPotentialJobOffersIn(Set<PotentialJobOffer> potentialJobOffers);
	
	List<Job> findByServiceProvidersListIn(List<ServiceProvider> serviceProviders);

	@Query("SELECT CASE WHEN COUNT(j) > 0 THEN true ELSE false END FROM Job j JOIN j.serviceProvidersList sp WHERE j.id = :jobId AND sp.id = :serviceProviderId")
	boolean existsJobServiceProvider(@Param("jobId") Long jobId,
									 @Param("serviceProviderId") Long serviceProviderId);


	@Modifying
	@Query(value = "INSERT INTO job_service_provider_ids (job_id , service_provider_id) VALUES ( :jobId, :serviceProviderId)" , nativeQuery = true)
	int addToServiceProvidersList(Long jobId,Long serviceProviderId);
	
	@Query(value ="SELECT COUNT(id) FROM job_service_provider_ids jsp\r\n"
			+ "JOIN job j ON jsp.job_id = j.id\r\n"
			+ "WHERE j.reviewed_by_client = 1 AND jsp.service_provider_id = :serviceProviderId", nativeQuery = true)
	int getReviewedJobsCount(Long serviceProviderId);
	
	@Transactional
	@Modifying
	@Query("UPDATE Job j SET j.reviewedByClient = true WHERE j.id = ?1")
	void setReviewedByClientTrue(Long id);
	
//	@Transactional
//	@Modifying
//	@Query("UPDATE Job j SET j.reviewedByServiceProvider = true WHERE j.id = ?1")
//	void setReviewedByServiceProviderTrue(Long id);
	
	@Transactional
	@Modifying
	@Query(value = "Update job SET reviewed_by_service_provider = :reviewedByServiceProvider WHERE id = :id", nativeQuery = true)
	void setReviewedByServiceProviderTrue(String reviewedByServiceProvider,Long id);


//	@Query(value = "SELECT * FROM job j " +
//            "WHERE (j.end_date < :currentDate OR (j.end_date = :currentDate AND j.end_time < :currentTime)) " +
//            "AND j.reviewed_by_client > 0 AND j.reviewed_by_service_provider IS NOT NULL AND j.status <> 'COMPLETED'", nativeQuery = true)
//List<Job> findExpiredJobs(@Param("currentDate") LocalDate currentDate, @Param("currentTime") LocalTime currentTime);
	
//	@Query(value = "SELECT * FROM job j " +
//            "WHERE (j.end_date < :currentDate OR (j.end_date = :currentDate AND j.end_time < :currentTime)) " +
//            "AND j.status <> 'COMPLETED'", nativeQuery = true)
//List<Job> findExpiredJobs(@Param("currentDate") LocalDate currentDate, @Param("currentTime") LocalTime currentTime);

	@Query(value = "SELECT * FROM job j " +
    "WHERE TIMESTAMP(j.end_date, j.end_time) < NOW() " +
      "AND TIMESTAMP(j.end_date, j.end_time) >= NOW() - INTERVAL 14 HOUR " +
      "AND j.status <> 'COMPLETED'", nativeQuery = true)
	List<Job> findRecentlyExpiredJobs(@Param("currentDate") LocalDate currentDate,
									  @Param("currentTime") LocalTime currentTime);
}
