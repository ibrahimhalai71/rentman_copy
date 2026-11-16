package com.rentmen.app.repositories;

import com.rentmen.app.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRequiredSkillRepository extends JpaRepository<Skill, Long> {
    
    @Modifying
    @Query(value ="UPDATE job_required_skills jrs SET jrs.job_id = :archiveJobId WHERE jsp.job_id = :jobId", nativeQuery = true)
    void updateJobId(@Param("jobId") Long jobId, @Param("archiveJobId") Long archiveJobId);
}