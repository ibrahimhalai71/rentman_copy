package com.rentmen.app.repositories;

import com.rentmen.app.entities.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

public interface ServiceProviderRepo extends JpaRepository<ServiceProvider, Long> {
	@Query(value = "SELECT u.* FROM users u WHERE u.rating >= :minRating", nativeQuery = true)
	    List<ServiceProvider> findByRatingGreaterThanOrEqual(Float minRating);

	@Query(value = "SELECT sp FROM ServiceProvider sp WHERE sp.availabilityStartDate <= :startDate AND ((sp.availabilityEndDate >= :endDate) OR sp.availabilityEndDate = :startDate)")
	List<ServiceProvider> findAvailableBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE users SET rating = :rating WHERE id = :serviceProviderId", nativeQuery = true)
	void updateRating(Float rating, Long serviceProviderId);
}
