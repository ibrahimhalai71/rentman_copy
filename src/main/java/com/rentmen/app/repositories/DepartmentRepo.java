package com.rentmen.app.repositories;

import com.rentmen.app.entities.Department;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {
	Optional<Department> findById(Integer depId);
}
