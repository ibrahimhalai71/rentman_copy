package com.rentmen.app.repositories;

import com.rentmen.app.entities.Job;
import com.rentmen.app.entities.PunchesDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PunchesDetailsRepo extends JpaRepository<PunchesDetails, Long> {
    List<PunchesDetails> findByProjectIdAndSpId(Long projectId, Long spId);
    List<PunchesDetails> findByProjectId(Long projectId);
    List<PunchesDetails> findAllByProjectIdIn(List<Long> projectId);
}
