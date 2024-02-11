package com.rentmen.app.services;

import java.util.Set;

import com.rentmen.app.DTO.SkillDto;

public interface SkillService {
	SkillDto createSkill(SkillDto skillDto);
	
	SkillDto updateSkill(SkillDto skillDto);
	
	SkillDto deleteSkill(Long id);
	
	SkillDto getSkill(Long id);
	
	Set<SkillDto> getAllSkills();
	
	

}
