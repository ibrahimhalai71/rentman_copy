package com.rentmen.app.repositories;

import com.rentmen.app.entities.ArchiveJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveRepo extends JpaRepository<ArchiveJob, Long> {

}
