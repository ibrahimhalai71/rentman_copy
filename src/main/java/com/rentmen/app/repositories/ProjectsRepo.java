package com.rentmen.app.repositories;

import com.rentmen.app.entities.Projects;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectsRepo extends JpaRepository<Projects, Long> {
}
