package com.rentmen.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentmen.app.entities.Moderator;

public interface ModeratorRepo extends JpaRepository<Moderator, Long> {

}
