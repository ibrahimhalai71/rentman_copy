package com.rentmen.app.repositories;

import com.rentmen.app.entities.Job;
import com.rentmen.app.entities.PunchesDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PunchesDetailsRepo extends JpaRepository<PunchesDetails, Long> {
    Boolean existsByJobIdAndSpId(Long jobId, Long spId);
    Boolean existsByJobId(Long jobId);

    List<PunchesDetails> findAllByJobIdIn(List<Long> jobIds);
}
