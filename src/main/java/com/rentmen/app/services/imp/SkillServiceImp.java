package com.rentmen.app.services.imp;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentmen.app.DTO.SkillDto;
import com.rentmen.app.entities.Skill;
import com.rentmen.app.repositories.SkillRepo;
import com.rentmen.app.services.SkillService;

@Service
public class SkillServiceImp implements SkillService {
	
	@Autowired
	private SkillRepo skillRepo;
	
	@Autowired
    private ModelMapper modelMapper;

	@Override
	public SkillDto createSkill(SkillDto skillDto) {
		Skill skill = modelMapper.map(skillDto, Skill.class);
		skill = skillRepo.save(skill);
		return modelMapper.map(skill, SkillDto.class);
	}

	@Override
	public SkillDto updateSkill(SkillDto skillDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SkillDto deleteSkill(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SkillDto getSkill(Long id) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public Set<SkillDto> getAllSkills() {
		Set<Skill> skillSet = new HashSet<Skill>();
		Type setType = new TypeToken<Set<Skill>>() {}.getType();
		List<Skill> skillList = skillRepo.findAll();
		skillSet = new HashSet<>(skillList);
		return modelMapper.map(skillSet, setType);
	}

}
