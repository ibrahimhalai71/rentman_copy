package com.rentmen.app.repositories;

import com.rentmen.app.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepo extends JpaRepository<Skill, Long> {

}
