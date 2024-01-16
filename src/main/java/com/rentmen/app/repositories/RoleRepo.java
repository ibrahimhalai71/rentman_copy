package com.rentmen.app.repositories;

import com.rentmen.app.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {}
