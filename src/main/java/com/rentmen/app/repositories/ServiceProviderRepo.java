package com.rentmen.app.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rentmen.app.entities.ServiceProvider;

public interface ServiceProviderRepo extends JpaRepository<ServiceProvider, Long> {
	@Query(value = "SELECT u.* FROM users u WHERE u.rating >= :minRating", nativeQuery = true)
	    List<ServiceProvider> findByRatingGreaterThanOrEqual(Float minRating);

	@Query(value = "SELECT sp FROM ServiceProvider sp WHERE (sp.availabilityStartDate <= :startDate AND sp.availabilityEndDate >= :endDate) OR (sp.availabilityStartDate IS NULL AND sp.availabilityEndDate IS NULL)")
	List<ServiceProvider> findAvailableBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
