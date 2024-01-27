package com.rentmen.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rentmen.app.entities.ServiceProvider;

public interface ServiceProviderRepo extends JpaRepository<ServiceProvider, Long> {
	@Query("SELECT u.* FROM user u WHERE u.rating >= :minRating")
	    List<ServiceProvider> findByRatingGreaterThanOrEqual(Float minRating);

}
