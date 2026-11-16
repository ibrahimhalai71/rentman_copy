package com.rentmen.app.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SortedSkillsDto {
    private Long id;
    
    @JsonProperty("skill_name")
    private String skillName;

    // Getters and setters
    
    public SortedSkillsDto() {

	}

    public SortedSkillsDto(Long id2, String skillName2) {
		this.id = id2;
		this.skillName = skillName2;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
}
