package com.rentmen.app.repositories;

import com.rentmen.app.entities.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModeratorRepo extends JpaRepository<Moderator, Long> {

}
