package com.rentmen.app.repositories;

import com.rentmen.app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String paramString);

  @Modifying
  @Query(value = "DELETE FROM archive_job_service_provider_ids WHERE service_provider_id = :userId", nativeQuery = true)
  void deleteUserReferences(@Param("userId") Long userId);
  @Modifying
  @Query(value = "DELETE FROM job_service_provider_ids WHERE service_provider_id = :userId", nativeQuery = true)
  void deleteUserReferencesInJobs(@Param("userId") Long userId);

  Optional<User> findByFirstName(String paramString);

  @Query("SELECT CASE WHEN COUNT(sp) > 0 THEN true ELSE false END FROM ServiceProvider sp WHERE sp.pin = :pin")
  boolean existsByPin(@Param("pin") String pin);

}
