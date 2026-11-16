package com.rentmen.app.repositories;

import com.rentmen.app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String paramString);
  
  Optional<User> findByFirstName(String paramString);
}
