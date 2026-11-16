package com.rentmen.app.repositories;

import com.rentmen.app.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {
	Optional<Department> findById(Integer depId);
}
