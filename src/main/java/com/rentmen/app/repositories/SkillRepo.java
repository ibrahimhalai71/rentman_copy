package com.rentmen.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentmen.app.entities.Skill;

public interface SkillRepo extends JpaRepository<Skill, Long> {

}
