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

    Boolean existsByPin(String pin);

    @Query(value = "SELECT u.* FROM users u WHERE u.rating >= :minRating", nativeQuery = true)
    List<ServiceProvider> findByRatingGreaterThanOrEqual(Float minRating);

    @Query(value = "SELECT sp FROM ServiceProvider sp WHERE sp.availabilityStartDate <= :startDate AND ((sp.availabilityEndDate >= :endDate) OR sp.availabilityEndDate = :startDate)")
    List<ServiceProvider> findAvailableBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET rating = :rating WHERE id = :serviceProviderId", nativeQuery = true)
    void updateRating(Float rating, Long serviceProviderId);

    @Query("SELECT DISTINCT sp FROM ServiceProvider sp JOIN sp.availabilityDates ad WHERE ad BETWEEN :startDate AND :endDate AND sp.active = true")
    List<ServiceProvider> findServiceProvidersWithAvailabilityBetween(@Param("startDate") LocalDate startDate,
                                                                      @Param("endDate") LocalDate endDate);

    @Query("SELECT DISTINCT sp FROM ServiceProvider sp JOIN sp.unavailabilityDates ad WHERE ad BETWEEN :startDate AND :endDate AND sp.active = true")
    List<ServiceProvider> findServiceProvidersWithUnavailabilityBetween(@Param("startDate") LocalDate startDate,
                                                                        @Param("endDate") LocalDate endDate);

    @Query("SELECT DISTINCT sp FROM ServiceProvider sp JOIN sp.unknownDates ad WHERE ad BETWEEN :startDate AND :endDate AND sp.active = true")
    List<ServiceProvider> findServiceProvidersWithUnknownBetween(@Param("startDate") LocalDate startDate,
                                                                 @Param("endDate") LocalDate endDate);

    @Query("SELECT sp FROM ServiceProvider sp " +
            "WHERE NOT EXISTS (SELECT ad FROM sp.availabilityDates ad WHERE ad BETWEEN :startDate AND :endDate) " +
            "  AND NOT EXISTS (SELECT uad FROM sp.unavailabilityDates uad WHERE uad BETWEEN :startDate AND :endDate) " +
            "  AND NOT EXISTS (SELECT ud FROM sp.unknownDates ud WHERE ud BETWEEN :startDate AND :endDate) AND sp.active = true")
    List<ServiceProvider> findServiceProvidersWithMaybeBetween(@Param("startDate") LocalDate startDate,
                                                               @Param("endDate") LocalDate endDate);

    @Query("SELECT sp FROM ServiceProvider sp " +
            "JOIN sp.availabilityDates ad " +
            "WHERE ad IN :dates " +
            "GROUP BY sp.id " +
            "HAVING COUNT(DISTINCT ad) = :count AND sp.active = true")
    List<ServiceProvider> findUsersAvailableOnDates(@Param("dates") List<LocalDate> dates,
                                                    @Param("count") long count);

    @Query("SELECT DISTINCT sp " +
            " FROM ServiceProvider sp " +
            "JOIN sp.availabilityDates ad " +
            "WHERE ad IN :dates " +
            "AND sp.active = true")
    List<ServiceProvider> findUsersAvailableOnSomeDates(@Param("dates") List<LocalDate> dates);

    @Query("SELECT sp FROM ServiceProvider sp " +
            "JOIN sp.unavailabilityDates ad " +
            "WHERE ad IN :dates " +
            "GROUP BY sp.id " +
            "HAVING COUNT(DISTINCT ad) = :count AND sp.active = true")
    List<ServiceProvider> findUsersUnavailableOnDates(@Param("dates") List<LocalDate> dates,
                                                      @Param("count") long count);

    @Query("SELECT sp FROM ServiceProvider sp " +
            "JOIN sp.unknownDates ad " +
            "WHERE ad IN :dates " +
            "GROUP BY sp.id " +
            "HAVING COUNT(DISTINCT ad) = :count AND sp.active = true")
    List<ServiceProvider> findUsersUnknownOnDates(@Param("dates") List<LocalDate> dates,
                                                  @Param("count") long count);

    @Query("SELECT sp FROM ServiceProvider sp " +
            "WHERE NOT EXISTS (SELECT ad FROM sp.availabilityDates ad WHERE ad IN :dates) " +
            "  AND NOT EXISTS (SELECT ud FROM sp.unavailabilityDates ud WHERE ud IN :dates) " +
            "  AND NOT EXISTS (SELECT kd FROM sp.unknownDates kd WHERE kd IN :dates) AND sp.active = true")
    List<ServiceProvider> findUsersMaybeOnDates(@Param("dates") List<LocalDate> dates);

    @Query("SELECT DISTINCT sp FROM ServiceProvider sp JOIN sp.availabilityDates ad WHERE ad = :startDate AND sp.active = true")
    List<ServiceProvider> findServiceProvidersWithAvailabilityBetween(@Param("startDate") LocalDate startDate);
}
