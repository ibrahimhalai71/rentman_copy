package com.rentmen.app.services.imp;

import com.rentmen.app.DTO.SkillDto;
import com.rentmen.app.entities.Skill;
import com.rentmen.app.repositories.SkillRepo;
import com.rentmen.app.services.SkillService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

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
		Set<Skill> skillSet = new TreeSet<Skill>();
		Type setType = new TypeToken<Set<Skill>>() {}.getType();
		List<Skill> skillList = skillRepo.findAll();
		skillSet = new TreeSet<>(skillList);
		return modelMapper.map(skillSet, setType);
	}
	
	@Override
	public List<SkillDto> getAllSkillsList() {
		List<Skill> skillList = skillRepo.findAll();
		return skillList.stream().map(skills -> modelMapper.map(skills, SkillDto.class)).collect(Collectors.toList());
	}

}
